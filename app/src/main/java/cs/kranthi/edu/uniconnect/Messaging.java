package cs.kranthi.edu.uniconnect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Messaging extends Activity {

    private static final String TAG = "StartPage";
    private static final String KEY_USER="user";
    private static final String KEY_PASS="password";
    private static final String KEY_FNAME="fname";
    private static final String KEY_LNAME="lname";
    private static final String KEY_UNAME="Uname";
    private static final String KEY_DEPT="dept";
    private MessageAdapter messageAdapter;
    private ListView messagesList;
    private EditText messageBodyField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        Intent intent = getIntent();
        final String Qstn = intent.getStringExtra("QUESTION");
        messagesList = (ListView) findViewById(R.id.listofMessages);
        messageAdapter = new MessageAdapter(this);

        final DBConnect dbc = new DBConnect();

        ArrayList<Answers> Anss = dbc.getAnswers(Qstn);

        for (int i = 0; i < Anss.size(); i++) {
            Answers ans = Anss.get(i);
            messageAdapter.addMessage(ans.getANSWER(), ans.getANSWERED_BY());
        }

        messagesList.setAdapter(messageAdapter);

        messageBodyField = (EditText) findViewById(R.id.messageBodyField);

        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Context context = getApplicationContext();
                messageBodyField = (EditText) findViewById(R.id.messageBodyField);
                String ans = messageBodyField.getText().toString();
                System.out.println(ans);
                boolean res = dbc.insertAnswers(Qstn,ans);
                if(!res){

                    Toast.makeText(context,"Invalid Message. Can't Send.",Toast.LENGTH_SHORT).show();
                }
                else{
                    messageAdapter.addMessage(ans, DBConnect.user);
                    messageBodyField.setText("");
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

    private void populateMessageHistory() {}
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
