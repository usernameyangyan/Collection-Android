package com.youngmanster.collection.activity;

import com.youngmanster.collection.BuildConfig;
import com.youngmanster.collection.common.AppApplication;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertNotNull;

/**
 * Created by yangy
 * 2019-12-08
 * Describe:
 */
@RunWith(RobolectricTestRunner.class)
@Config(application = AppApplication.class,sdk = 21)
public class MainActivityTest {
    private MainActivity mainActivity;

    @Before
    public void setUp() {
        mainActivity= Robolectric.buildActivity(MainActivity.class)
                .create()
                .resume()
                .get();
    }
    @Test
    public void onCLick(){
        assertNotNull(mainActivity);
    }
}