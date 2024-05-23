package com.gepardec.example.statemachine.usecase;

import com.gepardec.example.statemachine.event.EventPublisher;
import com.gepardec.example.statemachine.jpa.FlowExecutionDao;
import com.gepardec.example.statemachine.model.*;
import com.gepardec.example.statemachine.util.Pair;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ApplicationScoped
public class StateTransition {

  private static final Map<Pair<FlowAction, FlowActionResult>, FlowAction> nextActions = initNextActions();

  private static Map<Pair<FlowAction, FlowActionResult>, FlowAction> initNextActions() {
    Map<Pair<FlowAction, FlowActionResult>, FlowAction> nextActions = new HashMap<>();

    nextActions.put(Pair.of(FlowAction.ACTION_1_1, FlowActionResult.OK), FlowAction.ACTION_1_2);
    nextActions.put(Pair.of(FlowAction.ACTION_1_2, FlowActionResult.OK), FlowAction.ACTION_1_3);
    nextActions.put(Pair.of(FlowAction.ACTION_1_3, FlowActionResult.OK), null);
    nextActions.put(Pair.of(FlowAction.ACTION_2_1, FlowActionResult.OK), FlowAction.ACTION_2_2);
    nextActions.put(Pair.of(FlowAction.ACTION_2_2, FlowActionResult.OK), FlowAction.ACTION_2_3);
    nextActions.put(Pair.of(FlowAction.ACTION_2_3, FlowActionResult.OK), null);

    nextActions.put(Pair.of(FlowAction.ACTION_1_1, FlowActionResult.ERROR), FlowAction.ACTION_2_1);
    nextActions.put(Pair.of(FlowAction.ACTION_1_2, FlowActionResult.ERROR), FlowAction.ACTION_2_1);
    nextActions.put(Pair.of(FlowAction.ACTION_1_3, FlowActionResult.ERROR), FlowAction.ACTION_2_1);
    nextActions.put(Pair.of(FlowAction.ACTION_2_1, FlowActionResult.ERROR), null);
    nextActions.put(Pair.of(FlowAction.ACTION_2_2, FlowActionResult.ERROR), null);
    nextActions.put(Pair.of(FlowAction.ACTION_2_3, FlowActionResult.ERROR), null);
    return nextActions;
  }

  @Inject
  private FlowExecutionDao flowExecutionDao;

  @Inject
  private EventPublisher eventPublisher;

  @Transactional
  public void execute(Long flowExecutionId) {
    FlowExecution flowExecution = flowExecutionDao.getFlowExecutionById(flowExecutionId);

    FlowState nextState = calculateNextFlowState(flowExecution);

    if (Objects.nonNull(nextState)) {
      nextState.setFlowExecution(flowExecution);
      flowExecution.getFlowStates().add(nextState);
      flowExecutionDao.updateFlowExecution(flowExecution);

      eventPublisher.sendEvent(flowExecution.getId(), nextState.getAction().getQueueName());
    }
  }

  private FlowState calculateNextFlowState(FlowExecution flowExecution) {
    FlowState currentState = flowExecution.getLastFlowState();

    if (currentState == null) {
      return createFlowState(flowExecution.getInitialFlowPath().getFirstAction(), FlowActionResult.UNPROCESSED);
    }

    FlowAction currentAction = currentState.getAction();
    FlowActionResult currentResult = currentState.getResult();

    FlowAction nextAction = nextActions.get(Pair.of(currentAction, currentResult));

    if (nextAction == null) {
      return null;
    }

    return createFlowState(nextAction, FlowActionResult.UNPROCESSED);
  }

  private FlowState createFlowState(FlowAction action, FlowActionResult result) {
    FlowState flowState = new FlowState();
    flowState.setAction(action);
    flowState.setResult(result);
    flowState.setTimestamp(LocalDateTime.now());
    return flowState;
  }
}
