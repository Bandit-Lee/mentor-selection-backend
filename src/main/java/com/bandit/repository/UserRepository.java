package com.bandit.repository;

import com.bandit.entity.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author Bandit
 * @createTime 2022/6/16 19:34
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByNumber(String number);

    User findUserById(Long id);

    @Query("select * from user where id in (:ids)")
    List<User> findUsersByIds(List<Long> ids);

    @Query("select * from user where role=2")
    List<User> findTeachers();

    @Query("select * from user where role=1")
    List<User> findStudents();
}
