package com.yuri.ynweb_kj.dao;

import com.yuri.ynweb_kj.pojo.KeContentCharacter;
import java.util.List;

public interface KeContentCharacterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KeContentCharacter record);

    KeContentCharacter selectByPrimaryKey(Integer id);

    List<KeContentCharacter> selectAll();

    int updateByPrimaryKey(KeContentCharacter record);

    List<Integer> getCharacterIdsByContentId(Integer contentId);
}