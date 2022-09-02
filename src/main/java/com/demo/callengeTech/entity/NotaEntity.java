package com.demo.callengeTech.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "notas")
@RequiredArgsConstructor
public class NotaEntity {
  @Id
  @Getter
  @Setter
  @JsonProperty("notaid")
  @Column(name = "notaid")
  public Integer id;

  @Getter
  @Setter
  @Column(name = "personid")
  private String personid;

  @Getter
  @Setter
  @JsonProperty("nota")
  @Column(name = "nota")
  private Double nota;
}
