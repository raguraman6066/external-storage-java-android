package com.example.external_storage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {

    EditText person_name,person_age;
    Button save,load;
    TextView person_result;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        person_name=findViewById(R.id.person_name);
        person_age=findViewById(R.id.person_age);
        person_result=findViewById(R.id.person_result);
        save=findViewById(R.id.button);
        load=findViewById(R.id.button2);

    save.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           if(person_name.getText().toString().equalsIgnoreCase("")||person_age.getText().toString().equalsIgnoreCase("")){
               person_name.setError("Data cannot be empty");
               person_age.setError("Data cannot be empty");

           }else {
               String name=person_name.getText().toString();
               int age= Integer.parseInt(person_age.getText().toString());
               checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,123);
               File folder= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
               File file=new File(folder,"my_ext_file.txt");
               FileOutputStream fos;
               try {
                   fos=new FileOutputStream(file);
                   fos.write((name+"\n"+age).getBytes());
                   fos.close();
               } catch (Exception e) {
                   throw new RuntimeException(e);
               }
           }

            person_name.getText().clear();
            person_age.getText().clear();
            person_name.requestFocus();
        }
    });

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File folder= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File file=new File(folder,"my_ext_file.txt");
                try {
                    FileInputStream fis=new FileInputStream(file);
                    BufferedReader  br=new BufferedReader(new InputStreamReader(fis));
                    String line="";
                    StringBuilder sb=new StringBuilder();
                    while((line=br.readLine())!=null){
                        sb.append(line+"\n");
                    }
                    person_result.setText(sb.toString());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    // Function to check and request permission.
    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(MainActivity.this, new String[] { permission }, requestCode);
        }
        else {
          //  Toast.makeText(MainActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

}