package com.example.cameranovi.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.cameranovi.model.EditPicture;
import com.example.cameranovi.R;
import com.example.cameranovi.model.Saver;
import java.io.File;
import java.io.IOException;

//This is the Viewcontroller for the "bewerken" tab
public class EditFragment extends Fragment {

    private static final int PICTURE_PICK = 1000;

    private TextView textView;
    private static ImageView imgViewEdit;
    private Bitmap temp;
    private Uri imgUri;
    static View view;
    static LayerDrawable tempDrawable;

    public EditFragment() {
    }

    //Initialize view attributes
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_edit, container, false);

        imgViewEdit = view.findViewById(R.id.imageViewEdit);
        textView = view.findViewById(R.id.textView);
        return view;
    }

    //Saves edited LayerDrawable and saves the rotates Bitmap using static methods
    public void save() {
        if (tempDrawable != null) {

            File location = Saver.saveEdittedToPhone(view, tempDrawable);
            Bitmap rotated = EditPicture.rotate(location);
            imgViewEdit.setImageBitmap(rotated);
        }
    }

    public void editPicture(String button) {
        if (temp != null) {
            Log.d("mylog", "editpictre " + button);
            new EditPicture(view, button, temp).onClick();
        } else {
            Toast.makeText(getActivity(), "Open eerst een foto", Toast.LENGTH_LONG).show();
        }
    }

    //This methods gets called from Model class EditPicture, it received the LayerDrawable and sets the ImageVie
    public static void changePicture(View v, LayerDrawable l) {
        tempDrawable = l;
        ImageView imgViewEdit = v.findViewById(R.id.imageViewEdit);
        imgViewEdit.setImageDrawable(tempDrawable);
    }

    //Start intent to pick a picture from the phones Gallery
    public void imageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICTURE_PICK);
    }

    //Sets Imageview after intent and disables Textview
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == getActivity().RESULT_OK && requestCode == PICTURE_PICK) {
            imgUri = data.getData();
            imgViewEdit.setImageURI(imgUri);
            textView.setVisibility(View.INVISIBLE);

            // Make a seperate  temp Bitmap from Uri and save it to global variable
            try {
                temp = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imgUri));
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Er is iets fout gegaan", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
