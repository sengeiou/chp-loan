package com.creditharmony.loan.common.exception;

public class LoanImportXlsException extends Exception{
	private static final long serialVersionUID = -2551582150028968555L;

	public LoanImportXlsException() {
		super();
	}

	public LoanImportXlsException(String message) {
		super(message);
	}

	public LoanImportXlsException(Throwable cause) {
		super(cause);
	}

	public LoanImportXlsException(String message, Throwable cause) {
		super(message, cause);
	}
}
