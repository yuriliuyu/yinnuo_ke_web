package com.yuri.ynweb_kj.controller;

import ch.qos.logback.core.util.ContentTypeUtil;
import com.yuri.ynweb_kj.dto.content.ContentCountDto;
import com.yuri.ynweb_kj.dto.content.TrainingL1Dto;
import com.yuri.ynweb_kj.dto.content.TrainingL2Dto;
import com.yuri.ynweb_kj.dto.content.TrainingL3Dto;
import com.yuri.ynweb_kj.pojo.KeContent;
import com.yuri.ynweb_kj.service.ContentService;
import com.yuri.ynweb_kj.utils.EnumResCode;
import com.yuri.ynweb_kj.vo.BaseJsonResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 查询学生首页培训列表
     * @return
     *
     */
    @RequestMapping(value = "/front/content/list", method = RequestMethod.GET)
    public BaseJsonResultVO contentListCount() {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        List<ContentCountDto> list = contentService.getContentListCount();
        List<ContentCountDto> resultList = new LinkedList<>();
        for (ContentCountDto obj : list){
            switch (obj.getCategory()){
                case 1: obj.setTitle(CONTENT_CATEGORY_1);break;
                case 2: obj.setTitle(CONTENT_CATEGORY_2);break;
                case 3: obj.setTitle(CONTENT_CATEGORY_3);break;
                case 4: obj.setTitle(CONTENT_CATEGORY_4);break;
                case 5: obj.setTitle(CONTENT_CATEGORY_5);break;
            }

            List<KeContent> contentList = contentService.getContentListByCategoryAndPid(obj.getCategory(), -1, 4);
            List resultContentList = new LinkedList();
            for (KeContent content: contentList){
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
    public BaseJsonResultVO catDetail(@RequestParam(value = "category") Integer category) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        List<TrainingL1Dto> resultList = new LinkedList<>();
        List<KeContent> l1contentList = contentService.getContentListByCategoryAndPid(category, -1, null);
        for (int i = 0; i < l1contentList.size(); i++) {
            TrainingL1Dto l1dto = new TrainingL1Dto();
            l1dto.setId(l1contentList.get(i).getId());
            l1dto.setDescription(l1contentList.get(i).getDescription());
            l1dto.setTitle(l1contentList.get(i).getTitle());
            List<TrainingL2Dto> l2DtoList = new LinkedList<>();
            List<KeContent> l2contentList = contentService.getContentListByCategoryAndPid(category, l1contentList.get(i).getId(), null);
            for (int j = 0; j < l2contentList.size(); j++) {
                TrainingL2Dto l2dto = new TrainingL2Dto();
                l2dto.setId(l2contentList.get(j).getId());
                l2dto.setDescription(l2contentList.get(j).getDescription());
                l2dto.setTitle(l2contentList.get(j).getTitle());
                List<KeContent> l3contentList = contentService.getContentListByCategoryAndPid(category, l2contentList.get(j).getId(), null);
                List<TrainingL3Dto> l3DtoList = new LinkedList<>();
                for (int k = 0; k < l3contentList.size(); k++) {
                    TrainingL3Dto l3dto = new TrainingL3Dto();
                    l3dto.setId(l3contentList.get(k).getId());
                    l3dto.setTitle(l3contentList.get(k).getTitle());
                    l3dto.setType(l3contentList.get(k).getType());
                    l3dto.setUrl(l3contentList.get(k).getUrl());
                    l3dto.setContent(l3contentList.get(k).getContent());
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


}
