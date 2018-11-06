package com.yuri.ynweb_kj.dao;

import com.yuri.ynweb_kj.pojo.KeCharacterPic;
import java.util.List;

public interface KeCharacterPicMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KeCharacterPic record);

    KeCharacterPic selectByPrimaryKey(Integer id);

    List<KeCharacterPic> selectAll();

    int updateByPrimaryKey(KeCharacterPic record);

    List<KeCharacterPic> getCharacterPicByCharaId(Integer characterId);
}