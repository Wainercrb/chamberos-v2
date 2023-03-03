package com.chamberos.chamberosapi.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotBlank;
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

   @NotBlank(message = "Profession name cannot be blank")
   @Size(min = 3, max = 50)
   private String name;

   private ProfessionType type;
}
