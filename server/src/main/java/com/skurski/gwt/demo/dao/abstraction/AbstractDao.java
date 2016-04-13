package com.skurski.gwt.demo.dao.abstraction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by pskurski on 4/6/2016.
 */
public abstract class AbstractDao<T> implements Dao<T> {

    private static Logger LOG;

    protected Class<T> clazz;

    @PersistenceContext
    protected EntityManager entityManager;

    public AbstractDao() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        clazz = (Class) pt.getActualTypeArguments()[0];

        LOG = LoggerFactory.getLogger(clazz);
    }

    @Override
    public List<T> getAll() {
        TypedQuery<T> repository = entityManager.createQuery("from " + clazz.getSimpleName(), clazz);
        return repository.getResultList();
    }

    @Override
    public T getOneById(int id) {
        TypedQuery<T> repository =
                entityManager.createQuery("from " + clazz.getSimpleName() + " where id=:id", clazz);
        repository.setParameter("id", id);

        return repository.getSingleResult();
    }

    @Override
    public List<T> getByRange(int start, int end) {
        TypedQuery<T> repository = entityManager.createQuery("from " + clazz.getSimpleName(), clazz);
        repository.setFirstResult(start);
        repository.setMaxResults(end);

        return repository.getResultList();
    }

    @Override
    public List<T> getByRangeAndChoosenColumnId(String column, int value, int start, int end) {
        TypedQuery<T> repository = entityManager
                .createQuery("from " + clazz.getSimpleName() + " where " + column + " =:" + column + " ", clazz);
        repository.setParameter(column, value);

        repository.setFirstResult(start);
        repository.setMaxResults(end);

        return repository.getResultList();
    }

    @Override
    public int getRowsNumber() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(clazz)));

        Long result =  entityManager.createQuery(criteriaQuery).getSingleResult();
        return Integer.valueOf(result.intValue());    }

    @Override
    public int getRowsNumberByColumn(String column, int value) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> root = criteriaQuery.from(clazz);
        criteriaQuery.select(criteriaBuilder.count(root));

        //where clause
        ParameterExpression<Integer> parameter = criteriaBuilder.parameter(Integer.class);
        criteriaQuery.where(criteriaBuilder.equal(root.<Integer>get(column), value));

        Long result =  entityManager.createQuery(criteriaQuery).getSingleResult();
        return Integer.valueOf(result.intValue());
    }

    @Override
    public void saveOne(T object) {
        entityManager.persist(object);
    }

    @Override
    public void saveAll(List<T> records) {
        for (T record : records) {
            entityManager.merge(record);
            entityManager.flush();
            entityManager.clear();
        }
    }

    @Override
    public void delete(List<T> records) {
        for (T record : records) {
            entityManager.remove(entityManager.merge(record));
            LOG.debug("remove record: {}", record);
            entityManager.flush();
            entityManager.clear();
        }
    }

    @Override
    public T getOneByColumn(String column, String value) {
        TypedQuery<T> repository =
                    entityManager.createQuery("from " + clazz.getSimpleName() + " where " + column + " like :" + column + "", clazz);
        repository.setParameter(column, value);

        List<T> results  = repository.getResultList();
        if (!results.isEmpty()) {
            return results.get(0);
        }

        return null;
    }

}
