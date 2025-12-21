package com.example.springboot_backend;

import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class SecondProgrammaticApproach {

    TransactionTemplate transactionTemplate;

    public SecondProgrammaticApproach(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    public void user(){
        TransactionCallback<TransactionStatus> dbOperationTask = (TransactionStatus status) -> {
            System.out.println("Perform operation");
            return status;
        };
        transactionTemplate.execute(dbOperationTask);
    }
}
