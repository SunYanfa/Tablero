package es.unavarra.tlm.tablero;

import android.app.Activity;
import android.content.Intent;
import android.view.View;


public class OpenActivityTablecero implements View.OnClickListener {

    private Activity activity;

    public OpenActivityTablecero(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v){

        Intent intent = new Intent(this.activity, Tablero_Activity.class);
        intent.putExtra("game_id", 1);

        this.activity.startActivity(intent);
        this.activity.finish();
//        Gson gson = new Gson();
//        AsyncHttpClient client = new AsyncHttpClient();
//
//        try {
//            client.put(this.activity,
//                    "https://api.battleship.tatai.es/v2/user",
//                    new StringEntity(gson.toJson(new API_Request_game(1234))),
//                    "application/json",
//                    new API_ResponseHandle_game(this.activity, gson));
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        }
    }
}
