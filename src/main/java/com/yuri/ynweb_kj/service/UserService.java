package com.yuri.ynweb_kj.service;

import com.yuri.ynweb_kj.dao.*;
import com.yuri.ynweb_kj.dto.UserSimpleDto;
import com.yuri.ynweb_kj.pojo.*;
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
    @Autowired
    KeSubjectMapper keSubjectMapper;
    @Autowired
    KeReplyMapper keReplyMapper;

    public KeUser findUser(String userName, String password) {
        Map map = new HashMap<>();
        map.put("name", userName);
        map.put("password", password);
        return userMapper.findByNameAndPassword(map);
    }


    public List<UserSimpleDto> teacherFollow(Integer teacherId) {
        List<KeFollow> followList = keFollowMapper.teacherFollow(teacherId);
        List<UserSimpleDto> resultList = new LinkedList<>();
        for (int i = 0; i < followList.size(); i++) {
            KeUser student = userMapper.selectByPrimaryKey(followList.get(i).getStudentId());
            UserSimpleDto dto = new UserSimpleDto();
            dto.setId(student.getId());
            dto.setName(student.getName());
            dto.setMessage(student.getMessage());
            resultList.add(dto);
        }
        return resultList;
    }

    public KeUser findUserById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
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
        List<KeFollow> followList = keFollowMapper.studentFollow(studentId);
        List<UserSimpleDto> resultList = new LinkedList<>();
        for (int i = 0; i < followList.size(); i++) {
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
        return keFollowMapper.findFollow(studentId, teacherId);
    }

    public void follow(Integer studentId, Integer teacherId) {
        KeFollow follow = new KeFollow();
        follow.setStudentId(studentId);
        follow.setTeacherId(teacherId);
        follow.setCreateTime(new Date());
        keFollowMapper.insert(follow);
    }

    public void teacherToSubject(KeUser teacher, List<Integer> studentIdList, String question) {
        for (Integer studentId : studentIdList) {
            KeSubject keSubject = new KeSubject();
            keSubject.setContent(question);
            keSubject.setStudentId(studentId);
            keSubject.setTeacherId(teacher.getId());
            keSubject.setTeacherName(teacher.getName());
            keSubjectMapper.insert(keSubject);
            KeUser student = userMapper.selectByPrimaryKey(studentId);
            student.setMessage(student.getMessage() + 1);
            userMapper.updateByPrimaryKey(student);
        }

    }

    public void studentReply(Integer subjectId, String reply) {
        KeSubject keSubject = keSubjectMapper.selectByPrimaryKey(subjectId);
        KeReply keReply = new KeReply();
        keReply.setSubjectId(subjectId);
        keReply.setReply(reply);
        keReplyMapper.insert(keReply);
        KeUser teacher = userMapper.selectByPrimaryKey(keSubject.getTeacherId());
        teacher.setMessage(teacher.getMessage() + 1);
        userMapper.updateByPrimaryKey(teacher);
    }
}
