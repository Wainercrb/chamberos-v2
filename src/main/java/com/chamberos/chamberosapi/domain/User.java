package com.chamberos.chamberosapi.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
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
   private String fullName;
   
   @Indexed(unique = true)
   private String email;

   private String password;

   @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
   private Point location;
}
