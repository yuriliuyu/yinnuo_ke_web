package com.yuri.ynweb_kj.dao;

import com.yuri.ynweb_kj.dto.content.ContentCountDto;
import com.yuri.ynweb_kj.pojo.KeContent;

import java.util.List;
import java.util.Map;

public interface KeContentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KeContent record);

    KeContent selectByPrimaryKey(Integer id);

    List<KeContent> selectAll();

    int updateByPrimaryKey(KeContent record);

    List<KeContent> getContentListByCategoryAndPid(Map map);

    List<ContentCountDto> getContentListCount();

    Integer getNextReadableOrderId(Map map);

    Integer getAllCount(Integer level);
}