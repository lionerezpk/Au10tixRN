package com.Au10tixRN.KycService;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.au10tix.sdk.commons.Au10Error;
import com.au10tix.sdk.core.Au10xCore;
import com.au10tix.sdk.core.OnPrepareCallback;
import com.Au10tixRN.R;

public class LivenessActivity extends AppCompatActivity {
    LivenessFragment livenessFragment;

    private void showFragment() {
        livenessFragment = new LivenessFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, livenessFragment);
        transaction.commit();
    }

    private void init() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        String jwt = bundle.getString("jwt");
        if (jwt == null) return;
        Au10xCore.prepare(this, jwt, new OnPrepareCallback() {
            @Override
            public void onPrepareError(Au10Error au10Error) {

            }

            @Override
            public void onPrepared(String sessionId) {

                showFragment();
            }
        });
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
}
