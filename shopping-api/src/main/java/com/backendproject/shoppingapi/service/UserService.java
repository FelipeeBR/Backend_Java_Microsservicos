package com.backendproject.shoppingapi.service;

import java.net.URI;
import java.net.URISyntaxException;

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
            String baseUrl = "http://localhost:8080";

            URI uri = UriComponentsBuilder
                .fromUri(new URI(baseUrl))
                .path("/user/cpf/{cpf}")
                .queryParam("key", key)
                .buildAndExpand(cpf)
                .toUri();

            ResponseEntity<UserDTO> response = restTemplate.getForEntity(uri, UserDTO.class);
            return response.getBody();

        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Erro ao construir URI", e);
        }
    }
}
