package se.chalmers.agile.pairprogrammingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import se.chalmers.agile.pairprogrammingapp.activities.MainActivity;

public class WelcomeActivity extends AppCompatActivity {
    public TextView showMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        final Button bLogout = (Button) findViewById(R.id.bLogout);
        showMessage = (TextView) findViewById(R.id.tv_welcome);
        Intent in = getIntent();
        Bundle b = in.getExtras();
        String nameString = b.getString("name");

        showMessage.setText("Welcome " + nameString + "!");

        bLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent logoutIntent = new Intent(WelcomeActivity.this, MainActivity.class);
                WelcomeActivity.this.startActivity(logoutIntent);
            }
        });
    }
    public static int plus(int x, int y){
        return x + y;
    }
}
