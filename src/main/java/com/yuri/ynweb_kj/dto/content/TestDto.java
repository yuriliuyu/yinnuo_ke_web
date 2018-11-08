package com.yuri.ynweb_kj.dto.content;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yuri.ynweb_kj.pojo.KeTestOption;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class TestDto {
    private Integer id;
    private String question;
    private String picUrl;
    private Integer contentId;
    private List<KeTestOption> optionList;
}
