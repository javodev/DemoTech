package com.demo.callengeTech.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Person")
@RequiredArgsConstructor
public class PersonEntity{

    @Id
    @Getter
    @Setter
    @Column(name = "personid")
    public String id;

    @Getter
    @Setter
    @JsonProperty("lastname")
    @Column(name = "lastname")
    private String lastName;

    @Getter
    @Setter
    @JsonProperty("firstname")
    @Column(name = "firstname")
    private String firstName;
}
