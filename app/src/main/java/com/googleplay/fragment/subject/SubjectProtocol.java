package com.googleplay.fragment.subject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googleplay.core.Constant;
import com.googleplay.core.protocol.BaseProtocol;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author TanJJ
 * @time 2018/7/8 19:29
 * @des 专题页面的网络协议
 */

public class SubjectProtocol extends BaseProtocol<List<SubjectBean>> {
    @Override
    protected String getInterfacePath() {
        return Constant.SUBJECT;
    }

    @Override
    protected List<SubjectBean> parseJson(String json) {
        Type type = new TypeToken<List<SubjectBean>>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }
}
