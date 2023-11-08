package com.enigma.metawallet.entity.roleContract;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public enum ERole {
    USER,
    ADMIN;

    public static ERole getRole(String value){
        for (ERole role : ERole.values()){
            if (role.name().equalsIgnoreCase(value)) return role;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role is not found");
    }
}
