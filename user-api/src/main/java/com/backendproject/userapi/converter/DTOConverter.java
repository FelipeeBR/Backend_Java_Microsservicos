package com.backendproject.userapi.converter;

import com.backendproject.shoppingclient.dto.UserDTO;
import com.backendproject.userapi.model.User;

public class DTOConverter {
    public static UserDTO convert(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setNome(user.getNome());
        userDTO.setEndereco(user.getEndereco());
        userDTO.setCpf(user.getCpf());
        return userDTO;
    }
}
