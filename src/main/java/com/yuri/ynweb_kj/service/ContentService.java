package com.yuri.ynweb_kj.service;

import ch.qos.logback.core.util.ContentTypeUtil;
import com.yuri.ynweb_kj.dao.*;
import com.yuri.ynweb_kj.dto.content.ContentCountDto;
import com.yuri.ynweb_kj.pojo.KeCharacter;
import com.yuri.ynweb_kj.pojo.KeCharacterPic;
import com.yuri.ynweb_kj.pojo.KeContent;
import com.yuri.ynweb_kj.pojo.KeContentReadable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContentService {
    @Autowired
    KeContentMapper keContentMapper;
    @Autowired
    KeCharacterMapper keCharacterMapper;
    @Autowired
    KeContentCharacterMapper keContentCharacterMapper;
    @Autowired
    KeCharacterPicMapper keCharacterPicMapper;
    @Autowired
    KeContentReadableMapper keContentReadableMapper;


    public List<KeContent> getContentListByCategoryAndPid(Integer category, int pid, Integer limit) {
        Map map = new HashMap<>();
        map.put("category", category);
        map.put("pid", pid);
        map.put("limit", limit);
        return keContentMapper.getContentListByCategoryAndPid(map);
    }

    public List<ContentCountDto> getContentListCount() {
        return keContentMapper.getContentListCount();

    }

    public KeCharacter getCharacterById(Integer id) {
        return keCharacterMapper.selectByPrimaryKey(id);
    }

    public List<Integer> getCharacterIdsByContentId(Integer contentId) {
        return keContentCharacterMapper.getCharacterIdsByContentId(contentId);
    }

    public List<KeCharacterPic> getCharacterPicByCharaId(Integer characterId) {
        return keCharacterPicMapper.getCharacterPicByCharaId(characterId);
    }

    public Integer getIsReadable(Integer category, Integer studentId) {
        Map map = new HashMap<>();
        map.put("category", category);
        map.put("studentId", studentId);
        return keContentReadableMapper.getIsReadable(map);
    }

    public KeContent getContentById(Integer contentId) {
        return keContentMapper.selectByPrimaryKey(contentId);
    }
}
