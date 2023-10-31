package com.example.tdiframework.utils;

public enum ErrorMessage {
    INTERNAL_SERVER_ERROR("500", "Internal server error","Hệ thống xảy ra lỗi, Quý khách vui lòng thử lại sau.","Internal server error"),
    NOT_FOUND("404","Not found","Không tồn tại","Not found"),
    NO_DATA("403","No data","Không có dữ liệu","No data"),
    PARAMETER_ERROR("501","Param is required","Tham số bắt buộc nhập","Param is required"),
    DUPLICATE_PASSWORD("498","Duplicate password","Mật khẩu mới trùng với mật khẩu hiện tại","Duplicate password"),
    RE_ENTER_PASSWORD_ERROR("497","New password does not match re-enter password","Mật khẩu mới không khớp với nhập lại mật khẩu","New password does not match re-enter password"),
    CURRENT_PASSWORD_ERROR("496","Current password is incorrect","Mật khẩu hiện tại không chính xác","Current password is incorrect"),
    PHONE_PASSWORD_NOT_FOUND("495","Phone or password incorrect","Số điện thoại hoặc mật khẩu không đúng","Phone or password incorrect"),
    BAD_REQUEST("400","Param is required","Tham số bắt buộc nhập","Param is required"),
    UNAUTHORIZED("401","Unauthorized","Không được phép truy cập","Unauthorized"),
    REGISTERED_PHONE_NUMBER("494","Registered phone number","Số điện thoại đã được đăng ký","Registered phone number"),
    ;
    ErrorMessage(String code, String message, String messageVi, String messageEn) {
        this.code = code;
        this.message = message;
        this.messageVi = messageVi;
        this.messageEn = messageEn;
    }

    ErrorMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;
    private String message;
    private String messageVi;
    private String messageEn;

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
}