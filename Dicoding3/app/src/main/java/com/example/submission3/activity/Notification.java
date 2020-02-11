package com.example.submission3.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;

import com.example.submission3.R;
import com.example.submission3.Setting.SettingPreferince;
import com.example.submission3.notification.MovieDailyReceiver;
import com.example.submission3.notification.MovieReleaseReceiver;

import java.util.Objects;

public class Notification extends AppCompatActivity {
    private SwitchCompat mSwitchReminder;
    private SwitchCompat mSwitchRelease;
    private MovieDailyReceiver mMovieDailyReceiver;
    private MovieReleaseReceiver mMovieReleaseReceivers;
    private SettingPreferince mSettingPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        mSwitchReminder = findViewById(R.id.switch_daily_reminder);
        mSwitchRelease = findViewById(R.id.switch_release_today);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Setting");

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mMovieDailyReceiver = new MovieDailyReceiver();
        mMovieReleaseReceivers = new MovieReleaseReceiver();

        mSettingPreference = new SettingPreferince(this);
        setSwitchRelease();
        setSwitchReminder();

        mSwitchReminder.setOnClickListener(v -> {
            if (mSwitchReminder.isChecked()) {
                mMovieDailyReceiver.setDailyAlarm(getApplicationContext());
                mSettingPreference.setDailyReminder(true);
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.set_daily_reminder), Toast.LENGTH_SHORT).show();
            } else {
                mMovieDailyReceiver.cancelAlarm(getApplicationContext());
                mSettingPreference.setDailyReminder(false);
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.cancel_daily_reminder), Toast.LENGTH_SHORT).show();
            }
        });

        // Switch Release OnClick
        mSwitchRelease.setOnClickListener(v -> {
            if (mSwitchRelease.isChecked()) {
                mMovieReleaseReceivers.setReleaseAlarm(getApplicationContext());
                mSettingPreference.setReleaseReminder(true);
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.set_release_reminder), Toast.LENGTH_SHORT).show();
            } else {
                mMovieReleaseReceivers.cancelAlarm(getApplicationContext());
                mSettingPreference.setReleaseReminder(false);
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.cancel_release_reminder), Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setSwitchReminder() {
        if (mSettingPreference.getDailyReminder()) mSwitchReminder.setChecked(true);
        else mSwitchReminder.setChecked(false);
    }

    private void setSwitchRelease() {
        if (mSettingPreference.getReleaseReminder()) mSwitchRelease.setChecked(true);
        else mSwitchRelease.setChecked(false);
    }
}
