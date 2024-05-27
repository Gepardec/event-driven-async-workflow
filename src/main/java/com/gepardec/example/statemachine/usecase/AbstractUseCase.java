package com.gepardec.example.statemachine.usecase;

import com.gepardec.example.statemachine.jpa.FlowExecutionDao;
import com.gepardec.example.statemachine.model.FlowActionResult;
import com.gepardec.example.statemachine.model.FlowExecution;
import com.gepardec.example.statemachine.model.FlowState;
import com.gepardec.example.statemachine.model.PredefinedFlowActionResult;
import com.gepardec.example.statemachine.event.EventPublisher;
import com.gepardec.example.statemachine.util.ListUtils;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.logging.Logger;

public abstract class AbstractUseCase {

  @Inject
  private FlowExecutionDao flowExecutionDao;

  @Inject
  private EventPublisher eventPublisher;

  protected abstract Logger getLogger();

  @Transactional
  public void execute(Long flowExecutionId) {
    getLogger().info("Executing use case for flow execution with id " + flowExecutionId);

    FlowExecution flowExecution = flowExecutionDao.getFlowExecutionById(flowExecutionId);

    performAction(flowExecution);

    FlowState lastState = ListUtils.getMax(flowExecution.getFlowStates(), Comparator.comparing(FlowState::getId));
    lastState.setResult(calculateUseCaseResult(flowExecution, lastState));
    lastState.setTimestamp(LocalDateTime.now());

    flowExecutionDao.updateFlowExecution(flowExecution);
    eventPublisher.sendEvent(flowExecutionId, "STATETRANSITION");
    getLogger().info("Use case for flow execution with id " + flowExecutionId + " executed");
  }

  private void performAction(FlowExecution flowExecution) {
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      getLogger().severe("Error while executing use case for flow execution with id " + flowExecution.getId());
    }
  }

  private FlowActionResult calculateUseCaseResult(FlowExecution flowExecution, FlowState lastState) {
    return flowExecution.getPredefinedResults().stream()
        .filter(predefinedResult -> predefinedResult.getFlowAction().equals(lastState.getAction()))
        .map(PredefinedFlowActionResult::getResult)
        .findAny().orElseThrow(() -> new IllegalStateException("No predefined result found for action " + lastState.getAction()));
  }
}
