package com.backendproject.userapi.converter;

import com.backendproject.shoppingclient.dto.UserDTO;
import com.backendproject.userapi.model.User;

public class DTOConverter {
    public static UserDTO convert(User user) {
        UserDTO dto = new UserDTO();
        dto.setNome(user.getNome());
        dto.setCpf(user.getCpf());
        dto.setEndereco(user.getEndereco());
        dto.setEmail(user.getEmail());
        dto.setTelefone(user.getTelefone());
        dto.setDataCadastro(user.getDataCadastro());
        dto.setKey(user.getKey());
        return dto;
    }
}
