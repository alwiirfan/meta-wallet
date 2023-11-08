package com.enigma.metawallet.model.response;

import com.enigma.metawallet.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LoginResponse {
    private String email;
    private List<Role> roles;
    private String token;
}
