package es.unavarra.tlm.tablero;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

public class Tablero_Activity extends AppCompatActivity implements View.OnTouchListener, View.OnDragListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {

    private Context context;
    private GridView gridView;
    private int numShips = 0;
    private final int[][] ships = new int[10][];

    private final String TAG = "my";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablero);

        gridView = findViewById(R.id.gridView_you);
        TableroAdapter adapter = new TableroAdapter(this);
        gridView.setAdapter(adapter);
        gridView.setOnItemLongClickListener(this);

        // ---------------------------
        // Problema desde Aqui

//        gridView.setOnDragListener(this);
//        gridView.setOnDragListener(new gridDragListener(gridView));


//        gridView.setOnItemClickListener(this);


        findViewById(R.id.cruiser).setOnTouchListener(this);
        findViewById(R.id.battleship).setOnTouchListener(this);
        findViewById(R.id.carrier).setOnTouchListener(this);
        findViewById(R.id.submarine1).setOnTouchListener(this);
        findViewById(R.id.submarine2).setOnTouchListener(this);
        findViewById(R.id.destroyer1).setOnTouchListener(this);
        findViewById(R.id.destroyer2).setOnTouchListener(this);

        findViewById(R.id.linear).setOnDragListener(this);


        Button button = (Button) findViewById(R.id.empezar);
//        button.setOnClickListener(new OpenActivityPartida(Tablero_activity.this));
    }

    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouch(View v, android.view.MotionEvent event) {
        if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
            // 开始拖放操作
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDragAndDrop(data, shadowBuilder, v, 0);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onDrag(View layoutview, DragEvent dragevent) {

        int action = dragevent.getAction();
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                Log.d(TAG, "Drag event started");
                break;

            case DragEvent.ACTION_DRAG_ENTERED:
                Log.d(TAG, "Drag event entered into " + layoutview.toString());
                break;

            case DragEvent.ACTION_DRAG_EXITED:
                Log.d(TAG, "Drag event exited from " + layoutview.toString());
                break;

            case DragEvent.ACTION_DROP:
                Log.d(TAG, "onDrag: ========================================");
                Log.d(TAG, "Dropped");
                View view = (View) dragevent.getLocalState();
                // sacar los tag del los views
                String tagValue = view.getTag().toString();
                String[] tags = tagValue.split(", ");
                String firstTag = tags[0];
                String secondTag = tags[1];


                ViewGroup owner = (ViewGroup) view.getParent();


                int[] locationInWindow = new int[2];
                gridView.getLocationInWindow(locationInWindow);
                int xInWindow = locationInWindow[0];
                int yInWindow = locationInWindow[1];

                float x = dragevent.getX();
                float y = dragevent.getY();

                float cellWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50f, getResources().getDisplayMetrics());

                Log.d(TAG, "onDrag: xwindow " + xInWindow + " ywindow " + yInWindow + " cellwidth " + cellWidth);
                Log.d(TAG, "onDrag: colum " + ((x - xInWindow) / cellWidth));
                Log.d(TAG, "onDrag:  row " + ((y - yInWindow) / cellWidth));
                int column = (int) ((x - xInWindow) / cellWidth);
                int row = Math.round(((y - yInWindow) / cellWidth));


                if (column >= 0 && column <= 9 && row >= 0 && row <= 9){
                    Log.d(TAG, "onDrag: ok");
                    Log.d(TAG, "onDrag: colum " + column + " row " + row);
                    int position = (row) * 10 + column;
                    int numCeldaBarco = Integer.parseInt(secondTag);

                    // Obtenga los datos de ubicación de Barco a través de la función
                    int[] barco = getBarco(position, numCeldaBarco);

                    // Confirmar que la colacación de barco cumple con las condiciones
                    if (barco != null){
                        ships[numShips] = barco;
                        numShips ++;
                        for (int i = 0; i < ships.length; i++) {
                            Log.d(TAG, "onDrag: ships " + i + Arrays.toString(ships[i]));

                        }
                        colocarBarco(barco, Color.YELLOW);
                        owner.removeView(view);
                    }
                } else {
                    Log.d(TAG, "onDrag: no");
                }

                view.setVisibility(View.VISIBLE);
                break;

            case DragEvent.ACTION_DRAG_ENDED:
                Log.d(TAG, "Drag ended");
                break;

            default:
                break;
        }
        return true;
    }

    private int[] getBarco(int position, int numCeldaBarco){

        // Confirme el primer punto de barco y complete el [] de barco
        int [] barco = new int[numCeldaBarco];
        int pos = position - numCeldaBarco/2;
        for (int i = 0; i < numCeldaBarco; i++){
            barco[i] = pos;
            pos++;
        }
        barco = compruebaBarco(barco, null);

        return barco;
    }

    private int[] compruebaBarco(int[] barco, int[] barcoOriginal){
        for (int i=0; i<10;i++){
            Log.d(TAG, "compruebaBarco: ships" + i + "  " + Arrays.toString(ships[i]));
        }
        Log.d(TAG, "compruebaBarco: barco" + Arrays.toString(barco));
        // Determinar si barco excede el límite del tablero
        if (barco[1] - barco[0] < 10){
            // Barco horizontal
            // Determinar si la primera y la última celda están en la misma línea
            // Determinar si la primera celda está más allá del tablero (-1 o -2)
            int col1 = barco[0] / 10;
            int col2 = barco[barco.length - 1] / 10;
            if (col1 != col2 || barco[0] < 0) {
                Log.d(TAG, "compruebaBarco: 1");
                return null;
            }
        } else {
            // Barco vertical
            if (barco[0] < 0 || barco[barco.length - 1] > 99){
                Log.d(TAG, "compruebaBarco: 2");
                return null;
            }
        }


        // Determinar si hay otros barcos cerca.
        for (int j : barco) {
            for (int[] ship : ships) {
                if (ship != null) {
                    for (int i : ship) {
                        Set<Integer> validDiffs = new HashSet<>(Arrays.asList(0, 1, -1, 10, -10, 9, -9, 11, -11));
                        int diff = i - j;

                        if (validDiffs.contains(diff) && ship != barcoOriginal) {
                            Log.d(TAG, "compruebaBarco: 3");
                            return null;
                        }
                    }
                }
            }
        }

        return barco;
    }

    private void colocarBarco(int[] barco , int color) {
        Log.d(TAG, "colocarBarco: " +Arrays.toString(barco));
        for (int pos : barco) {
            gridView.getChildAt(pos).setBackgroundColor(color);
        }
    }


    // Los listner de gridView
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        // Create a ClipData object
        ClipData data = ClipData.newPlainText("", "");

        // Create a DragShadowBuilder from the View parameter
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

        // Start the drag and drop operation
        view.startDragAndDrop(data, shadowBuilder, view, 0);

        // Set the visibility to invisible to hide the original view during drag
        view.setVisibility(View.INVISIBLE);

        // Trigger long press feedback
        view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);

        return true;
    }


    private Bitmap getBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.d(TAG, "Item ID: " + position);

        for (int i = 0 ; i<ships.length;i++){
            if (ships[i] != null){
                for (int j=0; j<ships[i].length; j++){
                    if (position == ships[i][j]){
                        Log.d(TAG, "onItemLongClick: numship"+ Arrays.toString(ships[i]));


                        int [] newBarco = new int[ships[i].length];
                        for (int k=0; k<ships[i].length; k++){
                            Log.d(TAG, "onItemClick: 123" + (ships[i][1] - ships[i][0]));
                            if (ships[i][1] - ships[i][0] < 10){
                                newBarco[k] = (ships[i][k]-position) * 10 + position;
                            } else {
                                newBarco[k] = (ships[i][k] - position) / 10 + position;
                            }
                        }

                        int[] resultado = compruebaBarco(newBarco, ships[i]);
                        Log.d(TAG, "onItemClick: resultado"+Arrays.toString(resultado));

                        if (resultado != null){
                            colocarBarco(ships[i], Color.WHITE);
                            ships[i] = newBarco;
                            colocarBarco(ships[i], Color.YELLOW);
                        }
                        break;
                    }
                }
            }
        }
    };

}