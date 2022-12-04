package com.Au10tixRN.KycService;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.au10tix.sdk.commons.Au10Error;
import com.au10tix.sdk.core.Au10xCore;
import com.au10tix.sdk.core.OnPrepareCallback;
import com.Au10tixRN.R;

public class SDCActivity extends AppCompatActivity {
    private SDCFragment sdcFragment;

    private void showSDCFragment() {
        sdcFragment = new SDCFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, sdcFragment);
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
                Log.v("Au10tixRN", "Prepare SDK error ::: " + au10Error.getMessage());
                Au10tixRNModule.sessionHelper.sendResult("Au10xCore prepare error ::: " + au10Error.getMessage());
            }

            @Override
            public void onPrepared(String sessionId) {
                Log.v("Au10tixRN", "Prepare SDK success with session id ::: " + sessionId);
                showSDCFragment();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == PackageManager.PERMISSION_GRANTED && sdcFragment != null) {
            sdcFragment.initSDKSDC();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
}
