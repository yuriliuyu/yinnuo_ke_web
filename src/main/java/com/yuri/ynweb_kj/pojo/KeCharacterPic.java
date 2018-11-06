package com.yuri.ynweb_kj.pojo;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class KeCharacterPic {
    private Integer id;

    private Integer characterId;

    private String picture;

    private Integer orderId;

    private Integer isRight;

    private String description;

}