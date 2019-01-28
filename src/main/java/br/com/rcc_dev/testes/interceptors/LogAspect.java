package br.com.rcc_dev.testes.interceptors;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class LogAspect {

  @Around("execution(* *(..)) && @annotation(br.com.rcc_dev.testes.interceptors.LogMsg)")
  public Object run(ProceedingJoinPoint jp) throws Throwable {
    Method method = ((MethodSignature) jp.getSignature() ).getMethod();
    LogMsg ann = method.getAnnotation( LogMsg.class );
    String msg = ann.value();
    log.info("--> Mensagem: {}", msg);

    Object obj = jp.proceed();

    return obj;
  }

}