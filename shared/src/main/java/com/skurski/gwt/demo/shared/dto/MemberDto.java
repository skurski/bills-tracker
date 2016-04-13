package com.skurski.gwt.demo.shared.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by pskurski on 4/6/2016.
 */
public class MemberDto {

    private int id;
    private String name;
    private String lastname;
    private String email;
    private String password;
    private List<CategoryDto> categories;

    @JsonCreator
    public MemberDto(@JsonProperty("name") String name,
                     @JsonProperty("lastname") String lastname,
                     @JsonProperty("email") String email,
                     @JsonProperty("password") String password) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    @JsonCreator
    public MemberDto(@JsonProperty("id") int id,
                     @JsonProperty("name") String name,
                     @JsonProperty("lastname") String lastname,
                     @JsonProperty("email") String email,
                     @JsonProperty("password") String password) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    @JsonCreator
    public MemberDto(@JsonProperty("id") int id,
                     @JsonProperty("name") String name,
                     @JsonProperty("lastname") String lastname,
                     @JsonProperty("email") String email,
                     @JsonProperty("password") String password,
                     @JsonProperty("categories") List<CategoryDto> categories) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.categories = categories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }
}
