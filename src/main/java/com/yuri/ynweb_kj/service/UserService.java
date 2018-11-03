package com.yuri.ynweb_kj.service;

import com.yuri.ynweb_kj.dao.KeFollowMapper;
import com.yuri.ynweb_kj.dao.KeProgressMapper;
import com.yuri.ynweb_kj.dao.KeSigninMapper;
import com.yuri.ynweb_kj.dao.KeUserMapper;
import com.yuri.ynweb_kj.dto.UserSimpleDto;
import com.yuri.ynweb_kj.pojo.KeFollow;
import com.yuri.ynweb_kj.pojo.KeProgress;
import com.yuri.ynweb_kj.pojo.KeSignin;
import com.yuri.ynweb_kj.pojo.KeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.util.*;

@Service
public class UserService {

    @Autowired
    KeUserMapper userMapper;
    @Autowired
    KeFollowMapper keFollowMapper;
    @Autowired
    KeSigninMapper keSigninMapper;

    @Autowired
    KeProgressMapper keProgressMapper;

    public KeUser findUser(String userName, String password) {
        Map map = new HashMap<>();
        map.put("name", userName);
        map.put("password", password);
        return userMapper.findByNameAndPassword(map);
    }


    public List<UserSimpleDto> teacherFollow(Integer teacherId) {
        List<KeFollow> followList =  keFollowMapper.teacherFollow(teacherId);
        List<UserSimpleDto> resultList = new LinkedList<>();
        for(int i =0;i<followList.size();i++){
            KeUser student = userMapper.selectByPrimaryKey(followList.get(i).getStudentId());
            UserSimpleDto dto = new UserSimpleDto();
            dto.setId(student.getId());
            dto.setName(student.getName());
            dto.setMessage(student.getMessage());
            resultList.add(dto);
        }
        return resultList;
    }

    public KeUser findUserById(Integer teacherId) {
        return userMapper.selectByPrimaryKey(teacherId);
    }

    public void updateUser(KeUser user) {
        userMapper.updateByPrimaryKey(user);
    }

    public void signin(Integer studentId) {
        KeSignin signin = new KeSignin();
        signin.setDate(new Date());
        signin.setStudentId(studentId);
        signin.setCreateTime(new Date());
        keSigninMapper.insert(signin);
    }

    public List<KeSignin> findSignin(Integer studentId) {
        return keSigninMapper.getBySid(studentId);
    }

    public List<KeProgress> findProgressList(Integer studentId, Integer type) {
        Map map = new HashMap<>();
        map.put("studentId", studentId);
        map.put("type", type);
        return keProgressMapper.findProgressList(map);
    }

    public List<UserSimpleDto> studentFollow(Integer studentId) {
        List<KeFollow> followList =  keFollowMapper.studentFollow(studentId);
        List<UserSimpleDto> resultList = new LinkedList<>();
        for(int i =0;i<followList.size();i++){
            KeUser teacher = userMapper.selectByPrimaryKey(followList.get(i).getTeacherId());
            UserSimpleDto dto = new UserSimpleDto();
            dto.setId(teacher.getId());
            dto.setName(teacher.getName());
            resultList.add(dto);
        }
        return resultList;
    }

    public KeUser findUserByName(Integer name) {
        return userMapper.findUserByName(name);
    }

    public KeFollow findFollow(Integer studentId, Integer teacherId) {
        return keFollowMapper.findFollow(studentId,teacherId);
    }

    public void follow(Integer studentId, Integer teacherId) {
        KeFollow follow = new KeFollow();
        follow.setStudentId(studentId);
        follow.setTeacherId(teacherId);
        follow.setCreateTime(new Date());
        keFollowMapper.insert(follow);
    }
}
