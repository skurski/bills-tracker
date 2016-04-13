package com.skurski.gwt.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * Created by pskurski on 4/5/2016.
 */
@Entity
@Table(name="member")
public class Member implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @NotEmpty
    @Column(name="name")
    private String name;

    @NotEmpty
    @Column(name="lastname")
    private String lastname;

    @NotEmpty
    @Email
    @Column(name="email")
    private String email;

    @NotEmpty
    @Size(min=4, max=20)
    @Column(name="password")
    private String password;

    // mappedBy is the object name in Member class
    @JsonIgnore
    @OneToMany(mappedBy="member", cascade = CascadeType.MERGE,
            orphanRemoval = true, fetch=FetchType.EAGER)
    private List<Category> categories;

    public Member() {}

    public Member(String name, String lastname, String email, String password) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public Member(String name, String lastname, String email, String password, List<Category> categories) {
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Member member = (Member) o;

        if (id != member.id) return false;
        if (!email.equals(member.email)) return false;
        if (!lastname.equals(member.lastname)) return false;
        if (!name.equals(member.name)) return false;
        if (!password.equals(member.password)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + lastname.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password +
                '}';
    }
}
