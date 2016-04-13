package com.skurski.gwt.demo.shared.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gwt.view.client.ProvidesKey;

import java.math.BigDecimal;


/**
 * Created by PSkurski on 3/24/2016.
 */
public class ExpenseDto {

    private Integer id;
    private String title;
    private BigDecimal amount;
    private String shortDate;
    private CategoryDto category;

    @JsonCreator
    public ExpenseDto(@JsonProperty("id") Integer id,
                      @JsonProperty("title") String title,
                      @JsonProperty("amount") BigDecimal amount,
                      @JsonProperty("shortDate") String shortDate,
                      @JsonProperty("category") CategoryDto categoryDto) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.shortDate = shortDate;
        this.category = categoryDto;
    }

    @JsonCreator
    public ExpenseDto(@JsonProperty("title")  String title,
                      @JsonProperty("amount") BigDecimal amount,
                      @JsonProperty("shortDate") String shortDate,
                      @JsonProperty("category") CategoryDto category) {
        this.title = title;
        this.amount = amount;
        this.shortDate = shortDate;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getShortDate() {
        return shortDate;
    }

    public void setShortDate(String shortDate) {
        this.shortDate = shortDate;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }
}
