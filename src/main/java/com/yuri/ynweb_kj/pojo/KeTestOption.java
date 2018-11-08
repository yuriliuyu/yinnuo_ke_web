package com.yuri.ynweb_kj.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class KeTestOption {
    private Integer id;

    private String option;

    private Integer testId;

    private Integer isAnswer;

    private Integer orderId;

    private Date createTime;

    private Date updateTime;
}