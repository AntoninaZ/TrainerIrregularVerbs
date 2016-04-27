package verbs.irregular.trainer.ua.trainerirregularverbs;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by antonina on 26.04.2016.
 */
public class WordDataBase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "extenalDB.db";
    private static final int DATABASE_VERSION = 1;

    public WordDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public Cursor getEmployees() {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {"id", "infinitive", "p_simple"};
        String sqlTables = "irregularVerbs";

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);

        c.moveToFirst();
        return c;

    }

}
