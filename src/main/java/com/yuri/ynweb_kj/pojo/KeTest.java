package com.yuri.ynweb_kj.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
@Data
@ToString
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class KeTest {
    private Integer id;

    private String question;

    private Integer contentId;

    private String picUrl;

    private Integer orderId;

    private Date createTime;

    private Date updateTime;
}