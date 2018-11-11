package com.yuri.ynweb_kj.controller;

import com.yuri.ynweb_kj.dto.ProgressDto;
import com.yuri.ynweb_kj.dto.SubjectDto;
import com.yuri.ynweb_kj.dto.UCenterDto;
import com.yuri.ynweb_kj.dto.UserSimpleDto;
import com.yuri.ynweb_kj.pojo.*;
import com.yuri.ynweb_kj.service.ContentService;
import com.yuri.ynweb_kj.service.UserService;
import com.yuri.ynweb_kj.utils.*;
import com.yuri.ynweb_kj.vo.BaseJsonResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = {"/userapi"})
public class UserController {
    private Logger logger = LoggerFactory.getLogger("FILE_LOG");

    @Autowired
    private UserService userService;
    @Autowired
    private ContentService contentService;

    /**
     * 登录接口
     *
     * @param userName 用户名
     * @param password 密码
     * @return BaseJsonResultVO
     */
    @RequestMapping(value = "/front/login", method = RequestMethod.POST)
    public BaseJsonResultVO login(@RequestParam(value = "username") String userName, @RequestParam(value = "password") String password) {
        KeUser user = userService.findUser(userName, password);
        if (user != null) {
            if (user.getType().equals(EnumUserType.STUDENT.value())) {
                user.setLastLogin(new Date());
                //更新学生最后登录时间
                userService.updateUser(user);
            }
            BaseJsonResultVO vo = new BaseJsonResultVO();
            UserSimpleDto resultDto = new UserSimpleDto();
            resultDto.setId(user.getId());
            resultDto.setName(user.getName());
            vo.setCode(EnumResCode.SUCCESSFUL.value());
            vo.setData(resultDto);
            vo.setMessage("ok");
            return vo;
        } else {
            BaseJsonResultVO vo = new BaseJsonResultVO();
            vo.setCode(EnumResCode.SERVER_ERROR.value());
            vo.setMessage("用户不存在");
            return vo;
        }
    }

    /**
     * 查找关注老师的学生列表
     *
     * @param teacherId 老师id
     * @return BaseJsonResultVO
     */
    @RequestMapping(value = "/front/teacher/follow", method = RequestMethod.POST)
    public BaseJsonResultVO followTeacher(@RequestParam(value = "teacherid") Integer teacherId) {
        List<UserSimpleDto> studentList = userService.teacherFollow(teacherId);
        BaseJsonResultVO vo = new BaseJsonResultVO();
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setData(studentList);
        vo.setMessage("ok");
        return vo;
    }

    /**
     * 学生关注老师
     *
     * @param studentId 学生id
     * @return
     */
    @RequestMapping(value = "/front/student/tofollow", method = RequestMethod.POST)
    public BaseJsonResultVO toFollowTeacher(@RequestParam(value = "studentid") Integer studentId, @RequestParam(value = "teachername") String teacherName) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        KeUser teacher = userService.findUserByName(teacherName);
        KeUser student = userService.findUserById(studentId);
        if (teacher == null) {
            vo.setCode(EnumResCode.SERVER_ERROR.value());
            vo.setMessage("系统无该名老师，不能关注");
            return vo;
        }
        if (teacher.getSchoolId() != null && teacher.getSchoolId() != student.getSchoolId()) {
            vo.setCode(EnumResCode.SERVER_ERROR.value());
            vo.setMessage("不是同校，不能关注");
            return vo;
        }
        KeFollow follow = userService.findFollow(studentId, teacher.getId());
        if (follow != null) {
            vo.setCode(EnumResCode.SERVER_ERROR.value());
            vo.setMessage("已经关注");
            return vo;
        } else {
            userService.follow(studentId, teacher.getId());
            vo.setCode(EnumResCode.SUCCESSFUL.value());
            vo.setMessage("ok");
            return vo;
        }
    }

    /**
     * 查找学生关注的老师列表
     *
     * @param studentId
     * @return
     */
    @RequestMapping(value = "/front/student/follow", method = RequestMethod.POST)
    public BaseJsonResultVO followStudent(@RequestParam(value = "studentid") Integer studentId) {
        List<UserSimpleDto> teacherList = userService.studentFollow(studentId);
        if (teacherList != null && !teacherList.isEmpty()) {
            BaseJsonResultVO vo = new BaseJsonResultVO();
            vo.setCode(EnumResCode.SUCCESSFUL.value());
            vo.setData(teacherList);
            vo.setMessage("ok");
            return vo;
        } else {
            BaseJsonResultVO vo = new BaseJsonResultVO();
            vo.setCode(EnumResCode.SERVER_ERROR.value());
            vo.setMessage("ok");
            return vo;
        }
    }


    /**
     * 查询个人未读消息数
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/front/message", method = RequestMethod.POST)
    public BaseJsonResultVO message(@RequestParam(value = "userid") Integer userId) {
        KeUser teacher = userService.findUserById(userId);
        if (teacher != null) {
            BaseJsonResultVO vo = new BaseJsonResultVO();
            vo.setCode(EnumResCode.SUCCESSFUL.value());
            vo.setData(teacher.getMessage());
            vo.setMessage("ok");
            return vo;
        } else {
            BaseJsonResultVO vo = new BaseJsonResultVO();
            vo.setCode(EnumResCode.SERVER_ERROR.value());
            vo.setMessage("用户不存在");
            return vo;
        }
    }

    /**
     * 学生签到
     *
     * @param studentId 学生id
     * @return
     */
    @RequestMapping(value = "/front/student/signin", method = RequestMethod.POST)
    public BaseJsonResultVO singin(@RequestParam(value = "studentid") Integer studentId) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        KeUser student = userService.findUserById(studentId);
        if (student != null) {
            KeProgress progress = userService.getSignin(studentId, TimeUtils.makeYMDStringFormat(new Date()));
            if (progress != null) {
                vo.setCode(EnumResCode.SERVER_ERROR.value());
                vo.setMessage("重复签到");
                return vo;
            }
            userService.signin(studentId);
            student.setSigninNum(student.getSigninNum() + 1);
            userService.updateUser(student);
            userService.updateProgress(student, EnumCreditType.SIGNIN.value(), 0, 0);

            vo.setCode(EnumResCode.SUCCESSFUL.value());
            vo.setMessage("ok");
            return vo;
        } else {
            vo.setCode(EnumResCode.SERVER_ERROR.value());
            vo.setMessage("用户不存在");
            return vo;
        }
    }

    /**
     * 学生个人中心
     *
     * @param studentId
     * @return
     */
    @RequestMapping(value = "/front/student/ucenter", method = RequestMethod.POST)
    public BaseJsonResultVO ucenter(@RequestParam(value = "studentid") Integer studentId) {
        KeUser student = userService.findUserById(studentId);
        if (student != null) {
            List<KeSignin> signinList = userService.findSignin(studentId);
            List<String> resultList = new LinkedList<>();
            for (int i = 0; i < signinList.size(); i++) {
                String signinString = TimeUtils.makeYMDStringFormat(signinList.get(i).getDate());
                resultList.add(signinString);
            }
            UCenterDto dto = new UCenterDto();
            dto.setSignin(resultList);
            dto.setCredit(student.getCredit());
            dto.setEmail(student.getEmail());
            dto.setGender(student.getGender() == EnumGender.MALE.value() ? "男" : "女");
            dto.setLastLogin(TimeUtils.makeYMDHMSStringFormat(student.getLastLogin()));
            dto.setMajor(student.getMajor());
            dto.setSchool(student.getSchool());
            Integer contentDoneCount = contentService.getContentDoneCountByStudentId(studentId);
            Integer contentCount = contentService.getContentCount(EnumLevel.LEVEL3.value());
            dto.setProcess((int) Math.floor(contentDoneCount * 100 / contentCount));
            dto.setPortrait(student.getPortrait());
            dto.setName(student.getName());
            dto.setSigninNum(student.getSigninNum());
            dto.setSignin(resultList);
            BaseJsonResultVO vo = new BaseJsonResultVO();
            vo.setCode(EnumResCode.SUCCESSFUL.value());
            vo.setData(dto);
            vo.setMessage("ok");
            return vo;
        } else {
            BaseJsonResultVO vo = new BaseJsonResultVO();
            vo.setCode(EnumResCode.SERVER_ERROR.value());
            vo.setMessage("用户不存在");
            return vo;
        }
    }

    /**
     * 学习进度数据统计
     *
     * @param studentId 学生id
     * @param type      1-今日，2-近7日，3-近30日，4-全部
     * @return
     */
    @RequestMapping(value = "/front/student/progress_stat", method = RequestMethod.POST)
    public BaseJsonResultVO progressStat(@RequestParam(value = "studentid") Integer studentId, @RequestParam(value = "type") Integer type) {
        List<KeProgress> list = userService.findProgressList(studentId, type);
        List<String> dateList = new LinkedList<>();
        ProgressDto result = new ProgressDto();
        for (int i = 0; i < list.size(); i++) {
            dateList.add(TimeUtils.makeYMDStringFormat(list.get(i).getDate()));
            list.get(i).setDate(null);
        }
        result.setDate(dateList);
        result.setProgress(list);
        BaseJsonResultVO vo = new BaseJsonResultVO();
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setData(result);
        vo.setMessage("ok");
        return vo;
    }

    /**
     * 学习进度个人情况
     *
     * @param studentId 学生id
     * @return BaseJsonResultVO
     */
    @RequestMapping(value = "/front/student/progess_user", method = RequestMethod.POST)
    public BaseJsonResultVO progessUser(@RequestParam(value = "studentid") Integer studentId) {
        KeUser student = userService.findUserById(studentId);
        Integer testCount = contentService.getAllTestCount();
        List<KeTestDone> keTestDoneList = contentService.getTestDoneByStudentId(studentId);
        Integer right = 0;
        Integer wrong = 0;
        for (KeTestDone keTestDone : keTestDoneList) {
            if (EnumIsAnswer.YES.value() == keTestDone.getIsRight()) {
                right += 1;
            } else {
                wrong += 1;
            }
        }
        ProgressDto result = new ProgressDto();
        result.setExerciseAll(testCount);
        result.setExerciseDone(keTestDoneList.size());
        result.setExerciseRight(right);
        result.setExerciseWill(testCount - keTestDoneList.size());
        result.setExerciseWrong(wrong);
        result.setDoneRate(result.getExerciseAll() == 0 ? 0 : (int) Math.floor(result.getExerciseDone() * 100 / result.getExerciseAll()));
        result.setRightRate(result.getExerciseDone() == 0 ? 0 : (int) Math.floor(result.getExerciseRight() * 100 / result.getExerciseDone()));
        BaseJsonResultVO vo = new BaseJsonResultVO();
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setData(result);
        vo.setMessage("ok");
        return vo;
    }

    /**
     * 老师向学生提问
     *
     * @param teacherId  老师id
     * @param studentIds 学生id列表
     * @param question   问题
     * @return
     */
    @RequestMapping(value = "/front/teacher/tosubject", method = RequestMethod.POST)
    public BaseJsonResultVO toSubject(@RequestParam(value = "teacherid") Integer teacherId, @RequestParam(value = "studentids") String studentIds, @RequestParam(value = "question") String question) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        if (StringUtils.isEmpty(question)) {
            vo.setCode(EnumResCode.SERVER_ERROR.value());
            vo.setMessage("问题不能为空");
            return vo;
        }
        List<String> studentIdStrings = Arrays.asList(studentIds.split(","));
        List<Integer> studentIdList = studentIdStrings.stream().map(Integer::parseInt).collect(Collectors.toList());
        KeUser teacher = userService.findUserById(teacherId);
        userService.teacherToSubject(teacher, studentIdList, question);

        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    /**
     * 学生回答老师提问
     *
     * @param subjectId 问题id
     * @param reply     回答
     * @return
     */
    @RequestMapping(value = "/front/student/reply", method = RequestMethod.POST)
    public BaseJsonResultVO reply(@RequestParam(value = "studentid") Integer studentId, @RequestParam(value = "subjectid") Integer subjectId, @RequestParam(value = "reply") String reply) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        if (StringUtils.isEmpty(reply)) {
            vo.setCode(EnumResCode.SERVER_ERROR.value());
            vo.setMessage("回答不能为空");
            return vo;
        }
        if (userService.getReplyBySubjectId(subjectId) != null) {
            vo.setCode(EnumResCode.SERVER_ERROR.value());
            vo.setMessage("此问题已有回答");
            return vo;
        }
        KeSubject subject = userService.getSubjectById(subjectId);
        if (studentId != subject.getStudentId()) {
            vo.setCode(EnumResCode.SERVER_ERROR.value());
            vo.setMessage("学生id非法");
            return vo;
        }
        userService.studentReply(studentId, subject.getTeacherId(), subjectId, reply);
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    /**
     * 学生通过未读消息查看互动列表
     *
     * @param studentId 学生id
     * @return
     */
    @RequestMapping(value = "/front/student/message/list", method = RequestMethod.POST)
    public BaseJsonResultVO studentMessageList(@RequestParam(value = "studentid") Integer studentId) {
        Integer nextTeacherId = userService.getNextUnreadSubjectTeacherId();
        List<KeSubject> subjectList = userService.studentMessageList(studentId, nextTeacherId, EnumIsRead.UNREAD.value());
        List<SubjectDto> resultList = new LinkedList<>();
        for (KeSubject subject : subjectList) {
            SubjectDto dto = new SubjectDto();
            dto.setSubjectId(subject.getId());
            dto.setSubjectContent(subject.getContent());
            dto.setTeacherName(subject.getTeacherName());
            dto.setTeacherId(subject.getTeacherId());
            KeReply reply = userService.getStudentReply(subject.getId());
            dto.setReply(reply == null ? null : reply.getReply());
            dto.setReplyId(reply == null ? null : reply.getId());
            resultList.add(dto);
        }
        userService.updateSubjectReadable(studentId, nextTeacherId);
        BaseJsonResultVO vo = new BaseJsonResultVO();
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        vo.setData(resultList);
        return vo;
    }

    /**
     * 老师通过未读消息查看师生互动信息
     *
     * @param teacherId 老师id
     * @param studentId 学生id
     * @return BaseJsonResultVO
     */
    @RequestMapping(value = "/front/teacher/message/list", method = RequestMethod.POST)
    public BaseJsonResultVO teacherMessageList(@RequestParam(value = "teacherid") Integer teacherId, @RequestParam(value = "studentid") Integer studentId) {
        List<Integer> subjectIdList = userService.teacherMessageList(studentId, teacherId, EnumIsRead.UNREAD.value());
        List<SubjectDto> resultList = new LinkedList<>();
        for (Integer id : subjectIdList) {
            KeSubject subject = userService.getSubjectById(id);
            SubjectDto dto = new SubjectDto();
            dto.setSubjectId(id);
            dto.setStudentName(subject.getStudentName());
            dto.setSubjectContent(subject.getContent());
            dto.setTeacherName(subject.getTeacherName());
            dto.setTeacherId(subject.getTeacherId());
            KeReply reply = userService.getStudentReply(subject.getId());
            dto.setReply(reply == null ? null : reply.getReply());
            dto.setReplyId(reply == null ? null : reply.getId());
            resultList.add(dto);
        }
        userService.updateReplyIsRead(teacherId, studentId, EnumIsRead.READED.value());
        BaseJsonResultVO vo = new BaseJsonResultVO();
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        vo.setData(resultList);
        return vo;
    }

    /**
     * 学生查看互动列表
     *
     * @param studentId 学生id
     * @param teacherId 老师id
     * @param type      1-上一个，2-下一个
     * @return BaseJsonResultVO
     */
    @RequestMapping(value = "/front/student/subject/list", method = RequestMethod.POST)
    public BaseJsonResultVO studentSubjectList(@RequestParam(value = "studentid") Integer studentId, @RequestParam(value = "teacherid", required = false) Integer teacherId,
                                               @RequestParam(value = "type", required = false) Integer type) {
        BaseJsonResultVO vo = new BaseJsonResultVO();

        if (type != null && type != 1 && type != 2) {
            vo.setCode(EnumResCode.SERVER_ERROR.value());
            vo.setMessage("参数非法");
            return vo;
        }
        Integer nextTeacherId = userService.getNextSubjectTeacherId(type, teacherId);
        List<KeSubject> subjectList = userService.studentMessageList(studentId, nextTeacherId, null);
        List<SubjectDto> resultList = new LinkedList<>();
        for (KeSubject subject : subjectList) {
            SubjectDto dto = new SubjectDto();
            dto.setSubjectId(subject.getId());
            dto.setSubjectContent(subject.getContent());
            dto.setTeacherName(subject.getTeacherName());
            dto.setTeacherId(subject.getTeacherId());
            KeReply reply = userService.getStudentReply(subject.getId());
            dto.setReply(reply == null ? null : reply.getReply());
            dto.setReplyId(reply == null ? null : reply.getId());
            resultList.add(dto);
        }
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        vo.setData(resultList);
        return vo;
    }

    /**
     * 老师查看互动列表
     *
     * @param studentId 学生id
     * @param teacherId 老师id
     * @return BaseJsonResultVO
     */
    @RequestMapping(value = "/front/teacher/subject/list", method = RequestMethod.POST)
    public BaseJsonResultVO teacherSubjectList(@RequestParam(value = "studentid") Integer studentId, @RequestParam(value = "teacherid") Integer teacherId) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        List<KeSubject> subjectList = userService.studentMessageList(studentId, teacherId, null);
        List<SubjectDto> resultList = new LinkedList<>();
        for (KeSubject subject : subjectList) {
            SubjectDto dto = new SubjectDto();
            dto.setSubjectId(subject.getId());
            dto.setSubjectContent(subject.getContent());
            dto.setTeacherName(subject.getTeacherName());
            dto.setStudentName(subject.getStudentName());
            dto.setTeacherId(subject.getTeacherId());
            KeReply reply = userService.getStudentReply(subject.getId());
            dto.setReply(reply == null ? null : reply.getReply());
            dto.setReplyId(reply == null ? null : reply.getId());
            resultList.add(dto);
        }
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        vo.setData(resultList);
        return vo;
    }


}
