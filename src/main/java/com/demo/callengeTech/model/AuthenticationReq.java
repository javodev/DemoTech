package com.demo.callengeTech.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@RequiredArgsConstructor
public class AuthenticationReq implements Serializable {

  private static final long serialVersionUID = 1L;

  @Getter
  @Setter
  private String usuario;

  @Getter
  @Setter
  private String clave;
}
