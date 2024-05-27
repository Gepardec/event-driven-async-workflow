package com.gepardec.example.workflow;

import com.gepardec.example.workflow.event.EventPublisher;
import com.gepardec.example.workflow.jpa.FlowExecutionDao;
import com.gepardec.example.workflow.model.FlowExecution;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.lang.invoke.MethodHandles;
import java.util.logging.Logger;

@ApplicationScoped
public class StartFlowExecutionService {

  private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

  @Inject
  private FlowExecutionDao flowExecutionDao;

  @Inject
  private EventPublisher eventPublisher;

  @Transactional
  public Long persistAndStartFlowPath(FlowExecution flowPath) {
    FlowExecution persistedFlowExecution = flowExecutionDao.persistFlowExecution(flowPath);
    eventPublisher.sendEvent(persistedFlowExecution.getId(), "STATETRANSITION");
    logger.info("Flow execution started with id " + persistedFlowExecution.getId());
    return persistedFlowExecution.getId();
  }
}
