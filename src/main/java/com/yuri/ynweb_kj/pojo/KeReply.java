package com.yuri.ynweb_kj.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
@Data
@ToString
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class KeReply {
    private Integer id;

    private Integer subjectId;

    private String reply;

    private Integer isRead;

    private Date createTime;

    private Date updateTime;

    private Integer studentId;

    private Integer teacherId;

}