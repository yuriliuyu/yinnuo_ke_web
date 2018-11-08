package com.yuri.ynweb_kj.service;

import com.yuri.ynweb_kj.dao.*;
import com.yuri.ynweb_kj.dto.content.ContentCountDto;
import com.yuri.ynweb_kj.pojo.*;
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
    KeCharacterPicMapper keCharacterPicMapper;
    @Autowired
    KeTestMapper keTestMapper;
    @Autowired
    KeTestOptionMapper keTestOptionMapper;
    @Autowired
    KeTestDoneMapper keTestDoneMapper;
    @Autowired
    KeContentDoneMapper keContentDoneMapper;
    @Autowired
    KeCharacterDoneMapper keCharacterDoneMapper;


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
        return keCharacterMapper.getIdsByContentId(contentId);
    }

    public List<KeCharacterPic> getCharacterPicByCharaId(Integer characterId) {
        return keCharacterPicMapper.getCharacterPicByCharaId(characterId);
    }

    public KeContent getContentById(Integer contentId) {
        return keContentMapper.selectByPrimaryKey(contentId);
    }

    public List<KeTest> getTestListByContentId(Integer contentId) {
        return keTestMapper.getTestListByContentId(contentId);
    }

    public List<KeTestOption> getOptionListByTestId(Integer testId) {
        return keTestOptionMapper.getOptionListByTestId(testId);
    }

    public KeTestOption getTestOptionById(Integer testOptionId) {
        return keTestOptionMapper.selectByPrimaryKey(testOptionId);
    }

    public KeTest getTestById(Integer testId) {
        return keTestMapper.selectByPrimaryKey(testId);
    }

    public void insertTestWrong(KeTestDone keTestDone) {
        keTestDoneMapper.insert(keTestDone);
    }

    public List<KeTestDone> getWrongTestList(Integer studentId) {
        return keTestDoneMapper.getWrongTestList(studentId);
    }

    public Integer getMaxReadOrderId(Integer studentId, Integer category) {
        Map map = new HashMap<>();
        map.put("category", category);
        map.put("studentId", studentId);
        return keContentDoneMapper.getMaxReadOrderId(map);
    }

    public Integer getNextReadableOrderId(Integer orderId, Integer category) {
        Map map = new HashMap<>();
        map.put("category", category);
        map.put("orderId", orderId);
        return keContentMapper.getNextReadableOrderId(map);
    }

    public KeContentDone getContentDone(Integer contentId, Integer studentId) {
        Map map = new HashMap<>();
        map.put("contentId", contentId);
        map.put("studentId", studentId);
        return keContentDoneMapper.getContentDone(map);
    }

    public KeCharacterDone getCharacterDone(Integer characterId, Integer studentId) {
        Map map = new HashMap<>();
        map.put("characterId", characterId);
        map.put("studentId", studentId);
        return keCharacterDoneMapper.getCharacterDone(map);
    }

    public void insertCharacterDone(Integer studentId, Integer characterId, Integer contentId) {
        KeCharacterDone keCharacterDone = new KeCharacterDone();
        keCharacterDone.setCharacterId(characterId);
        keCharacterDone.setStudentId(studentId);
        keCharacterDone.setContentId(contentId);
        keCharacterDoneMapper.insert(keCharacterDone);
    }

    public void insertContentDone(KeContent keContent, Integer studentId) {
        KeContentDone keContentDone = new KeContentDone();
        keContentDone.setContentId(keContent.getId());
        keContentDone.setStudentId(studentId);
        keContentDone.setCategory(keContent.getCategory());
        keContentDone.setOrderId(keContent.getOrderId());
        keContentDoneMapper.insert(keContentDone);
    }

    public List<Integer> getDoneCharacterIdsByContentId(Integer contentId, Integer studentId) {
        Map map = new HashMap<>();
        map.put("contentId", contentId);
        map.put("studentId", studentId);
        return keCharacterDoneMapper.getDoneCharacterIdsByContentId(map);
    }

    public KeTestDone getTestDoneByStudentAndTestId(Integer studentId, Integer testId) {
        Map map = new HashMap<>();
        map.put("testId", testId);
        map.put("studentId", studentId);
        return keTestDoneMapper.getTestDoneByStudentAndTestId(map);
    }
}
