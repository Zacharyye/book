package com.zacharye.book.util;

public class Result<T>{
    private static final int SUCCESS_CODE = 0;
    private static final int FAILURE_CODE = -1;
    private int status;
    private T data;
    private String msg;

    public Result(){}

    public Result success(){
        this.status = SUCCESS_CODE;
        this.data = null;
        this.msg = "";
        return this;
    }

    public Result success(T t){
        this.status = SUCCESS_CODE;
        this.data = t;
        this.msg = "";
        return this;
    }

    public Result failure(){
        this.status = FAILURE_CODE;
        this.data = null;
        this.msg = "";
        return this;
    }

    public Result failure(String msg){
        this.status = FAILURE_CODE;
        this.data = null;
        this.msg = msg;
        return this;
    }

    public Result failure(T t){
        this.status = FAILURE_CODE;
        this.data = t;
        this.msg = "";
        return this;
    }

    public boolean isSuccess () {
        return this.status == SUCCESS_CODE;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
