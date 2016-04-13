package com.skurski.gwt.demo.dao.abstraction;


/**
 * Created by pskurski on 4/8/2016.
 */
public interface MemberDao<T> extends Dao<T> {

    public T getOneByEmailAndPassword(String email, String password);
}
