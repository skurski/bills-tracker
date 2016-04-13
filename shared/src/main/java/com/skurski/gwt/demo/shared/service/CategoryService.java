package com.skurski.gwt.demo.shared.service;

import com.skurski.gwt.demo.shared.dto.CategoryDto;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import java.util.List;

/**
 * Created by pskurski on 4/6/2016.
 */
@Path("/json/categories")
public interface CategoryService extends RestService {

    @GET
    @Path("/get-all")
    void getAll(MethodCallback<List<CategoryDto>> categories);

    @PUT
    @Path("/save-all")
    void setAll(List<CategoryDto> categories, MethodCallback<Void> callback);

    @PUT
    @Path("/save-new")
    void setOne(CategoryDto category, MethodCallback<Void> callback);

    @PUT
    @Path("/delete-selected")
    void delete(List<CategoryDto> deletedCategories, MethodCallback<Void> callback);
}
