package com.googleplay.fragment.category.vo;

import java.util.List;

/**
 * @author TanJJ
 * @time 2018/7/8 22:05
 * @des 它就是一个bean,由于服务器的数据很乱,不能被服务器牵着鼻子走,
 * 我要在客户端要做到,不管服务器返回怎样的数据,我们的代码一样不受影响,所以这次解析服务器的json用了两个Bean
 * ########## 一个负责解析服务器数据 ##########
 * ########## 一个负责把解析好的数据按照我们自己的来显示 ##########
 */

public class CategoryVo {
    public String title;
    public List<CategoryInfoBean> infos;

    public class CategoryInfoBean {
        public String name1;
        public String name2;
        public String name3;
        public String url1;
        public String url2;
        public String url3;
    }
}
