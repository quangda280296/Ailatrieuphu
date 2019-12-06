package ailatrieuphu.trieuphu2019.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by keban on 4/6/2018.
 */

public class ALTPHelper extends SQLiteAssetHelper {

    private static final int SCHEMA_VERSION = 1;

    private String question = "Question";
    private String answer_one = "AnswerOne";
    private String answer_two = "AnswerTwo";
    private String answer_three = "AnswerThree";
    private String answer_four = "AnswerFour";
    private String correct_answer = "CorrectAnswer";

    private int table;

    public ALTPHelper(Context context, int table) {
        super(context, "trieuphumobile.sqlite", null, SCHEMA_VERSION);
        this.table = table;
    }

    public Cursor getCursor() {
        SQLiteDatabase db = getWritableDatabase();
        String[] columns = {question, answer_one, answer_two, answer_three, answer_four, correct_answer};

        Cursor cursor = db.query("Questions" + table, columns, null, null, null, null, null);
        return cursor;
    }

    public String getQuestion(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex(question));
    }

    public String getAnswerOne(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex(answer_one));
    }

    public String getAnswerTwo(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex(answer_two));
    }

    public String getAnswerThree(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex(answer_three));
    }

    public String getAnswerFour(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex(answer_four));
    }

    public String getCorrectAnswer(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex(correct_answer));
    }
}
