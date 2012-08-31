package com.framework.iginger.widget;

/*
 * Copyright (C) 2012 Benjamin Mock
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnActionExpandListener;
import com.framework.iginger.util.ToastUtils;

/**
 * <p>
 * This class creates a MenuItem with an expandable and collapsable ActionView
 * for search purposes
 * </p>
 * 
 * <p>
 * It is based on the great ActionBar Sherlock of Jake Wharton. The problem with
 * the original SearchView is, that the style for the dark ActionBar is wrong;
 * i.e. black text on a dark gray background.
 * </p>
 * 
 * @author Benjamin Mock <mail@benjaminmock.de>
 */
public class MenuItemSearchAction extends MultiAutoCompleteTextView {

    private MenuItem searchItem;
    private Context context;
    private SearchPerformListener searchPerformListener;
    private boolean mIsPerformLocation;
    public MenuItemSearchAction(Context context, SearchPerformListener searchPerformListener) {
        super(context);
        this.searchPerformListener = searchPerformListener;
        this.context = context;
        // show search button on keyboard; this needs the setting of
        // TYPE_TEXT... to be shown
        setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        setMaxLines(1);
        setThreshold(1);
        setTextColor(Color.BLACK);
    }

    public void setHindAdater(List<String> list) {
        MyArrayAdapter<String> adapter = new MyArrayAdapter<String>(context,
                android.R.layout.simple_dropdown_item_1line, list);
        setAdapter(adapter);
        setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public MenuItemSearchAction(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param context
     * @param attrs
     */
    public MenuItemSearchAction(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param context
     */
    public MenuItemSearchAction(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    /**
     * Listen for back button clicks, in order to close the keyboard and
     * collapse the ActionView
     */
    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_UP) {
            searchItem.collapseActionView();
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * Returns the the created MenuItem for customizations etc.
     * 
     * @return the MenuItem, which holds search ActionView
     */
    public MenuItem getMenuItem() {
        return searchItem;
    }

    public void createMenuItem(Menu menu, int firstDrawable, int SecDrawable) {
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(layoutParams);

        searchItem = menu.add("搜索");

        // Add this EditText into the layout.
        RelativeLayout layout = new RelativeLayout(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.LEFT_OF, 1000);
        layout.addView(this, params);

        // Add this search icon into the layout.
        ImageView imageView = new ImageView(context);
        imageView.setId(1000);
        imageView.setImageResource(firstDrawable);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });

        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params1.addRule(RelativeLayout.CENTER_VERTICAL);
        params1.addRule(RelativeLayout.LEFT_OF, 1001);
        layout.addView(imageView, params1);

        // Add this location icon into the layout.
        ImageView location = new ImageView(context);
        location.setId(1001);
        location.setImageResource(SecDrawable);
        location.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                performLocation();
            }
        });

        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params2.addRule(RelativeLayout.CENTER_VERTICAL);
        params2.setMargins(10, 0, 10, 0);
        layout.addView(location, params2);

        searchItem.setActionView(layout);
        searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
                | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

        searchItem.setOnActionExpandListener(new OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // open keyboard
                MenuItemSearchAction.this.setText("");
                MenuItemSearchAction.this.requestFocus();

                MenuItemSearchAction.this.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager imm = (InputMethodManager) context
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0,
                                InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }, 200);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // close keyboard
                InputMethodManager imm = (InputMethodManager) context
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(
                        MenuItemSearchAction.this.getWindowToken(), 0);
                if (mIsPerformLocation) {
                    searchPerformListener.performLocation();
                    mIsPerformLocation = false;
                }

                return true;
            }
        });

        setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                    KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // perform Search
                    performSearch();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 
     */
    protected void performLocation() {
        mIsPerformLocation = true;
        searchItem.collapseActionView();
    }

    /**
     * 
     */
    protected void performSearch() {
        String inputString = MenuItemSearchAction.this.getText().toString();
        if (inputString != null && !"".equals(inputString.trim())) {
            searchPerformListener.performSearch(inputString);
            searchItem.collapseActionView();
        } else {
            ToastUtils.show(context, "请输入搜索关键字!");
        }
    }
}