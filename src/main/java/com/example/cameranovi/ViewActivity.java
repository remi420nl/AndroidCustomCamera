package com.example.cameranovi;

/*
Eindopdracht Software Development Praktijk 1
Remi Peerlings
Novi Maastricht
februari 2020
*/
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.View;
import com.example.cameranovi.fragments.CamFragment;
import com.example.cameranovi.fragments.EditFragment;
import com.example.cameranovi.fragments.SendFragment;
import com.example.cameranovi.model.Permission;
import com.google.android.material.tabs.TabLayout;

//This is the "Main" ViewActivity / Activity which sets the Viewpageradapter and initializes the tabs
//It also calls the static checkPermission method in the Permission Class
//It extends toe Permission class so it has its Activity inhereted
public class ViewActivity extends Permission {
    CamFragment camFragment;
    EditFragment editFragment;
    SendFragment sendFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.viewactivity);

        //Checks all neccesary permissons for this Activity / App
        Permission.checkPermissions(this);

        TabLayout tabLayout = findViewById(R.id.tab_Layout);
        ViewPager viewPager = findViewById(R.id.view_Pager);

        camFragment= new CamFragment();
        editFragment = new EditFragment();
        sendFragment = new SendFragment();

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(camFragment, "Camera");
        viewPagerAdapter.addFragment(editFragment, "Bewerk");
        viewPagerAdapter.addFragment(sendFragment, "Verzend");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }


    // Onclickhandler received OnClicks from the XML View an forwards the click to the desired
    // ViewController Method based on their id
    public void onClick(View view){
        switch(view.getId()){
            case R.id.cameraButton:
                camFragment.takePicture();
                break;
            case R.id.openEditButton:
                editFragment.imageFromGallery();
                break;
            case R.id.saveEditButton:
                editFragment.save();
                break;
            case R.id.openSendButton:
                sendFragment.imageFromGallery();
                break;
            case R.id.saveSendButton:
                sendFragment.save();
                break;
            case R.id.employeeMonthButton:
                sendFragment.setEmployeeMonth();
                break;
            case R.id.sendButton:
                sendFragment.send();
                break;
            case R.id.hairButton:
                editFragment.editPicture("hair");
                break;
            case R.id.earsButton:
                editFragment.editPicture("ears");
                break;
            case R.id.glassesButton:
                editFragment.editPicture("glasses");
                break;
            case R.id.vikingButton:
                editFragment.editPicture("viking");
                break;
            case R.id.beardButton:
                editFragment.editPicture("beard");
                break;
        }
}}
