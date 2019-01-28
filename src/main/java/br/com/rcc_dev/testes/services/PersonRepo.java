package br.com.rcc_dev.testes.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.ebean.repository.EbeanRepository;
import org.springframework.stereotype.Repository;

import br.com.rcc_dev.testes.interceptors.LogMsg;
import br.com.rcc_dev.testes.entities.db.Person;

@Repository
public interface PersonRepo extends EbeanRepository<Person, Integer> {
  
  @LogMsg("chamando Repository de Person")
  public List<Person> findByBirthdateBetween(LocalDate date1, LocalDate date2);
  
}
