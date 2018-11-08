package com.yuri.ynweb_kj.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
@Data
@ToString
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class KeProgress {
    private Integer id;

    private Integer studentId;

    private Date date;

    private Integer video;

    private Integer test;

    private Integer credit;

    private Date createTime;

    private Date updateTime;

}