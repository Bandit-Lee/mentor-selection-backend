package com.bandit.service;

import com.bandit.entity.Teacher;
import com.bandit.entity.TeacherStudent;
import com.bandit.entity.User;
import com.bandit.exception.XException;
import com.bandit.repository.TeacherRepository;
import com.bandit.repository.TeacherStudentRepository;
import com.bandit.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Bandit
 * @createTime 2022/6/16 20:32
 */
@Service
@Slf4j
public class TeacherStudentService {

    private final TeacherStudentRepository teacherStudentRepository;

    private final TeacherRepository teacherRepository;

    private final UserRepository userRepository;

    public TeacherStudentService(TeacherStudentRepository teacherStudentRepository, TeacherRepository teacherRepository, UserRepository userRepository) {
        this.teacherStudentRepository = teacherStudentRepository;
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
    }

    /**
     * 老师获取选自己课的学生
     * @param tid
     * @return
     */
    public List<User> getOwnedStudents(Long tid) {
        List<TeacherStudent> teacherStudentList = teacherStudentRepository.findAllByTid(tid);
        if (teacherStudentList.size() != 0) {
            List<Long> sids = teacherStudentList
                    .stream()
                    .map(TeacherStudent::getSid)
                    .collect(Collectors.toList());
            return userRepository.findUsersByIds(sids);
        }
        return null;
    }

    /**
     * 学生获取自己已选的老师
     * @param tid
     * @return
     */
    public User getMentorByTid(Long tid) {
        return userRepository.findUserById(tid);
    }

    /**
     * 通过学生的sid查到老师的tid
     * @param sid
     * @return
     */
    public User getMentorBySid(Long sid) {
        return teacherStudentRepository.findTeacherBySid(sid);
    }

    @Transactional(rollbackFor = Exception.class)
    public void chooseTeacher(Long sid, Long tid) {
        Teacher teacher = teacherRepository.queryTeacherByTid(tid);
        TeacherStudent tsBySid = teacherStudentRepository.findBySid(sid);
        if (tsBySid != null) {
            throw new XException(401, "学生已选择导师！");
        }
        List<TeacherStudent> teacherStudentList = teacherStudentRepository.findAllByTid(tid);
        if (teacherStudentList.size() + 1 > teacher.getLimit()) {
            throw new XException(401, "老师达到选择上限！");
        } else {
            TeacherStudent teacherStudent = TeacherStudent.builder().sid(sid).tid(tid).build();
            teacherStudentRepository.save(teacherStudent);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteTeacherStudent(TeacherStudent teacherStudent) {
        Long tid = teacherStudent.getTid();
        Long sid = teacherStudent.getSid();
        TeacherStudent ts = teacherStudentRepository.findBySidAndTid(sid, tid);
        teacherStudentRepository.delete(ts);
    }

}
