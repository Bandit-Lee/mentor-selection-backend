package com.bandit.utils;

/**
 * @author Bandit
 * @createTime 2022/6/16 19:52
 */
public enum Role {
    STUDENT(1,"student"),
    TEACHER(2,"teacher");

    private final int id;
    private final String name;

    Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
