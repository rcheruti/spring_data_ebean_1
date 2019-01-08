package br.com.rcc_dev.testes;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utils {
  
  public static final String charUTF_8 = "UTF-8";
  public static final String charISO_8859_1 = "ISO-8859-1";

  // ----------------------------------
  
  public static String fileContentsJar(String name){
    String lf = System.getProperty("line.separator");
    InputStream inRes = Utils.class.getClassLoader().getResourceAsStream(name);
    return new BufferedReader(new InputStreamReader(inRes)).lines().reduce("", (a,b)->{ return a + lf + b; }); 
  }
  
}
