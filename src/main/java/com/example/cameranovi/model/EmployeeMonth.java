package com.example.cameranovi.model;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.InputType;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import android.widget.EditText;
import com.example.cameranovi.R;
import com.example.cameranovi.fragments.SendFragment;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

//Class to draw Employee of the Month text onto choosen Bitmap
public class EmployeeMonth {

    private Bitmap bitmap;

    List<String> months;
    Date date;
    View view;

    //Constructor for receiving the activity and bitmap and to set the Array with monts
    public EmployeeMonth(View v, Bitmap b) {
        months = Arrays.asList("Januari", "Februari", "Maart", "April", "Mei", "Juni", "Juli", "Augustus", "September", "Oktober", "November", "December");
        view = v;
        bitmap = b;
    }

    //Pops up dialog for user to enter name of employee of month, when clicking ok the next method starts for drawing the name etc
    public void start() {

        final String[] text = new String[1];

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Wie is medewerker van de maand?");

        final EditText input = new EditText(view.getContext());
// S
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                text[0] = input.getText().toString();
                addEmployeeMonth(text[0]);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    //Draw the text with current month depending on what time of the year it is
    //Using paint it set a white font with black border so its more visible in darker pictures
    public void addEmployeeMonth(String name) {
        int color = view.getResources().getColor(R.color.FireBrick);

        Bitmap b = bitmap;
        TextPaint paint = new TextPaint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        paint.setColor(color);
        paint.setFakeBoldText(true);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(120);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        Bitmap copyBitmap = b.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(copyBitmap);
        String month = getMonth();
        canvas.drawText("Novi Medewerker Van", 3, 115, paint);
        canvas.drawText(month, 220, 222, paint);

        StaticLayout layout = new StaticLayout(name, paint, canvas.getWidth() - 10, Layout.Alignment.ALIGN_CENTER, 1.3f, 0, false);
        canvas.translate(5, canvas.getHeight() - 135);
        layout.draw(canvas);
        SendFragment.drawEmployeeMonth(view, copyBitmap);
    }

    //Get the current month and return it in String format
    public String getMonth() {
        date = new Date();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        return months.get(month);
    }
}
