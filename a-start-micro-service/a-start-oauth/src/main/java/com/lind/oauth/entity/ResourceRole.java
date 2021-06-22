package com.lind.oauth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceRole implements com.lind.uaa.entity.ResourceRole {
    private String id;
    private String name;
}
