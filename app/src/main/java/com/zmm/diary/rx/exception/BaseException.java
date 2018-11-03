package com.zmm.diary.rx.exception;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/5/25
 * Time:上午10:29
 */

public class BaseException extends Exception {



    //Base Exception
    /*连接网络超时*/
    public static final int SOCKET_TIMEOUT_ERROR = 10004;
    /*无网络连接*/
    public static final int SOCKET_ERROR = 10003;
    /*未知错误*/
    public static final int UNKNOWN_ERROR = 10002;



    private int code;

    private String displayMessage;

    public BaseException() {
    }

    public BaseException(int code, String displayMessage) {
        this.code = code;
        this.displayMessage = displayMessage;
    }

    public int getCode() {
        return code;
    }


    public void setCode(int code) {
        this.code = code;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }
}
