package com.chamberos.chamberosapi.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
   private String email;
   private String password;
   private Location location;

   public void Constructor(String fullName, String email, String password, Location location) {
      this.fullName = fullName;
      this.email = email;
      this.password = password;
      this.location = location;
   }

   public String getEncodePass() {
      return new BCryptPasswordEncoder().encode(this.password);
   }
}
