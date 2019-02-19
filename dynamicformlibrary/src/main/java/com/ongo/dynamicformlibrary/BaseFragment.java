package com.ongo.dynamicformlibrary;

/*
 * Created by CX on 13-11-2018.
 */

import android.support.v4.app.Fragment;
import android.view.View;

public abstract class BaseFragment extends Fragment {
    public abstract void initView(View view);
    public abstract void initListeners();
}
