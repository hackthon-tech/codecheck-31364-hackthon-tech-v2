package net.tech.yboy.alarm.view;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import net.tech.yboy.alarm.util.PrefUtil;

/**
 * Created by manabu on 2018/03/20.
 */

public class BaseActivity extends AppCompatActivity implements BaseViewUserCase {

    @Override
    public String getToken() {
        return PrefUtil.getUid(getApplicationContext());
    }
}
