package com.mjeanroy.springhub.exceptions;

public class UploadException extends ApplicationException {

	private UploadExceptionType type;

	public UploadException(UploadExceptionType type, String msg) {
		super(msg);
		this.type = type;
	}

	@Override
	public String getType() {
		return "UPLOAD_" + this.type.toString();
	}

	public static enum UploadExceptionType {
		MULTIPART_HTTP,
		FILE_NOT_FOUND,
		FILE_EMPTY,
		FILE_TYPE
	}
}
