package com.gepardec.example.statemachine.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@SequenceGenerator(name = "flow_state_id_seq", sequenceName = "flow_state_id_seq", allocationSize = 1)
public class FlowState {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flow_state_id_seq")
  private Long id;

  @Enumerated(EnumType.STRING)
  private FlowAction action;

  @Enumerated(EnumType.STRING)
  private FlowActionResult result;

  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime timestamp;

  @ManyToOne
  private FlowExecution flowExecution;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public FlowAction getAction() {
    return action;
  }

  public void setAction(FlowAction action) {
    this.action = action;
  }

  public FlowActionResult getResult() {
    return result;
  }

  public void setResult(FlowActionResult result) {
    this.result = result;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public FlowExecution getFlowExecution() {
    return flowExecution;
  }

  public void setFlowExecution(FlowExecution flowExecution) {
    this.flowExecution = flowExecution;
  }
}
