package com.example.yotis.omdb.logic;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.yotis.omdb.R;
import com.example.yotis.omdb.models.Ratings;
import com.example.yotis.omdb.models.movie;

import java.util.ArrayList;
import java.util.List;


public class DynamicTable {


    private TableLayout table;
    private ArrayList<TableRow> rowns;
    private Activity activity;
    private Resources rs;


    public DynamicTable(Activity activity, TableLayout table) {
        this.activity = activity;
        this.table = table;
        rs = this.activity.getResources();
        rowns = new ArrayList<TableRow>();


    }

    public void addRowName(ArrayList<String> info) {
        if (rowns.size() > 1) {
            rowns.clear();
            table.removeAllViews();
        }


        for (int i = 0; i < info.size(); i++) {

            TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            TableRow rown = new TableRow(activity);
            rown.setLayoutParams(layoutFila);
            final TextView name1 = new TextView(activity);
            name1.setGravity(Gravity.CENTER);
            name1.setText(info.get(i));
            name1.setTextColor(Color.WHITE);
            name1.setPadding(5, 20, 5, 20);
            rown.addView(name1);

            rowns.add(rown);

            table.addView(rown);

        }


    }

    public void addRowImage(final Bitmap elementos) {
        if (rowns.size() > 1) {
            rowns.clear();
            table.removeAllViews();
        }
        TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow rown = new TableRow(activity);
        rown.setLayoutParams(layoutFila);

        final ImageButton img = new ImageButton(activity);


        final int id_ = img.getId();
        img.setImageBitmap(resizeImage(elementos, 300, 300));
        img.setBackgroundColor(Color.TRANSPARENT);
        img.setPadding(5, 20, 5, 20);
        rown.addView(img);


        rowns.add(rown);
        table.addView(rown);


    }

    public Bitmap resizeImage(Bitmap mBitmap, float newWidth, float newHeigth) {

        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeigth) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
    }
}
