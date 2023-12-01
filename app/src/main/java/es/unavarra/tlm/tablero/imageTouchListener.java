package es.unavarra.tlm.tablero;

import android.content.ClipData;
import android.view.View;

public class imageTouchListener implements View.OnTouchListener {
    @Override
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
}
