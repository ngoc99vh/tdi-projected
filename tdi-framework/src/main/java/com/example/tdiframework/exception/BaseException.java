package com.example.tdiframework.exception;


import com.example.tdiframework.utils.ErrorMessage;

public class BaseException extends RuntimeException{

    private static final long serialVersionUID = 1230941668611398418L;

    private String code;
    private String fullCode;

    private String message;

    private Object data;

    public BaseException(String code, String message, String prefix) {
        this.code = code;
        this.fullCode = (prefix == null || prefix.isEmpty()) ? code : prefix + "-" + code;
        this.message = message;
    }

    public BaseException(ErrorMessage error, String prefix) {
        this.code = error.getCode();
        this.fullCode = (prefix == null || prefix.isEmpty()) ? code : prefix + "-" + code;
        this.message = error.getMessage();
    }

    public BaseException(ErrorMessage error, String prefix, Object data) {
        this.code = error.getCode();
        this.fullCode = (prefix == null || prefix.isEmpty()) ? code : prefix + "-" + code;
        this.message = error.getMessage();
        this.data = data;
    }

    public BaseException(String code, String prefix, String message, Object data) {
        this.code = code;
        this.fullCode = (prefix == null || prefix.isEmpty()) ? code : prefix + "-" + code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFullCode() {
        return fullCode;
    }

    public void setFullCode(String fullCode) {
        this.fullCode = fullCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public interface PrefixErrorCode {
        String TDI = "TDI-API";
        String AUTH = "TDI-AUTH";
    }
}
