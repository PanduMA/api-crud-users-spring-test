package com.awda.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    private Long id;
    private String userName;
    private String accountNumber;
    private String emailAddress;
    private String identityNumber;
    private String password;
}
