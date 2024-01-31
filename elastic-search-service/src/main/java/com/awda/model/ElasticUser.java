package com.awda.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = "user")
public class ElasticUser {
    private Long id;
    private String userName;
    private String accountNumber;
    private String emailAddress;
    private String identityNumber;
    private String password;
}
