package br.com.rcc_dev.testes.interceptors;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    if( !(handler instanceof HandlerMethod) ) return true;
    log.info("Verificando permissões de acesso");

    Permission permission = ((HandlerMethod) handler).getMethod().getAnnotation(Permission.class);
    if(permission == null) return true; // permitir acesso, pois nenhuma permissão exigida
    String[] arrPermission = permission.value();
    String[] userPermission = (String[])request.getAttribute("permissions");
    
    if(userPermission != null && userPermission.length > 0){
      for(String perm : userPermission){
        if( Arrays.stream(arrPermission).anyMatch(x -> perm.equalsIgnoreCase(x)) ){
          return true;
        }
      }
    }

    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    return false;
  }

}
