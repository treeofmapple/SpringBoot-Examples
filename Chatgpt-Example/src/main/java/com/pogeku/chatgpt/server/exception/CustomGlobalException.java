package com.pogeku.chatgpt.server.exception;

@SuppressWarnings("serial")
public abstract class CustomGlobalException extends RuntimeException {
	public abstract String getMsg();
}
