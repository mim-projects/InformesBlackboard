package com.mimsoft.informesblackboard.application.modules.upload_execute.services;

import com.mimsoft.informesblackboard.Configuration;
import javax.persistence.EntityManager;
import javax.transaction.*;

import java.util.ArrayList;
import java.util.List;

public class ProcessMassiveServices<T> {
    private final EntityManager entityManager;
    private final UserTransaction userTransaction;

    private final List<String> listQueryString;
    private final List<T> list;
    private int counter;
    private int counterStr;

    public ProcessMassiveServices(EntityManager entityManager, UserTransaction userTransaction) {
        this.entityManager = entityManager;
        this.userTransaction = userTransaction;

        counter = 0;
        list = new ArrayList<T>();

        counterStr = 0;
        listQueryString = new ArrayList<>();
    }

    public void add(T item) {
        if (counter == 0) list.clear();
        list.add(item);
        counter++;
        if (counter > Configuration.MAX_BATCH_SIZE_MYSQL) {
            execute();
            counter = 0;
        }
    }

    public void addQueryString(String queryString) {
        if (counterStr == 0) listQueryString.clear();
        listQueryString.add(queryString);
        counterStr++;
        if (counterStr > Configuration.MAX_BATCH_SIZE_MYSQL) {
            executeString();
            counterStr = 0;
        }
    }

    public void execute() {
        if (counter == 0) return;
        try {
            userTransaction.setTransactionTimeout(Configuration.TRANSACTION_TIME_SQL);
            userTransaction.begin();
            for (T item: list) entityManager.persist(item);
            entityManager.flush();
            entityManager.clear();
            userTransaction.commit();
        } catch (NotSupportedException | SystemException | HeuristicRollbackException | HeuristicMixedException |
                 RollbackException e) {
            e.printStackTrace();
        }
    }

    public void executeString() {
        if (counterStr == 0) return;
        try {
            userTransaction.setTransactionTimeout(Configuration.TRANSACTION_TIME_SQL);
            userTransaction.begin();
            for (String item: listQueryString) entityManager.createNativeQuery(item).executeUpdate();
            entityManager.flush();
            entityManager.clear();
            userTransaction.commit();
        } catch (NotSupportedException | SystemException | HeuristicRollbackException | HeuristicMixedException |
                 RollbackException e) {
            e.printStackTrace();
        }
    }
}
