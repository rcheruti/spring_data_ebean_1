package br.com.rcc_dev.testes.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AsyncService {

  @Async
  public ListenableFuture<String> executarAsync() {
    try {
      Thread.sleep(5000);
      return AsyncResult.forValue("Async concluido!");
    } catch (InterruptedException e) {
      log.error("Erro no Async!", e);
    }
    return null;
  }

}