package com.skurski.gwt.demo.dao;

import com.skurski.gwt.demo.config.SpringRootConfigTest;
import com.skurski.gwt.demo.dao.abstraction.MemberDao;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
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
public class MemberDaoImplTest {

    @Autowired
    private MemberDao memberDao;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    @Transactional
    public void getAllTest() {
        Member user = new Member("Peter", "Admin", "admin@admin.pl", "admin");
        user.setId(1);
        Category category = new Category("Hobby");
        category.setId(3);
        Expense expectedExpense = new Expense("Wood", new BigDecimal(234.54), category, "2015-02-01");
        expectedExpense.setId(20);

        List<Member> users = memberDao.getAll();

        Assert.assertNotNull("Object with list of users is null.", users);
        Assert.assertEquals("List of users is empty.", users.isEmpty(), false);
        Assert.assertEquals("User retrieved from db is not valid.", users.get(0), user);
    }

    @Test
    @Transactional
    public void getOneByColumnTest() {
        Member expected = new Member("Peter", "Admin", "admin@admin.pl", "admin");
        expected.setId(1);

        String email = "admin@admin.pl";
        Member actual = (Member) memberDao.getOneByColumn("email", email);

        Assert.assertEquals(expected, actual);
    }

    @Test
    @Transactional
    public void getOneByEmailAndPassword() {
        String email = "admin@admin.pl";
        String password = "admin";

        Member actual = (Member) memberDao.getOneByEmailAndPassword(email, password);

        Assert.assertNotNull(actual);
        Assert.assertEquals(email, actual.getEmail());
        Assert.assertEquals(password, actual.getPassword());
    }
}
