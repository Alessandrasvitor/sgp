package com.sansyro.sgpspring.constants;

import lombok.Getter;

@Getter
public enum FunctionalityEnum {

    BASIC("home"),
    COURSE("course"),
    INSTITUITION("instituition"),
    USER("user");

    private String page;

    FunctionalityEnum(String page) {
        this.page = page;
    }

}
