package com.example.cameranovi.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.cameranovi.model.EmployeeMonth;
import com.example.cameranovi.R;
import com.example.cameranovi.model.Saver;
import com.example.cameranovi.model.Sender;

import java.io.IOException;

//This is the Viewcontroller for the "Verzenden" tab
public class SendFragment extends Fragment {

    private static final int PICTURE_PICK = 1000;

    private Uri imgUri;
    private Bitmap temp;
    private static ImageView sendView;
    private static View view;
    private TextView textView;
    private static Bitmap employeeMonth;

    public SendFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_send, container, false);
        sendView = view.findViewById(R.id.sendView);
        textView = view.findViewById(R.id.textView);

        return view;
    }

    public void send() {
        if (imgUri != null) {
            new Sender(getActivity(), imgUri).shareSend();
        } else {
            Toast.makeText(getActivity(), "Open eerst een foto", Toast.LENGTH_LONG).show();
        }
    }

    public void setEmployeeMonth() {
        if (temp != null) {
            new EmployeeMonth(view, temp).start();
        } else {
            Toast.makeText(getActivity(), "Open eerst een foto", Toast.LENGTH_LONG).show();
        }
    }

    //Being called from addEmployeeMonth method in EmployteeMonth class and sets the Imageview
    public static void drawEmployeeMonth(View v, Bitmap returned) {
        employeeMonth = returned;
        ImageView sendView = v.findViewById(R.id.sendView);
        sendView.setImageBitmap(returned);
    }


    //Pic image from Gallery using intent
    public void imageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(
                Intent.createChooser(intent, "Selecteer een bestand om te uploaden"),
                PICTURE_PICK);
    }

    public void save() {
        if (employeeMonth != null) {
            Saver.saveEdittedToPhone(view, employeeMonth);
        } else {
            Toast.makeText(getActivity(), "Geen wijziging gevonden", Toast.LENGTH_LONG).show();
        }
    }

    //Displays choosen picture in Imageview and disables Textview
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == getActivity().RESULT_OK && requestCode == PICTURE_PICK) {
            imgUri = data.getData();
            sendView.setImageURI(imgUri);
            textView.setVisibility(View.INVISIBLE);
            //Makes temp bitmap from Uri variable
            try {
                temp = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imgUri));
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Er is iets fout gegaan", Toast.LENGTH_SHORT).show();
            }
        }
    }
}