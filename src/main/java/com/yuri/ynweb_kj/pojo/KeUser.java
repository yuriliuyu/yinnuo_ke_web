package com.yuri.ynweb_kj.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.ToString;

import java.util.Date;


@Data
@ToString
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class KeUser {
    private Integer id;

    private String name;

    private String password;

    private Integer type;

    private String school;

    private Integer schoolId;

    private Integer gender;

    private String email;

    private String portrait;

    private String major;

    private Integer signinNum;

    private Integer credit;

    private Date lastLogin;

    private Integer message;
}