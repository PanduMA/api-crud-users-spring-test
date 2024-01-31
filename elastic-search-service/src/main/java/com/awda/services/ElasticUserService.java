package com.awda.services;

import com.awda.model.ElasticUser;

public interface ElasticUserService {
    Iterable<ElasticUser> getAllElasticUser();

    ElasticUser getElasticUser(Long id);
}
