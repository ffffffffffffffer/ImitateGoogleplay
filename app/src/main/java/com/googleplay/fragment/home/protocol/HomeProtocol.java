package com.googleplay.fragment.home.protocol;

import com.google.gson.Gson;
import com.googleplay.core.Constant;
import com.googleplay.core.protocol.BaseProtocol;
import com.googleplay.fragment.home.bean.HomeBean;

/**
 * @author TanJJ
 * @time 2018/7/8 8:22
 * @des 主页的网络请求协议
 */

public class HomeProtocol extends BaseProtocol<HomeBean> {
    @Override
    protected String getInterfacePath() {
        return Constant.HOME;
    }

    @Override
    protected HomeBean parseJson(String json) {
        return new Gson().fromJson(json, HomeBean.class);
    }
}
