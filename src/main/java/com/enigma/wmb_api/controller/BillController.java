package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.constant.APIUrl;
import com.enigma.wmb_api.dto.request.BillRequest;
import com.enigma.wmb_api.dto.request.GetBillRequest;
import com.enigma.wmb_api.dto.response.BillDetailResponse;
import com.enigma.wmb_api.dto.response.BillResponse;
import com.enigma.wmb_api.dto.response.CommonResponse;
import com.enigma.wmb_api.dto.response.PagingResponse;
import com.enigma.wmb_api.entity.TBill;
import com.enigma.wmb_api.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.BILL_API)
public class BillController {
    private final BillService billService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<BillResponse>> createNewBill(@RequestBody BillRequest request) {
        BillResponse transaction = billService.create(request);

        CommonResponse<BillResponse> response = CommonResponse.<BillResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("bill created successfully")
                .data(transaction)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<List<BillResponse>>> getAllBills(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "transDate") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "transDate", required = false) String transDate,
            @RequestParam(name = "userId", required = false) String userId,
            @RequestParam(name = "tableId", required = false) String tableId,
            @RequestParam(name = "transTypeId", required = false) String transTypeId
    ) {
        GetBillRequest request = GetBillRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .transDate(transDate)
                .userId(userId)
                .tableId(tableId)
                .transTypeId(transTypeId)
                .build();

        Page<TBill> bills = billService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(bills.getTotalPages())
                .totalElements(bills.getTotalElements())
                .page(bills.getPageable().getPageNumber() + 1)
                .size(bills.getPageable().getPageSize())
                .hasNext(bills.hasNext())
                .hasPrevious(bills.hasPrevious())
                .build();

        List<TBill> billList = bills.getContent();

        List<BillResponse> billResponses = billList.stream().map(bill -> {
            List<BillDetailResponse> billDetailResponses = bill.getBillDetails().stream().map(billDetail ->
                    BillDetailResponse.builder()
                            .id(billDetail.getId())
                            .menuId(billDetail.getMenu().getId())
                            .menuPrice(billDetail.getMenuPrice())
                            .qty(billDetail.getQty())
                            .build()).toList();

            return BillResponse.builder()
                    .id(bill.getId())
                    .transDate(bill.getTransDate())
                    .userId(bill.getUser().getId())
                    .tableId(bill.getTable().getId())
                    .transTypeId(bill.getTransType().getId().toString())
                    .billDetails(billDetailResponses)
                    .build();
        }).toList();

        CommonResponse<List<BillResponse>> response = CommonResponse.<List<BillResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("bills fetched successfully")
                .data(billResponses)
                .paging(pagingResponse)
                .build();

        return ResponseEntity
                .ok(response);
    }

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<BillResponse>> getBillById(
            @PathVariable(name = "id") String id
    ) {
        BillResponse bill = billService.getById(id);

        CommonResponse<BillResponse> response = CommonResponse.<BillResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("bill fetched successfully")
                .data(bill)
                .build();

        return ResponseEntity
                .ok(response);
    }
}