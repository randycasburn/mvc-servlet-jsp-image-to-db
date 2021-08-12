package com.company.model;


import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Thing {

    private Long id;
    @NotBlank
    @Size(max = 40)
    private String name;
    private byte[] avatar;

    public Thing() {
    }

    public Thing(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public void setAvatar(byte[] bytes) {
        avatar = bytes;
    }

    public byte[] getAvatar() {
        return avatar;
    }
}
