package com.example.demo.service;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> find(PageRequest pageRequest) {
        final Page<Product> page = repository.findAll(pageRequest);
        repository.findProductsAndCategories(page.stream().collect(Collectors.toList())); // Puts results in memory
        return page.map(ProductDTO::new);
    }
}
