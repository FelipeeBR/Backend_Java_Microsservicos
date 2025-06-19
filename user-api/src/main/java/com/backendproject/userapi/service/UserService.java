package com.backendproject.userapi.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.backendproject.userapi.model.User;
import com.backendproject.userapi.repository.UserRepository;
import com.backendproject.shoppingclient.dto.UserDTO;
import com.backendproject.shoppingclient.exception.UserNotFoundException;
import com.backendproject.userapi.converter.DTOConverter;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getAll() {
        List<User> usuarios = userRepository.findAll();
        return usuarios.stream().map(DTOConverter::convert).collect(Collectors.toList());
    }

    public UserDTO findById(Long userId) {
        Optional<User> usuario = userRepository.findById(userId);
        if(usuario.isPresent()) {
            return DTOConverter.convert(usuario.get());
        }
        return null;
    }

    public UserDTO save(UserDTO userDTO) {
        userDTO.setKey(UUID.randomUUID().toString());
        User user = userRepository.save(User.convert(userDTO));
        return DTOConverter.convert(user);
    }

    public UserDTO delete(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            userRepository.delete(user.get());
        }
        return null;
    }

    public UserDTO findByCpf(String cpf, String key) {
        User user = userRepository.findByCpfAndKey(cpf, key);
        if(user != null) {
            return DTOConverter.convert(user);
        }
        throw new UserNotFoundException();
    }

    public List<UserDTO> queryByName(String name) {
        List<User> usuarios = userRepository.queryByNomeLike(name);
        return usuarios.stream().map(DTOConverter::convert).collect(Collectors.toList());
    }

    public UserDTO getUserByCpf(String cpf) {
        try {
            RestTemplate restTemplate = new RestTemplate(); 
            String url = "http://localhost:8080/user/cpf/" + cpf; 
            ResponseEntity<UserDTO> response = 
            restTemplate.getForEntity(url, UserDTO.class);
            return response.getBody(); 
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException();
        } 
    }
}
