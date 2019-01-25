package br.com.rcc_dev.testes.services;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.rcc_dev.testes.App;
import br.com.rcc_dev.testes.entities.db.Person;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class PersonRepoTest {
  
  @Rule
  public ErrorCollector error = new ErrorCollector();
  
  @Autowired
  public PersonRepo personRepo;
  
  @Before
  public void before(){
    
  }
  
  @Test
  public void testarBuscaEntreDatas(){
    List<Person> persons = personRepo.findByBirthdateBetween(LocalDate.parse("1980-01-01"), LocalDate.parse("1991-01-01"));
    error.checkThat(persons, is(hasSize(5)));
  }
  
}