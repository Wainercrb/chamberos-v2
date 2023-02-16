package com.chamberos.chamberosapi.utils;

import org.bson.types.ObjectId;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomObjectId {
    public List<ObjectId> getObjectIdFromString(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        List<ObjectId> validIds = idList.stream()
                .filter(ObjectId::isValid)
                .map(ObjectId::new)
                .collect(Collectors.toList());
        return validIds;
    }
}
