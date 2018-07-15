package com.googleplay.fragment.hot;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SizeUtils;
import com.googleplay.base.BaseFragment;
import com.googleplay.core.app.GooglePlay;
import com.googleplay.core.view.FlowLayout;
import com.googleplay.fragment.load.LoadUI;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * @author TanJJ
 * @time 2018/7/15 20:39
 * @des 排行页面
 */

public class HotFragment extends BaseFragment {

    private HotProtocol mHotProtocol;
    private List<String> mDataList;

    @Override
    public LoadUI.LoadState onStartLoadDate() {
        // 网络请求
        mHotProtocol = new HotProtocol();
        try {
            // 请求网络
            mDataList = mHotProtocol.execute(0, "hot");
            if (mDataList == null || mDataList.size() == 0) {
                return LoadUI.LoadState.EMPTY;
            }
            return LoadUI.LoadState.SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
            return LoadUI.LoadState.ERROR;
        }
    }

    @Override
    public View onSuccessView() {
        ScrollView mRootView = new ScrollView(GooglePlay.getApplicationContext());

        // 添加flowlayout
        FlowLayout mLayout = new FlowLayout(GooglePlay.getApplicationContext());
        mRootView.addView(mLayout);

        // 设置flowlayout的padding
        mLayout.setPadding(SizeUtils.dp2px(8), SizeUtils.dp2px(8), SizeUtils.dp2px(8), SizeUtils.dp2px(8));
        mLayout.setSpace(SizeUtils.dp2px(8), SizeUtils.dp2px(8));

        Random rdm = new Random();
        // 给Flowlayout加载数据
        for (int i = 0; i < mDataList.size(); i++) {
            TextView view = new TextView(GooglePlay.getApplicationContext());
            view.setText(mDataList.get(i));
            // view.setBackgroundColor(Color.GRAY);
            view.setTextColor(Color.WHITE);
            view.setPadding(5, 5, 5, 5);
            view.setGravity(Gravity.CENTER);
            view.setTextSize(14);

            // view.setBackgroundResource(R.drawable.item_hot_bg);
            GradientDrawable normal = new GradientDrawable();// normal
            normal.setShape(GradientDrawable.RECTANGLE);
            normal.setCornerRadius(SizeUtils.dp2px(8));

            // #ff 33 33 33
            int red = rdm.nextInt(200) + 20; // 20-220
            int green = rdm.nextInt(200) + 20;
            int blue = rdm.nextInt(200) + 20;
            int argb = Color.argb(255, red, green, blue);// 0-255
            normal.setColor(argb);

            GradientDrawable pressed = new GradientDrawable();
            pressed.setShape(GradientDrawable.RECTANGLE);
            pressed.setCornerRadius(SizeUtils.dp2px(8));
            pressed.setColor(Color.GRAY);

            // 设置selector
            StateListDrawable selector = new StateListDrawable();
            selector.addState(new int[]{android.R.attr.state_pressed}, pressed);
            selector.addState(new int[]{}, normal);

            view.setBackgroundDrawable(selector);

            final int index = i;
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(GooglePlay.getApplicationContext(), "" + mDataList.get(index), Toast.LENGTH_SHORT)
                            .show();
                }
            });

            mLayout.addView(view);
        }

        return mRootView;
    }

}
