package com.skurski.gwt.demo.webservice;

import com.skurski.gwt.demo.dao.abstraction.MemberDao;
import com.skurski.gwt.demo.model.Member;
import com.skurski.gwt.demo.util.FieldsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

/**
 * Created by pskurski on 4/7/2016.
 */
@RestController
@RequestMapping("/login")
@Transactional
public class LoginRestController {

    private MemberDao memberDao;

    @Autowired
    public LoginRestController(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @RequestMapping(value="/auth-member", method= RequestMethod.POST, consumes="application/json")
    @ResponseStatus(HttpStatus.OK)
    public Member authMember(@RequestBody Member memberDto) {
        if (!FieldsValidator.checkParams(memberDto)) return null;

        Member member = (Member) memberDao.getOneByEmailAndPassword(memberDto.getEmail(), memberDto.getPassword());

        if (member != null) return member;

        return null;
    }
}
