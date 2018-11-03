package com.zmm.diary.rx;

import com.zmm.diary.rx.exception.ApiException;
import com.zmm.diary.rx.exception.BaseException;
import com.zmm.diary.utils.ToastUtils;

import java.net.SocketException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;


/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/5/25
 * Time:下午1:33
 */

public class RxErrorHandler {


    public RxErrorHandler() {
    }

    public BaseException handlerError(Throwable e){

        BaseException exception = new BaseException();

        if(e instanceof ApiException){

            exception.setCode(((ApiException) e).getCode());
            exception.setDisplayMessage(((ApiException) e).getDisplayMessage());


        }else if (e instanceof SocketException){

            exception.setCode(BaseException.SOCKET_ERROR);
            exception.setDisplayMessage("服务器繁忙");


        }else if (e instanceof SocketTimeoutException){

            exception.setCode(BaseException.SOCKET_TIMEOUT_ERROR);
            exception.setDisplayMessage("网络请求超时");

        }else if (e instanceof HttpException){

            exception.setCode(((HttpException) e).code());
            exception.setDisplayMessage("网络请求异常");

        }else{

            exception.setCode(BaseException.UNKNOWN_ERROR);
            exception.setDisplayMessage("未知错误");

        }


        return exception;
    }

    public void  showErrorMessage(BaseException e){


        ToastUtils.SimpleToast(e.getDisplayMessage());

    }
}
