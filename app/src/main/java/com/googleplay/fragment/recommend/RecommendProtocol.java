package com.googleplay.fragment.recommend;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googleplay.core.Constant;
import com.googleplay.core.protocol.BaseProtocol;
import com.googleplay.core.util.log.GLogger;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author TanJJ
 * @time 2018/7/15 20:02
 * @des 推荐页面的网络请求协议
 */

public class RecommendProtocol extends BaseProtocol<List<String>> {
    @Override
    protected String getInterfacePath() {
        return Constant.RECOMMEND;
    }

    @Override
    protected List<String> parseJson(String json) {
        GLogger.json("parseJson",json);
        Type type = new TypeToken<List<String>>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }
}
