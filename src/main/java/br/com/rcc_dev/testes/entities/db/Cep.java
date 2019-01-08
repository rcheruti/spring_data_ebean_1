package br.com.rcc_dev.testes.entities.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Table(name = "cep")
public class Cep {
  
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;
  
  @Column(name = "bairro")
  private String bairro;
  
  @Column(name = "cep")
  private String cep;
  
  @Column(name = "cidade")
  private String cidade;
  
  @Column(name = "complemento")
  private String complemento;
  
  @Column(name = "complemento2")
  private String complemento2;
  
  @Column(name = "endereco")
  private String endereco;
  
  @Column(name = "uf")
  private String uf;
  
  // ----------------------------
  
  @Transient
  @Builder.Default
  private boolean database = false;
  
  
}