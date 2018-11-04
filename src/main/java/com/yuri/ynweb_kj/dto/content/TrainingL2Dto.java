package com.yuri.ynweb_kj.dto.content;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class TrainingL2Dto {
    private Integer id;
    private String title;
    private String description;
    private List<TrainingL3Dto> trainingL3DtoList;
}
