package com.gepardec.example.statemachine.mdb;

import com.gepardec.example.statemachine.usecase.AbstractUseCase;
import com.gepardec.example.statemachine.usecase.UseCase_2_3;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;

import java.lang.invoke.MethodHandles;
import java.util.logging.Logger;

/**
 * <p>
 * A simple Message Driven Bean that asynchronously receives and processes the messages that are sent to the queue.
 * </p>
 *
 * @author Serge Pagop (spagop@redhat.com)
 */
@MessageDriven(name = "MDB_2_3_MDB", activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/QUEUE23"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue"),
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class MDB_2_3 extends AbstractMDB {

  private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

  @Inject
  private UseCase_2_3 useCase;

  @Override
  protected Logger getLogger() {
    return logger;
  }

  @Override
  protected AbstractUseCase getUseCase() {
    return useCase;
  }
}
