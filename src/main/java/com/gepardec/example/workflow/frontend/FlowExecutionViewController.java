package com.gepardec.example.workflow.frontend;

import com.gepardec.example.workflow.jpa.FlowExecutionDao;
import com.gepardec.example.workflow.model.FlowExecution;
import com.gepardec.example.workflow.model.FlowState;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@ViewScoped
@Named
public class FlowExecutionViewController implements Serializable {

  @Inject
  private FlowExecutionDao flowExecutionDao;

  private Long selectedId;

  private List<FlowState> chronolineEvents = new ArrayList<>();

  public Long getSelectedId() {
    if(selectedId == null) {
      return null;
    }

    return selectedId;
  }

  public void setSelectedId(Long selectedId) {
    if(selectedId == null) {
      return;
    }
    this.selectedId = selectedId;
    updateChronolineEvents();
  }

  public List<FlowState> getChronolineEvents() {
    return chronolineEvents;
  }

  public void setChronolineEvents(List<FlowState> chronolineEvents) {
    this.chronolineEvents = chronolineEvents;
  }

  public void updateChronolineEvents() {
    FlowExecution flowExecution = flowExecutionDao.getFlowExecutionById(selectedId);
    chronolineEvents = flowExecution.getFlowStates().stream()
        .sorted(Comparator.comparing(FlowState::getId))
        .toList();
  }
}
