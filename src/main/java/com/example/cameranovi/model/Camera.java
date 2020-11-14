package com.example.cameranovi.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import androidx.lifecycle.ViewModel;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Camera extends ViewModel {

    static String picLocation;
    static Uri imageUri;
    static Bitmap imageSource2;

    //Creates image filename and directory and sets its location to global variable picLocation
    public static File createFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + File.separator);
        dir.mkdirs();
        File image = File.createTempFile(timeStamp, ".jpg", dir
        );
        picLocation = image.getAbsolutePath();

        return image;
    }

    //Scales picture to 1280x960, sets View and saves using Saver model
    public static void saveBitmap(Context c) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(picLocation, bmOptions);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = ((float) 960) / width;
        float scaleHeight = ((float) 1280) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bitmap, 0, 0, width, height, matrix, false);

        //Save to memory and reassign picLocation variable
        picLocation = Saver.saveCamPicToPhone(c, resizedBitmap);

        setBitmap(resizedBitmap);
    }

    //Intent starten om foto in Pictures map te krijgen
    public static void addToPictures(Context context) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(picLocation);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    public static void setImgUri(Uri imgUri) {
        imageUri = imgUri;
    }

    public static void setBitmap(Bitmap bitmap) {
        imageSource2 = bitmap;
    }

    public static Uri getImageUri() {
        return imageUri;
    }

}



