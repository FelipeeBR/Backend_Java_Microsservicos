package com.backendproject.userapi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.backendproject.shoppingclient.dto.UserDTO;
import com.backendproject.shoppingclient.exception.UserNotFoundException;
import com.backendproject.userapi.model.User;
import com.backendproject.userapi.repository.UserRepository;
import com.backendproject.userapi.converter.DTOConverter;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getAll() {
        List<User> usuarios = userRepository.findAll();
        return usuarios.stream().map(UserDTO::convert).collect(Collectors.toList());
    }

    public UserDTO findById(Long userId) {
        Optional<User> usuario = userRepository.findById(userId);
        if(usuario.isPresent()) {
            return UserDTO.convert(usuario.get());
        }
        return null;
    }

    public UserDTO save(UserDTO userDTO) {
        User user = userRepository.save(User.convert(userDTO));
        return UserDTO.convert(user);
    }

    public UserDTO delete(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            userRepository.delete(user.get());
        }
        return null;
    }

    public UserDTO findByCpf(String cpf) {
        User user = userRepository.findByCpf(cpf);
        if(user != null) {
            return UserDTO.convert(user);
        }
        return null;
    }

    public List<UserDTO> queryByName(String name) {
        List<User> usuarios = userRepository.queryByNomeLike(name);
        return usuarios.stream().map(UserDTO::convert).collect(Collectors.toList());
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
