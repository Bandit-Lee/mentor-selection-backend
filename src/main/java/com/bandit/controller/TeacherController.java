package com.bandit.controller;

import com.bandit.dto.TeacherDTO;
import com.bandit.entity.Teacher;
import com.bandit.entity.TeacherStudent;
import com.bandit.service.TeacherService;
import com.bandit.service.TeacherStudentService;
import com.bandit.vo.ResultVO;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Bandit
 * @createTime 2022/6/16 21:45
 */
@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    private final TeacherStudentService teacherStudentService;

    private final TeacherService teacherService;

    public TeacherController(TeacherStudentService teacherStudentService, TeacherService teacherService) {
        this.teacherStudentService = teacherStudentService;
        this.teacherService = teacherService;
    }

    @GetMapping("/list")
    public ResultVO list() {
        List<TeacherDTO> teacherList = teacherService.getTeacherList();
        return new ResultVO(200, "查询成功", Map.of("teacherList",teacherList));
    }

    @GetMapping("/info")
    public ResultVO info(HttpServletRequest request) {
        Long tid = (Long) request.getAttribute("uid");
        TeacherDTO teacherInfo = teacherService.getTeacherInfo(tid);
        return new ResultVO(200, "查询成功", Map.of("teacher",teacherInfo));
    }

    @PostMapping("/deleteStudent")
    public ResultVO deleteTeacherStudent(@RequestBody TeacherStudent teacherStudent, HttpServletRequest request) {
        Long tid = (Long) request.getAttribute("uid");
        teacherStudent.setTid(tid);
        teacherStudentService.deleteTeacherStudent(teacherStudent);
        return new ResultVO(200,"删除成功",Map.of());
    }

    @PostMapping("/limitChange")
    public ResultVO changeLimit(@RequestBody Teacher teacher, HttpServletRequest request) {
        Integer limit = teacher.getLimit();
        Long tid = (Long) request.getAttribute("uid");
        Teacher t = teacherService.updateTeacherLimit(tid, limit);
        return new ResultVO(200,"更新成功",Map.of("teacher",t));
    }

}
