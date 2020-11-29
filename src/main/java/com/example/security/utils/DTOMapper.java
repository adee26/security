package com.example.security.utils;

import com.example.security.dto.UserDTO;
import com.example.security.entities.User;

public class DTOMapper {
    public static UserDTO mapUserToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getUserPersonalInfo().getEmail());
        userDTO.setFirstName(user.getUserPersonalInfo().getFirstName());
        userDTO.setLastName(user.getUserPersonalInfo().getLastName());
        userDTO.setPhoneNumber(user.getUserPersonalInfo().getPhoneNumber());

        return userDTO;
    }
}
