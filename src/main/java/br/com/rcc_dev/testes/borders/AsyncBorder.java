package br.com.rcc_dev.testes.borders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import br.com.rcc_dev.testes.services.AsyncService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/async")
public class AsyncBorder {

  @Autowired
  private AsyncService asyncService;

  // ----------------------------------------

  @GetMapping
  public DeferredResult<String> executarAsync() {
    log.info("Iniciando AsyncBorder.executarAsync");
    DeferredResult<String> res = new DeferredResult<>(7_000L, "Aconteceu um Timeout!");

    this.asyncService.executarAsync().addCallback(
      msg -> {
        if( res.setResult("Respondendo: " + msg) ) log.info("Enviando resposta de AsyncBorder.executarAsync");
        else log.info("Erro ao enviar resposta de AsyncBorder.executarAsync");
      }
      , 
      ex -> {
        if( res.setResult("Erro: " + ex.getMessage()) ) log.info("Enviando mensagem de erro de AsyncBorder.executarAsync");
        else log.info("Erro ao enviar mensagem de erro de AsyncBorder.executarAsync");
      }
    );

    log.info("Terminado AsyncBorder.executarAsync");
    return res;
  }

}