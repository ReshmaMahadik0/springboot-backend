package com.example.springboot_backend;


import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

@Component
public class FirstProgrammaticApproach {

    PlatformTransactionManager platformTransactionManager;

    public FirstProgrammaticApproach(PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
    }


    public void user(){
        TransactionStatus status = platformTransactionManager.getTransaction(null);

        try{

            System.out.println("some do operation");
            platformTransactionManager.commit(status);
            //throw new RuntimeException("exception");

        }catch (Exception e){
            platformTransactionManager.rollback(status);
        }
    }
}
