package ru.pnzgu.crm.service;

import ru.pnzgu.crm.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> readAll();

    ProductDto read(Long id);

    List<ProductDto> readByDealId(Long id);

    ProductDto create(ProductDto productDto);

    ProductDto update(Long id, ProductDto productDto);
}
