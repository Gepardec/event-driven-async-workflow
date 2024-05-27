package com.gepardec.example.workflow.jpa;

import com.gepardec.example.workflow.model.FlowExecution;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.lang.invoke.MethodHandles;
import java.util.logging.Logger;

@ApplicationScoped
public class FlowExecutionDao {

  private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

  @PersistenceContext
  private EntityManager entityManager;

  public FlowExecution getFlowExecutionById(Long flowExecutionId) {
    return entityManager.find(FlowExecution.class, flowExecutionId);
  }

  public FlowExecution persistFlowExecution(FlowExecution flowExecution) {
    logger.info("Persisting FlowExecution");
    entityManager.persist(flowExecution);
    return flowExecution;
  }

  public FlowExecution updateFlowExecution(FlowExecution flowExecution) {
    logger.info("Updating FlowExecution with id: " + flowExecution.getId());
    return entityManager.merge(flowExecution);
  }
}
