package com.yuri.ynweb_kj.dao;

import com.yuri.ynweb_kj.pojo.KeContentReadable;
import java.util.List;
import java.util.Map;

public interface KeContentReadableMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KeContentReadable record);

    KeContentReadable selectByPrimaryKey(Integer id);

    List<KeContentReadable> selectAll();

    int updateByPrimaryKey(KeContentReadable record);

    Integer getIsReadable(Map map);
}