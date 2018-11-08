package com.yuri.ynweb_kj.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class SubjectDto {
    private Integer subjectId;
    private Integer teacherId;
    private String teacherName;
    private String subjectContent;
    private String reply;
    private Integer replyId;
    private String studentName;
}
