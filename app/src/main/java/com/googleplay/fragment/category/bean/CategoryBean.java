package com.googleplay.fragment.category.bean;

/**
 * @author TanJJ
 * @time 2018/7/8 21:53
 * @des 分类页面的bean, 负责与CategoryVo对接数据, 不管服务器怎么样返回数据都不影响我们的显式,我们显示要的数据就这些
 */

public class CategoryBean {
    public static final int TYPE_TITLE = 0;
    public static final int TYPE_ITEM = 1;
    // 标记
    public int type;
    // title类型的数据
    public String title;
    // item 类型的数据
    public String name1;
    public String name2;
    public String name3;
    public String url1;
    public String url2;
    public String url3;
}
