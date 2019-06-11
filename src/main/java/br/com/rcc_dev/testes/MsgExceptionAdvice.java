package br.com.rcc_dev.testes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

/**
 * Uma instância dessa classe será usada para tratar exceções que subirem a pilha até os códigos de borda.
 */
@ControllerAdvice
@Slf4j
public class MsgExceptionAdvice {
  
  @ResponseBody
  @ExceptionHandler(MsgException.class)
  public ResponseEntity<MsgException> excecao(MsgException ex){
    return new ResponseEntity<MsgException>(ex, HttpStatus.resolve(ex.getHttpStatus()) );
  }
  
  @ResponseBody
  @ExceptionHandler(Exception.class)
  public ResponseEntity<MsgException> excecao(Exception ex){
    log.error("Erro durante execução!", ex);
    return excecao(new MsgException(ex));
  }
  
}