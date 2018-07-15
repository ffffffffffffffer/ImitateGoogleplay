package com.googleplay.fragment.category;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googleplay.core.Constant;
import com.googleplay.core.protocol.BaseProtocol;
import com.googleplay.fragment.category.bean.CategoryBean;
import com.googleplay.fragment.category.vo.CategoryVo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author TanJJ
 * @time 2018/7/8 21:52
 * @des 分类页面的网络协议
 */

public class CategoryProtocol extends BaseProtocol<List<CategoryBean>> {
    @Override
    protected String getInterfacePath() {
        return Constant.CATEGORY;
    }

    @Override
    protected List<CategoryBean> parseJson(String json) {
        // 把数据都储存到指定的Bean中
        List<CategoryBean> categoryBeanList = new ArrayList<>();
        // 解析json数据
        // 解析泛型类
        Type type = new TypeToken<List<CategoryVo>>() {
        }.getType();
        List<CategoryVo> categoryVos = new Gson().fromJson(json, type);
        // 遍历vo,把它的所有数据转移到指定的Bean
        int size = categoryVos.size();
        for (int i = 0; i < size; i++) {
            CategoryVo categoryVo = categoryVos.get(i);
            // 将json数据分成两部分,title和内容
            // 储存title部分
            CategoryBean titleBean = new CategoryBean();
            titleBean.title = categoryVo.title;
            titleBean.type = CategoryBean.TYPE_TITLE;
            categoryBeanList.add(titleBean);
            // 遍历分类信息
            List<CategoryVo.CategoryInfoBean> infos = categoryVo.infos;
            int infoSize = infos.size();
            for (int j = 0; j < infoSize; j++) {
                // 储存内容info部分
                CategoryBean infoBean = new CategoryBean();
                CategoryVo.CategoryInfoBean categoryInfoBean = infos.get(j);
                infoBean.type = CategoryBean.TYPE_ITEM;
                infoBean.name1 = categoryInfoBean.name1;
                infoBean.name2 = categoryInfoBean.name2;
                infoBean.name3 = categoryInfoBean.name3;
                infoBean.url1 = categoryInfoBean.url1;
                infoBean.url2 = categoryInfoBean.url2;
                infoBean.url3 = categoryInfoBean.url3;
                categoryBeanList.add(infoBean);
            }
        }
        return categoryBeanList;
    }
}
