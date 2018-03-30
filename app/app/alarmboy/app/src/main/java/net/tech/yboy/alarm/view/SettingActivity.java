package net.tech.yboy.alarm.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import net.tech.yboy.alarm.R;
import net.tech.yboy.alarm.model.EkiStationInfoData;
import net.tech.yboy.alarm.model.EkiStationLightData;
import net.tech.yboy.alarm.presenter.UserRegisterPresenter;
import net.tech.yboy.alarm.presenter.UserSettingPresenter;
import net.tech.yboy.alarm.server.api.ApiAlarmData;
import net.tech.yboy.alarm.server.api.EkiApiStationInfo;
import net.tech.yboy.alarm.server.api.EkiApiStationLight;
import net.tech.yboy.alarm.util.PrefUtil;
import net.tech.yboy.alarm.util.TimeUtil;
import net.tech.yboy.alarm.view.dialog.CheckListDialog;
import net.tech.yboy.alarm.view.dialog.MessageDialog;
import net.tech.yboy.alarm.view.dialog.TimePickerDialogFragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.POST;

import static android.Manifest.permission.READ_CONTACTS;

public class SettingActivity extends BaseActivity implements SettingUseCase.View {

    private EditText mDepartureStation;

    private EditText mArrivalStation;

    private TextView mTime1;

    private Switch mSwitch;

    RelativeLayout mProgressLayout;

    SettingUseCase.Presenter mPresenter;

    EkiStationLightData mEkiStationLightData;

    EkiStationInfoData mEkiStationInfoData;

    private String mDepartureCode;

    private String mArrivalCode;

    private String mCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mDepartureStation = findViewById(R.id.departureStation);
        mArrivalStation = findViewById(R.id.arrivalStation);
        mProgressLayout = findViewById(R.id.progressLayout);
        mTime1 = findViewById(R.id.time1);
        mSwitch = findViewById(R.id.switch1);

        mEkiStationLightData = new EkiStationLightData();
        mEkiStationInfoData = new EkiStationInfoData();

        setupActionBar();

        mDepartureStation.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_ACTION_NEXT || id == EditorInfo.IME_NULL) {
                    showProgress();
                    ekiMulti(v.getText().toString(), true);
                    return true;
                }
                return false;
            }
        });

        mArrivalStation.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_ACTION_NEXT || id == EditorInfo.IME_NULL) {
                    showProgress();
                    ekiMulti(v.getText().toString(), false);
                    return true;
                }
                return false;
            }
        });

        findViewById(R.id.station1Btn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress();
                ekiMulti(mDepartureStation.getText().toString(), true);
            }
        });

        findViewById(R.id.station2Btn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress();
                ekiMulti(mArrivalStation.getText().toString(), false);
            }
        });

        findViewById(R.id.updateBtn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });

        findViewById(R.id.time1Btn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialogFragment timePicker = new TimePickerDialogFragment();
                Bundle bundle = new Bundle();
                int[] time = TimeUtil.toInt(mTime1.getText().toString());
                if (time != null) {
                    bundle.putInt("h", time[0]);
                    bundle.putInt("m", time[1]);
                }
                timePicker.setArguments(bundle);
                timePicker.set(new TimePickerDialogFragment.DialogListener() {
                    @Override
                    public void onTimeSet(int hourOfDay, int minute) {
                        mTime1.setText(TimeUtil.toString(hourOfDay, minute));
                    }
                });
                timePicker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        mPresenter = new UserSettingPresenter(this);
        mPresenter.get(getApplicationContext());
    }

    private void ekiMulti(String text, boolean start) {
        mEkiStationLightData.get(text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    hideProgress();

                    if (response.body() == null || response.body().ResultSet == null || response.body().ResultSet.Point == null) {
                        MessageDialog dialog = new MessageDialog();
                        dialog.set("確認", "駅取得に失敗しました");
                        dialog.show(getSupportFragmentManager(), "message");
                        return;
                    }

                    ArrayList<String> list = new ArrayList<String>();
                    for (EkiApiStationLight.Point p : response.body().ResultSet.Point) {
                        list.add(p.Station.Name);
                    }
                    String[] s = list.toArray(new String[list.size()]);

                    CheckListDialog dialog = new CheckListDialog();
                    dialog.set("駅候補");
                    dialog.setText(s);
                    dialog.set(new CheckListDialog.DialogListener() {
                        @Override
                        public void onCheck(DialogFragment dlg, int item) {
                            final String name = response.body().ResultSet.Point[item].Station.Name;
                            if (start) {
                                mDepartureStation.setText(name);
                                mDepartureCode = response.body().ResultSet.Point[item].Station.code;
                            } else {
                                mArrivalStation.setText(name);
                                mArrivalCode = response.body().ResultSet.Point[item].Station.code;
                            }
                        }
                    });
                    dialog.show(getSupportFragmentManager(), "choice");

                    return;
                }, throwable -> {

                    ekiSingle(text, start);
                    return;
                });
    }

    private void ekiSingle(String text, boolean start) {
        mEkiStationLightData.singleGet(text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    hideProgress();

                    if (response.body() == null || response.body().ResultSet == null || response.body().ResultSet.Point == null) {
                        MessageDialog dialog = new MessageDialog();
                        dialog.set("確認", "駅取得に失敗しました");
                        dialog.show(getSupportFragmentManager(), "message");
                        return;
                    }

                    ArrayList<String> list = new ArrayList<String>();
                    list.add(response.body().ResultSet.Point.Station.Name);
                    String[] s = list.toArray(new String[list.size()]);

                    CheckListDialog dialog = new CheckListDialog();
                    dialog.set("駅候補");
                    dialog.setText(s);
                    dialog.set(new CheckListDialog.DialogListener() {
                        @Override
                        public void onCheck(DialogFragment dlg, int item) {
                            final String name = response.body().ResultSet.Point.Station.Name;
                            if (start) {
                                mDepartureStation.setText(name);
                                mDepartureCode = response.body().ResultSet.Point.Station.code;
                            } else {
                                mArrivalStation.setText(name);
                                mArrivalCode = response.body().ResultSet.Point.Station.code;
                            }
                        }
                    });
                    dialog.show(getSupportFragmentManager(), "choice");

                    return;
                }, throwable -> {

                    hideProgress();

                    MessageDialog dialog = new MessageDialog();
                    dialog.set("エラー", "駅候補取得に失敗しました");
                    dialog.show(getSupportFragmentManager(), "message");
                    return;
                });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void check() {

        mDepartureStation.setError(null);
        mArrivalStation.setError(null);

        String station1 = mDepartureStation.getText().toString();
        String station2 = mArrivalStation.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(station2)) {
            mArrivalStation.setError("入力されていません");
            focusView = mArrivalStation;
            cancel = true;
        }

        if (TextUtils.isEmpty(station1)) {
            mDepartureStation.setError("入力されていません");
            focusView = mDepartureStation;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            boolean alarm = mSwitch.isChecked();
            String departureStation = mDepartureStation.getText().toString();
            String arrivalStation = mArrivalStation.getText().toString();
            String timeValue = mTime1.getText().toString();
            int[] time = TimeUtil.toInt(timeValue);
            if (time == null) {
                MessageDialog dialog = new MessageDialog();
                dialog.set("確認", "起床時刻が入力されていません");
                dialog.show(getSupportFragmentManager(), "message");
            } else {

                if (mDepartureCode == null || "".equals(mDepartureCode) || mArrivalCode == null || "".equals(mArrivalCode)) {
                    MessageDialog dialog = new MessageDialog();
                    dialog.set("確認", "駅名が候補から選択されていません");
                    dialog.show(getSupportFragmentManager(), "message");
                    return;
                }


                mCompany = "";
                mParam1 = alarm;
                mParam2 = departureStation;
                mParam3 = arrivalStation;
                mParam4 = timeValue;
                mParam5 = mDepartureCode;
                mParam6 = mArrivalCode;

                showProgress();
                ekiInfoMulti(true);
            }
        }
    }

    private void register() {
        hideProgress();
        mPresenter.register(getApplicationContext(), mParam1, mParam2, mParam3, mParam4, mParam5, mParam6, mCompany);
    }


    private boolean mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    private String mParam5;
    private String mParam6;

    private void ekiInfoMulti(boolean start) {

        String s = "";
        if (start) {
            s = mDepartureCode;
        } else {
            s = mArrivalCode;
        }

        mEkiStationInfoData.get(s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if (response.body() == null) {
                        hideProgress();
                        MessageDialog dialog = new MessageDialog();
                        dialog.set("確認", "更新に失敗しました");
                        dialog.show(getSupportFragmentManager(), "message");
                        return;
                    }
                    for (EkiApiStationInfo.Corporation c : response.body().ResultSet.Information.Corporation) {
                        if ("JR".equals(c.Name)) {
                            continue;
                        }
                        if ("".equals(mCompany)) {
                            mCompany = c.Name;
                        } else {
                            mCompany = mCompany + "," + c.Name;
                        }
                    }
                    if (start) {
                        ekiInfoMulti(false);
                    } else {
                        register();
                    }
                    return;
                }, throwable -> {
                    ekiInfoSingle(start);
                    return;
                });
    }

    private void ekiInfoSingle(boolean start) {

        String s = "";
        if (start) {
            s = mDepartureCode;
        } else {
            s = mArrivalCode;
        }

        mEkiStationInfoData.singleGet(s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if (response.body() == null) {
                        hideProgress();
                        MessageDialog dialog = new MessageDialog();
                        dialog.set("確認", "更新に失敗しました");
                        dialog.show(getSupportFragmentManager(), "message");
                        return;
                    }
                    if (response.body().ResultSet.Information.Corporation != null) {
                        if (!"JR".equals(response.body().ResultSet.Information.Corporation.Name)) {
                            if ("".equals(mCompany)) {
                                mCompany = response.body().ResultSet.Information.Corporation.Name;
                            } else {
                                mCompany = mCompany + "," + response.body().ResultSet.Information.Corporation.Name;
                            }
                        }
                    }
                    if (start) {
                        ekiInfoMulti(false);
                    } else {
                        register();
                    }
                    return;
                }, throwable -> {
                    hideProgress();
                    MessageDialog dialog = new MessageDialog();
                    dialog.set("確認", "更新に失敗しました");
                    dialog.show(getSupportFragmentManager(), "message");
                    return;
                });
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
    public void failMessage(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MessageDialog dialog = new MessageDialog();
                dialog.set("エラー", message);
                dialog.set(new MessageDialog.DialogListener() {
                    @Override
                    public void onOkClick(DialogFragment dlg) {
                        finish();
                    }
                });
                dialog.show(getSupportFragmentManager(), "message");
            }
        });
    }

    @Override
    public void update(boolean alarm, String departureStation, String arrivalStation, String wakeUpTime, String departureStationCode, String arrivalStationCode, String company) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSwitch.setChecked(alarm);
                if (departureStation != null) {
                    mDepartureStation.setText(departureStation);
                }
                if (arrivalStation != null) {
                    mArrivalStation.setText(arrivalStation);
                }
                if (wakeUpTime != null) {
                    mTime1.setText(wakeUpTime);
                }
                mArrivalCode = arrivalStationCode;
                mDepartureCode = departureStationCode;
                mCompany = company;
            }
        });
    }

    @Override
    public void registerSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PrefUtil.setSetting(getApplicationContext(), true);
                boolean alarm = mSwitch.isChecked();
                PrefUtil.setSettingNotify(getApplicationContext(), alarm);
                MessageDialog dialog = new MessageDialog();
                dialog.set("確認", "設定を更新しました");
                dialog.set(new MessageDialog.DialogListener() {
                    @Override
                    public void onOkClick(DialogFragment dlg) {
                        finish();
                    }
                });
                dialog.show(getSupportFragmentManager(), "message");
            }
        });
    }
}

