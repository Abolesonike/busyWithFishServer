package org.fizzy.busywithfish.dataserver.dto;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;

}
