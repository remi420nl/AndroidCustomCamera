package com.example.cameranovi.model;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


//Check for Camera and Read/Write permissions which are also set at the manifest (For Android marshmallow and up)
//According to new security policies i have set "android:requestLegacyExternalStorage="true" at the manifest for the latest Andrlod Q/10

public class Permission extends AppCompatActivity {

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String permission = "";
        for (String x : permissions) {
            permission += x;
        }
        //Check permissions
        Log.i("Mijnlog", requestCode + " On request result permissionclass : " + permission);
    }

    public static boolean checkPermissions(final Context context) {

        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "U heeft geen rechten", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 0);
            } else {
                Toast.makeText(context, "U heeft voldoende rechten", Toast.LENGTH_LONG).show();
            }
        } else {
            return false;

        }
        return true;
    }
}