package com.tom.sample.base.exception;

import com.tom.sample.base.exception.global.DateGlobalException;

import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
public class InvalidDateException extends DateGlobalException {
	
	public InvalidDateException(String msg) {
		super(msg);
	}
	
	public InvalidDateException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
