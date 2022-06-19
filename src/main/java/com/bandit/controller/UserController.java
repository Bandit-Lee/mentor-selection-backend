package com.bandit.controller;

import com.bandit.dto.UserDTO;
import com.bandit.entity.User;
import com.bandit.service.UserService;
import com.bandit.utils.JWTComponent;
import com.bandit.vo.ResultVO;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Bandit
 * @createTime 2022/6/16 22:42
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/modify")
    public ResultVO modifyPassword(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        Long uid = (Long) request.getAttribute("uid");
        User user = userService.modifyPassword(uid, userDTO);
        return new ResultVO(200,"密码修改成功, 请重新登录",Map.of("user",user));
    }

    @GetMapping("/list")
    public ResultVO listUser() {
        List<User> studentList = userService.getStudentList();
        List<User> teacherList = userService.getTeacherList();
        return new ResultVO(200,"查询成功",Map.of("studentList",studentList,"teacherList",teacherList));
    }

}
