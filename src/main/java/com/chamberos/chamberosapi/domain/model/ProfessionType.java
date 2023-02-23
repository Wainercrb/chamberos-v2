package com.chamberos.chamberosapi.domain.model;

import org.springframework.data.mongodb.core.mapping.Field;

public enum ProfessionType {
    @Field("fulltime")
    FULLTIME,

    @Field("temporal")
    TEMPORAL
}
