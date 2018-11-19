package com.yuri.ynweb_kj.service;

import com.sun.javafx.tk.FontLoader;
import com.yuri.ynweb_kj.dao.*;
import com.yuri.ynweb_kj.dto.UserSimpleDto;
import com.yuri.ynweb_kj.pojo.*;
import com.yuri.ynweb_kj.utils.EnumIsRead;
import com.yuri.ynweb_kj.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private Logger logger = LoggerFactory.getLogger("FILE_LOG");


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
    @Autowired
    KeTestMapper keTestMapper;
    @Autowired
    KeSchoolMapper keSchoolMapper;
    @Autowired
    KeUserMapper keUserMapper;

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
            Map map = new HashMap<>();
            map.put("studentId", followList.get(i).getStudentId());
            map.put("teacherId", teacherId);
            map.put("isRead", EnumIsRead.UNREAD.value());
            //TODO
            Integer unreadCount = keReplyMapper.getByTeacherIdAndStudentId(map);
            dto.setMessage(unreadCount);
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

    public KeUser findUserByName(String name) {
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
        List<KeFollow> followList = keFollowMapper.teacherFollow(teacher.getId());
        List<Integer> followStudentIdList = new LinkedList<>();
        for (KeFollow follow : followList) {
            followStudentIdList.add(follow.getStudentId());
        }
        for (Integer studentId : studentIdList) {
            if (!followStudentIdList.contains(studentId)) {
                logger.error("{}老师并未关注id为{}的学生", new Object[]{teacher.getId(), studentId});
                continue;
            }
            KeSubject keSubject = new KeSubject();
            keSubject.setContent(question);
            keSubject.setStudentId(studentId);
            keSubject.setTeacherId(teacher.getId());
            keSubject.setTeacherName(teacher.getName());
            keSubject.setIsRead(EnumIsRead.UNREAD.value());
            keSubject.setCreateTime(new Date());
            KeUser student = userMapper.selectByPrimaryKey(studentId);
            keSubject.setStudentName(student.getName());
            keSubjectMapper.insert(keSubject);
        }
    }

    public void studentReply(Integer studentId, Integer teacherId, Integer subjectId, String reply) {
        KeSubject keSubject = keSubjectMapper.selectByPrimaryKey(subjectId);
        KeReply keReply = new KeReply();
        keReply.setSubjectId(subjectId);
        keReply.setStudentId(studentId);
        keReply.setTeacherId(teacherId);
        keReply.setReply(reply);
        keReply.setIsRead(EnumIsRead.UNREAD.value());
        keReply.setCreateTime(new Date());
        keReplyMapper.insert(keReply);
    }

    public Integer getNextUnreadSubjectTeacherId() {
        return keSubjectMapper.getNextUnreadSubjectTeacherId();
    }

    public List<KeSubject> studentMessageList(Integer studentId, Integer teacherId, Integer isRead) {
        Map map = new HashMap<>();
        map.put("studentId", studentId);
        map.put("teacherId", teacherId);
        map.put("isRead", isRead);
        return keSubjectMapper.studentMessageList(map);
    }

    public KeReply getStudentReply(Integer subjectId) {
        return keReplyMapper.getBySubjectId(subjectId);
    }

    public void updateSubjectReadable(Integer studentId, Integer nextTeacherId) {
        Map map = new HashMap<>();
        map.put("studentId", studentId);
        map.put("nextTeacherId", nextTeacherId);
        keSubjectMapper.updateSubjectReadable(map);
    }

    public Integer getNextSubjectTeacherId(Integer type, Integer teacherId) {
        Map map = new HashMap<>();
        map.put("type", type);
        map.put("teacherId", teacherId);
        return keSubjectMapper.getNextSubjectTeacherId(map);
    }

    public void updateReplyIsRead(Integer teacherId, Integer studentId, Integer isRead) {
        Map map = new HashMap<>();
        map.put("teacherId", teacherId);
        map.put("studentId", studentId);
        map.put("isRead", isRead);
        keReplyMapper.updateReplyIsRead(map);
    }

    public List<Integer> teacherMessageList(Integer studentId, Integer teacherId, Integer isRead) {
        Map map = new HashMap<>();
        map.put("studentId", studentId);
        map.put("teacherId", teacherId);
        map.put("isRead", isRead);
        return keReplyMapper.teacherMessageList(map);
    }

    public KeSubject getSubjectById(Integer id) {
        return keSubjectMapper.selectByPrimaryKey(id);
    }

    public KeReply getReplyBySubjectId(Integer subjectId) {
        return keReplyMapper.getReplyBySubjectId(subjectId);

    }

    public void updateProgress(KeUser student, Integer credit, Integer test, Integer video) {
        KeProgress progress = getSignin(student.getId(), TimeUtils.makeYMDStringFormat(new Date()));
        if (progress == null) {
            progress = new KeProgress();
            progress.setDate(new Date());
            progress.setCreateTime(new Date());
            progress.setCredit(credit);
            progress.setVideo(video);
            progress.setTest(test);
            progress.setStudentId(student.getId());
            keProgressMapper.insert(progress);
        } else {
            progress.setCredit(progress.getCredit() + credit);
            progress.setTest(progress.getTest() + test);
            progress.setVideo(progress.getVideo() + video);
            keProgressMapper.updateByPrimaryKey(progress);
        }
        if(credit != null || credit != 0){
            student.setCredit(student.getCredit() + credit);
            updateUser(student);
        }
    }

    public KeProgress getSignin(Integer studentId, String date) {
        Map map = new HashMap<>();
        map.put("studentId", studentId);
        map.put("date", date);
        return keProgressMapper.findProgress(map);
    }

    public KeSchool getSchoolById(Integer id){
        return keSchoolMapper.selectByPrimaryKey(id);
    }

    public void insertUser(KeUser user) {
        keUserMapper.insert(user);
    }

    public Integer getStudentUnreadMessgaeNumByUserId(Integer studentId) {
        return keSubjectMapper.getStudentUnreadMessgaeNumByUserId(studentId);
    }

    public Integer getTeacherUnreadMessgaeNumByUserId(Integer teacherId) {
        return keReplyMapper.getTeacherUnreadMessgaeNumByUserId(teacherId);
    }
}
