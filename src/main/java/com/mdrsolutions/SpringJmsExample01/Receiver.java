package com.mdrsolutions.SpringJmsExample01;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component  // which will cause this class to be added to spring context as the application starts out.
public class Receiver {

    @JmsListener(destination ="order-queue") // can take several properties, two of them are  mandatory.
    public void receiveMessage(String message){
        System.out.println("message is received : "+message);
    }
}
