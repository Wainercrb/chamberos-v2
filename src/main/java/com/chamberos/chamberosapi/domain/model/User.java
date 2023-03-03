package com.chamberos.chamberosapi.domain.model;

import javax.validation.constraints.Pattern;
import java.util.List;
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
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import com.chamberos.chamberosapi.domain.customvalidations.ValidPoint;
import com.chamberos.chamberosapi.utils.AppConstants;
import org.springframework.data.geo.Point;
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

   @NotBlank(message = "User Full Name cannot be blank")
   @Size(min = 3, max = 50)
   private String fullName;

   @NotBlank(message = "User Email cannot be blank")
   @Size(min = 3, max = 50)
   @Email
   @Indexed(unique = true)
   private String email;

   @NotBlank(message = "User Username cannot be blank")
   @Size(min = 3, max = 50)
   @Indexed(unique = true)
   private String username;

   @NotBlank(message = "User Password cannot be blank")
   @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$", message = AppConstants.PASSWORD_REGEX_ERROR_MESSAGE)
   private String password;

   @NotNull(message = "User Is Active cannot be blank")
   private Boolean isActive;

   @NotNull(message = "User Location cannot be null")
   @Valid
   @ValidPoint
   @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
   private Point location;

   @NotNull(message = "User Professions cannot be null")
   @Valid
   @DocumentReference(lazy = true)
   private List<Profession> professions;

   @NotNull(message = "User Roles cannot be null")
   @Valid
   @DocumentReference(lazy = true)
   private List<UserRole> roles;
}
