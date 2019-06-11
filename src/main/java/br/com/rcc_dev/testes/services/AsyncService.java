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
  public ListenableFuture<String> executarAsync(int tempo) {
    int passado = tempo;
    while( passado > 0 ) {
      log.info("Tarefa de tempo {} executada para tempo {}, interrupção: {}.", tempo, tempo - passado,
        Thread.currentThread().isInterrupted() );
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) { // o disparo dessa exceção limpa o Status de interrupção
        log.info("Interrupção da tarefa de tempo {} ignorada.", tempo);
      }
      // this.sleep(1000);
      passado -= 1000;
    }
    return AsyncResult.forValue("Async concluido!");
  }

  // -------------------------------------------

  // Essa função não interfere no Status de interrupção, mas ocupa muito tempo do processador!!!
  private void sleep(int tempoMs) {
    long agora = System.currentTimeMillis();
    while( System.currentTimeMillis() - agora < tempoMs ) {
      Thread.yield();
    }
  }

}