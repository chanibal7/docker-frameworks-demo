/**
 * User: jayesh
 * Date: Feb 18, 2009
 *
 */
package com.neevtech;

import javax.ejb.*;
import javax.jms.MessageListener;
import javax.jms.Message;

@MessageDriven(mappedName = "testQueue",activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination",
                propertyValue = "testQueue") })
public class MyMessageBean implements MessageDrivenBean, MessageListener {
    public MessageDrivenContext getCtx() {
        return ctx;
    }

    public void setCtx(MessageDrivenContext ctx) {
        this.ctx = ctx;
    }

    private MessageDrivenContext ctx;

    public MyMessageBean() {
    }

    public void onMessage(Message message) {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Queue Message received  >>>>> : " + message + "<<<<<<<<<<<<<");
    }

    public void ejbRemove() throws EJBException {
    }

    public void setMessageDrivenContext(MessageDrivenContext messageDrivenContext) throws EJBException {
    }

    public void ejbCreate() {
    }
}
