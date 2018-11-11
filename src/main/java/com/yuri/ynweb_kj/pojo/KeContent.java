package com.yuri.ynweb_kj.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
@Data
@ToString
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class KeContent {
    private Integer id;

    private Integer pid;

    private String title;

    private Integer category;

    private Integer level;

    private Integer type;

    private Integer orderId;

    private String url;

    private Date createTime;

    private Date updateTime;

    private String description;

    private String content;
}