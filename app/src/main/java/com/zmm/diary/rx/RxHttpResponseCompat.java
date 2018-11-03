package com.zmm.diary.rx;

import com.zmm.diary.bean.BaseBean;
import com.zmm.diary.rx.exception.ApiException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/5/25
 * Time:上午9:55
 */

public class RxHttpResponseCompat {


    public static <T>ObservableTransformer<BaseBean<T>,T> compatResult(){

        return new ObservableTransformer<BaseBean<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseBean<T>> baseBeanObservable) {

                return baseBeanObservable.flatMap(new Function<BaseBean<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(final BaseBean<T> tBaseBean) {

                        if(tBaseBean.getCode() == 200){
                            return Observable.create(new ObservableOnSubscribe<T>() {
                                @Override
                                public void subscribe(ObservableEmitter<T> subscriber) {
                                    try {
                                        subscriber.onNext(tBaseBean.getData());
                                        subscriber.onComplete();

                                    }catch (Exception e){
                                        subscriber.onError(e);
                                    }
                                }
                            });
                        }else {
                            return Observable.error(new ApiException(tBaseBean.getCode(),tBaseBean.getMessage()));
                        }
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}
