package com.teorerras.buynowdotcom.dtos;

import com.teorerras.buynowdotcom.enums.AddressType;
import lombok.Data;

@Data
public class AddressDto {
    private Long id;
    private String country;
    private String state;
    private String city;
    private String street;
    private String addressType;
}
