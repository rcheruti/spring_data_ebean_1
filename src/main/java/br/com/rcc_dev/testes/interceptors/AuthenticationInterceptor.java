package br.com.rcc_dev.testes.interceptors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    log.info("Verificando credenciais de acesso");

    if( request.getCookies() != null ) for(Cookie cookie : request.getCookies()){
      if( "permissions".equalsIgnoreCase(cookie.getName()) ){
        request.setAttribute("permissions", cookie.getValue().split(","));
        return true;
      }
    }
    
    if("alguem".equalsIgnoreCase(request.getHeader("user"))){
      String[] perms = {"PERSON"};
      Cookie cookie = new Cookie("permissions", String.join(",", perms) );
      cookie.setHttpOnly(true);
      response.addCookie(cookie);
      request.setAttribute("permissions", perms);
      return true;
    }

    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    return false;
  }

}
