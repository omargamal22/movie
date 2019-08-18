package com.example.moviapp;

import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    public static class MovieSettingFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener{

        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.setting);

            Preference minMagnitude = findPreference("type");
            bindPreferenceSummaryToValue(minMagnitude);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {

            ListPreference lp = (ListPreference)preference;
            String old_val = String.valueOf(lp.getValue());
            CharSequence[] v = lp.getEntryValues();
            CharSequence[] e = lp.getEntries();
            for(int i =0 ;i<3;i++){
                if(String.valueOf(v[i]).equals(String.valueOf(newValue))){
                    String entry = String.valueOf(e[i]);
                    lp.setSummary(entry);
                    if(!old_val.equals(String.valueOf(newValue))){
                        GlobalBus.getBus().postSticky(new Events.PreferenceChanged(String.valueOf(newValue)));
                    }
                    return true;
                }
            }
            return false;
        }
        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            onPreferenceChange(preference, preferences.getString("type",""));
        }

        @Override
        public void onDestroy() {
            Preference minMagnitude = findPreference("type");
            ListPreference lp = (ListPreference)minMagnitude;
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(minMagnitude.getContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(minMagnitude.getKey(),String.valueOf(lp.getValue()));
            editor.apply();
            super.onDestroy();
        }
    }
}
