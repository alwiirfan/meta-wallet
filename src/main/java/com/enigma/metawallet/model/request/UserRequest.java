package com.enigma.metawallet.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserRequest {
    private String name;

    @NotBlank
    private String address;

    @NotBlank
    private String mobilePhone;

    @NotBlank
    private String city;

    @NotBlank
    private String country;

    @NotBlank
    @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}$", message = "please use format dd-MM-yyyy")
    private String dateOfBirth;

    @Email
    private String email;

}
