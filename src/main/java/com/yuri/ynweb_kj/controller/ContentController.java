package com.yuri.ynweb_kj.controller;

import com.yuri.ynweb_kj.dto.content.*;
import com.yuri.ynweb_kj.pojo.*;
import com.yuri.ynweb_kj.service.ContentService;
import com.yuri.ynweb_kj.service.UserService;
import com.yuri.ynweb_kj.utils.*;
import com.yuri.ynweb_kj.vo.BaseJsonResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(value = {"/contentapi"})
public class ContentController {
    private static String CONTENT_CATEGORY_1 = "养老机构知多少";
    private static String CONTENT_CATEGORY_2 = "照护技术知识库";
    private static String CONTENT_CATEGORY_3 = "养老医疗知识点";
    private static String CONTENT_CATEGORY_4 = "养老十万个Why？";
    private static String CONTENT_CATEGORY_5 = "适老产品营销学";
    @Autowired
    private ContentService contentService;
    @Autowired
    private UserService userService;

    /**
     * 查询学生首页培训列表
     *
     * @return
     */
    @RequestMapping(value = "/front/content/list", method = RequestMethod.GET)
    public BaseJsonResultVO contentListCount() {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        List<ContentCountDto> list = contentService.getContentListCount();
        List<ContentCountDto> resultList = new LinkedList<>();
        for (ContentCountDto obj : list) {
            switch (obj.getCategory()) {
                case 1:
                    obj.setTitle(CONTENT_CATEGORY_1);
                    break;
                case 2:
                    obj.setTitle(CONTENT_CATEGORY_2);
                    break;
                case 3:
                    obj.setTitle(CONTENT_CATEGORY_3);
                    break;
                case 4:
                    obj.setTitle(CONTENT_CATEGORY_4);
                    break;
                case 5:
                    obj.setTitle(CONTENT_CATEGORY_5);
                    break;
            }

            List<KeContent> contentList = contentService.getContentListByCategoryAndPid(obj.getCategory(), -1, 4);
            List resultContentList = new LinkedList();
            for (KeContent content : contentList) {
                KeContent resultContent = new KeContent();
                resultContent.setId(content.getId());
                resultContent.setTitle(content.getTitle());
                resultContentList.add(resultContent);
            }
            obj.setContentList(resultContentList);
            resultList.add(obj);
        }
        vo.setData(resultList);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }


    /**
     * 按分类查找培训内容列表
     *
     * @param category
     * @return
     */
    @RequestMapping(value = "/front/content/cat/detail", method = RequestMethod.POST)
    public BaseJsonResultVO catDetail(@RequestParam(value = "category") Integer category, @RequestParam(value = "studentid") Integer studentId) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        List<TrainingL1Dto> resultList = new LinkedList<>();
        //查看某个分类下已读内容
        Integer orderId = contentService.getMaxReadOrderId(studentId, category);
        Integer nextReadableOrderId = 0;
        if (orderId != null) {
            nextReadableOrderId = contentService.getNextReadableOrderId(orderId, category);
        }
        //查找一级目录
        List<KeContent> l1contentList = contentService.getContentListByCategoryAndPid(category, -1, null);
        for (int i = 0; i < l1contentList.size(); i++) {
            TrainingL1Dto l1dto = new TrainingL1Dto();
            l1dto.setId(l1contentList.get(i).getId());
            l1dto.setDescription(l1contentList.get(i).getDescription());
            l1dto.setTitle(l1contentList.get(i).getTitle());
            List<TrainingL2Dto> l2DtoList = new LinkedList<>();
            //查找二级目录
            List<KeContent> l2contentList = contentService.getContentListByCategoryAndPid(category, l1contentList.get(i).getId(), null);
            for (int j = 0; j < l2contentList.size(); j++) {
                TrainingL2Dto l2dto = new TrainingL2Dto();
                l2dto.setId(l2contentList.get(j).getId());
                l2dto.setDescription(l2contentList.get(j).getDescription());
                l2dto.setTitle(l2contentList.get(j).getTitle());
                //查找三级内容
                List<KeContent> l3contentList = contentService.getContentListByCategoryAndPid(category, l2contentList.get(j).getId(), null);
                List<TrainingL3Dto> l3DtoList = new LinkedList<>();
                for (int k = 0; k < l3contentList.size(); k++) {
                    TrainingL3Dto l3dto = new TrainingL3Dto();
                    l3dto.setId(l3contentList.get(k).getId());
                    l3dto.setTitle(l3contentList.get(k).getTitle());
                    l3dto.setType(l3contentList.get(k).getType());
                    l3dto.setUrl(l3contentList.get(k).getUrl());
                    //增加可读标识
                    if (orderId == null) {
                        if (k == 0) {
                            l3dto.setIsReadable(EnumIsReadable.READABLE.value());
                        } else {
                            l3dto.setIsReadable(EnumIsReadable.UNREADABLE.value());
                        }
                    } else {
                        if(nextReadableOrderId == null){
                            l3dto.setIsReadable(EnumIsReadable.READABLE.value());
                        }else{
                            if (l3contentList.get(k).getOrderId() <= nextReadableOrderId) {
                                l3dto.setIsReadable(EnumIsReadable.READABLE.value());
                            } else {
                                l3dto.setIsReadable(EnumIsReadable.UNREADABLE.value());
                            }
                        }
                    }
                    l3DtoList.add(l3dto);
                    l2dto.setTrainingL3DtoList(l3DtoList);
                }
                l2DtoList.add(l2dto);
                l1dto.setTrainingL2DtoList(l2DtoList);
            }
            resultList.add(l1dto);
        }
        vo.setData(resultList);
        vo.setCode(EnumResCode.SERVER_ERROR.value());
        vo.setMessage("ok");
        return vo;
    }

    /**
     * 案例分析1
     *
     * @param contentId
     * @return
     */
    @RequestMapping(value = "/front/content/case", method = RequestMethod.POST)
    public BaseJsonResultVO cases(@RequestParam(value = "contentid") Integer contentId) {
        List<Integer> characterIdList = contentService.getCharacterIdsByContentId(contentId);
        List<KeCharacter> resultList = new LinkedList<>();
        for (Integer id : characterIdList) {
            KeCharacter character = contentService.getCharacterById(id);
            KeCharacter result = new KeCharacter();
            result.setId(character.getId());
            result.setBackgroundPic1(character.getBackgroundPic1());
            result.setBackgroundText(character.getBackgroundText());
            result.setProtrait(character.getProtrait());
            resultList.add(result);
        }
        BaseJsonResultVO vo = new BaseJsonResultVO();
        vo.setData(resultList);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    /**
     * 案例分析_step2
     *
     * @param characterId
     * @return
     */
    @RequestMapping(value = "/front/content/case_step2", method = RequestMethod.POST)
    public BaseJsonResultVO casesStep2(@RequestParam(value = "characterid") Integer characterId) {
        KeCharacter character = contentService.getCharacterById(characterId);
        CharacterDto result = new CharacterDto();
        result.setProtrait(character.getProtrait());
        result.setInformation(character.getInformation());
        result.setName(character.getName());
        result.setGender(character.getGender() == 1 ? EnumGender.MALE.getDescription() : EnumGender.FEMALE.getDescription());
        result.setAge(character.getAge());
        result.setId(character.getId());

        BaseJsonResultVO vo = new BaseJsonResultVO();
        vo.setData(result);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    /**
     * 案例分析_step3
     *
     * @param characterId
     * @return
     */
    @RequestMapping(value = "/front/content/case_step3", method = RequestMethod.POST)
    public BaseJsonResultVO casesStep3(@RequestParam(value = "characterid") Integer characterId) {
        KeCharacter character = contentService.getCharacterById(characterId);
        KeCharacter result = new KeCharacter();
        result.setBackgroundPic3(character.getBackgroundPic3());
        result.setQuestion(character.getQuestion());
        result.setId(character.getId());

        BaseJsonResultVO vo = new BaseJsonResultVO();
        vo.setData(result);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    /**
     * 案例分析_step4
     *
     * @param characterId
     * @return
     */
    @RequestMapping(value = "/front/content/case_step4", method = RequestMethod.POST)
    public BaseJsonResultVO casesStep4(@RequestParam(value = "characterid") Integer characterId) {
        KeCharacter character = contentService.getCharacterById(characterId);
        List<KeCharacterPic> keCharacterPicList = contentService.getCharacterPicByCharaId(characterId);
        CharacterStep4Dto result = new CharacterStep4Dto();
        result.setCharacterId(characterId);
        result.setBackgroundPic4(character.getBackgroundPic4());
        result.setPicList(keCharacterPicList);

        BaseJsonResultVO vo = new BaseJsonResultVO();
        vo.setData(result);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    /**
     * 案例分析_step5
     *
     * @param characterId
     * @return
     */
    @RequestMapping(value = "/front/content/case_step5", method = RequestMethod.POST)
    public BaseJsonResultVO casesStep5(@RequestParam(value = "characterid") Integer characterId, @RequestParam(value = "studentid") Integer studentId) {
        KeCharacter character = contentService.getCharacterById(characterId);
        KeCharacter result = new KeCharacter();
        result.setSummary(character.getSummary());
        //先查看内容已读表里是否有该记录
        KeContentDone keContentDone = contentService.getContentDone(character.getContentId(), studentId);
        if (keContentDone == null) {
            //再查看主人公已读表里是否有该记录
            KeCharacterDone keCharacterDone = contentService.getCharacterDone(characterId, studentId);
            if (keCharacterDone == null) {
                contentService.insertCharacterDone(studentId, characterId, character.getContentId());
            }
            //查看该内容下所有主人公是否全部浏览完毕
            List<Integer> characterIdList = contentService.getCharacterIdsByContentId(character.getContentId());
            List<Integer> doneCharacterIdList = contentService.getDoneCharacterIdsByContentId(character.getContentId(), studentId);
            Collections.sort(characterIdList);
            Collections.sort(doneCharacterIdList);
            //所有主人公全部浏览完毕，插入contentdone
            if (characterIdList.equals(doneCharacterIdList)) {
                KeContent kecontent = contentService.getContentById(character.getContentId());
                contentService.insertContentDone(kecontent, studentId);
            }
        }
        BaseJsonResultVO vo = new BaseJsonResultVO();
        vo.setData(result);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    /**
     * 随堂测试
     *
     * @param contentId 内容id
     * @return
     */
    @RequestMapping(value = "/front/content/test", method = RequestMethod.POST)
    public BaseJsonResultVO test(@RequestParam(value = "contentid") Integer contentId) {
        List<KeTest> keTestList = contentService.getTestListByContentId(contentId);
        List<TestDto> resultList = new LinkedList<>();
        for (KeTest test : keTestList) {
            List<KeTestOption> optionList = contentService.getOptionListByTestId(test.getId());
            TestDto dto = new TestDto();
            dto.setContentId(test.getContentId());
            dto.setId(test.getId());
            dto.setOptionList(optionList);
            dto.setPicUrl(test.getPicUrl());
            dto.setQuestion(test.getQuestion());
            resultList.add(dto);
        }
        BaseJsonResultVO vo = new BaseJsonResultVO();
        vo.setData(resultList);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    /**
     * 随堂测试题答案
     *
     * @param testOptionId 选项id
     * @param studentId    学生id
     * @return
     */
    @RequestMapping(value = "/front/content/test/judge", method = RequestMethod.POST)
    public BaseJsonResultVO judge(@RequestParam(value = "testoptionid") Integer testOptionId, @RequestParam(value = "studentid") Integer studentId) {
        KeTestOption option = contentService.getTestOptionById(testOptionId);
        KeTest keTest = contentService.getTestById(option.getTestId());
        KeUser student = userService.findUserById(studentId);

        //先查看内容已读表里是否有该记录
        KeContentDone keContentDone = contentService.getContentDone(keTest.getContentId(), studentId);
        if (keContentDone == null) {
            //再查看随堂测试已读表里是否有该记录
            KeTestDone keTestDone = contentService.getTestDoneByStudentAndTestId(studentId, option.getTestId());
            if (keTestDone == null) {
                if (option.getIsAnswer() == EnumIsAnswer.YES.value()) {
                    userService.updateUser(student);
                    userService.updateProgress(student, EnumCreditType.TEST.value(), 1, 0);
                } else {
                    userService.updateProgress(student, 0, 1, 0);
                }
                keTestDone = new KeTestDone();
                keTestDone.setCreateTime(new Date());
                keTestDone.setTestId(option.getTestId());
                keTestDone.setContentId(keTest.getContentId());
                keTestDone.setStudentId(studentId);
                keTestDone.setQustion(keTest.getQuestion());
                keTestDone.setIsRight(option.getIsAnswer());
                contentService.insertTestWrong(keTestDone);
            }
            //查看该内容下所有随堂测试是否全部浏览完毕
            List<Integer> testIdList = contentService.getTestIdsByContentId(keTest.getContentId());
            List<Integer> doneTestIdList = contentService.getDoneTestIdsByContentId(keTest.getContentId(), studentId);
            Collections.sort(testIdList);
            Collections.sort(doneTestIdList);
            //所有测试全部回答完毕，插入contentdone
            if (testIdList.equals(doneTestIdList)) {
                KeContent kecontent = contentService.getContentById(keTest.getContentId());
                contentService.insertContentDone(kecontent, studentId);
            }
        }
        Integer isAnswer = option.getIsAnswer();
        BaseJsonResultVO vo = new BaseJsonResultVO();
        vo.setData(isAnswer);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    /**
     * 错题本
     *
     * @param studentId 学生id
     * @return
     */
    @RequestMapping(value = "/front/content/wrongTest", method = RequestMethod.POST)
    public BaseJsonResultVO wrongTest(@RequestParam(value = "studentid") Integer studentId) {
        List<KeTestDone> resultList = contentService.getWrongTestList(studentId);
        BaseJsonResultVO vo = new BaseJsonResultVO();
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setData(resultList);
        vo.setMessage("ok");
        return vo;
    }

    /**
     * 视频播放完成
     *
     * @param contentId 视频id
     * @param studentId 学生id
     * @return
     */
    @RequestMapping(value = "/front/content/videofinish", method = RequestMethod.POST)
    public BaseJsonResultVO videoFinish(@RequestParam(value = "contentid") Integer contentId, @RequestParam(value = "studentid") Integer studentId) {
        KeContent content = contentService.getContentById(contentId);
        KeUser student = userService.findUserById(studentId);
        //先查看内容已读表里是否有该记录
        KeContentDone keContentDone = contentService.getContentDone(contentId, studentId);
        if (keContentDone == null) {
            userService.updateProgress(student, EnumCreditType.VIDEO.value(), 0, 1);
            contentService.insertContentDone(content, studentId);
        }
        BaseJsonResultVO vo = new BaseJsonResultVO();
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    /**
     * 获取图文混排内容
     *
     * @param contentId 视频id
     * @param studentId 学生id
     * @return
     */
    @RequestMapping(value = "/front/content/mixcontent", method = RequestMethod.POST)
    public BaseJsonResultVO mixContent(@RequestParam(value = "contentid") Integer contentId, @RequestParam(value = "studentid") Integer studentId) {
        KeContent content = contentService.getContentById(contentId);
        KeUser student = userService.findUserById(studentId);
        //先查看内容已读表里是否有该记录
        KeContentDone keContentDone = contentService.getContentDone(contentId, studentId);
        if (keContentDone == null) {
            contentService.insertContentDone(content, studentId);
        }
        BaseJsonResultVO vo = new BaseJsonResultVO();
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }
}
