package com.enigma.metawallet.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AuthRequest {

    private String name;

    private String username;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
