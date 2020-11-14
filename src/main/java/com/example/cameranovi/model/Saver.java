package com.example.cameranovi.model;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.LayerDrawable;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

//Class to sat filename, create bitmap and save new and editted pictures
public class Saver {

    public static File getDir() {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator);
        dir.mkdirs();
        return dir;
    }

    public static String getTimeStamp() {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return timeStamp;
    }

    //Save new taken picture in phones memory
    //it received the context from the Activity and the bitmap
    public static String saveCamPicToPhone(Context c, Bitmap image) {
        String name = getTimeStamp();
        File dir = getDir();

        File photoPath = new File(dir, name + ".jpg");
        FileOutputStream fos = null;
        try {
            //New fileOutputstream with Bitmap included
            fos = new FileOutputStream(photoPath);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            Toast.makeText(c, "Opgeslagen", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(c, "Opslaan mislukt", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return photoPath.getPath();
    }

    //Convert LayerDrawable to Bitmap by using Canvas
    public static Bitmap createBitmap(LayerDrawable drawable) {

        Bitmap bitmap = Bitmap.createBitmap(960, 1280, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    //Save edited picture to phones memory
    //Returns saved location in File format
    public static File saveEdittedToPhone(View view, LayerDrawable l) {
        FragmentActivity activity = (FragmentActivity) view.getContext();

        Bitmap bitmap = createBitmap(l);
        File dir = getDir();
        String timeStamp = getTimeStamp();
        dir.mkdirs();
        File temp = new File(dir, timeStamp + ".png");

        try {
            FileOutputStream fos = new FileOutputStream(temp);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            Toast.makeText(activity, "Opgeslagen", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(activity, "Opslaan mislukt", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        //Save using MediaStore with Contentvalues added.
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATA, temp.toString());

        activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        return temp;
    }


    //Method saveEdittedToPhone overloaded for using with a Bitmap in steds of LayerDrawable
    //Returns saved location in File format
    public static File saveEdittedToPhone(View view, Bitmap b) {
        FragmentActivity activity = (FragmentActivity) view.getContext();
        File dir = getDir();
        String timeStamp = getTimeStamp();
        File temp = new File(dir, timeStamp + ".png");

        try {
            FileOutputStream fos = new FileOutputStream(temp);
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            Toast.makeText(activity, "Opgeslagen", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(activity, "Opslaan mislukt", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATA, temp.toString());

        activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        return temp;
    }
}
