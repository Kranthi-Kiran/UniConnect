package cs.kranthi.edu.uniconnect;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class EnterQuestion extends AppCompatActivity {

    private static final String TAG = "EnterQuestion";
    private static final String KEY_USER="user";
    private static final String KEY_PASS="password";
    private static final String KEY_FNAME="fname";
    private static final String KEY_LNAME="lname";
    private static final String KEY_UNAME="Uname";
    private static final String KEY_DEPT="dept";
    private Spinner Deptspin;
    private static String dept;

    private EditText Qstn;
    private Button Askqstn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_question);

        final Context context = getApplicationContext();
        Qstn = (EditText) findViewById(R.id.QstnEditText);
        final String question = Qstn.getText().toString();
        System.out.println(question);

        Deptspin = (Spinner) findViewById(R.id.DeptSpinner1);
        final DBConnect dbc = new DBConnect();

        ArrayList<String> depts = dbc.getDepartments();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,depts);
        Deptspin.setAdapter(adapter);

        Deptspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dept = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /*DBConnect dbc = new DBConnect();
        boolean res = dbc.insertQuestion(question);*/

        Askqstn = (Button) findViewById(R.id.BtnAsk);
        Askqstn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Qstn = (EditText) findViewById(R.id.QstnEditText);
                final String question = Qstn.getText().toString();
                System.out.println(question);
                DBConnect dbc = new DBConnect();
                if(dept.equals("                          ")){
                    Toast.makeText(context,"Select A Department",Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean res = dbc.insertQuestion(question, dept);
                    if (!res) {

                        Toast.makeText(context, "Invalid Message. Can't Send.", Toast.LENGTH_SHORT).show();
                    } else {
                        Qstn.setText("");
                        finish();
                    }
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
}
