package com.example.ravi.profilersql;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    DataopenHelper dataopenHelper;
    SQLiteDatabase db;
    Button btnChangeWallpa,btnMusic,btnsetWall,btnplayring;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        btnChangeWallpa = findViewById(R.id.btnWall);
        btnMusic = findViewById(R.id.btnMusicselect);
        btnsetWall = findViewById(R.id.btnWallset);
        btnplayring = findViewById(R.id.playselect);

        dataopenHelper = new DataopenHelper(MainActivity.this,"DD",null,1);
        db = dataopenHelper.getWritableDatabase();






        btnsetWall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Cursor c = db.query("Data",null,null,null,null,null,null);


                c.moveToLast();

                String con = "content://media";
                String  s= c.getString(0);
                String ur = con+s;


                if (ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }

                Toast.makeText(MainActivity.this, " "+s, Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse(ur);

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                WallpaperManager wallpaperManager = WallpaperManager.getInstance(MainActivity.this);
                try {

                    wallpaperManager.setBitmap(bitmap);
                    Toast.makeText(MainActivity.this, "WallPaper Sucseefully Changed..", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

        btnplayring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Cursor c = db.query("MusicData",null,null,null,null,null,null);
                c.moveToLast();


                String con = "content://media";
                String  s= c.getString(0);
                String ur = con+s;

                Intent intent = new Intent();
                intent.setAction( Intent.ACTION_VIEW );

                Uri uri = Uri.parse(ur);
                intent.setDataAndType( uri, "audio/mp3");

                startActivity( intent );

            }
        });


        btnChangeWallpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Re"),1);



               }




        });


        btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                startActivityForResult(intent,66);



            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode==1)
        {
            Uri uri = data.getData();
            String s = uri.getPath();

            ContentValues contentValues = new ContentValues();
            contentValues.put("ImageData",s);


            long count = db.insert("Data",null,contentValues);
            if (count >0)
            {
                Toast.makeText(this, "Data in sert Succefullu", Toast.LENGTH_SHORT).show();
            }



        }


        if (requestCode ==66)
        {




             Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

            String s = uri.getPath();

            ContentValues contentValues = new ContentValues();
            contentValues.put("Musicdatase",s);

            long count = db.insert("MusicData",null,contentValues);
            Log.e("Count", String.valueOf(count));
            if (count >0)
            {
                Toast.makeText(this, "Data in sert Succefullu", Toast.LENGTH_SHORT).show();
            }



        }






    }


}
