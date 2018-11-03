package com.yuri.ynweb_kj.dao;

import com.yuri.ynweb_kj.pojo.KeSignin;
import java.util.List;

public interface KeSigninMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KeSignin record);

    KeSignin selectByPrimaryKey(Integer id);

    List<KeSignin> selectAll();

    int updateByPrimaryKey(KeSignin record);

    List<KeSignin> getBySid(Integer studentId);
}