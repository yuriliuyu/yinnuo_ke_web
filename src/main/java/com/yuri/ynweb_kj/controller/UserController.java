package com.yuri.ynweb_kj.controller;

import com.yuri.ynweb_kj.dto.ProgressDto;
import com.yuri.ynweb_kj.dto.UCenterDto;
import com.yuri.ynweb_kj.dto.UserSimpleDto;
import com.yuri.ynweb_kj.pojo.KeFollow;
import com.yuri.ynweb_kj.pojo.KeProgress;
import com.yuri.ynweb_kj.pojo.KeSignin;
import com.yuri.ynweb_kj.pojo.KeUser;
import com.yuri.ynweb_kj.service.UserService;
import com.yuri.ynweb_kj.utils.EnumGender;
import com.yuri.ynweb_kj.utils.EnumResCode;
import com.yuri.ynweb_kj.utils.EnumUserType;
import com.yuri.ynweb_kj.utils.TimeUtils;
import com.yuri.ynweb_kj.vo.BaseJsonResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(value = {"/userapi"})
public class UserController {
    private Logger logger = LoggerFactory.getLogger("FILE_LOG");

    @Autowired
    private UserService userService;

    /**
     * 登录接口
     * @param userName
     * @param password
     * @return
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
            vo.setCode(EnumResCode.SUCCESSFUL.value());
            user.setPassword(null);
            user.setCredit(null);
            user.setLastLogin(null);
            user.setSigninNum(null);
            vo.setData(user);
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
     * @param teacherId
     * @return
     */
    @RequestMapping(value = "/front/teacher/follow", method = RequestMethod.POST)
    public BaseJsonResultVO followTeacher(@RequestParam(value = "teacherid") Integer teacherId) {
        List<UserSimpleDto> studentList = userService.teacherFollow(teacherId);
        if (studentList != null && !studentList.isEmpty()) {
            BaseJsonResultVO vo = new BaseJsonResultVO();
            vo.setCode(EnumResCode.SUCCESSFUL.value());
            vo.setData(studentList);
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
     * 学生关注老师
     * @param studentId
     * @return
     */
    @RequestMapping(value = "/front/student/tofollow", method = RequestMethod.POST)
    public BaseJsonResultVO toFollowTeacher(@RequestParam(value = "studentid") Integer studentId, @RequestParam(value = "teachername") Integer teacherName) {
        KeUser teacher = userService.findUserByName(teacherName);
        KeFollow follow = userService.findFollow(studentId, teacher.getId());
        BaseJsonResultVO vo = new BaseJsonResultVO();
        if(follow != null){
            vo.setCode(EnumResCode.SERVER_ERROR.value());
            vo.setMessage("已经关注");
        }else{
            userService.follow(studentId, teacher.getId());
            vo.setCode(EnumResCode.SUCCESSFUL.value());
            vo.setMessage("ok");
        }
        return vo;
    }

    /**
     * 查找学生关注的老师列表
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
     * @param teacherId
     * @return
     */
    @RequestMapping(value = "/front/message", method = RequestMethod.POST)
    public BaseJsonResultVO message(@RequestParam(value = "teacherid") Integer teacherId) {
        KeUser teacher = userService.findUserById(teacherId);
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
     * @param studentId
     * @return
     */
    @RequestMapping(value = "/front/student/signin", method = RequestMethod.POST)
    public BaseJsonResultVO singin(@RequestParam(value = "studentid") Integer studentId) {
        KeUser student = userService.findUserById(studentId);
        if (student != null) {
            try {
                userService.signin(studentId);
                student.setSigninNum(student.getSigninNum() + 1);
                student.setCredit(student.getCredit() + 2);
                userService.updateUser(student);
            } catch (Exception e) {
                logger.error("重复签到:studentId:" + studentId);
            }
            BaseJsonResultVO vo = new BaseJsonResultVO();
            vo.setCode(EnumResCode.SUCCESSFUL.value());
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
     * 学生个人中心
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
            BigDecimal decimalProcess = new BigDecimal(student.getProcess());
            dto.setProcess(decimalProcess.divide(new BigDecimal(10)).toString());
            dto.setPortrait(student.getPortrait());
            dto.setName(student.getName());
            dto.setLoginTotal((int) Math.floor(student.getLoginTotal() / 60));
            dto.setSigninNum(student.getSigninNum());
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
     * @param studentId
     * @param type      1-今日，2-近7日，3-近30日，4-全部
     * @return
     */
    @RequestMapping(value = "/front/student/progess_stat", method = RequestMethod.POST)
    public BaseJsonResultVO progessStat(@RequestParam(value = "studentid") Integer studentId, @RequestParam(value = "type") Integer type) {
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
     * @param studentId
     * @return
     */
    @RequestMapping(value = "/front/student/progess_user", method = RequestMethod.POST)
    public BaseJsonResultVO progessUser(@RequestParam(value = "studentid") Integer studentId) {
        KeUser student = userService.findUserById(studentId);
        ProgressDto result = new ProgressDto();
        result.setExerciseAll(student.getExerciseAll());
        result.setExerciseDone(student.getExerciseDone());
        result.setExerciseRight(student.getExerciseRight());
        result.setExerciseWill(student.getExerciseAll() - student.getExerciseDone());
        result.setExerciseWrong(student.getExerciseDone() - student.getExerciseRight());
        result.setDoneRate(student.getExerciseAll() == 0 ? 0 : (int) Math.floor(student.getExerciseDone()*100 / student.getExerciseAll()));
        result.setRightRate(student.getExerciseDone() == 0 ? 0 : (int) Math.floor(student.getExerciseRight()*100 / student.getExerciseDone()));
        BaseJsonResultVO vo = new BaseJsonResultVO();
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setData(result);
        vo.setMessage("ok");
        return vo;
    }
}
