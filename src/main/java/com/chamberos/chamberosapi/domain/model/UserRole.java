package com.chamberos.chamberosapi.domain.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import javax.validation.constraints.Pattern;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("roles")
@Builder
public class UserRole {
    @Id
    private String id;

    @Pattern(regexp = "^ROLE_.*", message = "ROLE must start with 'ROLE_'")
    @NotBlank(message = "Role Name cannot be blank")
    @Size(min = 1, max = 50)
    @Indexed(unique = true)
    private String name;

    private String description;
}
