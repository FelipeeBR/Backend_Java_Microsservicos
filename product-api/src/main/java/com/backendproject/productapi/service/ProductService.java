package com.backendproject.productapi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.backendproject.productapi.model.Product;
import com.backendproject.productapi.repository.ProductRepository;
import com.backendproject.shoppingclient.dto.ProductDTO;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDTO> getAll() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductDTO::convert).collect(Collectors.toList());
    }

    public ProductDTO findByProductIdentifier(String productIdentifier) {
        Product product = productRepository.findByProductIdentifier(productIdentifier);
        if(product != null) {
            return ProductDTO.convert(product);
        }
        return null;
    }

    public ProductDTO save(ProductDTO productDTO) {
        Product product = productRepository.save(Product.convert(productDTO));
        return ProductDTO.convert(product);
    }

    public void delete(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()) {
            productRepository.delete(product.get());
        }
    }

    public ProductDTO getProductByIdentifier(String productIdentifier) { 
        RestTemplate restTemplate = new RestTemplate(); 
        String url = 
        "http://localhost:8081/product/" + productIdentifier; 
        ResponseEntity<ProductDTO> response = 
        restTemplate.getForEntity(url, ProductDTO.class); 
        return response.getBody(); 
    }
}
