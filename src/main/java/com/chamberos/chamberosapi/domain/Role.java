package com.chamberos.chamberosapi.domain;

import org.springframework.data.mongodb.core.mapping.Field;

public enum Role {
    @Field("fulltime")
    FULLTIME,

    @Field("temporal")
    TEMPORAL
}
