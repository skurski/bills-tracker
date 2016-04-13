package com.skurski.gwt.demo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by pskurski on 3/23/2016.
 */
@Entity
@Table(name="expense")
public class Expense implements Serializable {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="title")
    private String title;

    @Column(name="amount")
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name="category_id", nullable=false)
    private Category category;

    @Column(name="shortDate")
    private String shortDate;

    public Expense() {}

    public Expense(String title, BigDecimal amount, Category category, String shortDate) {
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.shortDate = shortDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getShortDate() {
        return shortDate;
    }

    public void setShortDate(String shortDate) {
        this.shortDate = shortDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Expense expense = (Expense) o;

        if (id != expense.id) return false;
        if (!amount.equals(expense.amount)) return false;
        if (!shortDate.equals(expense.shortDate)) return false;
        if (!title.equals(expense.title)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + title.hashCode();
        result = 31 * result + amount.hashCode();
        result = 31 * result + shortDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", amount=" + amount +
                ", shortDate='" + shortDate + '\'' +
                '}';
    }
}
