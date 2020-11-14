package com.example.cameranovi.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.view.View;

import com.example.cameranovi.R;
import com.example.cameranovi.fragments.EditFragment;

import java.io.File;


//Class to edit picture and return editted version
public class EditPicture{
    private Drawable[] transparant = new Drawable[5];
    private String button;
    private Bitmap temp;
    private View view;

    public EditPicture(View v, String string, Bitmap bitmap) {
        button = string;
        temp = bitmap;
        view = v;

        //Array with transparent Drawable files
        transparant[0] = view.getResources().getDrawable(R.drawable.oren);
        transparant[1] = view.getResources().getDrawable(R.drawable.kapsel);
        transparant[2] = view.getResources().getDrawable(R.drawable.bril);
        transparant[3] = view.getResources().getDrawable(R.drawable.baard);
        transparant[4] = view.getResources().getDrawable(R.drawable.viking);
    }

    //For every button in the EditFragment viewController class it selects its Array indez and calls the returnPic method
    public void onClick() {
        switch (button) {
            case ("ears"):
                editAndReturn(0);
                break;
            case ("hair"):
                editAndReturn(1);
                break;
            case ("glasses"):
                editAndReturn(2);
                break;
            case ("beard"):
                editAndReturn(3);
                break;
            case ("viking"):
                editAndReturn(4);
                Log.d("mylog", "viking " + temp.getHeight());
                break;
        }
    }

    //Makes seperte Drawable Array with size of 2 and combines them using the Layerdrawable class
    //Then it calls the static method in the EditFragment class to return the LayerDrawable (and its view)
    public void editAndReturn(int i) {
        Drawable drawable = new BitmapDrawable(view.getResources(), temp);
        Drawable[] layers = new Drawable[2];
        layers[0] = drawable;
        layers[1] = transparant[i];
        LayerDrawable layerDrawable = new LayerDrawable(layers);
     
 	//sending the edited picture back to the fragment, including the view.
        EditFragment.changePicture(view, layerDrawable);
    }

    public static Bitmap rotate(File loc) {
        String location = loc.toString();
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(location, bounds);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(location, opts);
        Matrix matrix = new Matrix();
        matrix.setRotate(0, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
        return rotatedBitmap;
    }


}
