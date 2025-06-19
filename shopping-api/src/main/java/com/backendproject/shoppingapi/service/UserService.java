package com.backendproject.shoppingapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.backendproject.shoppingclient.dto.UserDTO;
import com.backendproject.shoppingclient.exception.UserNotFoundException;

public class UserService {

    @Value("${USER_API_URL:http://localhost:8081/product/}")
    private String userApiURL;

    public UserDTO getUserByCpf(String cpf, String key) {
        try {
           RestTemplate restTemplate = new RestTemplate(); 
            String url = userApiURL + cpf; 
            ResponseEntity<UserDTO> response = 
            restTemplate.getForEntity(url, UserDTO.class); 
            return response.getBody(); 
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException();
        }
    }
}
