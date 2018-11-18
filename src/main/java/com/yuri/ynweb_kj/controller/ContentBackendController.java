package com.yuri.ynweb_kj.controller;

import com.yuri.ynweb_kj.pojo.*;
import com.yuri.ynweb_kj.service.ContentService;
import com.yuri.ynweb_kj.service.UserService;
import com.yuri.ynweb_kj.utils.EnumResCode;
import com.yuri.ynweb_kj.utils.StringUtils;
import com.yuri.ynweb_kj.vo.BaseJsonResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/contentapi"})
public class ContentBackendController {
    private static String CONTENT_CATEGORY_1 = "养老机构知多少";
    private static String CONTENT_CATEGORY_2 = "照护技术知识库";
    private static String CONTENT_CATEGORY_3 = "养老医疗知识点";
    private static String CONTENT_CATEGORY_4 = "养老十万个Why？";
    private static String CONTENT_CATEGORY_5 = "适老产品营销学";
    @Autowired
    private ContentService contentService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/backend/content/list", method = RequestMethod.POST)
    public BaseJsonResultVO contentList(@RequestParam(value = "level") Integer level, @RequestParam(value = "pid", required = false) Integer pid, @RequestParam(value = "category") Integer category) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        List<KeContent> contentList;
        if (level == 1) {
            contentList = contentService.getContentListBackend(level, null, category);
        } else {
            contentList = contentService.getContentListBackend(level, pid, category);
        }
        vo.setData(contentList);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    @RequestMapping(value = "/backend/character/list", method = RequestMethod.POST)
    public BaseJsonResultVO characterList(@RequestParam(value = "contentid") Integer contentId) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        List<KeCharacter> characterList = contentService.getcharacterListByContentId(contentId);
        vo.setData(characterList);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    @RequestMapping(value = "/backend/test/list", method = RequestMethod.POST)
    public BaseJsonResultVO testList(@RequestParam(value = "contentid") Integer contentId) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        List<KeTest> testList = contentService.getTestListByContentId(contentId);
        vo.setData(testList);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    @RequestMapping(value = "/backend/character/pic/list", method = RequestMethod.POST)
    public BaseJsonResultVO characterPicList(@RequestParam(value = "characterid") Integer characterId) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        List<KeCharacterPic> characterPicList = contentService.getCharacterPicByCharaId(characterId);
        vo.setData(characterPicList);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    @RequestMapping(value = "/backend/testoption/list", method = RequestMethod.POST)
    public BaseJsonResultVO testOptionList(@RequestParam(value = "testid") Integer testId) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        List<KeTestOption> testOptionList = contentService.getTestoptionByTestId(testId);
        vo.setData(testOptionList);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }


    @RequestMapping(value = "/backend/content/add", method = RequestMethod.POST)
    public BaseJsonResultVO contentAdd(@RequestParam(value = "pid") Integer pid, @RequestParam(value = "title") String title,
                                       @RequestParam(value = "category") Integer category, @RequestParam(value = "level") Integer level,
                                       @RequestParam(value = "type") Integer type, @RequestParam(value = "orderid") Integer orderId,
                                       @RequestParam(value = "url", required = false) String url, @RequestParam(value = "description", required = false) String description,
                                       @RequestParam(value = "content", required = false) String content) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        if (level == 3) {
            if (StringUtils.isEmpty(url) && StringUtils.isEmpty(content)) {
                vo.setCode(EnumResCode.SERVER_ERROR.value());
                vo.setMessage("参数有误");
            }
        } else if (level == 1 || level == 2) {

        } else {
            vo.setCode(EnumResCode.SERVER_ERROR.value());
            vo.setMessage("参数有误");
        }
        contentService.insertContent(pid, title, category, level, type, orderId, url, description, content);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    @RequestMapping(value = "/backend/character/add", method = RequestMethod.POST)
    public BaseJsonResultVO characterAdd(@RequestParam(value = "backgroundpic1") String backgroundPic1, @RequestParam(value = "protrait") String protrait,
                                         @RequestParam(value = "name") String name, @RequestParam(value = "age") Integer age,
                                         @RequestParam(value = "gender") Integer gender, @RequestParam(value = "backgroundpic3") String backgroundPic3,
                                         @RequestParam(value = "backgroundpic4") String backgroundPic4, @RequestParam(value = "contentid") Integer contentId,
                                         @RequestParam(value = "orderid") Integer orderId, @RequestParam(value = "backgroundtext") String backgroundText,
                                         @RequestParam(value = "information") String information, @RequestParam(value = "question") String question,
                                         @RequestParam(value = "summary") String summary) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        contentService.insertCharacter(backgroundPic1, backgroundPic3, backgroundPic4, protrait, name, age, gender, contentId, orderId, backgroundText, information, question, summary);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    @RequestMapping(value = "/backend/test/add", method = RequestMethod.POST)
    public BaseJsonResultVO testAdd(@RequestParam(value = "question") String question, @RequestParam(value = "contentid") Integer contentId,
                                    @RequestParam(value = "picurl") String picUrl, @RequestParam(value = "orderid") Integer orderId) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        contentService.insertTest(question, contentId, picUrl, orderId);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    @RequestMapping(value = "/backend/character/pic/add", method = RequestMethod.POST)
    public BaseJsonResultVO characterPicAdd(@RequestParam(value = "characterid") Integer characterId, @RequestParam(value = "picture") String picture,
                                            @RequestParam(value = "orderid") Integer orderId, @RequestParam(value = "isright") Integer isRight, @RequestParam(value = "description") String description) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        contentService.insertCharacterPic(characterId, picture, orderId, isRight, description);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    @RequestMapping(value = "/backend/test/option/add", method = RequestMethod.POST)
    public BaseJsonResultVO testOptionAdd(@RequestParam(value = "option") String option, @RequestParam(value = "testid") Integer testId,
                                          @RequestParam(value = "isanswer") Integer isAnswer, @RequestParam(value = "orderid") Integer orderId) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        contentService.insertTestOption(option, testId, isAnswer, orderId);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    @RequestMapping(value = "/backend/content", method = RequestMethod.POST)
    public BaseJsonResultVO content(@RequestParam(value = "id") Integer id) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        KeContent keContent = contentService.getContentById(id);
        vo.setData(keContent);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }


    @RequestMapping(value = "/backend/test", method = RequestMethod.POST)
    public BaseJsonResultVO test(@RequestParam(value = "id") Integer id) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        KeTest keTest = contentService.getTestById(id);
        vo.setData(keTest);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    @RequestMapping(value = "/backend/character", method = RequestMethod.POST)
    public BaseJsonResultVO character(@RequestParam(value = "id") Integer id) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        KeCharacter keCharacter = contentService.getCharacterById(id);
        vo.setData(keCharacter);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    @RequestMapping(value = "/backend/character/pic", method = RequestMethod.POST)
    public BaseJsonResultVO characterPic(@RequestParam(value = "id") Integer id) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        KeCharacterPic keCharacterPic = contentService.getCharacterPicById(id);
        vo.setData(keCharacterPic);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    @RequestMapping(value = "/backend/test/option", method = RequestMethod.POST)
    public BaseJsonResultVO testOption(@RequestParam(value = "id") Integer id) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        KeTestOption keTestOption = contentService.getTestOptionById(id);
        vo.setData(keTestOption);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    @RequestMapping(value = "/backend/content/delete", method = RequestMethod.POST)
    public BaseJsonResultVO contentDelete(@RequestParam(value = "id") Integer id) {

        return contentService.deleteContent(id);
    }

    @RequestMapping(value = "/backend/test/delete", method = RequestMethod.POST)
    public BaseJsonResultVO testDelete(@RequestParam(value = "id") Integer id) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        contentService.deleteTest(id);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    @RequestMapping(value = "/backend/testoption/delete", method = RequestMethod.POST)
    public BaseJsonResultVO testOptionDelete(@RequestParam(value = "id") Integer id) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        contentService.deleteTestOption(id);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    @RequestMapping(value = "/backend/character/delete", method = RequestMethod.POST)
    public BaseJsonResultVO characterDelete(@RequestParam(value = "id") Integer id) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        contentService.deleteCharacter(id);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    @RequestMapping(value = "/backend/characterPic/delete", method = RequestMethod.POST)
    public BaseJsonResultVO characterPicDelete(@RequestParam(value = "id") Integer id) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        contentService.deleteCharacterPic(id);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    @RequestMapping(value = "/backend/content/edit", method = RequestMethod.POST)
    public BaseJsonResultVO contentEdit(@RequestParam(value = "id") Integer id, @RequestParam(value = "title", required = false) String title,
                                        @RequestParam(value = "orderid", required = false) Integer orderId, @RequestParam(value = "url", required = false) String url,
                                        @RequestParam(value = "description", required = false) String description,
                                        @RequestParam(value = "content", required = false) String content) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        KeContent keContent = contentService.getContentById(id);
        if (title != null) {
            keContent.setTitle(title);
        }
        if (orderId != null) {
            keContent.setOrderId(orderId);
        }
        if (url != null) {
            keContent.setUrl(url);
        }
        if (description != null) {
            keContent.setDescription(description);
        }
        if (content != null) {
            keContent.setContent(content);
        }
        contentService.updateContent(keContent);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    @RequestMapping(value = "/backend/character/edit", method = RequestMethod.POST)
    public BaseJsonResultVO characterEdit(@RequestParam(value = "id") Integer id, @RequestParam(value = "backgroundpic1", required = false) String backgroundPic1, @RequestParam(value = "protrait", required = false) String protrait,
                                          @RequestParam(value = "name", required = false) String name, @RequestParam(value = "age", required = false) Integer age,
                                          @RequestParam(value = "gender", required = false) Integer gender, @RequestParam(value = "backgroundpic3", required = false) String backgroundPic3,
                                          @RequestParam(value = "backgroundpic4", required = false) String backgroundPic4,
                                          @RequestParam(value = "orderid", required = false) Integer orderId, @RequestParam(value = "backgroundtext", required = false) String backgroundText,
                                          @RequestParam(value = "information", required = false) String information, @RequestParam(value = "question", required = false) String question,
                                          @RequestParam(value = "summary", required = false) String summary) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        KeCharacter keCharacter = contentService.getCharacterById(id);

        if (backgroundPic1 != null) {
            keCharacter.setBackgroundPic1(backgroundPic1);
        }
        if (protrait != null) {
            keCharacter.setProtrait(protrait);
        }
        if (name != null) {
            keCharacter.setName(name);
        }
        if (age != null) {
            keCharacter.setAge(age);
        }
        if (gender != null) {
            keCharacter.setGender(gender);
        }
        if (backgroundPic3 != null) {
            keCharacter.setBackgroundPic3(backgroundPic3);
        }
        if (backgroundPic4 != null) {
            keCharacter.setBackgroundPic4(backgroundPic4);
        }
        if (orderId != null) {
            keCharacter.setOrderId(orderId);
        }
        if (backgroundText != null) {
            keCharacter.setBackgroundText(backgroundText);
        }
        if (information != null) {
            keCharacter.setInformation(information);
        }
        if (question != null) {
            keCharacter.setQuestion(question);
        }
        if (summary != null) {
            keCharacter.setSummary(summary);
        }
        contentService.updateCharacter(keCharacter);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    @RequestMapping(value = "/backend/test/edit", method = RequestMethod.POST)
    public BaseJsonResultVO testEdit(@RequestParam(value = "id") Integer id,@RequestParam(value = "question", required = false) String question,
                                    @RequestParam(value = "picurl", required = false) String picUrl, @RequestParam(value = "orderid", required = false) Integer orderId) {
        KeTest keTest = contentService.getTestById(id);
        BaseJsonResultVO vo = new BaseJsonResultVO();
        if (picUrl != null) {
            keTest.setPicUrl(picUrl);
        }
        if (orderId != null) {
            keTest.setOrderId(orderId);
        }
        if (question != null) {
            keTest.setQuestion(question);
        }
        contentService.updateTest(keTest);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    @RequestMapping(value = "/backend/character/pic/edit", method = RequestMethod.POST)
    public BaseJsonResultVO characterPicEdit(@RequestParam(value = "id") Integer id, @RequestParam(value = "picture", required = false) String picture,
                                            @RequestParam(value = "orderid", required = false) Integer orderId, @RequestParam(value = "isright", required = false) Integer isRight,
                                             @RequestParam(value = "description", required = false) String description) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        KeCharacterPic keCharacterPic = contentService.getCharacterPicById(id);
        if (picture != null) {
            keCharacterPic.setPicture(picture);
        }
        if (orderId != null) {
            keCharacterPic.setOrderId(orderId);
        }
        if (isRight != null) {
            keCharacterPic.setIsRight(isRight);
        }
        if (description != null) {
            keCharacterPic.setDescription(description);
        }
        contentService.updateCharacterPic(keCharacterPic);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    @RequestMapping(value = "/backend/test/option/edit", method = RequestMethod.POST)
    public BaseJsonResultVO testOptionEdit(@RequestParam(value = "id") Integer id,@RequestParam(value = "option", required = false) String option,
                                          @RequestParam(value = "isanswer", required = false) Integer isAnswer, @RequestParam(value = "orderid", required = false) Integer orderId) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        KeTestOption keTestOption = contentService.getTestOptionById(id);
        if (option != null) {
            keTestOption.setOption(option);
        }
        if (orderId != null) {
            keTestOption.setOrderId(orderId);
        }
        if (isAnswer != null) {
            keTestOption.setIsAnswer(isAnswer);
        }
        contentService.updateTestOption(keTestOption);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

}
