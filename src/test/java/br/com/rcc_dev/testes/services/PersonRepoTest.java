package br.com.rcc_dev.testes.services;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.greaterThan;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.rcc_dev.testes.App;
import br.com.rcc_dev.testes.Utils;
import br.com.rcc_dev.testes.entities.db.Person;
import io.ebean.Ebean;
import io.ebean.ExpressionFactory;
import io.ebean.FetchConfig;
import lombok.extern.slf4j.Slf4j;

@Ignore
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class PersonRepoTest {
  
  @Rule
  public ErrorCollector error = new ErrorCollector();
  
  @Autowired
  public PersonRepo personRepo;
  
  // ===================================
  
  @Test
  @Ignore
  public void testarBuscaEntreDatas(){
    List<Person> persons = personRepo.findByBirthdateBetween(LocalDate.parse("1980-01-01"), LocalDate.parse("1991-01-01"));
    error.checkThat(persons, hasSize(5) );

    persons = personRepo.buscarPorNome("ne");
    error.checkThat(persons, hasSize(3) );
    
    persons = personRepo.findByCars_ColorContaining("re");
    error.checkThat(persons, hasSize(4) );

    persons = personRepo.findByBirthdateBetween(LocalDate.parse("1980-01-01"), LocalDate.parse("1991-01-01"), PageRequest.of(0, 4) );
    for(Person x : persons) log.info("Idade: {}, '{}', {}", x.getAge(), x.getBirthdate().toString(), x.getName() );
    error.checkThat(persons, hasSize(4) );

    // ---

    persons = personRepo.encontrar(LocalDate.parse("1980-01-01"), LocalDate.parse("1991-01-01"));
    for(Person x : persons) log.info("Idade pelo método padrão: {}, '{}', {}", x.getAge(), x.getBirthdate().toString(), x.getName() );

  }
  
  @Test
  @Ignore
  public void testarCache(){
    ExpressionFactory ef = Ebean.getExpressionFactory();
    List<Person> persons = 
      Ebean.find(Person.class)
      .setFirstRow(0)
      .setMaxRows(4)
      .fetch("cars")
      .where( ef.between("birthdate", LocalDate.parse("1980-01-01"), LocalDate.parse("1991-01-01")) )
      .findList();
    for(Person x : persons) log.info("Person: {}", x.getId() );

    log.info("Person: {}", Ebean.find(Person.class, 3).getId() );
    log.info("Person: {}", Ebean.find(Person.class, 3).getId() );
  }

  @Test
  public void testarXML(){
    String sql;
    
    sql = Utils.sql("sqlCarregarPerson");
    error.checkThat( sql.length(), greaterThan(0) );
    sql = Utils.sql("carregarTudo");
    error.checkThat( sql.length(), greaterThan(0) );
    sql = Utils.sql("sql.carregar.car");
    error.checkThat( sql.length(), greaterThan(0) );
  }

}