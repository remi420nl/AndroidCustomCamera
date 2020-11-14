package com.example.cameranovi.model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Sender {

    Uri uri;
    Context context;

    public Sender(Context c, Uri u) {
        context = c;
        uri = u;
    }

    //Start sharing intent to share chosen picture via Whatsapp,Email etc
    public void shareSend() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Shared");
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
        sendIntent.setType("image/*");
        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(sendIntent);
    }
}
