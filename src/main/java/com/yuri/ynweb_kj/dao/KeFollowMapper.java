package com.yuri.ynweb_kj.dao;

import com.yuri.ynweb_kj.pojo.KeFollow;
import java.util.List;

public interface KeFollowMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KeFollow record);

    KeFollow selectByPrimaryKey(Integer id);

    List<KeFollow> selectAll();

    int updateByPrimaryKey(KeFollow record);

    List<KeFollow> teacherFollow(Integer teacherId);

    List<KeFollow> studentFollow(Integer studentId);

    KeFollow findFollow(Integer studentId, Integer teacherId);
}