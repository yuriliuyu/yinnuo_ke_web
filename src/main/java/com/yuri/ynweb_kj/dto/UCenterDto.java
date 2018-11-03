package com.yuri.ynweb_kj.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yuri.ynweb_kj.pojo.KeUser;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class UCenterDto {

    private String name;

    private String school;

    private String gender;

    private String email;

    private String portrait;

    private String major;

    private Integer signinNum;

    private Integer credit;

    private String lastLogin;

    private Integer loginTotal;

    private String process;

    private List<String> signin;

}
