package br.com.rcc_dev.testes.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.ebean.annotation.Query;
import org.springframework.data.ebean.repository.EbeanRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.rcc_dev.testes.interceptors.LogMsg;
import io.ebean.ExpressionFactory;
import br.com.rcc_dev.testes.entities.db.Person;

/**
 * !! Atenção: <br>
 * No momento a Impl. do repositório Ebean não tem suporte para ordenações. <br>
 * Isso vale tanto ordenações pelo padrão "...OrderBy..." no nome dos métodos, como
 * para o uso da classe {@code Sort} do <b>Spring Data</b>! <br>
 * <br>
 * Ainda é possível fazer ordenações com a anotação {@code Query}, ou com métodos padrão usando
 * o Servidor do EBean diretamente.
 */
@Repository
public interface PersonRepo extends EbeanRepository<Person, Integer> {
  
  @LogMsg("chamando Repository de Person") // testar se o AspectJ intercepta a Impl. das interfaces do Spring Data
  List<Person> findByBirthdateBetween(LocalDate date1, LocalDate date2);

  @Query("where birthdate between :date1 and :date2 order by birthdate desc")
  List<Person> findByBirthdateBetween(@Param("date1") LocalDate date1, @Param("date2") LocalDate date2, Pageable page);

  @Query("fetch cars where name like %:nome%")
  List<Person> buscarPorNome(@Param("nome") String nome);
  
  List<Person> findByCars_ColorContaining(String color);

  // ====================================

  // é possível criar métodos padrão na própria interface
  default List<Person> encontrar(LocalDate date1, LocalDate date2){
    ExpressionFactory ef = db().getExpressionFactory();
    return db().find(Person.class)
          .where( ef.between("birthdate", date1, date2) )
          .order("birthdate desc").findList();
  }

}
