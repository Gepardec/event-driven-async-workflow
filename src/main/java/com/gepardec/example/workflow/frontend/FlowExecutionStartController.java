package com.gepardec.example.workflow.frontend;

import com.gepardec.example.workflow.StartFlowExecutionService;
import com.gepardec.example.workflow.model.*;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.util.List;

@RequestScoped
@Named
public class FlowExecutionStartController {

  @Inject
  private StartFlowExecutionService startFlowExecutionService;

  private String initialFlowPath;

  private String useCase_1_1_Result;
  private String useCase_1_2_Result;
  private String useCase_1_3_Result;
  private String useCase_2_1_Result;
  private String useCase_2_2_Result;
  private String useCase_2_3_Result;

  private final List<FlowActionResult> possibleFlowActionResults = List.of(FlowActionResult.OK, FlowActionResult.ERROR);
  private final List<FlowPath> possibleFlowPaths = List.of(FlowPath.PATH_1, FlowPath.PATH_2);

  @PostConstruct
  public void initialize() {
    initialFlowPath = FlowPath.PATH_1.name();
    useCase_1_1_Result = FlowActionResult.OK.name();
    useCase_1_2_Result = FlowActionResult.OK.name();
    useCase_1_3_Result = FlowActionResult.OK.name();
    useCase_2_1_Result = FlowActionResult.OK.name();
    useCase_2_2_Result = FlowActionResult.OK.name();
    useCase_2_3_Result = FlowActionResult.OK.name();
  }

  public void startFlowExecution() {
    FlowExecution flowExecution = buildFlowExecution();
    Long previousExecutionId = startFlowExecutionService.persistAndStartFlowPath(flowExecution);
    FacesContext.getCurrentInstance().addMessage("latest_id", new FacesMessage(FacesMessage.SEVERITY_INFO, "Processing started", "Flow Execution ID: " + previousExecutionId));
  }

  private FlowExecution buildFlowExecution() {
    FlowExecution flowExecution = new FlowExecution();
    flowExecution.setPredefinedResults(new ArrayList<>());

    flowExecution.setInitialFlowPath(FlowPath.valueOf(initialFlowPath));

    flowExecution.getPredefinedResults().add(buildFlowActionResult(flowExecution, FlowAction.ACTION_1_1));
    flowExecution.getPredefinedResults().add(buildFlowActionResult(flowExecution, FlowAction.ACTION_1_2));
    flowExecution.getPredefinedResults().add(buildFlowActionResult(flowExecution, FlowAction.ACTION_1_3));
    flowExecution.getPredefinedResults().add(buildFlowActionResult(flowExecution, FlowAction.ACTION_2_1));
    flowExecution.getPredefinedResults().add(buildFlowActionResult(flowExecution, FlowAction.ACTION_2_2));
    flowExecution.getPredefinedResults().add(buildFlowActionResult(flowExecution, FlowAction.ACTION_2_3));

    return flowExecution;
  }

  private PredefinedFlowActionResult buildFlowActionResult(FlowExecution flowExecution, FlowAction flowAction) {
    PredefinedFlowActionResult predefinedFlowActionResult = new PredefinedFlowActionResult();
    predefinedFlowActionResult.setFlowExecution(flowExecution);
    predefinedFlowActionResult.setFlowAction(flowAction);

    FlowActionResult result = switch(flowAction) {
      case ACTION_1_1 -> FlowActionResult.valueOf(useCase_1_1_Result);
      case ACTION_1_2 -> FlowActionResult.valueOf(useCase_1_2_Result);
      case ACTION_1_3 -> FlowActionResult.valueOf(useCase_1_3_Result);
      case ACTION_2_1 -> FlowActionResult.valueOf(useCase_2_1_Result);
      case ACTION_2_2 -> FlowActionResult.valueOf(useCase_2_2_Result);
      case ACTION_2_3 -> FlowActionResult.valueOf(useCase_2_3_Result);
    };

    predefinedFlowActionResult.setResult(result);

    return predefinedFlowActionResult;
  }

  public String getInitialFlowPath() {
    return initialFlowPath;
  }

  public void setInitialFlowPath(String initialFlowPath) {
    this.initialFlowPath = initialFlowPath;
  }

  public String getUseCase_1_1_Result() {
    return useCase_1_1_Result;
  }

  public void setUseCase_1_1_Result(String useCase_1_1_Result) {
    this.useCase_1_1_Result = useCase_1_1_Result;
  }

  public String getUseCase_1_2_Result() {
    return useCase_1_2_Result;
  }

  public void setUseCase_1_2_Result(String useCase_1_2_Result) {
    this.useCase_1_2_Result = useCase_1_2_Result;
  }

  public String getUseCase_1_3_Result() {
    return useCase_1_3_Result;
  }

  public void setUseCase_1_3_Result(String useCase_1_3_Result) {
    this.useCase_1_3_Result = useCase_1_3_Result;
  }

  public String getUseCase_2_1_Result() {
    return useCase_2_1_Result;
  }

  public void setUseCase_2_1_Result(String useCase_2_1_Result) {
    this.useCase_2_1_Result = useCase_2_1_Result;
  }

  public String getUseCase_2_2_Result() {
    return useCase_2_2_Result;
  }

  public void setUseCase_2_2_Result(String useCase_2_2_Result) {
    this.useCase_2_2_Result = useCase_2_2_Result;
  }

  public String getUseCase_2_3_Result() {
    return useCase_2_3_Result;
  }

  public void setUseCase_2_3_Result(String useCase_2_3_Result) {
    this.useCase_2_3_Result = useCase_2_3_Result;
  }

  public List<FlowActionResult> getPossibleFlowActionResults() {
    return possibleFlowActionResults;
  }

  public List<FlowPath> getPossibleFlowPaths() {
    return possibleFlowPaths;
  }
}
