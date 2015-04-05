package project.android.ssau.jungleofc;


import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.ContentUris;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import java.io.IOException;
import android.widget.Toast;


public class SettingsActivity extends ActionBarActivity  implements OnPreparedListener,
        OnCompletionListener {
    final String LOG_TAG = "myLogs";

    final String DATA_HTTP = "http://dl.dropboxusercontent.com/u/6197740/explosion.mp3";
    final String DATA_STREAM = "http://online.radiorecord.ru:8101/rr_128";
    final String DATA_SD = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
            + "/music.mp3";
    final Uri DATA_URI = ContentUris
            .withAppendedId(
                    android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    13359);

    MediaPlayer mediaPlayer;
    AudioManager am;


    private int selectedTest;
    private int selectedTest2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        am = (AudioManager) getSystemService(AUDIO_SERVICE);
        RadioGroup radio = (RadioGroup)findViewById(R.id.radioGroup1 );
        selectedTest = radio.getCheckedRadioButtonId();
        Log.d("onCreate", "selectedTest="+selectedTest);

        RadioGroup radio1 = (RadioGroup)findViewById(R.id.radioGroup2 );
        selectedTest2 = radio1.getCheckedRadioButtonId();
        Log.d("onCreate", "selectedTest2="+selectedTest2);

        TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);

        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("tag2");

        spec.setContent(R.id.tab2);
        spec.setIndicator("Уровень");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Музыка");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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


    /**
     * Called when the end of a media source is reached during playback.
     *
     * @param mp the MediaPlayer that reached the end of the file
     */
    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    /**
     * Called when the media file is ready for playback.
     *
     * @param mp the MediaPlayer that is ready for playback
     */
    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    public void onClick(View view) throws IOException
    {
        releaseMP();
        Log.d(LOG_TAG, "start Raw");
        mediaPlayer = MediaPlayer.create(this, R.raw.mymusic2);
        mediaPlayer.start();
        if (mediaPlayer != null) {
            mediaPlayer.setLooping(true);
        }

    }
    private void releaseMP() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void  onClick1(View view)
    {

        mediaPlayer.stop();
    }
    public void onClick2 (View v)
    {
        selectedTest2 = v.getId();
        Log.d("onClick2", "selectedTest2="+selectedTest2);
        RadioButton button = (RadioButton) v;
        Toast.makeText(SettingsActivity.this,"Был выбран уровень: "+ button.getText(), Toast.LENGTH_SHORT).show();
    }

}
