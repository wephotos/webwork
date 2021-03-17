package com.github.wephotos.webwork.file.stor;

import com.github.wephotos.webwork.error.WebworkRuntimeException;

/**
 * 文件存储异常
 * @author TQ
 *
 */
public class FileStorException extends WebworkRuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FileStorException(String message) {
        super(message);
    }
	
	public FileStorException(String message, Throwable cause) {
        super(message, cause);
    }
}
