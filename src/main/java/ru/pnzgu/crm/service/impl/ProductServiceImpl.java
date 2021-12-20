package ru.pnzgu.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pnzgu.crm.dto.ProductDto;
import ru.pnzgu.crm.exception.util.ExMes;
import ru.pnzgu.crm.exception.NotFoundException;
import ru.pnzgu.crm.service.ProductService;
import ru.pnzgu.crm.store.entity.Product;
import ru.pnzgu.crm.store.repository.ProductRepository;
import ru.pnzgu.crm.util.mapping.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductDto> readAll() {
        return productRepository
                .findAll()
                .stream()
                .map(Mappers.PRODUCT_MAPPER::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto read(Long id) {
        return Mappers
                .PRODUCT_MAPPER
                .mapEntityToDto(
                        productRepository
                                .findById(id)
                                .orElseThrow(() -> new NotFoundException(String.format(ExMes.PRODUCT_MESSAGE, id))));
    }

    @Override
    public List<ProductDto> readByDealId(Long id) {
        return productRepository
                .findAll()
                .stream()
                .map(Mappers.PRODUCT_MAPPER::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto create(ProductDto productDto) {
        return Mappers
                .PRODUCT_MAPPER
                .mapEntityToDto(
                        productRepository
                                .save(Mappers
                                        .PRODUCT_MAPPER
                                        .mapDtoToEntity(productDto))
                );
    }

    @Override
    public ProductDto update(Long id, ProductDto productDto) {
        productRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ExMes.PRODUCT_MESSAGE, id)));

        Product product = Mappers.PRODUCT_MAPPER.mapDtoToEntity(productDto);
        product.setId(id);

        return Mappers
                .PRODUCT_MAPPER
                .mapEntityToDto(
                        productRepository.save(product)
                );
    }
}
