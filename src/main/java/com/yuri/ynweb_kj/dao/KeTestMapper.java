package com.yuri.ynweb_kj.dao;

import com.yuri.ynweb_kj.pojo.KeTest;
import java.util.List;

public interface KeTestMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KeTest record);

    KeTest selectByPrimaryKey(Integer id);

    List<KeTest> selectAll();

    int updateByPrimaryKey(KeTest record);

    List<KeTest> getTestListByContentId(Integer contentId);

    List<Integer> getTestIdsByContentId(Integer contentId);

    Integer getAllTestCount();
}