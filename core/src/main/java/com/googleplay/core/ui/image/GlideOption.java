package com.googleplay.core.ui.image;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * @author TanJJ
 * @time 2018/7/3 18:37
 * @des Glide的配置信息
 */

public class GlideOption {
    public static final RequestOptions OPTIONS = new RequestOptions()
            .centerCrop()
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.ALL);
}
