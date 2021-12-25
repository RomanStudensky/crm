package ru.pnzgu.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pnzgu.crm.dto.DealSostavDto;
import ru.pnzgu.crm.exception.NotFoundException;
import ru.pnzgu.crm.exception.util.MessageConst;
import ru.pnzgu.crm.service.DealSostavService;
import ru.pnzgu.crm.store.entity.Deal;
import ru.pnzgu.crm.store.entity.DealProduct;
import ru.pnzgu.crm.store.entity.Product;
import ru.pnzgu.crm.store.repository.DealProductRepository;
import ru.pnzgu.crm.store.repository.DealRepository;
import ru.pnzgu.crm.store.repository.ProductRepository;
import ru.pnzgu.crm.util.mapping.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DealSostavServiceImpl implements DealSostavService {

    private final DealRepository dealRepository;
    private final ProductRepository productRepository;
    private final DealProductRepository dealProductRepository;

    @Override
    public List<DealSostavDto> readAll() {
        return dealProductRepository
                .findAll()
                .stream()
                .map(Mappers.DEAL_PRODUCT::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DealSostavDto read(Long id) {
        return Mappers.DEAL_PRODUCT.mapEntityToDto(dealProductRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(MessageConst.DEAL_PRODUCT)));
    }

    @Override
    public List<DealSostavDto> readAllByDealId(Long dealId) {
        return dealProductRepository
                .findDealProductByDeal_Id(dealId)
                .stream()
                .map(Mappers.DEAL_PRODUCT::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DealSostavDto create(DealSostavDto dealProductDto, Long dealId, Long productId) {
        Deal deal = dealRepository
                .findById(dealId)
                .orElseThrow(() -> new NotFoundException(MessageConst.DEAL));

        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new NotFoundException(MessageConst.PRODUCT));

        DealProduct dealProduct = Mappers.DEAL_PRODUCT.mapDtoToEntity(dealProductDto);
        dealProduct.setDeal(deal);
        dealProduct.setProduct(product);

        return Mappers.DEAL_PRODUCT.mapEntityToDto(dealProductRepository.save(dealProduct));
    }

    @Override
    public DealSostavDto update(Long id, DealSostavDto dealProductDto) {
        DealProduct dealProduct = dealProductRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(MessageConst.DEAL_PRODUCT));

        dealProduct.setCount(dealProductDto.getCount());

        return Mappers.DEAL_PRODUCT.mapEntityToDto(dealProductRepository.save(dealProduct));
    }

    @Override
    public void delete(Long id) {
        dealProductRepository.deleteById(id);
    }

    @Override
    public Long findDealIdBySostavId(Long id) {
        return dealProductRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(MessageConst.DEAL_PRODUCT))
                .getDeal()
                .getId();
    }
}
