package br.com.rcc_dev.testes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableConfigurationProperties
@EnableAspectJAutoProxy
public class App implements WebMvcConfigurer {

  public static void main( String[] args ){
    SpringApplication.run(App.class, args);
  }
  
  // ----------------------------------------------
  
  @Override
  public void addFormatters (FormatterRegistry registry) {
      registry.addConverter(new CalendarConverter());
  }
  
}
