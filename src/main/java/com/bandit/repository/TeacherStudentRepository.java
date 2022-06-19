package com.bandit.repository;

import com.bandit.entity.TeacherStudent;
import com.bandit.entity.User;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Bandit
 * @createTime 2022/6/16 20:20
 */
@Repository
public interface TeacherStudentRepository extends CrudRepository<TeacherStudent, Long> {

    @Query("select * from teacher_student")
    List<TeacherStudent> findAll();

    List<TeacherStudent> findAllByTid(Long tid);

    @Query("select u.id as id, u.name as name, u.password as password, u.number as number, u.role as role " +
            "from teacher_student ts " +
            "left join user u on ts.sid = u.id " +
            "where ts.tid = :tid")
    List<User> findStudentByTid(Long tid);

    @Query("select u.id as id, u.name as name , u.number as number " +
            "from teacher_student ts " +
            "left join user u on ts.tid = u.id " +
            "where ts.sid = :sid")
    User findTeacherBySid(Long sid);

    TeacherStudent findBySid(Long sid);


    @Query("select * from teacher_student " +
            "where sid = :sid and tid = :tid")
    TeacherStudent findBySidAndTid(Long sid, Long tid);

}
