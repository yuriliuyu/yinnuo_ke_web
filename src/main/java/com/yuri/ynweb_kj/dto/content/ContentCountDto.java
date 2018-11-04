package com.yuri.ynweb_kj.dto.content;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yuri.ynweb_kj.pojo.KeContent;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ContentCountDto {
    private String title;
    private Integer count;
    private Integer category;
    private List<KeContent> contentList;
}
