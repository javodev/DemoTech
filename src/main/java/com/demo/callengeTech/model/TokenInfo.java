package com.demo.callengeTech.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@RequiredArgsConstructor
public class TokenInfo implements Serializable {

  private static final long serialVersionUID = 1L;

  @Getter
  @Setter
  private final String jwtToken;
}
