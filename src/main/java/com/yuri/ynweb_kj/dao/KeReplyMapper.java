package com.yuri.ynweb_kj.dao;

import com.yuri.ynweb_kj.pojo.KeReply;

import java.util.List;
import java.util.Map;

public interface KeReplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KeReply record);

    KeReply selectByPrimaryKey(Integer id);

    List<KeReply> selectAll();

    int updateByPrimaryKey(KeReply record);

    KeReply getBySubjectId(Integer subjectId);

    void updateReplyIsRead(Map map);

    List<Integer> teacherMessageList(Map map);

    KeReply getReplyBySubjectId(Integer subjectId);

    Integer getByTeacherIdAndStudentId(Map map);

    Integer getTeacherUnreadMessgaeNumByUserId(Integer teacherId);
}