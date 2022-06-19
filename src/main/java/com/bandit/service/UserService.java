package com.bandit.service;

import com.bandit.dto.UserDTO;
import com.bandit.entity.User;
import com.bandit.exception.XException;
import com.bandit.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Bandit
 * @createTime 2022/6/16 20:24
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByNumber(String number) {
        return userRepository.findByNumber(number);
    }

    public User getUserById(Long id) {return  userRepository.findUserById(id);}

    public List<User> getStudentList() {
        return userRepository.findStudents();
    }

    public List<User> getTeacherList() {
        return userRepository.findTeachers();
    }

    @Transactional(rollbackFor = Exception.class)
    public User modifyPassword(Long uid, UserDTO userDTO) {
        User user = userRepository.findUserById(uid);
        if (userDTO.getOldPassword().equals(user.getPassword())) {
            user.setPassword(userDTO.getNewPassword());
            return userRepository.save(user);
        }
        throw new XException(401, "密码校验错误！");
    }

}
