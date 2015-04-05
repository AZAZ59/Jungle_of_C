package project.android.ssau.jungleofc;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    }
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, ReferenceActivity.class);
        startActivity(intent);

    }
    public void onClick1(View view) {
        Intent intent = new Intent(MainActivity.this, MyCreationActivity.class);
        startActivity(intent);

    }
    public void onClick2(View view) throws IOException {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);

    }

    public void onClick4(View view) {
        Intent intent = new Intent(MainActivity.this, DemoBDActivity.class);
        startActivity(intent);

    }


    public void onClick5(View view) {
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        startActivity(intent);
    }


    public void onClick6(View view) {
        Intent intent = new Intent(MainActivity.this, FirstScreenActivity.class);
        startActivity(intent);
    }
}
