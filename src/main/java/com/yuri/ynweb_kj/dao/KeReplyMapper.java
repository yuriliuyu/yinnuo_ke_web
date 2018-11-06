package com.yuri.ynweb_kj.dao;

import com.yuri.ynweb_kj.pojo.KeReply;

import java.util.List;

public interface KeReplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KeReply record);

    KeReply selectByPrimaryKey(Integer id);

    List<KeReply> selectAll();

    int updateByPrimaryKey(KeReply record);
}