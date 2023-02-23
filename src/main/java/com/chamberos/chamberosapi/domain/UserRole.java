package com.chamberos.chamberosapi.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {
    @Id
    private String id;

    @NotBlank(message = "Role Name cannot be blank")
    @Size(min = 1, max = 50)
    private String name;

    @NotBlank(message = "Role Description cannot be blank")
    @Size(min = 1, max = 50)
    private String description;
}
