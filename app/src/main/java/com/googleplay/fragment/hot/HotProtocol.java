package com.googleplay.fragment.hot;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googleplay.core.Constant;
import com.googleplay.core.protocol.BaseProtocol;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author TanJJ
 * @time 2018/7/15 20:39
 * @des 排序页面的网络请求协议
 */

public class HotProtocol extends BaseProtocol<List<String>> {
    @Override
    protected String getInterfacePath() {
        return Constant.HOT;
    }

    @Override
    protected List<String> parseJson(String json) {
        Type type = new TypeToken<List<String>>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }
}
