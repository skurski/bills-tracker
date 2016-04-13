package com.skurski.gwt.demo.webservice;

import com.skurski.gwt.demo.dao.abstraction.CategoryDao;
import com.skurski.gwt.demo.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by pskurski on 4/6/2016.
 */
@RestController
@RequestMapping("/json/categories")
@Transactional
public class CategoryRestController {

    private CategoryDao categoryDao;

    @Autowired
    public CategoryRestController(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @RequestMapping(value="/get-all", method = RequestMethod.GET, produces = "application/json")
    public List<Category> getAllCategories() {
        return categoryDao.getAll();
    }
}
