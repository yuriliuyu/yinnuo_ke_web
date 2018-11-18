package com.yuri.ynweb_kj.service;

import com.yuri.ynweb_kj.dao.*;
import com.yuri.ynweb_kj.dto.content.ContentCountDto;
import com.yuri.ynweb_kj.pojo.*;
import com.yuri.ynweb_kj.utils.EnumContentType;
import com.yuri.ynweb_kj.utils.EnumResCode;
import com.yuri.ynweb_kj.vo.BaseJsonResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    public List<Integer> getTestIdsByContentId(Integer contentId) {
        return keTestMapper.getTestIdsByContentId(contentId);
    }

    public List<Integer> getDoneTestIdsByContentId(Integer contentId, Integer studentId) {
        Map map = new HashMap<>();
        map.put("contentId", contentId);
        map.put("studentId", studentId);
        return keTestDoneMapper.getDoneTestIdsByContentId(map);
    }

    public Integer getAllTestCount() {
        return keTestMapper.getAllTestCount();
    }

    public List<KeTestDone> getTestDoneByStudentId(Integer studentId) {
        return keTestDoneMapper.getTestDoneByStudentId(studentId);
    }

    public Integer getContentCount(Integer level) {
        return keContentMapper.getAllCount(level);
    }

    public Integer getContentDoneCountByStudentId(Integer studentId) {
        return keContentDoneMapper.getContentDoneCountByStudentId(studentId);
    }

    public List<KeContent> getContentListBackend(Integer level, Integer pid, Integer category) {
        Map map = new HashMap<>();
        map.put("level", level);
        map.put("pid", pid);
        map.put("category", category);
        return keContentMapper.getContentListBackend(map);
    }

    public List<KeCharacter> getcharacterListByContentId(Integer contentId) {
        return keCharacterMapper.getcharacterListByContentId(contentId);
    }

    public List<KeTestOption> getTestoptionByTestId(Integer testId) {
        return keTestOptionMapper.getTestoptionByTestId(testId);
    }

    public void insertContent(Integer pid, String title, Integer category, Integer level, Integer type, Integer orderId, String url, String description, String content) {
        KeContent keContent = new KeContent();
        keContent.setTitle(title);
        keContent.setCategory(category);
        keContent.setCreateTime(new Date());
        keContent.setDescription(description);
        keContent.setLevel(level);
        keContent.setOrderId(orderId);
        keContent.setUrl(url);
        keContent.setPid(pid);
        keContent.setType(type);
        keContent.setContent(content);
        keContentMapper.insert(keContent);
    }

    public void insertCharacter(String backgroundPic1, String backgroundPic3, String backgroundPic4, String protrait, String name, Integer age, Integer gender, Integer contentId, Integer orderId, String backgroundText, String information, String question, String summary) {
        KeCharacter keCharacter = new KeCharacter();
        keCharacter.setSummary(summary);
        keCharacter.setQuestion(question);
        keCharacter.setBackgroundPic3(backgroundPic3);
        keCharacter.setAge(age);
        keCharacter.setGender(gender);
        keCharacter.setInformation(information);
        keCharacter.setBackgroundText(backgroundText);
        keCharacter.setBackgroundPic4(backgroundPic4);
        keCharacter.setProtrait(protrait);
        keCharacter.setName(name);
        keCharacter.setBackgroundPic1(backgroundPic1);
        keCharacter.setContentId(contentId);
        keCharacter.setOrderId(orderId);
        keCharacterMapper.insert(keCharacter);
    }

    public void insertTest(String question, Integer contentId, String picUrl, Integer orderId) {
        KeTest keTest = new KeTest();
        keTest.setOrderId(orderId);
        keTest.setContentId(contentId);
        keTest.setCreateTime(new Date());
        keTest.setPicUrl(picUrl);
        keTest.setQuestion(question);
        keTestMapper.insert(keTest);
    }

    public void insertCharacterPic(Integer characterId, String picture, Integer orderId, Integer isRight, String description) {
        KeCharacterPic keCharacterPic = new KeCharacterPic();
        keCharacterPic.setCharacterId(characterId);
        keCharacterPic.setDescription(description);
        keCharacterPic.setIsRight(isRight);
        keCharacterPic.setOrderId(orderId);
        keCharacterPic.setPicture(picture);
        keCharacterPicMapper.insert(keCharacterPic);
    }

    public void insertTestOption(String option, Integer testId, Integer isAnswer, Integer orderId) {
        KeTestOption keTestOption = new KeTestOption();
        keTestOption.setCreateTime(new Date());
        keTestOption.setIsAnswer(isAnswer);
        keTestOption.setOption(option);
        keTestOption.setOrderId(orderId);
        keTestOption.setTestId(testId);
        keTestOptionMapper.insert(keTestOption);
    }

    public KeCharacterPic getCharacterPicById(Integer id) {
        return keCharacterPicMapper.selectByPrimaryKey(id);
    }

    public BaseJsonResultVO deleteContent(Integer id) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        KeContent keContent = keContentMapper.selectByPrimaryKey(id);
        if(keContent.getLevel() != 3){
            vo.setCode(EnumResCode.SERVER_ERROR.value());
            vo.setMessage("目录不可删除");
            return vo;
        }
        if(keContent.getType() == EnumContentType.VIDEO.value() || keContent.getType() == EnumContentType.TEXT.value()){
            keContentMapper.deleteByPrimaryKey(id);
        }else if(keContent.getType() == EnumContentType.TEST.value()){
            List<KeTest> testList = getTestListByContentId(id);
            for(KeTest keTest : testList){
                deleteTest(keTest.getId());
            }
        }else{
            List<KeCharacter> keCharacterList = keCharacterMapper.getcharacterListByContentId(id);
            for(KeCharacter keCharacter : keCharacterList){
                deleteCharacter(keCharacter.getId());
            }
        }
        keContentMapper.deleteByPrimaryKey(id);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    public void deleteTest(Integer id) {
        List<KeTestOption> keTestOptionList = keTestOptionMapper.getOptionListByTestId(id);
        for(KeTestOption keTestOption : keTestOptionList){
            deleteTestOption(keTestOption.getId());
        }
        keTestMapper.deleteByPrimaryKey(id);
    }


    public void deleteCharacter(Integer id) {
        List<KeCharacterPic> keCharacterPicList = keCharacterPicMapper.getCharacterPicByCharaId(id);
        for(KeCharacterPic keCharacterPic : keCharacterPicList){
            deleteCharacterPic(keCharacterPic.getId());
        }
        keCharacterMapper.deleteByPrimaryKey(id);
    }


    public void deleteTestOption(Integer id) {
        keTestOptionMapper.deleteByPrimaryKey(id);
    }

    public void deleteCharacterPic(Integer id) {
        keCharacterPicMapper.deleteByPrimaryKey(id);
    }

    public void updateContent(KeContent keContent) {
        keContentMapper.updateByPrimaryKey(keContent);
    }

    public void updateCharacter(KeCharacter keCharacter) {
        keCharacterMapper.updateByPrimaryKey(keCharacter);
    }

    public void updateTest(KeTest keTest) {
        keTestMapper.updateByPrimaryKey(keTest);
    }

    public void updateCharacterPic(KeCharacterPic keCharacterPic) {
        keCharacterPicMapper.updateByPrimaryKey(keCharacterPic);
    }

    public void updateTestOption(KeTestOption keTestOption) {
        keTestOptionMapper.updateByPrimaryKey(keTestOption);
    }
}
