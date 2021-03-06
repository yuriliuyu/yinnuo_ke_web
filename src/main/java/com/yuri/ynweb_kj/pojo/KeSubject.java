package com.yuri.ynweb_kj.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class KeSubject {
    private Integer id;

    private String content;

    private Integer teacherId;

    private String teacherName;

    private Integer studentId;

    private String studentName;

    private Integer isRead;

    private Date createTime;

    private Date updateTime;
}