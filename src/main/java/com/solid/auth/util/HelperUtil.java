package com.solid.auth.util;

import org.springframework.util.ClassUtils;

import java.util.Collection;
import java.util.Objects;

public class HelperUtil {
    public static boolean isArrayOrCollection(Object obj) {

        if (Objects.nonNull(obj)) {
            if (obj.getClass().isArray()) {
                return true;
            } else {
                return ClassUtils.isAssignable(obj.getClass(), Collection.class);
            }
        }
        return false;
    }
}
