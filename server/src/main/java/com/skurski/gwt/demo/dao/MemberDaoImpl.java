package com.skurski.gwt.demo.dao;

import com.skurski.gwt.demo.dao.abstraction.AbstractDao;
import com.skurski.gwt.demo.dao.abstraction.MemberDao;
import com.skurski.gwt.demo.model.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;


/**
 * Created by pskurski on 4/5/2016.
 */
@Repository
public class MemberDaoImpl extends AbstractDao<Member> implements MemberDao<Member> {

    @Override
    public Member getOneByEmailAndPassword(String email, String password) {
        TypedQuery<Member> repository =
                entityManager.createQuery("from " + clazz.getSimpleName() + " where email =:email and " +
                        "password = :password", clazz);
        repository.setParameter("email", email);
        repository.setParameter("password", password);

        List<Member> results = repository.getResultList();
        if (!results.isEmpty()) {
            return results.get(0);
        }

        return null;
    }
}
