package com.backendproject.productapi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backendproject.productapi.converter.DTOConverter;
import com.backendproject.productapi.model.Product;
import com.backendproject.productapi.repository.CategoryRepository;
import com.backendproject.productapi.repository.ProductRepository;
import com.backendproject.shoppingclient.dto.ProductDTO;
import com.backendproject.shoppingclient.exception.CategoryNotFoundException;
import com.backendproject.shoppingclient.exception.ProductNotFoundException;

@Service
public class ProductService {

    @Autowired(required = false)
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public List<ProductDTO> getAll() {
        List<Product> produtos = productRepository.findAll();
        return produtos.stream()
                .map(DTOConverter::convert)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductByCategoryId(long categoryId) {
        List<Product> produtos = productRepository.getProductByCategoryId(categoryId);
        return produtos.stream()
                .map(DTOConverter::convert)
                .collect(Collectors.toList());
    }

    public ProductDTO findByProductIdentifier(String productIdentifier) {
        Product produto = productRepository.findByProductIdentifier(productIdentifier);
        if (produto == null) {
            throw new ProductNotFoundException();
        }
        return DTOConverter.convert(produto);
    }

    public ProductDTO save(ProductDTO dto) {
        boolean existsCategory = categoryRepository.existsById(dto.getCategory().getId());
        if (!existsCategory) {
            throw new CategoryNotFoundException();
        }
        Product produto = productRepository.save(Product.convert(dto));
        return DTOConverter.convert(produto);
    }

    public void delete(long id) {
        Optional<Product> produto = productRepository.findById(id);
        produto.ifPresentOrElse(p -> productRepository.delete(p), () -> {
            throw new ProductNotFoundException();
        });
    }
}