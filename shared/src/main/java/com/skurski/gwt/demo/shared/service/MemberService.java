package com.skurski.gwt.demo.shared.service;

import com.skurski.gwt.demo.shared.dto.MemberDto;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

/**
 * Created by pskurski on 4/8/2016.
 */
@Path("/json/member")
public interface MemberService extends RestService {

    @GET
    @Path("/get-all")
    void getAll(MethodCallback<List<MemberDto>> callback);

    @POST
    @Path("/get-one")
    void getOneByEmail(MemberDto memberDto, MethodCallback<MemberDto> callback);
}
