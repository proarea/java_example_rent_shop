package com.snowboard_rental_crm.shared_module.util;

import com.snowboard_rental_crm.shared_data.exception.BadRequestException;
import com.snowboard_rental_crm.shared_data.exception.FileNotFoundException;
import com.snowboard_rental_crm.shared_data.exception.NotFoundException;
import com.snowboard_rental_crm.shared_data.exception.StartupException;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
public class ValidationUtil {

    public <E> void validateItemsUnique(List<E> items, String message) {
        Map<E, Integer> itemCounterMap = new HashMap<>();
        items.forEach(item -> {
            if (itemCounterMap.containsKey(item)) {
                itemCounterMap.put(item, itemCounterMap.get(item) + 1);
            } else {
                itemCounterMap.put(item, 1);
            }
            ValidationUtil.validateOrBadRequest(
                    (itemCounterMap.get(item) == 1),
                    String.format(message, item)
            );
        });
    }

    @SneakyThrows
    public void validateOrBadRequest(boolean condition, String message) {
        validate(condition, message, BadRequestException.class);
    }

    @SneakyThrows
    public void validateOrNotFound(boolean condition, String message) {
        validate(condition, message, NotFoundException.class);
    }

    @SneakyThrows
    public void validateOrFileNotFound(boolean condition, String message) {
        validate(condition, message, FileNotFoundException.class);
    }

    @SneakyThrows
    public void validateOrStartupException(boolean condition, String message) {
        validate(condition, message, StartupException.class);
    }

    @SneakyThrows
    private void validate(boolean condition, String message, Class<? extends RuntimeException> exceptionClazz) {
        if (!condition) {
            Constructor<? extends RuntimeException> defaultConstructor = exceptionClazz.getConstructor(String.class);
            throw defaultConstructor.newInstance(message);
        }
    }
}
