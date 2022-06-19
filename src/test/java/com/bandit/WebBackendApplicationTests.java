package com.bandit;

import com.bandit.entity.Teacher;
import com.bandit.repository.TeacherRepository;
import com.bandit.repository.TeacherStudentRepository;
import com.bandit.service.TeacherStudentService;
import com.bandit.utils.JWTComponent;
import com.bandit.entity.User;
import com.bandit.repository.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class WebBackendApplicationTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    TeacherStudentRepository teacherStudentRepository;

    @Autowired
    JWTComponent jwtComponent;

    @Autowired
    TeacherStudentService teacherStudentService;

    @Test
    //@Transactional(rollbackFor = Exception.class)
    @Rollback(value = false)
    void contextLoads() {
        User u0 = User.builder().name("李东升").number("2019214225").password("123456").role(1).build();
        User u1 = User.builder().name("小明").number("2019100000").password("123456").role(1).build();
        User u2 = User.builder().name("小张").number("2019100001").password("123456").role(1).build();
        User u3 = User.builder().name("小王").number("2019100002").password("123456").role(1).build();
        User u4 = User.builder().name("小刘").number("2019100003").password("123456").role(1).build();
        User u5 = User.builder().name("小唐").number("2019100004").password("123456").role(1).build();
        User u6 = User.builder().name("小李").number("2019100005").password("123456").role(1).build();
        List<User> list = Lists.list(u0, u1, u2, u3, u4, u5, u6);
        userRepository.saveAll(list);
    }

    @Test
    //@Transactional(rollbackFor = Exception.class)
    @Rollback(value = false)
    void contextLoads2() {
//        User u0 = User.builder().name("王波").number("1001").password("123456").role(2).build();
//        User u1 = User.builder().name("刘丹").number("1002").password("123456").role(2).build();
//        User u2 = User.builder().name("赵玉茗").number("1003").password("123456").role(2).build();
//        User u3 = User.builder().name("边继龙").number("1004").password("123456").role(2).build();
//        User u4 = User.builder().name("张锡英").number("1005").password("123456").role(2).build();
//        List<User> list = Lists.list(u0, u1, u2, u3, u4);
        List<User> teachers = userRepository.findTeachers();
        List<Teacher> teacherList = teachers.stream().map(user ->
             Teacher.builder().tid(user.getId()).limit(5).build()
        ).collect(Collectors.toList());
        teacherRepository.saveAll(teacherList);
        //userRepository.saveAll(list);
    }

    @Test
    void test() {
        List<Long> list = Lists.list(986962182760374272L, 986965253808791552L, 986965254073032704L);
        userRepository.findUsersByIds(list).forEach(System.out::println);
    }

    @Test
    void testChoose() {
        teacherStudentService.chooseTeacher(986965253087371264L, 986965979293360128L);
    }

}
