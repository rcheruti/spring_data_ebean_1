package br.com.rcc_dev.testes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {

  public static final String charUTF_8 = "UTF-8";
  public static final String charISO_8859_1 = "ISO-8859-1";

  private static final String sqlFileName = "sql"; // sem a extensão (pode ser xml, properties e yml)
  private static Properties sqls = null;

  // ----------------------------------

  public static String contents(String name) {
    try {
      // byte[] info = Files.readAllBytes(Paths.get(Utils.class.getClassLoader().getResource(name).toURI()));
      InputStream input = contentsAsStream(name);
      byte[] info = new byte[ input.available() ];
      return new String(info, Charset.forName(charUTF_8));
    } catch (Exception e) {
      // log.warn("Problemas ao ler o arquivo '{}'! Telvez ele não exista, ou não temos permissão.", name, e);
    }
    return null;
  }
  public static InputStream contentsAsStream(String name) {
    return Utils.class.getClassLoader().getResourceAsStream(name);
  }

  public static String sql(String name) {
    if (sqls == null) { // problemas de multiprocessamento aqui!
      // atribução só pode acontecer no final !
      sqls = carregarSql(); 
    }
    String consulta = (String) sqls.get(name);
    if( consulta == null ) {
      log.error("Não existe um SQL definido para o ID '{}'!", name);
    }
    return consulta;
  }
  




  // =========================

  private static Properties carregarSql(){
    var props = new Properties();

    // carregar xml
    try {
      InputStream input = Utils.class.getClassLoader().getResourceAsStream(sqlFileName + ".xml");
      Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse( input );
      NodeList list = doc.getDocumentElement().getChildNodes();
      for( int i = 0; i < list.getLength(); i++ ) {
        Node node = list.item( i );
        if( node.getNodeType() != Node.ELEMENT_NODE ) continue;
        props.put( node.getAttributes().getNamedItem("id").getTextContent(), node.getTextContent() );
      }
      log.info("Carregado arquivo SQL '{}.xml'.", sqlFileName);
    } catch (IOException | SAXException | ParserConfigurationException | NullPointerException e) {
      log.error("Não foi encontrado o arquivo {}.xml.", sqlFileName, e);
    }

    // logs
    for( Object nome : props.keySet() ){
      log.debug("Chave: {}, valor: {}", nome, props.get(nome));
    }

    return props;
  }

}
