package com.skurski.gwt.demo.dao;

import com.skurski.gwt.demo.dao.abstraction.AbstractDao;
import com.skurski.gwt.demo.dao.abstraction.CategoryDao;
import com.skurski.gwt.demo.model.Category;
import org.springframework.stereotype.Repository;


/**
 * Created by pskurski on 4/5/2016.
 */
@Repository
public class CategoryDaoImpl extends AbstractDao<Category> implements CategoryDao<Category> {

}
