package cs.kranthi.edu.uniconnect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    private static final String TAG = "Login";
    private static final String KEY_USER="user";
    private static final String KEY_PASS="password";
    private static final String KEY_FNAME="fname";
    private static final String KEY_LNAME="lname";
    private static final String KEY_UNAME="Uname";
    private static final String KEY_DEPT="dept";
    private Button mLogin;
    private Button mSignUp;
    private EditText mUserName;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Context context = getApplicationContext();

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-9819089571653252~6270049721");

        AdView mAdView = (AdView) findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice("4E5498E20A40AF522D7D5D0995C607EF").build();
        mAdView.loadAd(adRequest);

        mUserName = (EditText) findViewById(R.id.userName);
        mPassword = (EditText) findViewById(R.id.ePassword);
        mLogin = (Button) findViewById(R.id.enter);
        mLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DBConnect dbc = new DBConnect();
                String name  = mUserName.getText().toString();
                String pass = mPassword.getText().toString();
                String result = dbc.getUser(name,pass);
                System.out.println("Returned Result is "+result);
                if(result == "null"){

                    Toast.makeText(context,"Invalid User",Toast.LENGTH_SHORT).show();
                }
                else{
                    startActivity(new Intent(Login.this, StartPage.class));
                }



            }
        });

        mSignUp = (Button) findViewById(R.id.signup);
        mSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(Login.this, SignUp.class));
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

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

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

    @Override
    protected void onResume() {
        super.onResume();
        mUserName.setText("");
        mPassword.setText("");
    }
}
