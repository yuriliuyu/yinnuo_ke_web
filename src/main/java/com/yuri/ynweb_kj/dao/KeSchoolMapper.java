package com.yuri.ynweb_kj.dao;

import com.yuri.ynweb_kj.pojo.KeSchool;
import java.util.List;

public interface KeSchoolMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KeSchool record);

    KeSchool selectByPrimaryKey(Integer id);

    List<KeSchool> selectAll();

    int updateByPrimaryKey(KeSchool record);
}