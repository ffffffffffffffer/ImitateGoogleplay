package com.googleplay.fragment.game;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googleplay.core.Constant;
import com.googleplay.core.protocol.BaseProtocol;
import com.googleplay.fragment.home.bean.AppInfo;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author TanJJ
 * @time 2018/7/8 11:29
 * @des 游戏页面的网络协议
 */

public class GameProtocol extends BaseProtocol<List<AppInfo>> {
    @Override
    protected String getInterfacePath() {
        return Constant.GAME;
    }

    @Override
    protected List<AppInfo> parseJson(String json) {
        // GSON泛型解析
        Type type = new TypeToken<List<AppInfo>>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }
}
