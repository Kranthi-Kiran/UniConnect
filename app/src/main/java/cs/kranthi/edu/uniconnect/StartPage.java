package cs.kranthi.edu.uniconnect;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StartPage extends AppCompatActivity {

    private static final String TAG = "StartPage";
    private static final String KEY_USER="user";
    private static final String KEY_PASS="password";
    private static final String KEY_FNAME="fname";
    private static final String KEY_LNAME="lname";
    private static final String KEY_UNAME="Uname";
    private static final String KEY_DEPT="dept";

    private static int QuestionID;
    private ListView mUserListView;
    private ArrayAdapter<String> AAQstns;
    private TextView txtvwName;
    private Button mBtnQstn;
    private Button mBtnMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        txtvwName = (TextView) findViewById(R.id.TVUName);
        txtvwName.setText(DBConnect.fName+" "+DBConnect.lName);
        final DBConnect dbc = new DBConnect();
        ArrayList<String> qs = dbc.getList();
        mUserListView = (ListView) findViewById(R.id.usersListView);
        AAQstns =
                new ArrayAdapter<String>(getApplicationContext(),
                        R.layout.user_list_item, qs);

        mUserListView.setAdapter(AAQstns);

        mUserListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int i, long l) {
                String qstn = AAQstns.getItem(i);
                Intent intent = new Intent(getApplicationContext(), Messaging.class);
                intent.putExtra("QUESTION",qstn);
                startActivity(intent);
                System.out.println("Print i in Start Page "+qstn);
            }
        });

        mBtnQstn = (Button) findViewById(R.id.btnQstn);
        mBtnQstn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(StartPage.this, EnterQuestion.class));
            }
        });

        mBtnMap = (Button) findViewById(R.id.btnMap);
        mBtnMap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent = new Intent(StartPage.this,Locations.class);
                startActivity(intent);
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

    @Override
    protected void onResume() {
        super.onResume();

        final DBConnect dbc = new DBConnect();
        ArrayList<String> qs = dbc.getList();
        mUserListView = (ListView) findViewById(R.id.usersListView);
        AAQstns =
                new ArrayAdapter<String>(getApplicationContext(),
                        R.layout.user_list_item, qs);

        mUserListView.setAdapter(AAQstns);
        mUserListView.refreshDrawableState();
    }
}
