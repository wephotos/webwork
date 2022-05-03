package com.github.wephotos.webwork.user.utils;

import com.github.wephotos.webwork.schema.exception.StateCode;
import com.github.wephotos.webwork.schema.exception.WebworkRuntimeException;

/**
 * @author chengzi
 * @date 2021-01-28 15:23
 */
public class ValidationUtil {
    public static void isTrue(StateCode code, boolean condition) {
        if (!condition) {
            failure(code);
        }
    }

    public static void isFalse(StateCode code, boolean condition) {
        if (condition) {
            failure(code);
        }
    }

    public static void failure(StateCode code) {
        throw new WebworkRuntimeException(code, "");
    }
}
