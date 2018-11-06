package com.yuri.ynweb_kj.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class KeContentCharacter {
    private Integer id;

    private Integer contentId;

    private Integer characterId;

    private Integer orderId;

}