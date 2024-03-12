package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.dto.request.GetMenuRequest;
import com.enigma.wmb_api.dto.request.MenuRequest;
import com.enigma.wmb_api.dto.response.MenuResponse;
import com.enigma.wmb_api.entity.MImage;
import com.enigma.wmb_api.entity.MMenu;
import com.enigma.wmb_api.repository.MenuRepository;
import com.enigma.wmb_api.service.ImageService;
import com.enigma.wmb_api.service.MenuService;
import com.enigma.wmb_api.specification.MenuSpecification;
import com.enigma.wmb_api.util.ValidationUtil;
import jakarta.validation.ConstraintViolationException;
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

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final ValidationUtil validationUtil;
    private final ImageService imageService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public MenuResponse create(MenuRequest request) {
        validationUtil.validate(request);

        if (request.getImage().isEmpty()) throw new ConstraintViolationException("image is required", null);
        MImage image = imageService.create(request.getImage());

        MMenu menu = MMenu.builder()
                .menu(request.getMenu())
                .price(request.getPrice())
                .image(image)
                .build();
        menuRepository.saveAndFlush(menu);

        return MenuResponse.builder()
                .id(menu.getId())
                .menu(menu.getMenu())
                .price(menu.getPrice())
                .image(image)
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<MenuResponse> getAll(GetMenuRequest request) {
        validationUtil.validate(request);
        validationUtil.validateSortFields(MMenu.class, request.getSortBy());

        if (request.getPage() <= 0) request.setPage(1);

        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());

        Pageable pageable = PageRequest.of((request.getPage() - 1), request.getSize(), sort);
        Specification<MMenu> specification = MenuSpecification.getSpecification(request);

        Page<MMenu> menuPage = menuRepository.findAll(specification, pageable);

        validationUtil.validateEmptyData(menuPage, "menus");

        return menuPage.map(menu -> MenuResponse.builder()
                .id(menu.getId())
                .menu(menu.getMenu())
                .price(menu.getPrice())
                .image(menu.getImage())
                .build());
    }

    @Transactional(readOnly = true)
    @Override
    public MMenu getByIdEntity(String id) {
        return findByIdOrNotFound(id);
    }

    @Transactional(readOnly = true)
    @Override
    public MenuResponse getByIdDTO(String id) {
        MMenu menu = findByIdOrNotFound(id);

        return MenuResponse.builder()
                .id(menu.getId())
                .menu(menu.getMenu())
                .price(menu.getPrice())
                .image(menu.getImage())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public MenuResponse update(MenuRequest request) {
        validationUtil.validate(request);
        MMenu currMenu = findByIdOrNotFound(request.getId());

        if (request.getImage() != null) {
            MImage image = imageService.create(request.getImage());
            String deletedImage = currMenu.getImage().getId();

            currMenu.setImage(image);
            imageService.deleteById(deletedImage);
        }

        currMenu.setMenu(request.getMenu());
        currMenu.setPrice(request.getPrice());
        menuRepository.saveAndFlush(currMenu);

        return MenuResponse.builder()
                .id(currMenu.getId())
                .menu(currMenu.getMenu())
                .price(currMenu.getPrice())
                .image(currMenu.getImage())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public MenuResponse delete(String id) {
        MMenu menu = findByIdOrNotFound(id);
        menuRepository.delete(menu);

        return MenuResponse.builder()
                .id(menu.getId())
                .menu(menu.getMenu())
                .price(menu.getPrice())
                .image(menu.getImage())
                .build();
    }

    private MMenu findByIdOrNotFound(String id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "menu not found"));
    }
}
