package br.com.rcc_dev.testes.entities.db;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "person")
public class Person {
  
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;
  
  @Column(name = "name")
  private String name;
  
  @Temporal(TemporalType.DATE)
  @Column(name = "birthdate")
  private LocalDate birthdate;
  
  @Column(name = "sex")
  private boolean sex;
  
  // ----------------------------------------
  
  @Transient
  private long age;
  
  @PostLoad
  public void calculateAge(){
    age = ChronoUnit.YEARS.between(birthdate, LocalDate.now());
  }
  
}
