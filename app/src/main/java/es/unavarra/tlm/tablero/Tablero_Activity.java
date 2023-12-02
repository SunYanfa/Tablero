package es.unavarra.tlm.tablero;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;

public class Tablero_Activity extends AppCompatActivity {

    private Context context;
    private int gameId = 79;

    private final String TAG = "my";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablero);

        GridView gridView = findViewById(R.id.gridView_you);
        TableroAdapter adapter = new TableroAdapter(this);
        gridView.setAdapter(adapter);



        findViewById(R.id.cruiser).setOnTouchListener(new BarcoTouchListener());
        findViewById(R.id.battleship).setOnTouchListener(new BarcoTouchListener());
        findViewById(R.id.carrier).setOnTouchListener(new BarcoTouchListener());
        findViewById(R.id.submarine1).setOnTouchListener(new BarcoTouchListener());
        findViewById(R.id.submarine2).setOnTouchListener(new BarcoTouchListener());
        findViewById(R.id.destroyer1).setOnTouchListener(new BarcoTouchListener());
        findViewById(R.id.destroyer2).setOnTouchListener(new BarcoTouchListener());



        findViewById(R.id.linear).setOnDragListener(new BarcoDragListener(gridView, this, gameId));


        Button button = (Button) findViewById(R.id.empezar);
//        button.setOnClickListener(new OpenActivityPartida(Tablero_activity.this));
    }

}