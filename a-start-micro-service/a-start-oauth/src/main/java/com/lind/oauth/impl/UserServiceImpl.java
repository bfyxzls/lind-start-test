package com.lind.oauth.impl;

import com.lind.uaa.entity.ResourceUser;
import com.lind.uaa.service.OauthUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements OauthUserService {
    @Override
    public ResourceUser getByUserName(String username) {
        com.lind.oauth.entity.ResourceUser user = new com.lind.oauth.entity.ResourceUser();
        user.setId("1");
        user.setUsername("test");
        user.setPassword(new BCryptPasswordEncoder().encode("123456"));
        return user;
    }
}
