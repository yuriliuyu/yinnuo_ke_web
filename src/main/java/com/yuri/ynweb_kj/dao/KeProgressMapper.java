package com.yuri.ynweb_kj.dao;

import com.yuri.ynweb_kj.pojo.KeProgress;
import java.util.List;
import java.util.Map;

public interface KeProgressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KeProgress record);

    KeProgress selectByPrimaryKey(Integer id);

    List<KeProgress> selectAll();

    int updateByPrimaryKey(KeProgress record);

    List<KeProgress> findProgressList(Map map);

    KeProgress findProgress(Map map);
}