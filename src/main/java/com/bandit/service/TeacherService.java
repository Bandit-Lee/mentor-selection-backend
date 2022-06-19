package com.bandit.service;

import com.bandit.dto.TeacherDTO;
import com.bandit.entity.Teacher;
import com.bandit.entity.User;
import com.bandit.repository.TeacherRepository;
import com.bandit.repository.TeacherStudentRepository;
import com.bandit.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Bandit
 * @createTime 2022/6/16 21:49
 */
@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    private final TeacherStudentRepository teacherStudentRepository;

    private final UserRepository userRepository;

    public TeacherService(TeacherRepository teacherRepository, TeacherStudentRepository teacherStudentRepository, UserRepository userRepository) {
        this.teacherRepository = teacherRepository;
        this.teacherStudentRepository = teacherStudentRepository;
        this.userRepository = userRepository;
    }

    public List<TeacherDTO> getTeacherList() {
        //先找出User表里所有User
        List<User> teachers = userRepository.findTeachers();
        //转成TeacherDTO
        return teachers.stream().map(user -> {
            //找出teacher的limit
            Teacher teacher = teacherRepository.queryTeacherByTid(user.getId());
            //找出该老师的所有student
            List<User> studentList = teacherStudentRepository.findStudentByTid(user.getId());
            return TeacherDTO.builder()
                    .tid(user.getId())
                    .name(user.getName())
                    .limit(teacher.getLimit())
                    .students(studentList)
                    .build();
        }).collect(Collectors.toList());
    }

    public TeacherDTO getTeacherInfo(Long tid) {
        User user = userRepository.findUserById(tid);
        Teacher teacher = teacherRepository.queryTeacherByTid(user.getId());
        List<User> studentList = teacherStudentRepository.findStudentByTid(user.getId());
        return TeacherDTO.builder()
                .tid(user.getId())
                .name(user.getName())
                .limit(teacher.getLimit())
                .students(studentList)
                .build();
    }

    public Teacher updateTeacherLimit(Long tid, Integer limit) {
        Teacher teacher = teacherRepository.queryTeacherByTid(tid);
        teacher.setLimit(limit);
        return teacherRepository.save(teacher);
    }
}
