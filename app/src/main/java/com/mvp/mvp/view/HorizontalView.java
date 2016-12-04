package com.mvp.mvp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.HorizontalScrollView;

import com.mvp.mvp.R;

/**
 * Created by TEAM on 12/4/2016.
 * That's it
 */

public class HorizontalView extends HorizontalScrollView {


    public HorizontalView(Context context) {
        super(context);
        init(context);
    }

    public HorizontalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.horizontal_view, this);

    }
}
