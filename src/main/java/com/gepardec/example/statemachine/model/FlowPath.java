package com.gepardec.example.statemachine.model;

public enum FlowPath {
  PATH_1(FlowAction.ACTION_1_1),
  PATH_2(FlowAction.ACTION_2_1);

  private final FlowAction firstAction;

  FlowPath(FlowAction firstAction) {
    this.firstAction = firstAction;
  }

  public FlowAction getFirstAction() {
    return firstAction;
  }
}
