package project.android.ssau.jungleofc;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class FirstScreenActivity extends ActionBarActivity implements OnTouchListener{

    private View selected_item = null;
    private int offset_x = 0;
    private int offset_y = 0;
    Boolean touchFlag = false;
    boolean dropFlag = false;
    LayoutParams imageParams;
    ImageView imageDrop, image1, image2;
    int eX, eY;
    int topY, leftX, rightX, bottomY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View root = findViewById(android.R.id.content).getRootView();
        imageDrop = (ImageView) findViewById(R.id.ImgDrop);
        image1 = (ImageView) findViewById(R.id.img);
        image2 = (ImageView) findViewById(R.id.img2);
        image1.setOnTouchListener(this);
        image2.setOnTouchListener(this);
        root.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (touchFlag) {
                    System.err.println("Display If  Part ::->" + touchFlag);
                    switch (event.getActionMasked()) {
                        case MotionEvent.ACTION_DOWN:
                            topY = imageDrop.getTop();
                            leftX = imageDrop.getLeft();
                            rightX = imageDrop.getRight();
                            bottomY = imageDrop.getBottom();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            eX = (int) event.getX();
                            eY = (int) event.getY();
                            int x = (int) event.getX() - offset_x;
                            int y = (int) event.getY() - offset_y;
                            int w = getWindowManager().getDefaultDisplay().getWidth() - 50;
                            int h = getWindowManager().getDefaultDisplay().getHeight() - 10;
                            if (x > w) x = w;
                            if (y > h) y = h;
                            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                            lp.setMargins(x, y, 0, 0);

                            if (eX > leftX && eX < rightX && eY > topY && eY < bottomY) {
                                imageDrop.setBackgroundColor(Color.RED);
                                selected_item.bringToFront();
                                dropFlag = true;
                            } else {
                                imageDrop.setBackgroundColor(Color.BLUE);
                            }
                            selected_item.setLayoutParams(lp);
                            break;
                        case MotionEvent.ACTION_UP:
                            touchFlag = false;
                            if (dropFlag) {
                                dropFlag = false;
                            } else {
                                selected_item.setLayoutParams(imageParams);
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
        getMenuInflater().inflate(R.menu.menu_first_screen, menu);
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
