package verbs.irregular.trainer.ua.trainerirregularverbs;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String SELECT_SQL = "SELECT * FROM irregular_verbs";

    DatabaseHelper sqlHelper;
    private WordDataBase db;

    private Cursor c;

    private TextView tvTranslateWord;
    private TextView tvInfinitiveWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvTranslateWord = (TextView) findViewById(R.id.translate_word);
        tvInfinitiveWord = (TextView) findViewById(R.id.infinitive_word);
        DatabaseHelper myDbHelper ;
        myDbHelper = new DatabaseHelper(this);

        try {

            myDbHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {
            File file = new File(myDbHelper.DB_PATH);
            if (file.exists() && !file.isDirectory())
            myDbHelper.openDataBase();

        }catch(SQLException sqle){

            throw sqle;

        }
        //db = new WordDataBase(this);
        //c = db.getEmployees();
        //String st = " u";
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void openDatabase() {
      //  db = openOrCreateDatabase("worddb.db", Context.MODE_PRIVATE, null);
       // Log.d("db", db.getPath());
    }

    protected void showRecords() {
        String id = c.getString(0);
        String translate = c.getString(4);
        String infinitive = c.getString(1);
        tvTranslateWord.setText(translate);
        tvInfinitiveWord.setText(infinitive);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        c.close();
        db.close();
    }


}
