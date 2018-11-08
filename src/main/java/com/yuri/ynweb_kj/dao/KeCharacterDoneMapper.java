package com.yuri.ynweb_kj.dao;

import com.yuri.ynweb_kj.pojo.KeCharacterDone;
import java.util.List;
import java.util.Map;

public interface KeCharacterDoneMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(KeCharacterDone record);

    KeCharacterDone selectByPrimaryKey(Integer id);

    List<KeCharacterDone> selectAll();

    int updateByPrimaryKey(KeCharacterDone record);

    KeCharacterDone getCharacterDone(Map map);

    List<Integer> getDoneCharacterIdsByContentId(Map map);
}