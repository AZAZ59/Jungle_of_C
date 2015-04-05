package project.android.ssau.jungleofc;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MyCreationActivity extends ActionBarActivity implements View.OnClickListener {
    TextView myText;
    MyBD dbHelper;
    Button btnCreat,generation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_creation);



        myText=(TextView)findViewById(R.id.textView8);

        btnCreat = (Button) findViewById(R.id.button4);
        btnCreat.setOnClickListener( this);

        generation = (Button) findViewById(R.id.mygeneraton);
        generation.setOnClickListener( this);
        // создаем объект для создания и управления версиями БД
        dbHelper = new   MyBD(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_creation, menu);
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
    public void onClick(View view)
    {

        // получаем данные
       // String zadanie = "Galina";
        String zadanie = "Задание:Написать программу,реализующую сложение двух целых чисел";
        String stOfCode ="Console.WriteLine(\"a = \");\n" +
                "        int a = Convert.ToInt32(Console.ReadLine());\n" +
                "        Console.WriteLine(\"b = \");\n" +
                "        int b = Convert.ToInt32(Console.ReadLine());\n" +
                "        int c = a + b\n" +
                "        Console.WriLine(\"c = \",c);\n" +
                "        Console.WriteLine(\"Please, press Enter...\");\n" +
                "        Console.ReadLine();";
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

       switch(view.getId())
        {
            case R.id.button4:
            {



                ContentValues cv = new ContentValues();
                int clearCount = db.delete("mytable", null, null);
                Toast toast = Toast.makeText(getApplicationContext(),
                        "cleared "+clearCount, Toast.LENGTH_SHORT);
                toast.show();
                cv.put("name", zadanie);
                cv.put("email", stOfCode);
                long rowID1 = db.insert("mytable", null, cv);
                Cursor c = db.query("mytable", null, null, null, null, null, null);

                myText.setText("");
                if (c.moveToFirst()) {
                    // определяем номера столбцов по имени в выборке
                    int idColIndex = c.getColumnIndex("id");
                    int nameColIndex = c.getColumnIndex("name");
                    int emailColIndex = c.getColumnIndex("email");
                    do {
                        myText.append("ID = " + c.getInt(idColIndex) +
                                ", name = " + c.getString(nameColIndex) +
                                ", email = " + c.getString(emailColIndex));
                    } while (c.moveToNext());
                }else myText.setText("Nothing found((");

                c.close();

                break;
            }
            case R.id.mygeneraton:
            {

                TextView userEditText= (TextView) findViewById(R.id.textView8);
                TextView giftEditText = (TextView) findViewById(R.id.textView81);
                Cursor c = db.query("mytable", null, null, null, null, null, null);
                c.moveToFirst();

                Variables.task=c.getString(c.getColumnIndex("name"));
                Variables.programm=new ArrayList<>();
                Variables.addProgramm(c.getString(c.getColumnIndex("email")));

                Intent intent = new Intent(MyCreationActivity.this, PerfomanceActivity.class);

                // в ключ username пихаем текст из первого текстового поля
                //intent.putExtra("username", userEditText.getText().toString());
                // в ключ gift пихаем текст из второго текстового поля
                //intent.putExtra("gift", giftEditText.getText().toString());

                startActivity(intent);
                break;
            }
        }
        dbHelper.close();
    }
}
