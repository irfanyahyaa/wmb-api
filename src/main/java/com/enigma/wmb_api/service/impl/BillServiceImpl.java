package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.dto.request.BillRequest;
import com.enigma.wmb_api.dto.request.GetBillRequest;
import com.enigma.wmb_api.dto.response.BillDetailResponse;
import com.enigma.wmb_api.dto.response.BillResponse;
import com.enigma.wmb_api.dto.response.PaymentResponse;
import com.enigma.wmb_api.entity.*;
import com.enigma.wmb_api.repository.BillRepository;
import com.enigma.wmb_api.service.*;
import com.enigma.wmb_api.specification.BillSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final BillDetailService billDetailService;
    private final UserService userService;
    private final MenuService menuService;
    private final TableService tableService;
    private final TransTypeService transTypeService;
    private final PaymentService paymentService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BillResponse create(BillRequest request) {
        MUser user = userService.getByIdEntity(request.getUserId());
        MTable table;
        if (request.getTableId() == null || request.getTableId().isBlank()) {
            table = tableService.getByNameEntity("Take Away");
        } else {
            table = tableService.getByIdEntity(request.getTableId());
        };
        MTransType transType = transTypeService.getByIdEntity(request.getTransTypeId());

        TBill trx = TBill.builder()
                .transDate(new Date())
                .user(user)
                .table(table)
                .transType(transType)
                .build();
        billRepository.saveAndFlush(trx);

        List<TBillDetail> trxDetails = request.getBillDetails().stream().map(detailRequest -> {
            MMenu menu = menuService.getByIdEntity(detailRequest.getMenuId());

            return TBillDetail.builder()
                    .bill(trx)
                    .menu(menu)
                    .menuPrice(menu.getPrice())
                    .qty(detailRequest.getQty())
                    .build();
        }).toList();
        billDetailService.createBulk(trxDetails);
        trx.setBillDetails(trxDetails);

        List<BillDetailResponse> trxDetailsResponse = trxDetails.stream().map(detail ->
                BillDetailResponse.builder()
                        .id(detail.getId())
                        .menuId(detail.getMenu().getId())
                        .menuPrice(detail.getMenuPrice())
                        .qty(detail.getQty())
                        .build()).toList();

        PaymentResponse payment = paymentService.createPayment(trx);

        BillResponse.BillResponseBuilder builder = BillResponse.builder();

        if (trx.getTable() != null) {
            builder.tableId(trx.getTable().getId());
        } else {
            builder.tableId(null);
        }

        return builder
                .id(trx.getId())
                .transDate(trx.getTransDate())
                .userId(trx.getUser().getId())
                .transTypeId(trx.getTransType().getId().toString())
                .billDetails(trxDetailsResponse)
                .paymentResponse(payment)
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TBill> getAll(GetBillRequest request) {
        if (request.getPage() <= 0) request.setPage(1);

        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());

        Pageable pageable = PageRequest.of((request.getPage() - 1), request.getSize(), sort);

        Specification<TBill> specification = BillSpecification.getSpecification(request);

        return billRepository.findAll(specification, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public BillResponse getById(String id) {
        TBill bill = billRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "bill not found"));

        List<BillDetailResponse> billDetails = bill.getBillDetails().stream().map(detail ->
                BillDetailResponse.builder()
                        .id(detail.getId())
                        .menuId(detail.getMenu().getId())
                        .menuPrice(detail.getMenuPrice())
                        .qty(detail.getQty())
                        .build()).toList();

        return BillResponse.builder()
                .id(bill.getId())
                .transDate(bill.getTransDate())
                .userId(bill.getUser().getId())
                .tableId(bill.getTable().getId())
                .transTypeId(bill.getTransType().getId().toString())
                .billDetails(billDetails)
                .build();
    }
}