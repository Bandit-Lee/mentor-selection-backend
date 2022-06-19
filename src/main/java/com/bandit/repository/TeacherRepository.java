package com.bandit.repository;

import com.bandit.entity.Teacher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Bandit
 * @createTime 2022/6/16 19:42
 */
@Repository
public interface TeacherRepository extends CrudRepository<Teacher, Long> {

    Teacher queryTeacherByTid(Long tid);

}
