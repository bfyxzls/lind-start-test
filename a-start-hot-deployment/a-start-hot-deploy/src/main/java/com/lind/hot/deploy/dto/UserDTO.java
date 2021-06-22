package com.lind.hot.deploy.dto;

import com.lind.hot.deploy.scope.ScopeSet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String name;
    @ScopeSet(value = "read")
    private String email;
    private Boolean sex;
    private Double total;
    private BigDecimal totalMoney;
    private Date birthday;
    private Date getup;

}
