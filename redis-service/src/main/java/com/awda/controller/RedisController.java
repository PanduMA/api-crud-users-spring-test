package com.awda.controller;

import com.awda.model.UserData;
import com.awda.services.RedisUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/redis")
public class RedisController {
    @Autowired
    RedisUserService redisUserService;

    @GetMapping("/getAllUser")
    @ResponseBody
    public ResponseEntity<?> getAllRedisUser() {
        List<UserData> userDataList = redisUserService.getAllRedisUser();
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("messages", "Success get all data user from redis");
        response.put("data", userDataList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getUser/{id}")
    @ResponseBody
    public ResponseEntity<?> getRedisCustomerData(@PathVariable Long id) {
        UserData userData = redisUserService.getRedisUser(id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("messages", "Success get data user id : " + id);
        response.put("data", userData);
        return ResponseEntity.ok(response);
    }
}
