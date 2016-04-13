package com.skurski.gwt.demo.shared.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by pskurski on 4/6/2016.
 */
public class CategoryDto {

    private int id;
    private String name;
    private MemberDto memberDto;

    @JsonCreator
    public CategoryDto(@JsonProperty("id") int id,
                       @JsonProperty("name") String name,
                       @JsonProperty("member") MemberDto memberDto) {
        this.id = id;
        this.name = name;
        this.memberDto = memberDto;
    }

    @JsonCreator
    public CategoryDto(@JsonProperty("name") String name,
                       @JsonProperty("member") MemberDto memberDto) {
        this.name = name;
        this.memberDto = memberDto;
    }

    @JsonCreator
    public CategoryDto(@JsonProperty("id") int id,
                       @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
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

    public MemberDto getMemberDto() {
        return memberDto;
    }

    public void setMemberDto(MemberDto memberDto) {
        this.memberDto = memberDto;
    }

}
