package com.skurski.gwt.demo.shared.service;

import com.skurski.gwt.demo.shared.dto.MemberDto;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * Created by pskurski on 4/7/2016.
 */
@Path("/login")
public interface LoginService extends RestService {

    @POST
    @Path("/auth-member")
    void authMember(MemberDto memberDto, MethodCallback<MemberDto> callback);

}
