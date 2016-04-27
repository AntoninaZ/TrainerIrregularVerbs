package verbs.irregular.trainer.ua.trainerirregularverbs;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String SELECT_SQL = "SELECT * FROM irregular_verbs";
    DatabaseHelper myDbHelper ;
    DatabaseHelper sqlHelper;
    private WordDataBase db;
    TestAdapter mDbHelper;
    private LoadDB_AsyncTask task;

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

        tvInfinitiveWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task = new LoadDB_AsyncTask(MainActivity.this);
                task.execute();
            }
        });




        //TestAdapter mDbHelper = new TestAdapter(this);
        //mDbHelper.createDatabase();
        //mDbHelper.open();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
       // c.close();
        //db.close();
    }


}


class LoadDB_AsyncTask extends AsyncTask<Void, Void, Boolean> {
    DatabaseHelper dataBase;
    Context mContext;
    boolean loadedDB = false;
    private ProgressDialog progressDialog;
    private static final String LOG_TAG = "log : ";

    public LoadDB_AsyncTask(Context context) {
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.i(LOG_TAG, "onPreExecute in loadDB");
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Data loading ...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        dataBase = new DatabaseHelper(mContext);
        boolean dbExist = dataBase.checkDataBase();

        if (dbExist) {
            loadedDB = true;
        } else {
            publishProgress(null);
        }

        try {
            dataBase.createDataBase();
        } catch (IOException e) {
            throw new Error("Error on create DataBase");
        }

        dataBase.close();
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (!loadedDB) {
            progressDialog.dismiss();
            Log.i(LOG_TAG, "Loaded DataBase");
            Toast.makeText(mContext, "Data Loaded", Toast.LENGTH_SHORT).show();
        } else {
            Log.i(LOG_TAG, "The database was already loaded");
        }
        try {
            finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}