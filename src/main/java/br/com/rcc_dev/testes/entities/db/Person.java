package br.com.rcc_dev.testes.entities.db;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import io.ebean.annotation.Cache;
import io.ebean.annotation.DbEnumValue;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Cache(naturalKey = "id")
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


  @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
  private List<Car> cars;


  // ----------------------------------------

  @Version
  private Instant version;

  @WhenCreated
  @Column(name = "created")
  private LocalDateTime created;

  @WhenModified
  @Column(name = "modified")
  private LocalDateTime modified;
  
  // ----------------------------------------
  
  @Transient
  private long age;
  
  @PostLoad
  @PostPersist
  @PostUpdate
  public void calculateAge(){
    age = ChronoUnit.YEARS.between(birthdate, LocalDate.now());
  }
  
  // ----------------------------------------

  public static enum Sexo {
    MASCULINO(true, "Masculino"),
    FEMININO(false, "Femino"),
    ;
    public final boolean tipo;
    public final String descricao;
    Sexo(boolean tipo, String descricao) {
      this.tipo = tipo;
      this.descricao = descricao;
    }
    @DbEnumValue
    public boolean getForDb() {
      return this.tipo;
    }
  }
  
}
