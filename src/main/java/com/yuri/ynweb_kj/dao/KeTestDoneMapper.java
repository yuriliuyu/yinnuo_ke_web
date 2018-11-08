package com.yuri.ynweb_kj.dao;

import com.yuri.ynweb_kj.pojo.KeTestDone;

import java.util.List;
import java.util.Map;

public interface KeTestDoneMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KeTestDone record);

    KeTestDone selectByPrimaryKey(Integer id);

    List<KeTestDone> selectAll();

    int updateByPrimaryKey(KeTestDone record);

    List<KeTestDone> getWrongTestList(Integer studentId);

    KeTestDone getTestDoneByStudentAndTestId(Map map);
}