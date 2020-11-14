package com.example.cameranovi.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.cameranovi.R;
import com.example.cameranovi.model.Camera;
import java.io.File;
import java.io.IOException;


//This is the Viewcontroller for the Camera Tab
public class CamFragment extends Fragment {
    private static final int PhotoCode = 1010;
    private TextView textView;
    private ImageView imageView;

    public CamFragment() {
    }

    //Initialize attributes from View
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cam, container, false);
        textView = view.findViewById(R.id.textView2);
        imageView = view.findViewById(R.id.imageView2);
        return view;
    }

    //Created picture file and starts intent with extra output
    public void takePicture() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Checks if camera is present
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = Camera.createFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (photoFile != null) {
                String authorities = getActivity().getApplicationContext().getPackageName() + ".fileprovider";
                Camera.setImgUri(FileProvider.getUriForFile(getActivity(),
                        authorities,
                        photoFile));
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Camera.getImageUri());
                startActivityForResult(takePictureIntent, PhotoCode);
            }
        }
    }

    //After intent it checks for result and requestcode and calls the 2 methods and disables textview
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PhotoCode && resultCode == getActivity().RESULT_OK) {
            Camera.saveBitmap(getActivity());
            Camera.addToPictures(getActivity());
            textView.setVisibility(View.INVISIBLE);
            imageView.setImageURI(Camera.getImageUri());
        }
    }
}