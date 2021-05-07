package com.github.wephotos.webwork.core.utils;

import com.github.wephotos.webwork.error.Errors;
import com.github.wephotos.webwork.error.WebworkRuntimeException;

/**
 * @author chengzi
 * @date 2021-01-28 15:23
 */
public class ValidationUtil {
    public static void isTrue(Errors errors, boolean condition) {
        if (!condition) {
            failure(errors);
        }
    }

    public static void isFalse(Errors errors, boolean condition) {
        if (condition) {
            failure(errors);
        }
    }

    public static void failure(Errors errors) {
        throw new WebworkRuntimeException(errors.getCode(), errors.getMessage());
    }
}
