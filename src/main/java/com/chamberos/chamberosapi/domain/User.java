package com.chamberos.chamberosapi.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document("users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
   @Id
   private String id;
   private String fullName;
   private String password;
   private Location location;

   public void Constructor(String fullName, String password, Location location) {
      this.fullName = fullName;
      this.password = password;
      this.location = location;
   }
}
