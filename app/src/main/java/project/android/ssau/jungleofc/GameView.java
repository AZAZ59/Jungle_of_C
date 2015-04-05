package project.android.ssau.jungleofc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by 123 on 18.03.2015.
 */
public class GameView  extends SurfaceView implements Runnable {
    /**Объект класса GameLoopThread*/
    private SoundPool sounds;
    private int sExplosion;
    private GameThread mThread;
    //===================================
    public int shotX;
    public int shotY;
    /**Переменная запускающая поток рисования*/
    private boolean running = false;
    //===================================
    private List<Bullet> ball = new ArrayList<Bullet>();
    private Player player;
    Bitmap players;
    //===================================
    private List<Enemy> enemy = new ArrayList<Enemy>();
    Bitmap enemies;
    private Thread thred = new Thread(this);

    /**
     * Starts executing the active part of the class' code. This method is
     * called when a thread is started that has been created with a class which
     * implements {@code Runnable}.
     */

    //-------------Start of GameThread--------------------------------------------------\\

    public class GameThread extends Thread
    {
        /**Объект класса*/
        private GameView view;

        /**Конструктор класса*/
        public GameThread(GameView view)
        {
            this.view = view;
        }

        /**Задание состояния потока*/
        public void setRunning(boolean run)
        {
            running = run;
        }

        /** Действия, выполняемые в потоке */
        public void run()
        {
            while (running)
            {
                Canvas canvas = null;
                try
                {
                    // подготовка Canvas-а
                    canvas = view.getHolder().lockCanvas();
                    synchronized (view.getHolder())
                    {
                        // собственно рисование
                        onDraw(canvas);
                    }
                }
                catch (Exception e) { }
                finally
                {
                    if (canvas != null)
                    {
                        view.getHolder().unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }

    //-------------End of GameThread--------------------------------------------------\\

    public GameView(Context context)
    {
        super(context);
        players= BitmapFactory.decodeResource(getResources(), R.drawable.player2);
        player= new Player(this,players);
        enemies = BitmapFactory.decodeResource(getResources(), R.drawable.target);
        enemy.add(new Enemy(this, enemies));
        enemy.add(new Enemy(this, enemies));
        enemy.add(new Enemy(this, enemies));
        enemy.add(new Enemy(this, enemies));
        sounds = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        sExplosion = sounds.load( context,R.raw.explosion, 1);
        mThread = new GameThread(this);

        /*Рисуем все наши объекты и все все все*/
        getHolder().addCallback(new SurfaceHolder.Callback()
        {
            /*** Уничтожение области рисования */
            public void surfaceDestroyed(SurfaceHolder holder)
            {
                boolean retry = true;
                mThread.setRunning(false);
                while (retry)
                {
                    try
                    {
                        // ожидание завершение потока
                        mThread.join();
                        retry = false;
                    }
                    catch (InterruptedException e) { }
                }
            }

            /** Создание области рисования */
            public void surfaceCreated(SurfaceHolder holder)
            {
                mThread.setRunning(true);
                mThread.start();
            }

            /** Изменение области рисования */
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
            {
            }
        });

    }

    /**Функция рисующая все спрайты и фон*/
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        Iterator<Bullet> j = ball.iterator();
        while(j.hasNext()) {
            Bullet b = j.next();
            if(b.x >= 1000 || b.x <= 1000) {
                b.onDraw(canvas);
            } else {
                j.remove();
            }
        }
        canvas.drawBitmap(players, 5, 120, null);
        Iterator<Enemy> i = enemy.iterator();
        while(i.hasNext()) {
            Enemy e = i.next();
            if(e.x >= 1000 || e.x <= 1000) {
                e.onDraw(canvas);
            } else {
                i.remove();
            }
        }
    }

    public Bullet createSprite(int resouce) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
        return new Bullet(this, bmp);
    }


    public boolean onTouchEvent(MotionEvent e)
    {
        shotX = (int) e.getX();
        shotY = (int) e.getY();

        if(e.getAction() == MotionEvent.ACTION_DOWN)
            ball.add(createSprite(R.drawable.bullet));

        return true;
    }
    /*Проверка на столкновения*/
    private void testCollision() {

        Iterator<Bullet> b = ball.iterator();
        while(b.hasNext()) {
            Bullet balls = b.next();
            Iterator<Enemy> i = enemy.iterator();
            while(i.hasNext()) {
                Enemy enemies = i.next();

                if ((Math.abs(balls.x - enemies.x) <= (balls.width + enemies.width) / 2f)
                        && (Math.abs(balls.y - enemies.y) <= (balls.height + enemies.height) / 2f)) {
                    i.remove();
                    b.remove();
                    //вот эта строчка добавляется для проигрыша звука
                    sounds.play(sExplosion, 1.0f, 1.0f, 0, 0, 1.5f);
                }
            }
        }

    }
    @Override
    public void run() {
        while(true) {
            Random rnd = new Random();
            try {
                Thread.sleep(rnd.nextInt(2000));
                enemy.add(new Enemy(this, enemies));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
