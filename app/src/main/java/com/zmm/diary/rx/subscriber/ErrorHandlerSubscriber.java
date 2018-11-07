package com.zmm.diary.rx.subscriber;

import com.zmm.diary.rx.RxErrorHandler;
import com.zmm.diary.rx.exception.BaseException;

import io.reactivex.disposables.Disposable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/5/25
 * Time:上午11:48
 */

public abstract class ErrorHandlerSubscriber<T> extends DefaultSubscriber<T> {


    protected RxErrorHandler mRxErrorHandler;

    public ErrorHandlerSubscriber(){

        mRxErrorHandler = new RxErrorHandler();

    }

    @Override
    public void onError(Throwable e) {


//        e.printStackTrace();
        BaseException baseException =  mRxErrorHandler.handlerError(e);

        if(baseException == null){
            e.printStackTrace();
        } else {
            mRxErrorHandler.showErrorMessage(baseException);
        }

    }


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onComplete() {

    }
}
