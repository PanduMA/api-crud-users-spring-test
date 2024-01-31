package com.awda.services;

import com.awda.model.ElasticUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ElasticUserServiceImpl implements ElasticUserService{
    @Autowired
    ElasticsearchRepository elasticsearchRepository;
    @Override
    public Iterable<ElasticUser> getAllElasticUser(){
        return elasticsearchRepository.findAll();
    }
    @Override
    public ElasticUser getElasticUser(Long id){
        Optional<ElasticUser> optionalElasticUser = elasticsearchRepository.findById(id);
        if (optionalElasticUser.isEmpty()){
            return new ElasticUser();
        }
        ElasticUser user = new ElasticUser();
        return user;
    }
}
