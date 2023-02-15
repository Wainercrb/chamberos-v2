package com.chamberos.chamberosapi.config.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.data.geo.Point;

public class PointValidator implements ConstraintValidator<ValidPoint, Point> {

    @Override
    public void initialize(ValidPoint constraintAnnotation) {
    }

    @Override
    public boolean isValid(Point value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // null values are considered valid
        }
        return value.getX() >= -180 && value.getX() <= 180 && value.getY() >= -90 && value.getY() <= 90;
    }
}