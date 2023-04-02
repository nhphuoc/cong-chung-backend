package vn.com.panda.learncardriving.enums;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import vn.com.panda.learncardriving.controller.exception.InvalidDataException;

public enum UserRole {
    USER,
    SUPER_ADMIN,
    ADMIN;

    public static UserRole fromName(String value) {
        return Arrays.stream(values())
                .filter(item -> StringUtils.equalsIgnoreCase(item.name(), value))
                .findFirst()
                .orElseThrow(() -> new InvalidDataException("The role '" + value + "' not found."));
    }
}
