package com.skurski.gwt.demo.webservice;

import com.skurski.gwt.demo.dao.abstraction.ExpenseDao;
import com.skurski.gwt.demo.model.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by pskurski on 3/23/2016.
 */
@RestController
@RequestMapping("/json/expenses")
@Transactional
public class ExpenseRestController {

    private ExpenseDao expenseDao;

    @Autowired
    public ExpenseRestController(ExpenseDao expenseDao) {
        this.expenseDao = expenseDao;
    }

    @RequestMapping(value="/get-all", method = RequestMethod.GET, produces="application/json")
    public List<Expense> getAllExpenses() {
        return expenseDao.getAll();
    }

    @RequestMapping(value="/get-range/{start}/{end}", method = RequestMethod.GET, produces="application/json")
    public List<Expense> getExpensesByRange(@PathVariable("start") int start,
                                            @PathVariable("end") int end) {
        return expenseDao.getByRange(start, end);
    }

    @RequestMapping(value="/get-range/{categoryId}/{start}/{end}", method = RequestMethod.GET, produces="application/json")
    public List<Expense> getExpensesByRangeAndCategory(@PathVariable("categoryId") int categoryId,
                                                           @PathVariable("start") int start,
                                                                @PathVariable("end") int end) {
        return expenseDao.getByRangeAndChoosenColumnId("category_id", categoryId, start, end);
    }

    @RequestMapping(value="/{expenseId}", method = RequestMethod.GET, produces="application/json")
    public Expense getExpenseById(@PathVariable int expenseId) {
        return (Expense) expenseDao.getOneById(expenseId);
    }

    @RequestMapping(value="/get-range/rows-number", method = RequestMethod.GET, produces = "application/json")
    public Integer getExpenseRowsNumber() {
        return expenseDao.getRowsNumber();
    }

    @RequestMapping(value="/get-range/{categoryId}/rows-number", method = RequestMethod.GET, produces = "application/json")
    public Integer getExpenseRowsNumberByCategory(@PathVariable("categoryId") int category) {
        return expenseDao.getRowsNumberByColumn("category", category);
    }

    @RequestMapping(value="/save-all", method=RequestMethod.PUT, consumes="application/json")
    @ResponseStatus(HttpStatus.OK)
    public void saveAllExpenses(@RequestBody List<Expense> expenses) {
        expenseDao.saveAll(expenses);
    }

    @RequestMapping(value="/save-new", method=RequestMethod.PUT, consumes="application/json")
    @ResponseStatus(HttpStatus.OK)
    public void saveOneExpense(@RequestBody Expense expense) {
        expenseDao.saveOne(expense);
    }

    @RequestMapping(value="/delete-selected", method=RequestMethod.PUT, consumes="application/json")
    @ResponseStatus(HttpStatus.OK)
    public void deleteExpenses(@RequestBody List<Expense> expenses) {
        expenseDao.delete(expenses);
    }
}
