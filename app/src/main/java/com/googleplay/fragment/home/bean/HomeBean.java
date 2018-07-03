package com.googleplay.fragment.home.bean;

import java.util.List;

/**
 * @author TanJJ
 * @time 2018/6/30 20:19
 * @des 应用信息Bean,写bean时一定要注意json各项节点的名字,一定要一一对上才行,不然就解析不出来
 */

public class HomeBean {
    public List<String> picture;// 轮播图资源
    public List<AppInfo> list;// ListView Item数据
}
