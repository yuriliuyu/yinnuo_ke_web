package com.yuri.ynweb_kj.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class UserSimpleDto {
    private Integer id;
    private String name;
    private Integer message;
}
