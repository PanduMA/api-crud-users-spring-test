package com.awda.model;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "maucash")
public class UserMongoDB {
    @Id
    private Long id;
    private String userName;
    private String accountNumber;
    private String emailAddress;
    private String identityNumber;
    private String password;

    public UserMongoDB(Long id, String userName, String accountNumber, String emailAddress, String identityNumber, String password){
        super();
        this.id = id;
        this.userName = userName;
        this.accountNumber = accountNumber;
        this.emailAddress = emailAddress;
        this.identityNumber = identityNumber;
        this.password = password;
    }

    public Long getId() {
        return id;
    }
}
