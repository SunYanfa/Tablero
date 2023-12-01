package es.unavarra.tlm.tablero;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import java.util.Arrays;

public class Tablero_Activity extends AppCompatActivity {

    private Context context;
    private GridView gridView;
    private int gameId = 79;

    private final String TAG = "my";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablero);

        gridView = findViewById(R.id.gridView_you);
        TableroAdapter adapter = new TableroAdapter(this);
        gridView.setAdapter(adapter);
//        gridView.setOnItemLongClickListener(this);

        // ---------------------------
        // Problema desde Aqui

//        gridView.setOnDragListener(this);
//        gridView.setOnDragListener(new gridDragListener(gridView));


//        gridView.setOnItemClickListener(this);


        findViewById(R.id.cruiser).setOnTouchListener(new imageTouchListener());
        findViewById(R.id.battleship).setOnTouchListener(new imageTouchListener());
        findViewById(R.id.carrier).setOnTouchListener(new imageTouchListener());
        findViewById(R.id.submarine1).setOnTouchListener(new imageTouchListener());
        findViewById(R.id.submarine2).setOnTouchListener(new imageTouchListener());
        findViewById(R.id.destroyer1).setOnTouchListener(new imageTouchListener());
        findViewById(R.id.destroyer2).setOnTouchListener(new imageTouchListener());

        findViewById(R.id.linear).setOnDragListener(new imageDragListener(gridView, this, gameId));


        Button button = (Button) findViewById(R.id.empezar);
//        button.setOnClickListener(new OpenActivityPartida(Tablero_activity.this));
    }






    private void colocarBarco(int[] barco , int color) {
        Log.d(TAG, "colocarBarco: " +Arrays.toString(barco));
        for (int pos : barco) {
            gridView.getChildAt(pos).setBackgroundColor(color);
        }
    }


}