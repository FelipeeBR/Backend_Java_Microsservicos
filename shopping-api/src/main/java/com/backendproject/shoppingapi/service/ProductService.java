package com.backendproject.shoppingapi.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.backendproject.shoppingclient.dto.ProductDTO;
import com.backendproject.shoppingclient.exception.UserNotFoundException;

public class ProductService {
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
