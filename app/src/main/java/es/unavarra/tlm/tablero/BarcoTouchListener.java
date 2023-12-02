package es.unavarra.tlm.tablero;

import android.content.ClipData;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Objects;


public class BarcoTouchListener implements View.OnTouchListener {

    private static final long DOUBLE_CLICK_TIME_DELTA = 300;
    private long lastClickTime = 0;

    @Override
    public boolean onTouch(View v, android.view.MotionEvent event) {
        if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
            long clickTime = System.currentTimeMillis();

            if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                // doble clic detectado
                Log.d("BarcoTouchListener", "Doble clic detectado");

                handleDoubleClick(v);
            } else {
                Log.d("BarcoTouchListener", "Clic simple detectado");

                // crear un shadow
                ClipData data = ClipData.newPlainText("", "");

                String viewTag = (String) v.getTag();
                int shipNum = Integer.parseInt(viewTag.split(", ")[1]);

                // reescribir onProvideShadowMetrics
                View.DragShadowBuilder shadowBuilder = new RotatedDragShadowBuilder(v, shipNum);
                v.startDragAndDrop(data, shadowBuilder, v, 0);
            }

            lastClickTime = clickTime;

            return true;
        } else {
            return false;
        }
    }


    private void handleDoubleClick(View v) {
        // Lógica para el doble clic
        Log.d("BarcoTouchListener", "Manejando doble clic");


        String tag = (String) v.getTag();
        String[] tags = tag.split(", ");
        Log.d("BarcoTouchListener", "handleDoubleClick: " + tags[0] + " " + tags.length);

        if (tags.length == 3){

            if (Objects.equals(tags[2], "horizontal")){
                setHeight(v, 400);
                v.setTag(tags[0] + ", " + tags[1] + ", vertical");
                v.setRotation(90);
            } else {
                setHeight(v, 150);
                v.setTag(tags[0] + ", " + tags[1] + ", horizontal");
                v.setRotation(0);
            }
        }

        lastClickTime = 0;
    }

    private void setHeight(View v, int height){
        LinearLayout parent = (LinearLayout) v.getParent();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                height
        );
        parent.setLayoutParams(layoutParams);
    }
}

