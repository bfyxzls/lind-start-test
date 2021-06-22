package com.lind.oauth.entity;

import com.lind.uaa.entity.ResourcePermission;
import com.lind.uaa.entity.ResourceRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceUser implements com.lind.uaa.entity.ResourceUser {

    private String id;
    private String username;
    private String password;
    private List<ResourceRole> resourceRoles;
    private List<ResourcePermission> resourcePermissions;

    @Override
    public List<ResourceRole> getResourceRoles() {
        return Arrays.asList(new com.lind.oauth.entity.ResourceRole("1", "管理员"));
    }

    @Override
    public List<ResourcePermission> getResourcePermissions() {
        return Arrays.asList(new com.lind.oauth.entity.Permission("1", "chanpin", "/products", "", "email"));

    }
}
