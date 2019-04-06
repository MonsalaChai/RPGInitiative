package com.monsalachai.rpginitiative.ui.bucket;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ImageButton;

import com.monsalachai.rpginitiative.R;

public class BucketListView extends ConstraintLayout {
    private RecyclerView mRecyclerView;
    private ImageButton  mAddButton;
    private ImageButton mRemoveButton;

    public BucketListView(Context context) {
        super(context);
        init();
    }

    public BucketListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BucketListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        // inflate the real view, all that jazz.
        inflate(getContext(), R.layout.view_drawer_bucket_list, this);
        mRecyclerView = (RecyclerView) getViewById(R.id.drawer_recycler);
        mAddButton = (ImageButton) getViewById(R.id.bucket_add_button);
        mRemoveButton = (ImageButton) getViewById(R.id.bucket_remove_button);
    }
}

