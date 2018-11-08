package com.yuri.ynweb_kj.dao;

import com.yuri.ynweb_kj.pojo.KeCharacter;
import java.util.List;

public interface KeCharacterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KeCharacter record);

    KeCharacter selectByPrimaryKey(Integer id);

    List<KeCharacter> selectAll();

    int updateByPrimaryKey(KeCharacter record);

    List<Integer> getIdsByContentId(Integer contentId);
}