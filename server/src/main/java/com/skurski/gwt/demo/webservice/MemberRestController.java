package com.skurski.gwt.demo.webservice;

import com.skurski.gwt.demo.dao.abstraction.MemberDao;
import com.skurski.gwt.demo.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by pskurski on 4/8/2016.
 */
@RestController
@RequestMapping("/json/member")
@Transactional
public class MemberRestController {

    private MemberDao memberDao;

    @Autowired
    public MemberRestController(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @RequestMapping(value="/get-all", method = RequestMethod.GET, produces = "application/json")
    public List<Member> getOneByEmail() {
        return memberDao.getAll();
    }

    @RequestMapping(value="/get-one", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Member getOneByEmail(@RequestBody Member memberDto) {
        return (Member) memberDao.getOneByColumn("email", memberDto.getEmail());
    }

}
