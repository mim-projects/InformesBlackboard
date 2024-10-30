package com.mimsoft.informesblackboard.domain.core;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.*;
import java.util.ArrayList;
import java.util.List;

abstract class RepositoryCore<T extends EntityCore> {
    @Inject
    protected EntityManager entityManager;

    @Resource
    protected UserTransaction userTransaction;

    private Class<T> clazz;

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public String getClazzName() {
        return clazz.getName();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public UserTransaction getUserTransaction() {
        return userTransaction;
    }

    public T create(final T entity) {
        entity.setId(null);
        try {
            userTransaction.begin();
            entityManager.persist(entity);
            entityManager.flush();
            userTransaction.commit();
        } catch (HeuristicRollbackException | SystemException | HeuristicMixedException | NotSupportedException |
                 RollbackException e) {
//            e.printStackTrace();
            return null;
        }
        return entity;
    }

    public T findId(final int id) {
        return entityManager.find(clazz, id);
    }

    public T findOne(final String key, final Object value) {
        String query = "select u from " + clazz.getName()
                + " u where u." + key + " = " + value;
        try {
            return entityManager.createQuery(query, clazz).setMaxResults(1).getSingleResult();
        } catch (Exception e) { /* e.printStackTrace(); */
            return null;
        }
    }

    public T findOneNative(final String key, final Object value) {
        String query = "select * from " + clazz.getSimpleName() + " where " + key + " = '" + value + "' limit 1";
        try {
            return (T) entityManager.createNativeQuery(query, clazz).getSingleResult();
        } catch (Exception e) { /* e.printStackTrace(); */
            return null;
        }
    }

    public T findOneWhere(final String key, final String operator, final Object value) {
        try {
            String query = "select u from " + clazz.getName() + " u where u." + key + " " + operator + " " + value;
            return entityManager.createQuery(query, clazz).getSingleResult();
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }

    public List<T> findWhere(final String key, final String operator, final Object value) {
        try {
            String query = "select u from " + clazz.getName() + " u where u." + key + " " + operator + " " + value;
            return entityManager.createQuery(query, clazz).getResultList();
        } catch (Exception e) {
            //e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public T findOneWhere(final String[] key, final String[] compare, final Object[] value) {
        if (key.length != value.length || key.length != compare.length || key.length == 0) return null;
        StringBuilder query = new StringBuilder("select u from " + clazz.getName() + " u where ");
        for (int k = 0; k < key.length; k++) {
            query.append("u.").append(key[k]).append(" ").append(compare[k]).append(" ").append(value[k]);
            if (k + 1 < key.length) query.append(" and ");
        }
        try {
            return entityManager.createQuery(query.toString(), clazz).getSingleResult();
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }

    public List<T> findWhere(final String[] key, final String[] compare, final Object[] value) {
        if (key.length != value.length || key.length != compare.length || key.length == 0) return new ArrayList<>();
        StringBuilder query = new StringBuilder("select u from " + clazz.getName() + " u where ");
        for (int k = 0; k < key.length; k++) {
            query.append("u.").append(key[k]).append(" ").append(compare[k]).append(" ").append(value[k]);
            if (k + 1 < key.length) query.append(" and ");
        }
        try {
            return entityManager.createQuery(query.toString(), clazz).getResultList();
        } catch (Exception e) {
            //e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<T> findWhereEqual(String join, final String[] key, final Object[] value) {
        if (key.length != value.length || key.length == 0) return new ArrayList<>();
        StringBuilder query = new StringBuilder("select u from " + clazz.getName() + " u where ");
        for (int k = 0; k < key.length; k++) {
            query.append("u.").append(key[k]).append(" = ").append(value[k]);
            if (k + 1 < key.length) query.append(" " + join + " ");
        }
        try {
            return entityManager.createQuery(query.toString(), clazz).getResultList();
        } catch (Exception e) {  /* e.printStackTrace(); */
            return null;
        }
    }

    public List<T> findWhereEqual(final String[] key, final Object[] value) {
        return findWhereEqual("and", key, value);
    }

    public T findOneWhereEqual(final String[] key, final Object[] value) {
        if (key.length != value.length || key.length == 0) return null;
        StringBuilder query = new StringBuilder("select u from " + clazz.getName() + " u where ");
        for (int k = 0; k < key.length; k++) {
            query.append("u.").append(key[k]).append(" = ").append(value[k]);
            if (k + 1 < key.length) query.append(" and ");
        }
        try {
            return entityManager.createQuery(query.toString(), clazz).getSingleResult();
        } catch (Exception e) { /*e.printStackTrace();*/
            return null;
        }
    }

    public T findLastWhere(final String key, final Object value) {
        return findLastWhere(new String[]{ key }, new Object[]{ value });
    }

    public T findLastWhere(final String[] key, final Object[] value) {
        if (key.length != value.length || key.length == 0) return null;
        StringBuilder query = new StringBuilder("select u from " + clazz.getName() + " u where ");
        for (int k = 0; k < key.length; k++) {
            query.append("u.").append(key[k]).append(" = ").append(value[k]);
            if (k + 1 < key.length) query.append(" and ");
        }
        query.append(" order by id desc");
        try {
            return entityManager.createQuery(query.toString(), clazz).setMaxResults(1).getSingleResult();
        } catch (Exception e) { /* e.printStackTrace(); */
            return null;
        }
    }

    public List<T> findAll() {
        try {
            return entityManager.createQuery("from " + clazz.getName(), clazz).getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public T findNativeOne(String query) {
        try {
            return (T) entityManager.createNativeQuery(query, clazz).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<T> findNativeAll(String query) {
        try {
            return (List<T>) entityManager.createNativeQuery(query, clazz).getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<T> findAllLimit(String tableName, int size) {
        String query = "select * from " + tableName + " limit " + size;
        try {
            return entityManager.createNativeQuery(query, clazz).getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public T update(final T entity) {
        try {
            userTransaction.begin();
            entityManager.merge(entity);
            entityManager.flush();
            userTransaction.commit();
        } catch (IllegalArgumentException | HeuristicRollbackException | SystemException | HeuristicMixedException |
                 NotSupportedException | RollbackException e) {
//            e.printStackTrace();
            return null;
        }
        return entity;
    }

    public T deleteNativeForId(final T entity) {
        String query = "delete from $carga$ where id='" + entity.getId() + "'";
        query = query.replace("$carga$", getClassNameSimple());
        try {
            userTransaction.begin();
            getEntityManager().createNativeQuery(query).executeUpdate();
            userTransaction.commit();
        } catch (NotSupportedException | SystemException | HeuristicRollbackException | HeuristicMixedException |
                 RollbackException e) {
//            e.printStackTrace();
            return null;
        }
        return findId(entity.getId());
    }

    public T delete(final T entity) {
        try {
            userTransaction.begin();
            entityManager.remove(entityManager.merge(entity));
            entityManager.flush();
            entityManager.clear(); // Clear persistence
            userTransaction.commit();
        } catch (IllegalArgumentException | HeuristicRollbackException | SystemException | HeuristicMixedException |
                 NotSupportedException | RollbackException e) {
//            e.printStackTrace();
            return null;
        }
        return findId(entity.getId());
    }

    public void executeNativeQuery(String query) {
        try {
            userTransaction.begin();
            getEntityManager().createNativeQuery(query).executeUpdate();
            userTransaction.commit();
        } catch (NotSupportedException | SystemException | HeuristicRollbackException | HeuristicMixedException |
                 RollbackException e) {
//            e.printStackTrace();
        }
    }

    private String getClassNameSimple() {
        String[] classSimpleArr = getClazzName().split("\\.");
        String classSimple = classSimpleArr[classSimpleArr.length - 1];
        String finalClassSimple = "";
        int k = 0;
        for (String str : classSimple.split("")) {
            if (Character.isUpperCase(str.charAt(0))) {
                if (k > 0) finalClassSimple += "_" + str.toLowerCase();
                else finalClassSimple += str.toLowerCase();
            } else finalClassSimple += str;
            k++;
        }
        return finalClassSimple;
    }
}