package com.yuri.ynweb_kj.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yuri.ynweb_kj.pojo.KeProgress;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ProgressDto {
    private List<String> date;
    private List<KeProgress> progress;
    private Integer exerciseDone;
    private Integer exerciseWill;
    private Integer exerciseRight;
    private Integer exerciseWrong;
    private Integer exerciseAll;
    private Integer rightRate;
    private Integer doneRate;
}
