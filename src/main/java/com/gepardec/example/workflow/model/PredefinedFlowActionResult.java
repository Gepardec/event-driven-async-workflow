package com.gepardec.example.workflow.model;

import jakarta.persistence.*;

@Entity
@SequenceGenerator(name = "predefined_flow_action_result_id_seq", sequenceName = "predefined_flow_action_result_id_seq", allocationSize = 1)
public class PredefinedFlowActionResult {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "predefined_flow_action_result_id_seq")
  private Long id;

  @Enumerated(EnumType.STRING)
  private FlowAction flowAction;

  @Enumerated(EnumType.STRING)
  private FlowActionResult result;

  @ManyToOne
  private FlowExecution flowExecution;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public FlowAction getFlowAction() {
    return flowAction;
  }

  public void setFlowAction(FlowAction flowAction) {
    this.flowAction = flowAction;
  }

  public FlowActionResult getResult() {
    return result;
  }

  public void setResult(FlowActionResult result) {
    this.result = result;
  }

  public FlowExecution getFlowExecution() {
    return flowExecution;
  }

  public void setFlowExecution(FlowExecution flowExecution) {
    this.flowExecution = flowExecution;
  }
}
