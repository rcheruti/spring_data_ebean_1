package br.com.rcc_dev.testes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.rcc_dev.testes.interceptors.AuthenticationInterceptor;
import br.com.rcc_dev.testes.interceptors.AuthorizationInterceptor;
import br.com.rcc_dev.testes.interceptors.LocalDateConverter;

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
    registry.addConverter(new LocalDateConverter());
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new AuthenticationInterceptor());
    registry.addInterceptor(new AuthorizationInterceptor());
  }
  
}
