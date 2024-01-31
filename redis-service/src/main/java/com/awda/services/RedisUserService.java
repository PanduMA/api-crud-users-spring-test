package com.awda.services;

import com.awda.model.UserData;
import com.awda.model.UserMongoDB;

import java.util.List;

public interface RedisUserService {
    List<UserData> getAllRedisUser();
    UserData getRedisUser(Long id);
}
