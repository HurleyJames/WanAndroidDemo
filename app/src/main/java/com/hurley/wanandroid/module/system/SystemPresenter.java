package com.hurley.wanandroid.module.system;



import android.annotation.SuppressLint;

import com.hurley.wanandroid.api.ApiService;
import com.hurley.wanandroid.base.BaseBean;
import com.hurley.wanandroid.base.BasePresenter;
import com.hurley.wanandroid.net.RetrofitManager;
import com.hurley.wanandroid.net.callback.RxSchedulers;

import javax.inject.Inject;

/**
 * <pre>
 *      @author hurley
 *      date    : 2019/3/9 6:40 PM
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public class SystemPresenter extends BasePresenter<SystemContract.View> implements SystemContract.Presenter {
    @Inject
    public SystemPresenter() {

    }

    @SuppressLint("CheckResult")
    @Override
    public void loadSystems() {
        //显示加载进度条
        mView.showLoading();
        RetrofitManager.create(ApiService.class)
                .getSystem()
                .compose(RxSchedulers.applySchedulers())
                .compose(mView.bindToLife())
                .subscribe(response -> {
                    if (response.getErrorCode() == BaseBean.SUCCESS) {
                        mView.setSystems(response.getData());
                    } else {
                        mView.showFailed(response.getErrorMsg());
                    }
                }, throwable -> mView.showFailed(throwable.getMessage()));

    }

    @Override
    public void refresh() {
        //刷新，重新加载数据
        loadSystems();
    }
}