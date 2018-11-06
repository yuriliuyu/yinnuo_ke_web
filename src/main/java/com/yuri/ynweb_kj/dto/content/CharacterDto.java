package com.yuri.ynweb_kj.dto.content;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CharacterDto {
    private Integer id;
    private String information;
    private String gender;
    private Integer age;
    private String name;
    private String protrait;
}
