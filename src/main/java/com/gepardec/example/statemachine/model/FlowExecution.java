package com.gepardec.example.statemachine.model;

import jakarta.persistence.*;

import java.util.Comparator;
import java.util.List;

@Entity
@SequenceGenerator(name = "flow_execution_id_seq", sequenceName = "flow_execution_id_seq", allocationSize = 1)
public class FlowExecution {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flow_execution_id_seq")
  private Long id;

  @Enumerated(EnumType.STRING)
  private FlowPath initialFlowPath;

  @OneToMany(mappedBy = "flowExecution", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
  private List<FlowState> flowStates;

  @OneToMany(mappedBy = "flowExecution", cascade = CascadeType.ALL)
  private List<PredefinedFlowActionResult> predefinedResults;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public FlowPath getInitialFlowPath() {
    return initialFlowPath;
  }

  public void setInitialFlowPath(FlowPath initialFlowPath) {
    this.initialFlowPath = initialFlowPath;
  }

  public List<FlowState> getFlowStates() {
    return flowStates;
  }

  public void setFlowStates(List<FlowState> flowStates) {
    this.flowStates = flowStates;
  }

  public List<PredefinedFlowActionResult> getPredefinedResults() {
    return predefinedResults;
  }

  public void setPredefinedResults(List<PredefinedFlowActionResult> predefinedResults) {
    this.predefinedResults = predefinedResults;
  }

  public FlowState getLastFlowState() {
    return flowStates.stream()
        .max(Comparator.comparing(FlowState::getId))
        .orElse(null);
  }
}
