package com.bandit.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

/**
 * @author Bandit
 * @createTime 2022/6/16 19:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherStudent {

    @Id
    @CreatedBy
    private Long id;
    private Long tid;
    private Long sid;
    @CreatedDate
    private LocalDateTime createTime;
    @LastModifiedDate
    private LocalDateTime updateTime;
}
