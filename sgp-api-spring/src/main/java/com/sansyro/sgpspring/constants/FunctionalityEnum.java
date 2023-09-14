package com.sansyro.sgpspring.constants;

import lombok.Getter;

@Getter
public enum FunctionalityEnum {

    HOME("home"),
    COURSE("course"),
    INSTITUITION("instituition"),
    BET("bet"),
    USER("user");

    private String page;

    FunctionalityEnum(String page) {
        this.page = page;
    }

}
