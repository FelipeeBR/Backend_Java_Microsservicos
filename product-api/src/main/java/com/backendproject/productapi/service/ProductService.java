package com.backendproject.productapi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.backendproject.productapi.converter.DTOConverter;
import com.backendproject.productapi.model.Product;
import com.backendproject.productapi.repository.CategoryRepository;
import com.backendproject.productapi.repository.ProductRepository;
import com.backendproject.shoppingclient.dto.ProductDTO;
import com.backendproject.shoppingclient.exception.CategoryNotFoundException;
import com.backendproject.shoppingclient.exception.ProductNotFoundException;
import com.backendproject.shoppingclient.exception.UserNotFoundException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public List<ProductDTO> getAll() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductDTO::convert).collect(Collectors.toList());
    }

    public ProductDTO findByProductIdentifier(String productIdentifier) {
        Product product = productRepository.findByProductIdentifier(productIdentifier);
        if(product != null) {
            return ProductDTO.convert(product);
        }
        throw new ProductNotFoundException();
    }

    public ProductDTO save(ProductDTO productDTO) {
        Boolean existsCategory = categoryRepository.existsById(productDTO.getCategory().getId());
        if(!existsCategory) {
            throw new CategoryNotFoundException();
        } 
        Product product = productRepository.save(Product.convert(productDTO));
        return DTOConverter.convert(product);
    }

    public void delete(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()) {
            productRepository.delete(product.get());
        }
        throw new ProductNotFoundException();
    }

    public ProductDTO getProductByIdentifier(String productIdentifier) { 
        try {
            RestTemplate restTemplate = new RestTemplate(); 
            String url = "http://localhost:8081/product/" + productIdentifier; 
            ResponseEntity<ProductDTO> response = 
            restTemplate.getForEntity(url, ProductDTO.class); 
            return response.getBody(); 
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException();
        }
    }
}
