package com.nlte.smartcity.base.impl;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nlte.smartcity.R;
import com.nlte.smartcity.base.BaseMenuDetailPager;
import com.nlte.smartcity.domain.NewsBean;
import com.nlte.smartcity.domain.NewsMenuBean;
import com.nlte.smartcity.global.GlobalConstants;
import com.nlte.smartcity.utils.ToastUtil;

/**
 * 描述：页签详情页
 *
 * @author NLTE
 * @time 2016/4/24 0024 15:31
 */
public class TabdetailPager extends BaseMenuDetailPager{
    private NewsMenuBean.NewsTabData mNewsTabData;//页签网络数据对象
    private TextView mView;
    @ViewInject(R.id.vp_top_news)
    private ViewPager mVpTopNews;
    @ViewInject(R.id.lv_news)
    private ListView mLvNews;
    private final String mUrl;//页签网络数据URL

    public TabdetailPager(Activity activity, NewsMenuBean.NewsTabData newsTabData) {
        super(activity);
        this.mNewsTabData = newsTabData;
        mUrl = GlobalConstants.SERVER_URL + mNewsTabData.url;
        //initData();
    }


    @Override
    public View initView() {
        /*mView = new TextView(mActivity);
        mView.setText("页签详情页");
        mView.setTextColor(Color.RED);
        mView.setGravity(Gravity.CENTER);*/

        View view = View.inflate(mActivity, R.layout.pager_tab_detail, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        //从服务器获取数据
        //mView.setText(mNewsTabData.title);
        getDataFromService();
    }

    //从服务器获取数据
    private void getDataFromService() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.GET, mUrl, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;//服务器返回的数据
                //解析数据
                processData(result);
            }

            //请求失败
            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
                ToastUtil.show(mActivity, msg);
            }
        });
    }
    //解析数据
    private void processData(String result) {
        Gson gson = new Gson();
        NewsBean newsBean = gson.fromJson(result, NewsBean.class);
        System.out.println("NewsBean结果"+newsBean);
    }
}
