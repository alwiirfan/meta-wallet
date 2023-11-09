package com.enigma.metawallet.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateOfBirth;

    @NotBlank
    private String email;

}
