package com.Au10tixRN.KycService;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.au10tix.faceliveness.FaceLivenessFeatureManager;
import com.au10tix.faceliveness.FaceLivenessUpdate;
import com.au10tix.sdk.commons.ImageRepresentation;
import com.au10tix.sdk.core.Au10xCore;
import com.au10tix.sdk.core.comm.SessionCallback;
import com.au10tix.sdk.protocol.Au10Update;
import com.au10tix.sdk.protocol.FeatureSessionError;
import com.au10tix.sdk.protocol.FeatureSessionResult;
import com.Au10tixRN.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class LivenessFragment extends Fragment {
    private FrameLayout capturePreview;
    private TextView captureStatus;
    private Au10xCore coreManager;
    private FaceLivenessFeatureManager livenessFeatureManager;

    private String saveImage(Bitmap image, Context context) {
        String dirPath = context.getFilesDir().getAbsolutePath();
        OutputStream outStream = null;
        String fileName = "liveness.png";
        String filePath = dirPath + fileName;
        File file = new File(filePath);
        try {
            outStream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.close();
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void startSession() {
        coreManager.startSession(livenessFeatureManager, capturePreview, new SessionCallback() {
            @Override
            public void onSessionResult(FeatureSessionResult featureSessionResult) {
                ImageRepresentation imageRepresentation = featureSessionResult.getImageFile();
                String imageUri = saveImage(imageRepresentation.getBitmap(), getContext());
                if (imageUri != null) {
                    Au10tixRNModule.sessionHelper.sendResult(imageUri);
                    requireActivity().finish();
                } else {

                }
            }

            @Override
            public void onSessionError(FeatureSessionError featureSessionError) {

            }

            @Override
            public void onSessionUpdate(Au10Update au10Update) {
                if (au10Update instanceof FaceLivenessUpdate) {
                    captureStatus.setText(((FaceLivenessUpdate) au10Update).getStatusDescription());
                }
            }
        });
    }

    private void initLocals() {
        if (coreManager == null) {
            coreManager = Au10xCore.getInstance(requireContext().getApplicationContext());
        }
        if (livenessFeatureManager == null) {
            livenessFeatureManager = new FaceLivenessFeatureManager(requireActivity(), this);
        }
    }

    public void initSDKSDC() {
        initLocals();
        startSession();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_sdc, container, false);
        capturePreview = view.findViewById(R.id.capture_preview);
        captureStatus = view.findViewById(R.id.capture_status);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initSDKSDC();
    }
}
