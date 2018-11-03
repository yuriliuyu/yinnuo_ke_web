package com.yuri.ynweb_kj.vo;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class BaseJsonResultVO<ResultDataType> {

    //-1 失败，1 成功
    private Integer code;
    private String message;
    private ResultDataType data;
    private Integer pageNo;
    private Long total;

}
