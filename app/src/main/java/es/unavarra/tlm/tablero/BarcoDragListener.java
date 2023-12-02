package es.unavarra.tlm.tablero;


import android.app.Activity;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.AdapterView;



import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.entity.StringEntity;

public class BarcoDragListener implements View.OnDragListener {
    private GridView gridView;
    private Activity activity;
    private  int game_id;


    private final String TAG="my";

    public BarcoDragListener(GridView gridView, Activity activity, int gameId) {
        this.gridView = gridView;
        this.activity = activity;
        this.game_id = gameId;
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
                String shipType = tags[0];
                String secondTag = tags[1];


                ViewGroup owner = (ViewGroup) view.getParent();


                int[] locationInWindow = new int[2];
                gridView.getLocationInWindow(locationInWindow);
                int xInWindow = locationInWindow[0];
                int yInWindow = locationInWindow[1];

                float x = dragevent.getX();
                float y = dragevent.getY();


                // 获取 GridView 在布局中的 margin
                int[] location = new int[2];
                gridView.getLocationOnScreen(location);

                int left = location[0]; // 左边界
                int top = location[1]; // 上边界

                int adjustedX = Math.round(x) - xInWindow;
                int adjustedY = Math.round(y) - yInWindow;

//                long position = gridView.pointToRowId((int) x, (int) y);
                int position = gridView.pointToPosition(adjustedX, adjustedY);

                Log.d("GridView", "onDrag: posicion " + position);
//                int position = gridView.getPositionForView(gridView.findViewById((int) itemId));

                if (position != AdapterView.INVALID_POSITION) {
                    Log.d("GridView", "Clicked item at adapter position: " + position);
                }

                int firstVisiblePosition = gridView.getFirstVisiblePosition();
                int lastVisiblePosition = gridView.getLastVisiblePosition();
                Log.d("GridView", "onDrag: firstVisiblePosition " + firstVisiblePosition + " lastVisiblePosition " + lastVisiblePosition);


//                float cellWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50f, this.activity.getResources().getDisplayMetrics());

                float cellWidth = gridView.getColumnWidth();
                Log.d(TAG, "onDrag: xwindow " + xInWindow + " ywindow " + yInWindow + " cellwidth " + cellWidth);
                Log.d(TAG, "onDrag: touchx " + dragevent.getX() + " touchY " + dragevent.getY());
                Log.d(TAG, "onDrag: colum " + ((x - xInWindow) / cellWidth));
                Log.d(TAG, "onDrag:  row " + ((y - yInWindow) / cellWidth));
                int column = Math.round(((x - xInWindow) / cellWidth));
                int row = Math.round(((y - yInWindow) / cellWidth));



                Log.d("GridView", "onDrag: colum " + ((x - xInWindow) / cellWidth) + " int " + column);
                Log.d("GridView", "onDrag:  row " + ((y - yInWindow) / cellWidth) + " int " + row);
                char tmp = (char)(row + 'A');
                Log.d(TAG, "onDrag: row" + tmp);

                int posicion = row * 10 + column;

                API_Response_Game.Position[] positions = getBarco(posicion, Integer. parseInt(secondTag));

                Gson gson1 = new Gson();

                Log.d(TAG, "onDrag: positions as JSON: " + gson1.toJson(positions));
                Log.d(TAG, "onDrag: request as Json" + gson1.toJson(new API_Request_Game(shipType, positions)));


                Gson gson = new Gson();
                AsyncHttpClient client = new AsyncHttpClient();
                String token = "5c61f4dfec3803ee49c0fa46120d70b7f9fbb6d3eb4b721e2316bf2323435a49";
                client.addHeader("X-Authentication", token);
                try {
                    client.post(this.activity,
                            "https://api.battleship.tatai.es/v2/game/79/ship",
                            new StringEntity(gson.toJson(new API_Request_Game(shipType, positions))),
                            "application/json",
                            new API_ResponseHandle_Game(this.activity, gson));
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
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


    private API_Response_Game.Position[] getBarco(int position, int numCeldaBarco){
        Log.d(TAG, "getBarco: posicion " + position);

        API_Response_Game.Position[] positions = new API_Response_Game.Position[numCeldaBarco];

        // Confirme el primer punto de barco y complete el [] de barco
        int [] barco = new int[numCeldaBarco];
        int pos = position - numCeldaBarco/2;
        for (int i = 0; i < numCeldaBarco; i++){

            char row = (char) (pos / 10 + 'A');
            int column = pos % 10 + 1;

            Log.d(TAG, "getBarco: row " + row);
            Log.d(TAG, "getBarco: column" + column);

            API_Response_Game.Position posArray = new API_Response_Game.Position(row, column);
            positions[i] = posArray;
            pos++;
        }
        return positions;
    }
}
