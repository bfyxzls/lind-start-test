package com.lind.oauth.impl;

import com.lind.oauth.entity.Permission;
import com.lind.uaa.entity.ResourcePermission;
import com.lind.uaa.service.OauthPermissionService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PermissionServiceImpl implements OauthPermissionService {
    @Override
    public List<ResourcePermission> getByType(int type) {
        return Arrays.asList(
                new Permission("1","chanpin", "/products", "", "email"),
                new Permission("2","首页", "/index", "read", "")
        );
    }
}
