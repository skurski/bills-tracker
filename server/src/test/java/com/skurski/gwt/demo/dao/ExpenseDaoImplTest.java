package com.skurski.gwt.demo.dao;

import com.skurski.gwt.demo.config.SpringRootConfigTest;
import com.skurski.gwt.demo.dao.abstraction.CategoryDao;
import com.skurski.gwt.demo.dao.abstraction.ExpenseDao;
import com.skurski.gwt.demo.model.Category;
import com.skurski.gwt.demo.model.Expense;
import com.skurski.gwt.demo.model.Member;
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
import java.math.BigDecimal;
import java.util.ArrayList;
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
//@TestPropertySource(locations = "classpath:test.properties")
public class ExpenseDaoImplTest {

    @Autowired
    private ExpenseDao expenseDao;

    @Autowired
    private CategoryDao categoryDao;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    @Transactional
    public void testGetAll_ShouldReturnCollectionOfExpensesFromDB() throws Exception {
        Category category = new Category("Food");
        category.setId(1);
        Expense expense = new Expense("Kebab", new BigDecimal("11.22"), category, "2015-01-01");
        expense.setId(1);

        List<Expense> expensesFromDb = expenseDao.getAll();

        Assert.assertNotNull(expensesFromDb);
        Assert.assertFalse(expensesFromDb.isEmpty());
        Assert.assertEquals(expense, expensesFromDb.get(0));
    }

    @Test
    @Transactional
    public void testSaveOne_ShouldInsertExpenseToDB() {
        Member user = new Member("Peter", "Admin", "admin@admin.pl", "admin");
        user.setId(1);
        Category category = new Category("Some New Category");
        category.setMember(user);
        Expense expense = new Expense("Bugatti Veyron", new BigDecimal("44"), category, "2016-12-15");

        categoryDao.saveOne(category);
        expenseDao.saveOne(expense);

        List<Expense> actualExpenses = expenseDao.getAll();

        Assert.assertEquals(expense, actualExpenses.get(actualExpenses.size()-1));
    }

    @Test
    @Transactional
    public void testDelete_ShouldDeleteCollectionOfExpensesFromDB() throws Exception {
        Member user = new Member("Peter", "Admin", "admin@admin.pl", "admin");
        user.setId(1);
        Category cat = new Category("Food", user);
        cat.setId(1);
        Expense ex1 = new Expense("Kebab", new BigDecimal("11.22"), cat, "2015-01-01");
        ex1.setId(1);
        Expense ex2 = new Expense("Lasagne", new BigDecimal("55.44"), cat, "2015-01-01");
        ex2.setId(2);

        List<Expense> expensesToDel = new ArrayList<Expense>();
        expensesToDel.add(ex1);
        expensesToDel.add(ex2);

        expenseDao.delete(expensesToDel);

        List<Expense> actualExpenses = expenseDao.getAll();

        Assert.assertEquals("Number of records in db is invalid.", 18, actualExpenses.size());
        Assert.assertNotEquals(ex1, actualExpenses.get(0));
        Assert.assertNotEquals(ex2, actualExpenses.get(1));
    }
}

