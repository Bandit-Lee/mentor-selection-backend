package com.bandit.dto;

import com.bandit.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Bandit
 * @createTime 2022/6/16 22:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherDTO {

    private Long tid;
    private String name;
    private Integer limit;
    private List<User> students;

}
