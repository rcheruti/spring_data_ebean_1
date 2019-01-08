package br.com.rcc_dev.testes;

import java.time.LocalDate;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CalendarConverter implements Converter<String, LocalDate> {

  @Override
  public LocalDate convert(String source) {
    return LocalDate.parse(source);
  }
  
  
  
}
