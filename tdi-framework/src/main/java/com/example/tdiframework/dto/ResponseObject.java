package com.example.tdiframework.dto;


import com.example.tdiframework.utils.ErrorMessage;

public class ResponseObject<T> {
    private String code;
    private String message;
    private String messageVi;
    private String messageEn;
    private T data;

    public ResponseObject(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseObject(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseObject() {

    }

    public ResponseObject(String code, String message, String messageVi, String messageEn, T data) {
        this.code = code;
        this.message = message;
        this.messageVi = messageVi;
        this.messageEn = messageEn;
        this.data = data;
    }

    public static <T> ResponseObject<T> success(T data) {
        return new ResponseObject<>("200", "Success", data);
    }

    public static <T> ResponseObject<T> success() {
        return new ResponseObject<>("200", "Success");
    }

    public static <T> ResponseObject<T> success(String messageVi, String messageEn, T data) {
        return new ResponseObject<>("200", "Success", messageVi, messageEn, data);
    }

    public static <T> ResponseObject<T> error(ErrorMessage message) {
        return new ResponseObject<>(message.getCode(), message.getMessage());
    }

    public static <T> ResponseObject<T> error(String code, String message, T data) {
        return new ResponseObject<>(code, message, data);
    }

    public static <T> ResponseObject<T> error(String code,String message, String messageVi, String messageEn, T data) {
        ResponseObject<T> res = new ResponseObject<>(code, message, data);
        res.setMessageEn(messageEn);
        res.setMessageVi(messageVi);
        return res;
    }

    public static <T> ResponseObject<T> error(ErrorMessage message, T data) {
        ResponseObject<T> res = new ResponseObject<>(message.getCode(), message.getMessage(), data);
        res.setMessageEn(message.getMessageEn());
        res.setMessageVi(message.getMessageVi());
        return res;
    }

    public static <T> ResponseObject<T> error(String prefixCode, ErrorMessage message, T data) {
        ResponseObject<T> res = new ResponseObject<>(prefixCode + "-" + message.getCode(), message.getMessage(), data);
        res.setMessageEn(message.getMessageEn());
        res.setMessageVi(message.getMessageVi());
        return res;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageVi() {
        return messageVi;
    }

    public void setMessageVi(String messageVi) {
        this.messageVi = messageVi;
    }

    public String getMessageEn() {
        return messageEn;
    }

    public void setMessageEn(String messageEn) {
        this.messageEn = messageEn;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
