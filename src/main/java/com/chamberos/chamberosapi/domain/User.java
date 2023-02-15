package com.chamberos.chamberosapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class User {
   private String id;
   private String fullName;
   private String password;
   private Location location;
}
