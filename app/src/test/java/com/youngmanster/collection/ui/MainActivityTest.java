package com.youngmanster.collection.ui;

import android.widget.TextView;

import com.youngmanster.collection.activity.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by yangy
 * 2019-12-07
 * Describe:
 */
@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
    private MainActivity mainActivity;


    @Before
    public void setUp(){
        mainActivity= Robolectric.buildActivity(MainActivity.class)
                .create()
                .resume()
                .get();

    }

    @Test
    public void onClickSubmitBtn(){
        assertNull(mainActivity);
    }
}
