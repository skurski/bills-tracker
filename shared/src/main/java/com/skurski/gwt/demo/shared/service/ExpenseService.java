package com.skurski.gwt.demo.shared.service;

import com.skurski.gwt.demo.shared.dto.ExpenseDto;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

/**
 * Created by PSkurski on 3/24/2016.
 */
@Path("/json/expenses")
public interface ExpenseService extends RestService {
    @GET
    @Path("/get-all")
    void getAll(MethodCallback<List<ExpenseDto>> expenses);

    @GET
    @Path("/get-range/{start}/{end}")
    void getByRange(@PathParam("start") int start,
                    @PathParam("end") int length,
                    MethodCallback<List<ExpenseDto>> methodCallback);

    @GET
    @Path("/get-range/{category}/{start}/{end}")
    void getByRangeAndCategory(@PathParam("category") int category,
                                @PathParam("start") int start,
                                @PathParam("end") int length,
                                MethodCallback<List<ExpenseDto>> methodCallback);

    @GET
    @Path("/get-range/rows-number")
    void getRowsNumber(MethodCallback<Integer> methodCallback);

    @GET
    @Path("/get-range/{category}/rows-number")
    void getRowsNumberByCategory(@PathParam("category") int category,
                                 MethodCallback<Integer> methodCallback);

    @PUT
    @Path("/save-all")
    void setAll(List<ExpenseDto> expenses, MethodCallback<Void> callback);

    @PUT
    @Path("/save-new")
    void setOne(ExpenseDto expense, MethodCallback<Void> callback);

    @PUT
    @Path("/delete-selected")
    void delete(List<ExpenseDto> deletedExpenses, MethodCallback<Void> callback);
}
