package com.chamberos.chamberosapi.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document("professions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Profession {
   @Id
   private String id;
   private String name;
}
