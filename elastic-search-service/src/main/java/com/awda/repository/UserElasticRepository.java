package com.awda.repository;

import com.awda.model.ElasticUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserElasticRepository extends ElasticsearchRepository<ElasticUser, Long> {
}
