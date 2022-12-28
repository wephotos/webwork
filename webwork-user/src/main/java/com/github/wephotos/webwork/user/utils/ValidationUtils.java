package com.github.wephotos.webwork.user.utils;

import com.github.wephotos.webwork.schema.exception.StateCode;
import com.github.wephotos.webwork.schema.exception.WebworkRuntimeException;

/**
 * @author chengzi
 * @date 2021-01-28 15:23
 */
public class ValidationUtils {
	
	public static void isTrue(boolean condition, StateCode code) {
        isTrue(condition, code, null);
    }

    public static void isTrue(boolean condition, StateCode code, String message) {
        if (!condition) {
            failure(code, message);
        }
    }

    public static void isFalse(boolean condition, StateCode code) {
        isFalse(condition, code, null);
    }
    
    public static void isFalse(boolean condition, StateCode code, String message) {
        if (condition) {
            failure(code, message);
        }
    }

    public static void failure(StateCode code, String message) {
        throw new WebworkRuntimeException(code, message);
    }
}
