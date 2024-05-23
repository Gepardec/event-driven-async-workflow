package com.gepardec.example.statemachine.model;

public enum FlowAction {
  ACTION_1_1 ("QUEUE11", "Use Case 1-1"),
  ACTION_1_2 ("QUEUE12", "Use Case 1-2"),
  ACTION_1_3 ("QUEUE13", "Use Case 1-3"),
  ACTION_2_1 ("QUEUE21", "Use Case 2-1"),
  ACTION_2_2 ("QUEUE22", "Use Case 2-2"),
  ACTION_2_3 ("QUEUE23", "Use Case 2-3");

  private final String queueName;
  private final String displayName;

  FlowAction(String queueName, String displayName) {
    this.queueName = queueName;
    this.displayName = displayName;
  }

  public String getQueueName() {
    return queueName;
  }

  public String getDisplayName() {
    return displayName;
  }
}
