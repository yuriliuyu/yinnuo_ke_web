package com.yuri.ynweb_kj.dao;

import com.yuri.ynweb_kj.pojo.KeSubject;
import org.apache.tomcat.jni.Mmap;

import java.util.List;
import java.util.Map;

public interface KeSubjectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KeSubject record);

    KeSubject selectByPrimaryKey(Integer id);

    List<KeSubject> selectAll();

    int updateByPrimaryKey(KeSubject record);

    Integer getNextUnreadSubjectTeacherId();

    List<KeSubject> studentMessageList(Map map);

    void updateSubjectReadable(Map map);

    Integer getNextSubjectTeacherId(Map map);

    Integer getStudentUnreadMessgaeNumByUserId(Integer studentId);
}