package com.yuri.ynweb_kj.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
@Data
@ToString
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class KeTestDone {
    private Integer id;

    private Integer testId;

    private String qustion;

    private Integer studentId;

    private Integer isRight;

    private Date createTime;

    private Date updateTime;
}