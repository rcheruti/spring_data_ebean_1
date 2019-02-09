package br.com.rcc_dev.testes.borders;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.rcc_dev.testes.interceptors.LogMsg;
import br.com.rcc_dev.testes.interceptors.Permission;
import br.com.rcc_dev.testes.services.PersonRepo;
import br.com.rcc_dev.testes.MsgException;
import br.com.rcc_dev.testes.entities.db.Person;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/person")
public class PersonBorder {

  @Autowired
  private PersonRepo personRepo;

  // ----------------------------------------

  @Permission({ "PERSON" })
  @LogMsg("fazendo contagem")
  @GetMapping("/count")
  public long countPersons() {
    log.info("Contando quantidade de pessoas no DB");
    return personRepo.count();
  }

  @GetMapping
  public List<Person> findPersons(@RequestParam LocalDate date1, @RequestParam LocalDate date2) {
    log.info("Buscando pessos entre as datas {} e {}", date1, date2);
    return personRepo.findByBirthdateBetween(date1, date2);
  }

  @PostMapping
  public Person post(@RequestBody Person person) {
    log.info("Inserindo a pessoa: {}", person);
    return personRepo.save(person);
  }

  @PutMapping
  public Person put(@RequestBody Person person) {
    log.info("Atualizando a pessoa de ID {}: {}", person.getId(), person);
    return personRepo.save(person);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable("id") int id) {
    log.info("Deletando a pessoa de ID {}", id);
    personRepo.deleteById(id);
  }

  // ----------------------------------------

  @PostMapping(value = "/doc", produces = "text/plain")
  public String postDocument(
      @RequestPart("person") Person person, // @RequestBody 
      @RequestPart(value = "file", required = false) MultipartFile file) {
    log.info("Recebemos (person: {}, file: {})", person, file);
    String fileName = "";
    long fileSize = 0;
    if( file != null ){
      try {
        fileName = file.getOriginalFilename();
        fileSize = file.getSize();
        file.transferTo(Paths.get("../", file.getOriginalFilename()));
      } catch (IllegalStateException | IOException e) {
        log.error("Erro ao receber upload de arquivo!", e);
        throw new MsgException("Erro ao gravar arquivo enviado!", e);
      }
    }
    return String.format("O arquivo se chama '%s', e pesa %d bytes.", 
      fileName, fileSize);
  }

  // ----------------------------------------

  @Autowired
  private HttpServletRequest request;

  @GetMapping("/singleton")
  public void testeSingleton(){
    log.info("- this: {}\n- request: {}", this.toString(), request.toString() );
  }

}
