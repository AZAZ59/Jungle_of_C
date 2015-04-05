package project.android.ssau.jungleofc;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
/**
 * Created by 123 on 18.03.2015.
 */
public class Bullet {
    /**Картинка*/
    private Bitmap bmp;

    /**Позиция*/
    public int x;
    public int y;

    /**Скорость по Х=15*/
    private int mSpeed=25;

    public double angle;

    /**Ширина*/
    public int width;

    /**Ввыоста*/
    public  int height;

    public GameView gameView;

    /**Конструктор*/
    public Bullet(GameView gameView, Bitmap bmp) {
        this.gameView=gameView;
        this.bmp=bmp;

        this.x = 0;            //позиция по Х
        this.y = 120;          //позиция по У
        this.width = 27;       //ширина снаряда
        this.height = 40;      //высота снаряда

        //угол полета пули в зависипости от координаты косания к экрану
        angle = Math.atan((double)(y - gameView.shotY) / (x - gameView.shotX));
    }

    /**Перемещение объекта, его направление*/
    private void update() {
        x += mSpeed * Math.cos(angle);         //движение по Х со скоростью mSpeed и углу заданном координатой angle
        y += mSpeed * Math.sin(angle);         // движение по У -//-
    }

    /**Рисуем наши спрайты*/
    public void onDraw(Canvas canvas) {
        update();                              //говорим что эту функцию нам нужно вызывать для работы класса
        canvas.drawBitmap(bmp, x, y, null);
    }
}
