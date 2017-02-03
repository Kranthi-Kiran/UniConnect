package cs.kranthi.edu.uniconnect;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SignUp extends AppCompatActivity {

    private static final String TAG = "SignUp";
    private static final String KEY_USER="user";
    private static final String KEY_PASS="password";
    private static final String KEY_FNAME="fname";
    private static final String KEY_LNAME="lname";
    private static final String KEY_UNAME="Uname";
    private static final String KEY_DEPT="dept";
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;

    private EditText UserName;
    private EditText password;
    private EditText FirstName;
    private EditText LastName;
    private Spinner Deptspin;
    private Spinner Semister;
    private Spinner Unispin;
    private Spinner lType;
    private EditText GRE;
    private EditText TOFEL;
    private Button reg;
    private Button ImgButton;
    private ImageView imageView;// = (ImageView) findViewById(R.id.imgvw);
    private static String dept,univ,type,sem;
    Intent cameraIntent;// = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    Uri fileUri;
    public static String finalURI;
    private static String file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(savedInstanceState == null){
            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        }

        /*Loading the intent with URI so the image can be stored at the path*/
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        ImgButton  = (Button) findViewById(R.id.cpimg);
        imageView = (ImageView) findViewById(R.id.imgvw);
        ImgButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Print fileUri "+fileUri);

                startActivityForResult(cameraIntent,CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

                /*Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);*/
            }
        });

        Deptspin = (Spinner) findViewById(R.id.DeptSpinner);
        final DBConnect dbc = new DBConnect();

        ArrayList<String> depts = dbc.getDepartments();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,depts);
        Deptspin.setAdapter(adapter);

        Unispin = (Spinner) findViewById(R.id.UniSpinner);
        ArrayList<String> uni = dbc.getUniversities();
        ArrayAdapter<String> adapterd = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,uni);
        Unispin.setAdapter(adapterd);

        Semister = (Spinner) findViewById(R.id.SemSpinner);
        ArrayList<String> semsp = new ArrayList<String>();
        semsp.add(0,"       ");
        semsp.add(1,"Fall");
        semsp.add(2,"Winter");
        semsp.add(3,"Spring");
        semsp.add(4,"Summer");
        ArrayAdapter<String> adapters = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,semsp);
        Semister.setAdapter(adapters);

        lType = (Spinner) findViewById(R.id.TypeSpinner);
        ArrayList<String> typesp = new ArrayList<String>();
        typesp.add(0,"            ");
        typesp.add(1,"Student");
        typesp.add(2,"Faculty");
        ArrayAdapter<String> adaptert = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,typesp);
        lType.setAdapter(adaptert);


        Deptspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dept = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Unispin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                univ = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Semister.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sem = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        reg = (Button) findViewById(R.id.reg);
        reg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                UserName = (EditText) findViewById(R.id.userName);
                String unsme = UserName.getText().toString();
                password = (EditText) findViewById(R.id.SUTUPWD);
                String pwd = password.getText().toString();

                FirstName = (EditText) findViewById(R.id.SUTUFNM);
                String fname = FirstName.getText().toString();
                LastName = (EditText) findViewById(R.id.SUTULNM);
                String lname = LastName.getText().toString();

                GRE = (EditText) findViewById(R.id.SUTUGRE);
                String gre = GRE.getText().toString();
                TOFEL = (EditText) findViewById(R.id.SUTUTOFEL);
                String tofel = TOFEL.getText().toString();

                final Context context = getApplicationContext();
                boolean res = dbc.insertAccount(unsme, pwd, fname, lname, dept, univ, type, sem, gre, tofel);

                if(res) {

                    String result = dbc.getUser(unsme, pwd);
                    if (result == "null") {

                        Toast.makeText(context, "Registration Not complete. User Already Exists.", Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(SignUp.this, StartPage.class));
                        Toast.makeText(context, "Registration Complete", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(context, "Registration Not complete. User Already Exists.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(savedInstanceState != null){
            DBConnect.user = savedInstanceState.getString(KEY_USER);
            DBConnect.Password = savedInstanceState.getString(KEY_PASS);
            DBConnect.fName = savedInstanceState.getString(KEY_FNAME);
            DBConnect.lName = savedInstanceState.getString(KEY_LNAME);
            DBConnect.uName = savedInstanceState.getString(KEY_UNAME);
            DBConnect.dept = savedInstanceState.getString(KEY_DEPT);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSaveInstanceState");
        if(DBConnect.user != null) {
            savedInstanceState.putString(KEY_USER, DBConnect.user);
            savedInstanceState.putString(KEY_PASS, DBConnect.Password);
            savedInstanceState.putString(KEY_FNAME, DBConnect.fName);
            savedInstanceState.putString(KEY_LNAME, DBConnect.lName);
            savedInstanceState.putString(KEY_UNAME, DBConnect.uName);
            savedInstanceState.putString(KEY_DEPT, DBConnect.dept);
        }
    }

    private static Uri getOutputMediaFileUri(int type){
        System.out.println("Entered Function getOutputMediaFileUri");
        return Uri.fromFile(getOutputMediaFile(type));
    }


    /*This function is basically written to derive the path to save the image when clicked.*/
    private static File getOutputMediaFile(int type){

        System.out.println("Entered Function getOutputMediaFile");
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "UniConnect");
        if (! mediaStorageDir.exists()){
            /*Creating a directory with name UniConnect if it doesn't exists.*/
            if (! mediaStorageDir.mkdirs()){
                Log.d("Uniconnect", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        File mediaFile;

        /*Creating a file path to where the image needs to be saved.*/
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        finalURI = mediaStorageDir.getPath() + File.separator + "HIST_IMG_"+ timeStamp + ".jpg";

        System.out.println("Returning from file URL with: "+mediaFile);
        file = mediaFile.toString();
        return mediaFile;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
            if(resultCode == RESULT_OK){

                imageView.setImageURI(fileUri);
            }
            else if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, "Image Cancelled", Toast.LENGTH_LONG).show();
            }
        }
    }
}
