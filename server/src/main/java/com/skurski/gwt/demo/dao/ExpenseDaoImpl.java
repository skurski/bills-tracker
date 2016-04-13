package com.skurski.gwt.demo.dao;

import com.skurski.gwt.demo.dao.abstraction.AbstractDao;
import com.skurski.gwt.demo.dao.abstraction.ExpenseDao;
import com.skurski.gwt.demo.model.Expense;
import org.springframework.stereotype.Repository;


/**
 * Created by pskurski on 3/23/2016.
 */
@Repository
public class ExpenseDaoImpl extends AbstractDao<Expense> implements ExpenseDao<Expense> {

}
