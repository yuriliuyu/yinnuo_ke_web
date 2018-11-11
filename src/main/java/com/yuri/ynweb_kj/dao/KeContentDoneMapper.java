package com.yuri.ynweb_kj.dao;

import com.yuri.ynweb_kj.pojo.KeContentDone;
import java.util.List;
import java.util.Map;

public interface KeContentDoneMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KeContentDone record);

    KeContentDone selectByPrimaryKey(Integer id);

    List<KeContentDone> selectAll();

    int updateByPrimaryKey(KeContentDone record);

    Integer getMaxReadOrderId(Map map);

    KeContentDone getContentDone(Map map);

    Integer getContentDoneCountByStudentId(Integer studentId);
}