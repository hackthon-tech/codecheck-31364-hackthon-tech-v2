package net.tech.yboy.alarm.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.tech.yboy.alarm.R;
import net.tech.yboy.alarm.model.GoogleHomeDns;
import net.tech.yboy.alarm.presenter.UserRegisterPresenter;
import net.tech.yboy.alarm.util.PrefUtil;
import net.tech.yboy.alarm.view.dialog.ConfirmDialog;
import net.tech.yboy.alarm.view.dialog.MessageDialog;

public class MainActivity extends BaseActivity implements MainUseCase.View {

    MainUseCase.Presenter mPresenter;

    RelativeLayout mProgressLayout;

    TextView mStatus;

    Button mSetting;

    Button mGoogleHome;

    Button mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressLayout = findViewById(R.id.progressLayout);
        mStatus = findViewById(R.id.statusText);

        mSetting = findViewById(R.id.settingBtn);
        mGoogleHome = findViewById(R.id.googleHomeConnectTest);
        mRegister = findViewById(R.id.registerBtn);
        mSetting.setAllCaps(false);
        mGoogleHome.setAllCaps(false);

        mSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        mGoogleHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDialog dialog = new ConfirmDialog();
                dialog.set("確認", "GoogleHomeに接続してテスト音声を流しますか？");
                dialog.set(new ConfirmDialog.DialogListener() {
                    @Override
                    public void onOkClick(DialogFragment dlg) {
                        mPresenter.googleHome(getApplicationContext());
                    }

                    @Override
                    public void onCancelClick(DialogFragment dlg) {
                    }
                });
                dialog.show(getSupportFragmentManager(), "message");
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.register(getApplicationContext());
            }
        });

        mPresenter = new UserRegisterPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.status(getApplicationContext());
        }
    }

    @Override
    public void showProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressLayout.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void showMessage(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MessageDialog dialog = new MessageDialog();
                dialog.set("確認", message);
                dialog.show(getSupportFragmentManager(), "message");
            }
        });
    }

    @Override
    public void updateRegisterStatus(String status) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSetting.setVisibility(View.GONE);
                mGoogleHome.setVisibility(View.INVISIBLE);
                mRegister.setVisibility(View.VISIBLE);
                if (status != null) {
                    mStatus.setText(status);
                }
            }
        });
    }

    @Override
    public void updateSettingStatus(String status) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSetting.setVisibility(View.VISIBLE);
                mGoogleHome.setVisibility(View.VISIBLE);
                mRegister.setVisibility(View.GONE);
                if (status != null) {
                    mStatus.setText(status);
                }
            }
        });
    }
}
