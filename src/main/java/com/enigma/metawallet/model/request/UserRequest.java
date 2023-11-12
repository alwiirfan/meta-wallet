package com.enigma.metawallet.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserRequest {
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotBlank
    private String mobilePhone;

    @NotBlank
    private String city;

    @NotBlank
    private String country;

}
