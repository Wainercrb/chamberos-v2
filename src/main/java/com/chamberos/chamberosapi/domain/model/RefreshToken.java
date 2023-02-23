package com.chamberos.chamberosapi.domain.model;

import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotBlank;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("refreshToken")
@Builder
public class RefreshToken {
    @Id
    private String id;

    @NotBlank(message = "Token Value cannot be blank")
    @Indexed(unique = true)
    private String token;

    @NotBlank(message = "Token ExpiryDate cannot be blank")
    private Instant expiryDate;

    @NotNull(message = "Token User cannot be null")
    @Valid
    @DocumentReference(lazy = true)
    private User user;
}
