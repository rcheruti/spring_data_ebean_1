package br.com.rcc_dev.testes.borders;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.rcc_dev.testes.entities.db.Person;
import br.com.rcc_dev.testes.services.PersonRepo;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
public class PersonBorder {
  
  @Autowired
  private PersonRepo personRepo;
  
  // ----------------------------------------
  
  @GetMapping("/person/count")
  public long countPersons(){
    log.info("Contand quantidade de pessoas no DB");
    return personRepo.count();
  }
  
  
  @GetMapping("/person")
  public List<Person> findPersons(@RequestParam LocalDate date1, @RequestParam LocalDate date2){
    log.info("Buscando pessos entre as datas {} e {}", date1, date2);
    return personRepo.findByBirthdateBetween(date1, date2);
  }
  
}
