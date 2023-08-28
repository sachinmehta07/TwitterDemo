package com.app.twitter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonPickApp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uninstallOldApp();
                installNewApp();
            }
        });


    }
    private void uninstallOldApp() {
        String packageName = "com.app.twitter"; // Replace with your app's package name
        Intent uninstallIntent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE);
        uninstallIntent.setData(Uri.parse("package:" + packageName));
        startActivity(uninstallIntent);
    }

    private void installNewApp() {

        File apkFile = new File(getExternalFilesDir(null), " apkFolder/app-release.apk");
        Intent installIntent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
        Uri apkUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", apkFile);
        installIntent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        installIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(installIntent);
    }

    private void createDirectory() {
        String directoryName = "apkFolder"; // Replace with your desired directory name
        File directory = new File(Environment.getExternalStorageDirectory(), directoryName);

        if (!directory.exists()) {
            if (directory.mkdirs()) {
                // Directory creation successful
                Log.d("Directory", "Directory created: " + directory.getAbsolutePath());
            } else {
                // Directory creation failed
                Log.e("Directory", "Failed to create directory");
            }
        } else {
            // Directory already exists
            Log.d("Directory", "Directory already exists: " + directory.getAbsolutePath());
        }
    }
}