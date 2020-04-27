package com.yptake.yplearnproject.mvp.model.entity;


/**
 * 基类实体
 */
public class BaseJson<T> {

    public int code;
    private T data;
    private T result;
    public String msg;
    public boolean success;
    public boolean failed;
    public String reason;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BaseJson{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                ", success=" + success +
                ", failed=" + failed +
                ", reason='" + reason + '\'' +
                '}';
    }

}

