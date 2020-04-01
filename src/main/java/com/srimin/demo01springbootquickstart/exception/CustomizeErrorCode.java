package com.srimin.demo01springbootquickstart.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    POSTS_NOT_FOUND (2001,"页面不存在或已被删除"),
    TARGET_PARENT_NOT_FOUND(2002,"未选中贴子进行回复"),
    NO_LOGIN(2003,"未登录"),
    SYS_ERROR(2004,"服务器正在忙..."),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"评论不存在或已被删除"),
    COMMENT_IS_EMPTY(2007,"评论不能为空"),
    ;
    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
