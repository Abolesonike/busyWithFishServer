package org.fizzy.busywithfish.dataserver.dto;

import lombok.Data;

@Data
public class UserRegistrationDto {
    private String username;
    private String password;
    private String email;
    private String phone;
}