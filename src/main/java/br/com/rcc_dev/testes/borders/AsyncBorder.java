package br.com.rcc_dev.testes.borders;

import java.util.Hashtable;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import br.com.rcc_dev.testes.MsgException;
import br.com.rcc_dev.testes.services.AsyncService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/async")
public class AsyncBorder {

  @Autowired
  private AsyncService asyncService;

  private Map<Integer, ListenableFuture<?>> tarefas = new Hashtable<>();

  // ----------------------------------------

  @GetMapping
  public DeferredResult<String> executarAsync() {
    log.info("Iniciando AsyncBorder.executarAsync");
    DeferredResult<String> res = new DeferredResult<>(7_000L, "Aconteceu um Timeout!");

    this.asyncService.executarAsync(3000).addCallback(
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

  @GetMapping("/{tempo}")
  public String executarAsync(@PathVariable("tempo") int tempo) {
    if( tarefas.containsKey(tempo) ) throw new MsgException(null, "Uma tarefa com este tempo já existe!", null);
    ListenableFuture<?> execucao = this.asyncService.executarAsync(tempo);
    tarefas.put(tempo, execucao);
    execucao.addCallback(
      msg -> {
        log.info("Concluido com sucesso tarefa de tempo {}, mensage: {}", tempo, msg);
        tarefas.remove(tempo);
      }
      , 
      ex -> {
        log.warn("Concluido com erro tarefa de tempo {}", tempo, ex);
        tarefas.remove(tempo);
      }
    );
    return "Iniciado tarefa com tempo " + tempo;
  }

  @DeleteMapping("/{tempo}")
  public String pararAsync(@PathVariable("tempo") int tempo) {
    if( !tarefas.containsKey(tempo) ) throw new MsgException(null, "Não existe tarefa com este tempo!", null);
    ListenableFuture<?> execucao = tarefas.get(tempo);
    execucao.cancel(true);
    return "Parado tarefa com tempo " + tempo;
  }

}