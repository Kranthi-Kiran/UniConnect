package cs.kranthi.edu.uniconnect;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Locations extends AppCompatActivity {

    private Button btnUniv;
    private Button btnDir;
    private Button btnRest;
    private Button btnApart;
    private Button btnGas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        btnUniv = (Button) findViewById(R.id.btnUniMap);
        btnUniv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+ DBConnect.uName);
                //Uri gmmIntentUri = Uri.parse("google.navigation:q="+ DBConnect.uName);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        btnDir = (Button) findViewById(R.id.btnDir);
        btnDir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+ DBConnect.uName);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        btnRest = (Button) findViewById(R.id.btnRest);
        btnRest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Uri gmmIntentUri = Uri.parse("geo:"+ DBConnect.uName+ "?q=restaurants");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        btnApart = (Button) findViewById(R.id.btnApart);
        btnApart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Uri gmmIntentUri = Uri.parse("geo:"+ DBConnect.uName+ "?q=Apartments");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        btnGas = (Button) findViewById(R.id.btnGas);
        btnGas.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Uri gmmIntentUri = Uri.parse("geo:"+ DBConnect.uName+ "?q=Gas Stations");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });




    }
}
