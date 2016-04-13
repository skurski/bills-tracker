package com.skurski.gwt.demo.dao;

import com.skurski.gwt.demo.config.SpringRootConfigTest;
import com.skurski.gwt.demo.dao.abstraction.CategoryDao;
import com.skurski.gwt.demo.model.Category;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by pskurski on 4/5/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringRootConfigTest.class, loader = AnnotationConfigContextLoader.class)
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class
})
@ActiveProfiles("testing")
public class CategoryDaoImplTest {

    @Autowired
    private CategoryDao categoryDao;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    @Transactional
    public void getAllTest() throws Exception {
        Category category = new Category("Food");
        category.setId(1);

        List<Category> categories = categoryDao.getAll();

        Assert.assertEquals("Object retrieved from db is empty", categories.isEmpty(), false);
        Assert.assertEquals("Category retrieved from db is not valid", categories.get(0), category);

    }
}

