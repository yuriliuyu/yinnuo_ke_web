package com.yuri.ynweb_kj.service;

import com.yuri.ynweb_kj.dao.KeContentMapper;
import com.yuri.ynweb_kj.dto.content.ContentCountDto;
import com.yuri.ynweb_kj.pojo.KeContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContentService {
    @Autowired
    KeContentMapper keContentMapper;


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
}
