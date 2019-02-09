package br.com.rcc_dev.testes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.yaml.snakeyaml.Yaml;

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
      byte[] info = Files.readAllBytes(Paths.get(Utils.class.getClassLoader().getResource(name).toURI()));
      return new String(info, Charset.forName(charUTF_8));
    } catch (URISyntaxException | IOException e) {
      log.error("Problemas ao ler o arquivo '{}'! Telvez ele não exista, ou não temos permissão.", name, e);
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
    return (String) sqls.get(name);
  }
  




  // =========================

  private static Properties carregarSql(){
    String conteudo;
      Properties props = new Properties();

      // carregar properties
      try {
        conteudo = Utils.contents(sqlFileName + ".properties"); // para ler em UTF-8
        props.load(new StringReader(conteudo));
        log.info("Carregado arquivo SQL '{}.properties'.", sqlFileName);
      } catch (IOException e) {
        log.info("Não foi encontrado o arquivo {}.properties.", sqlFileName);
      }

      // carregar xml
      try {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse( Utils.contentsAsStream(sqlFileName + ".xml") );
        NodeList list = doc.getDocumentElement().getChildNodes();
        for( int i = 0; i < list.getLength(); i++ ){
          Node node = list.item( i );
          if( node.getNodeType() != Node.ELEMENT_NODE ) continue;
          props.put( node.getNodeName(), node.getTextContent() );
        }
        log.info("Carregado arquivo SQL '{}.xml'.", sqlFileName);
      } catch (IOException | SAXException | ParserConfigurationException | NullPointerException e) {
        log.error("Não foi encontrado o arquivo {}.xml.", e);
      }

      // carregar yml
      try {
        conteudo = Utils.contents(sqlFileName + ".yml"); // para ler em UTF-8
        Properties pYaml = new Yaml().loadAs( conteudo, Properties.class);
        //props.putAll(pYaml);
        recMap( props, "", pYaml);
        log.info("Carregado arquivo SQL '{}.yml'.", sqlFileName );
      } catch (Exception e) {
        log.warn("Não foi encontrado o arquivo {}.yml.");
      }
      

      // logs
      for( Object nome : props.keySet() ){
        log.debug("Chave: {}, valor: {}", nome, props.get(nome));
      }

      return props;
  }

  private static void recMap(Properties props, String chave, Object valor){
    if( valor instanceof Map ){
      Map<String, Object> valores = (Map<String, Object>) valor;
      for( String key : valores.keySet() ){
        String novaChave = (chave != null && !chave.isEmpty()) ? chave + "." + key : key ;
        recMap(props, novaChave, valores.get(key));
      }
      return;
    }
    props.put(chave, valor);
  }

}
