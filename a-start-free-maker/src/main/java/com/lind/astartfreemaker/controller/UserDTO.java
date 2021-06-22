package com.lind.astartfreemaker.controller;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class UserDTO {
    private String name;
    private String email;
    private Date createTime;
}
