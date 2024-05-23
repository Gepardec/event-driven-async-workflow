package com.gepardec.example.statemachine.mdb;

import com.gepardec.example.statemachine.usecase.AbstractUseCase;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.ObjectMessage;

import java.util.logging.Logger;

public abstract class AbstractMDB implements MessageListener {

  protected abstract Logger getLogger();

  protected abstract AbstractUseCase getUseCase();

  public void onMessage(Message rcvMessage) {
    try {
      if (rcvMessage instanceof ObjectMessage) {
        Long flowExecutionId = (Long) ((ObjectMessage) rcvMessage).getObject();
        getLogger().info("Received message with flowExecutionId: " + flowExecutionId);

        getUseCase().execute(flowExecutionId);
      }
    } catch (JMSException e) {
      throw new RuntimeException(e);
    }
  }
}
