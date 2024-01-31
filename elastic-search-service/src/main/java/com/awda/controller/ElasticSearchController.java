package com.awda.controller;

import com.awda.model.ElasticUser;
import com.awda.services.ElasticUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/elasticsearch")
public class ElasticSearchController {
    @Autowired
    ElasticUserService elasticUserService;

    @GetMapping("/getAllUser")
    @ResponseBody
    public ResponseEntity<?> getAllElasticUser() {
        Iterable<ElasticUser> userDataList = elasticUserService.getAllElasticUser();
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("messages", "Success get all data user from redis");
        response.put("data", userDataList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getUser/{id}")
    @ResponseBody
    public ResponseEntity<?> getRedisCustomerData(@PathVariable Long id) {
        ElasticUser userData = elasticUserService.getElasticUser(id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("messages", "Success get data user id : " + id);
        response.put("data", userData);
        return ResponseEntity.ok(response);
    }
}
