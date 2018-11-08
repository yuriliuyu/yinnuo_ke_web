package com.yuri.ynweb_kj.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class KeCharacter {
    private Integer id;

    private String backgroundPic1;

    private String protrait;

    private String name;

    private Integer age;

    private Integer gender;

    private String backgroundPic3;

    private String backgroundPic4;

    private Integer contentId;

    private String backgroundText;

    private String information;

    private String question;

    private String summary;

}