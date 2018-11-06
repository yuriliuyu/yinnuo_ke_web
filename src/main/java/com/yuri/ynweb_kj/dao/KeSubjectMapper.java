package com.yuri.ynweb_kj.dao;

import com.yuri.ynweb_kj.pojo.KeSubject;
import java.util.List;

public interface KeSubjectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KeSubject record);

    KeSubject selectByPrimaryKey(Integer id);

    List<KeSubject> selectAll();

    int updateByPrimaryKey(KeSubject record);
}