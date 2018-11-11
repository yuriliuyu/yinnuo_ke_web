package com.yuri.ynweb_kj.dto.content;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class TrainingL3Dto {
    private Integer id;
    private String title;
    private Integer type;
    private String url;
    private Integer isReadable;
}
