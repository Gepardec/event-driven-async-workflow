package com.gepardec.example.workflow.usecase;

import jakarta.enterprise.context.ApplicationScoped;

import java.lang.invoke.MethodHandles;
import java.util.logging.Logger;

@ApplicationScoped
public class UseCase_1_2 extends AbstractUseCase {

  private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

  @Override
  protected Logger getLogger() {
    return logger;
  }
}
