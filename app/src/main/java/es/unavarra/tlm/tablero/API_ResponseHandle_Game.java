package es.unavarra.tlm.tablero;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class API_ResponseHandle_Game extends AsyncHttpResponseHandler {
    private final Activity activity;
    private final Gson gson;
    private final String TAG = "my";

    public API_ResponseHandle_Game(Activity activity, Gson gson) {
        this.activity = activity;
        this.gson = gson;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


        API_Response_Game apiResponseGame;
        try {
            apiResponseGame = this.gson.fromJson(
                    new String(responseBody, DEFAULT_CHARSET),
                    API_Response_Game.class
            );
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        Log.d(TAG, "onSuccess: " + gson.toJson(apiResponseGame));

        // Sacar todos los datos de los ships
        List<API_Response_Game.ship> ships = apiResponseGame.getShips();

        for (API_Response_Game.ship element : ships) {
            String type = element.getType();
            API_Response_Game.Position[] posiciones = element.getPosition();

            for (API_Response_Game.Position pos : posiciones){
                char r = pos.getRow();
                int row = ((int) r) - ((int)'A');
                int column = pos.getColumn();
                int item = row * 10 + column - 1;
                Log.d(TAG, "onSuccess: item " + item + " row " + row + " column " + column);

                GridView gridView = activity.findViewById(R.id.gridView_you);
                String s = (String) gridView.getItemAtPosition(item);
                TextView tv = (TextView) gridView.getChildAt(item);
                tv.setBackgroundColor(Color.RED);
            }
            Log.d(TAG, "onSuccess: ship" + element.getType());
        }

    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Log.d(TAG, "onFailure: statuscode " + statusCode);
        Log.d(TAG, "onFailure: " + error);
        API_Response400_Game apiResponse400Game;
        try {
            apiResponse400Game = this.gson.fromJson(
                    new String(responseBody, DEFAULT_CHARSET),
                    API_Response400_Game.class
            );
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        String fallo;
        if (apiResponse400Game != null && apiResponse400Game.getCode() != null){
            fallo = apiResponse400Game.getCode();
        } else {
            fallo = "no tiene contenido";
        }
        Log.d(TAG, "onFailure: fallo " +fallo);

        // Create the toast and set its position
        Toast toast = Toast.makeText(this.activity, fallo, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    public static int charToInt(char c) {
        int asciiValue = (int) c;
        if ('A' <= c && c <= 'Z') {
            return asciiValue - (int) 'A';
        } else {
            // 如果不是大写字母，你可能需要处理其他情况
            // 这里简单地返回一个错误值，你可以根据需要进行修改
            return -1;
        }
    }
}





