package com.bandit.controller;

import com.bandit.entity.TeacherStudent;
import com.bandit.entity.User;
import com.bandit.service.TeacherStudentService;
import com.bandit.utils.JWTComponent;
import com.bandit.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Bandit
 * @createTime 2022/6/16 21:45
 */
@RestController
@RequestMapping("/api/student")
@Slf4j
public class StudentController {


    private final TeacherStudentService teacherStudentService;

    public StudentController(TeacherStudentService teacherStudentService, JWTComponent jwtComponent) {
        this.teacherStudentService = teacherStudentService;
    }

    @PostMapping("/choose")
    public ResultVO chooseTeacherHandler(@RequestBody TeacherStudent teacherStudent, HttpServletRequest request) {
        Long uid = (Long) request.getAttribute("uid");
        Long tid = teacherStudent.getTid();
        teacherStudentService.chooseTeacher(uid, tid);
        return new ResultVO(200,"选择成功", Map.of());
    }

    @GetMapping("/chosenTeacher")
    public ResultVO getChosenTeacher(HttpServletRequest request) {
        Long sid = (Long) request.getAttribute("uid");
        User user = teacherStudentService.getMentorBySid(sid);
        if (user != null)
            return new ResultVO(200,"查询成功",Map.of("chosenTeacher",user));
        else return new ResultVO(200,"并未查到选择老师",Map.of());
    }

    @PostMapping("/deleteTeacher")
    public ResultVO deleteTeacher(@RequestBody TeacherStudent teacherStudent, HttpServletRequest request) {
        Long sid = (Long) request.getAttribute("uid");
        teacherStudent.setSid(sid);
        teacherStudentService.deleteTeacherStudent(teacherStudent);
        return new ResultVO(200,"退选成功",Map.of());
    }

}
