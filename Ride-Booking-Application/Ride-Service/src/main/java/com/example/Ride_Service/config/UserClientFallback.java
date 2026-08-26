package com.example.Ride_Service.config;

import com.example.Ride_Service.dto.response.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserClientFallback implements UserClient{
    private static  final Logger log = LoggerFactory.getLogger(UserClientFallback.class);

    @Override
    public UserResponse getUserByAuthId(Long authId) {
        log.error("User Server is Down");
        return null;
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        log.error("User server is down");
        return null;
    }
}
