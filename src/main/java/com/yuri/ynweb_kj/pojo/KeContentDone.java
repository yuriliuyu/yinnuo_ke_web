package com.yuri.ynweb_kj.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class KeContentDone {
    private Integer id;

    private Integer contentId;

    private Integer studentId;

    private Integer orderId;

    private Integer category;
}