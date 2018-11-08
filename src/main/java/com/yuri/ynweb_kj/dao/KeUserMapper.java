package com.yuri.ynweb_kj.dao;

import com.yuri.ynweb_kj.pojo.KeProgress;
import com.yuri.ynweb_kj.pojo.KeUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


public interface KeUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KeUser record);

    KeUser selectByPrimaryKey(Integer id);

    List<KeUser> selectAll();

    int updateByPrimaryKey(KeUser record);

    KeUser findByNameAndPassword(Map map);


    KeUser findUserByName(String name);
}