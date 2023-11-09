package com.enigma.metawallet.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AuthRequest {

    private String name;

    private String username;

    @Email(message = "Please enter a valid email!")
    private String email;

    @Pattern(regexp = "^[a-zA-Z0-9]{10}", message = "please you must enter 10 characters!")
    private String password;

}
