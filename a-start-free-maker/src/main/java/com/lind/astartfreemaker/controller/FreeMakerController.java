package com.lind.astartfreemaker.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class FreeMakerController {
    @GetMapping("index")
    public String index() {
        return "index";
    }

    @RequestMapping("user-list")
    public String selectUser(ModelMap map) {
        List<UserDTO> userList = new ArrayList<>();
        userList.add(UserDTO.builder().name("lind").email("zls@sina.com").createTime(new Date()).build());
        userList.add(UserDTO.builder().name("zzl").email("bfyxzls@sina.com").createTime(new Date()).build());
        map.put("userList", userList);
        return "userlist";
    }

}
