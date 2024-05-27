package com.gepardec.example.workflow.event;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.*;

import java.lang.invoke.MethodHandles;
import java.util.logging.Logger;

@JMSDestinationDefinitions(
    value = {
        @JMSDestinationDefinition(
            name = "java:/queue/STATETRANSITION",
            interfaceName = "jakarta.jms.Queue",
            destinationName = "StateTransition"
        ),
        @JMSDestinationDefinition(
            name = "java:/queue/QUEUE11",
            interfaceName = "jakarta.jms.Queue",
            destinationName = "Queue11"
        ),
        @JMSDestinationDefinition(
            name = "java:/queue/QUEUE12",
            interfaceName = "jakarta.jms.Queue",
            destinationName = "Queue12"
        ),
        @JMSDestinationDefinition(
            name = "java:/queue/QUEUE13",
            interfaceName = "jakarta.jms.Queue",
            destinationName = "Queue13"
        ),
        @JMSDestinationDefinition(
            name = "java:/queue/QUEUE21",
            interfaceName = "jakarta.jms.Queue",
            destinationName = "Queue21"
        ),
        @JMSDestinationDefinition(
            name = "java:/queue/QUEUE22",
            interfaceName = "jakarta.jms.Queue",
            destinationName = "Queue22"
        ),
        @JMSDestinationDefinition(
            name = "java:/queue/QUEUE23",
            interfaceName = "jakarta.jms.Queue",
            destinationName = "Queue23"
        )
    }
)

@ApplicationScoped
public class EventPublisher {

  private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

  @Inject
  private JMSContext jmsContext;

  @Resource(lookup = "java:/queue/QUEUE11")
  private Queue queue11;

  @Resource(lookup = "java:/queue/QUEUE12")
  private Queue queue12;

  @Resource(lookup = "java:/queue/QUEUE13")
  private Queue queue13;

  @Resource(lookup = "java:/queue/QUEUE21")
  private Queue queue21;

  @Resource(lookup = "java:/queue/QUEUE22")
  private Queue queue22;

  @Resource(lookup = "java:/queue/QUEUE23")
  private Queue queue23;

  @Resource(lookup = "java:/queue/STATETRANSITION")
  private Queue stateTransitionQueue;

  public void sendEvent(Long flowExecutionId, String queueName) {
    try {
      final ObjectMessage message = jmsContext.createObjectMessage();
      message.setObject(flowExecutionId);

      final JMSProducer jmsProducer = jmsContext.createProducer();
      logger.info("Sending message to queue: " + queueName + " with flowExecutionId: " + flowExecutionId);
      jmsProducer.send(getQueue(queueName), message);
    } catch (JMSException e) {
      logger.severe(e.getCause().getMessage());
    }
  }

  private Queue getQueue(String queueName) {
    return switch (queueName) {
      case "QUEUE11" -> queue11;
      case "QUEUE12" -> queue12;
      case "QUEUE13" -> queue13;
      case "QUEUE21" -> queue21;
      case "QUEUE22" -> queue22;
      case "QUEUE23" -> queue23;
      case "STATETRANSITION" -> stateTransitionQueue;
      default -> throw new IllegalArgumentException("Unknown queue name: " + queueName);
    };
  }
}
