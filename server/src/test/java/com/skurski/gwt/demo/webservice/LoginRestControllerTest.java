package com.skurski.gwt.demo.webservice;

import com.skurski.gwt.demo.dao.abstraction.MemberDao;
import com.skurski.gwt.demo.model.Member;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.transaction.Transactional;

import static org.mockito.Matchers.anyString;

/**
 * Created by pskurski on 4/8/2016.
 */
public class LoginRestControllerTest {

    @InjectMocks
    private LoginRestController loginRestController;

    @Mock
    private Member member;

    @Mock
    private MemberDao memberDao;

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);

        Mockito.when(memberDao.getOneByEmailAndPassword(anyString(), anyString())).thenReturn(member);
    }

    @Test
    @Transactional
    public void authMemberTest() throws Exception {
        member.setEmail("admin@admin.pl");
        Member actual = loginRestController.authMember(member);

        Assert.assertNotNull(actual);
        Assert.assertEquals(member.getEmail(), actual.getEmail());
    }
}
