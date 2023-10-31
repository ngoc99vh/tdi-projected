package com.example.tdiframework.exception;

import com.example.tdiframework.utils.ErrorMessage;

public class ApiException extends BaseException{

    private static final long serialVersionUID = 6210662186057457822L;

    public ApiException(ErrorMessage error) {
        super(error, PrefixErrorCode.TDI);
    }

    public ApiException(ErrorMessage error, Object data) {
        super(error, PrefixErrorCode.TDI, data);
    }
}
