package com.backendproject.shoppingapi.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.backendproject.shoppingclient.dto.UserDTO;
import com.backendproject.shoppingclient.exception.UserNotFoundException;

public class UserService {
    public UserDTO getUserByCpf(String cpf, String key) {
        try {
            RestTemplate restTemplate = new RestTemplate(); 
            String url = "http://localhost:8080"; 

            UriComponentsBuilder builder = UriComponentsBuilder
            .fromHttpUrl(url + "/user/cpf/" + cpf);
            
            builder.queryParam("key", key);

            ResponseEntity<UserDTO> response = restTemplate.getForEntity(builder.toUriString(), UserDTO.class); 
            return response.getBody(); 
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException();
        } 
    }
}
