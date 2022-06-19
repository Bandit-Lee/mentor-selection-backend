package com.bandit.controller;

import com.bandit.entity.User;
import com.bandit.exception.XException;
import com.bandit.service.UserService;
import com.bandit.utils.JWTComponent;
import com.bandit.utils.Role;
import com.bandit.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Bandit
 * @createTime 2022/6/16 21:00
 */
@RequestMapping("/api/login")
@RestController
public class LoginController {

    private final UserService userService;

    private final JWTComponent jwtComponent;

    public LoginController(UserService userService, JWTComponent jwtComponent) {
        this.userService = userService;
        this.jwtComponent = jwtComponent;
    }

    @PostMapping("")
    public ResultVO postLogin(@RequestBody User user, HttpServletResponse response) {
        User u = userService.getUserByNumber(user.getNumber());
        if (u == null || !u.getPassword().equals(user.getPassword())) {
            throw new XException(401, "用户名密码错误！");
        }
        String code = "";
        for (Role r : Role.values()) {
            if (r.getId() == u.getRole()) {
                code = r.getName();
                break;
            }
        }
        response.addHeader("role", code);
        response.addHeader("token", jwtComponent.encode(Map.of("uid", u.getId(), "role", u.getRole())));
        return ResultVO.success(Map.of("user",u));
    }
}
