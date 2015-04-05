package project.android.ssau.jungleofc;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.TextView;
import android.graphics.Color;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import java.util.ArrayList;


public class PerfomanceActivity extends ActionBarActivity implements OnTouchListener {


    private View selected_item = null;
    private int offset_x = 0;
    private int offset_y = 0;
    Boolean touchFlag = false;
    boolean dropFlag = false;
    LayoutParams imageParams;
    TextView text3,text1,text2;
    int eX, eY;
    int topY, leftX, rightX, bottomY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfomance);
        //дергаем из бд текст задания и массив строк
        //создаём из массова строк TextView`ы
        //применяем тачЛистнеры
        //определяем размещение
        //сверяем наш ответ с правильнымsafdasdfsa dfsad fasdf sadfasdfwera dsf

        String user = "ЖЫвотное";
        ArrayList<String> gift;// = "дырку от бублика";
        user = Variables.task;//getIntent().getExtras().getString("username");

        gift = Variables.programm;// getIntent().getExtras().getString("gift");
        TextView infoTextView = (TextView)findViewById(R.id.textView3);
        infoTextView.setText(user);
        TextView info = (TextView)findViewById(R.id.textView4);
        info.setText(gift.toString());

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View root = findViewById(android.R.id.content).getRootView();
        text1 = (TextView) findViewById(R.id.textView4);
        text2 = (TextView) findViewById(R.id.textView5);
        text1.setOnTouchListener(this);
        text2.setOnTouchListener(this);
        root.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (touchFlag) {
                    System.err.println("Display If  Part ::->" + touchFlag);
                    switch (event.getActionMasked()) {
                        case MotionEvent.ACTION_DOWN:
                            topY =  v.getTop();
                            leftX =  v.getLeft();
                            rightX =  v.getRight();
                            bottomY =  v.getBottom();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            eX = (int) event.getX();
                            eY = (int) event.getY();
                            int x = (int) event.getX();
                            int y = (int) event.getY();
                            int w = getWindowManager().getDefaultDisplay().getWidth();
                            int h = getWindowManager().getDefaultDisplay().getHeight();
                            if (x > w) x = w;
                            if (y > h) y = h;
                            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                            lp.setMargins(x, y, 0, 0);

                           if (eX > leftX && eX < rightX && eY > topY && eY < bottomY) {
                             //   text1.setBackgroundColor(Color.RED);
                                selected_item.bringToFront();
                                dropFlag = true;
                            }// else {
                              //  text1.setBackgroundColor(Color.BLUE);
                            //}
                            selected_item.setLayoutParams(lp);
                            break;
                        case MotionEvent.ACTION_UP:
                            touchFlag = false;
                            if (dropFlag) {
                                dropFlag = false;
                            } else {
                                //selected_item.setLayoutParams(imageParams);
                            }
                            break;
                        default:
                            break;
                    }
                }
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_perfomance, menu);
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
        Intent intent = new Intent(PerfomanceActivity.this, BestActivity.class);
        startActivity(intent);
    }

    /**
     * Called when a touch event is dispatched to a view. This allows listeners to
     * get a chance to respond before the target view.
     *
     * @param v     The view the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about
     *              the event.
     * @return True if the listener has consumed the event, false otherwise.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                touchFlag = true;
                offset_x = (int) event.getX();
                offset_y = (int) event.getY();
                selected_item = v;
                imageParams = v.getLayoutParams();
                break;
            case MotionEvent.ACTION_UP:
                selected_item = null;
                touchFlag = false;
                break;
            default:
                break;
        }
        return false;
    }
}
