package com.gepardec.example.workflow.mdb;

import com.gepardec.example.workflow.usecase.StateTransition;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.ObjectMessage;

import java.lang.invoke.MethodHandles;
import java.util.logging.Logger;

@MessageDriven(name = "STATE_TRANSITION_MDB", activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/STATETRANSITION"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue"),
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class StateTransitionMDB implements MessageListener {

  private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

  @Inject
  private StateTransition stateTransition;

  public void onMessage(Message rcvMessage) {
    try {
      if (rcvMessage instanceof ObjectMessage) {
        Long flowExecutionId = (Long) ((ObjectMessage) rcvMessage).getObject();
        logger.info("Received message with flowExecutionId: " + flowExecutionId);

        stateTransition.execute(flowExecutionId);
      }
    } catch (JMSException e) {
      throw new RuntimeException(e);
    }
  }
}
