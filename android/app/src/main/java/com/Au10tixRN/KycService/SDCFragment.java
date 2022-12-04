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

import com.Au10tixRN.R;
import com.au10tix.sdk.commons.ImageRepresentation;
import com.au10tix.sdk.core.Au10xCore;
import com.au10tix.sdk.core.comm.SessionCallback;
import com.au10tix.sdk.protocol.Au10Update;
import com.au10tix.sdk.protocol.FeatureSessionError;
import com.au10tix.sdk.protocol.FeatureSessionResult;
import com.au10tix.smartDocument.SmartDocumentFeatureManager;
import com.au10tix.smartDocument.SmartDocumentFeatureSessionFrame;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class SDCFragment extends Fragment {
    private FrameLayout capturePreview;
    private TextView captureStatus;
    private Au10xCore coreManager;
    private SmartDocumentFeatureManager smartDocumentFeatureManager;

    private String saveImage(Bitmap image, Context context) {
        String dirPath = context.getFilesDir().getAbsolutePath();
        OutputStream outStream = null;
        String fileName = "front_image.png";
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
        Log.v("Au10tixRN", "starting session...");
        coreManager.startSession(smartDocumentFeatureManager, capturePreview, new SessionCallback() {
            @Override
            public void onSessionResult(FeatureSessionResult featureSessionResult) {
                ImageRepresentation imageRepresentation = featureSessionResult.getImageFile();
                String imageUri = saveImage(imageRepresentation.getBitmap(), getContext());
                if (imageUri != null) {
                    Au10tixRNModule.sessionHelper.sendResult(imageUri);
                    requireActivity().finish();
                } else {
                    Au10tixRNModule.sessionHelper.sendResult("Image created but failed to save");
                    requireActivity().finish();
                }
            }

            @Override
            public void onSessionError(FeatureSessionError featureSessionError) {
                Log.v("Au10tixRN", "onSessionError ::: " + featureSessionError.getErrorMessage());
                Au10tixRNModule.sessionHelper.sendResult("Session error ::: " + featureSessionError.getErrorMessage());
            }

            @Override
            public void onSessionUpdate(Au10Update au10Update) {
                Log.v("Au10tixRN", "onSessionUpdate");
                SmartDocumentFeatureSessionFrame frame = (SmartDocumentFeatureSessionFrame) au10Update;
                int statusId = frame.getIdStatus();
                captureStatus.setText("" + statusId);
            }
        });
    }

    private void initLocals() {
        if (coreManager == null) {
            coreManager = Au10xCore.getInstance(requireContext().getApplicationContext());
        }
        if (smartDocumentFeatureManager == null) {
            smartDocumentFeatureManager = new SmartDocumentFeatureManager(requireActivity(), this);
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
