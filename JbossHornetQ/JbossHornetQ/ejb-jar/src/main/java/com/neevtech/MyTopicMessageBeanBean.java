/**
 * User: jayesh
 * Date: May 13, 2009
 *
 */
package com.neevtech;

import javax.ejb.*;
import javax.jms.MessageListener;
import javax.jms.Message;
@MessageDriven(mappedName = "testTopic",activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "destination",
                propertyValue = "testTopic") })
public class MyTopicMessageBeanBean implements MessageDrivenBean, MessageListener {
    public MyTopicMessageBeanBean() {
    }

    public void onMessage(Message message) {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Topic Message received  >>>>> : " + message+ "<<<<<<<<<<<<<");
    }

    public void ejbRemove() throws EJBException {
    }

    public void setMessageDrivenContext(MessageDrivenContext messageDrivenContext) throws EJBException {
    }

    public void ejbCreate() {
    }
}
