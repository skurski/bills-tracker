package com.skurski.gwt.demo.dao.abstraction;

import java.util.List;

/**
 * Created by pskurski on 4/6/2016.
 */
public interface Dao<T> {

    List<T> getAll();

    T getOneById(int id);

    T getOneByColumn(String column, String value);

    List<T> getByRange(int start, int end);

    List<T> getByRangeAndChoosenColumnId(String column, int value, int start, int end);

    int getRowsNumber();

    int getRowsNumberByColumn(String column, int value);

    void saveOne(T object);

    void saveAll(List<T> records);

    void delete(List<T> records);
}
