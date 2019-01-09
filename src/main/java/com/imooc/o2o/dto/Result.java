package com.imooc.o2o.dto;

/**
 * 记录商品 操作是否成功 返回数据
 * 错误信息
 * 如果成功是需要构造器返回数据
 * 如果失败通过相应的构造器返回错误信息
 * @author lixw
 * @date created in 12:27 2019/1/8
 */
public class Result <T>{
    /**
     * 是否成功的标志
     */
    private boolean success;

    /**
     * 成功时返回的数据
     */
    private T data;

    /**
     * 错误信息
     */
    private String errorMsg;

    private int errorCode;

    public Result() {

    }

    /**
     * 成功的构造方法
     *
     * @param success
     * @param data
     */
    public Result(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    /**
     * 错误的构造方法
     *
     * @param success
     * @param errorMsg
     * @param errorCode
     */
    public Result(boolean success, int errorCode, String errorMsg) {
        this.success = success;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
