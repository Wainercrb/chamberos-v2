package com.chamberos.chamberosapi.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.geo.Point;
import com.chamberos.chamberosapi.config.validations.ValidPoint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document("users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {
   @Id
   private String id;

   @NotBlank(message = "Full Name cannot be blank")
   @Size(min = 1, max = 50)
   private String fullName;

   @NotBlank(message = "Email cannot be blank")
   @Size(min = 1, max = 50)
   @Email
   @Indexed(unique = true)
   private String email;

   @NotBlank(message = "Password cannot be blank")
   @Size(min = 1, max = 50)
   private String password;

   @NotNull(message = "Location cannot be null")
   @Valid
   @ValidPoint
   @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
   private Point location;
}
