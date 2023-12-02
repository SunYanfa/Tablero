package es.unavarra.tlm.tablero;

import android.graphics.Point;
import android.graphics.Canvas;
import android.view.View;
import android.view.View.DragShadowBuilder;

public class RotatedDragShadowBuilder extends DragShadowBuilder {
    private int shipNum;
    private static final int ROTATION_DEGREES = 90;

    public RotatedDragShadowBuilder(View view, int shipNum) {
        super(view);

        this.shipNum = shipNum;
    }

    @Override
    public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {
        // Get the original shadow metrics
        super.onProvideShadowMetrics(outShadowSize, outShadowTouchPoint);
        View v = getView();

        outShadowSize.set(v.getWidth(), v.getHeight());

        int centerX;
        int centerY;

        // Determina si el barco tiene una longitud par o impar
        if (shipNum % 2 == 1) {
            // Calcula el punto medio de la sombra arrastrada
            centerX = outShadowSize.x / 2;
            centerY = outShadowSize.y / 2;
        } else {
            centerX = (outShadowSize.x / 2) - (outShadowSize.x / shipNum / 2);
            centerY = outShadowSize.y / 2;
        }

        // Configurar el punto de toque de la sombra
        outShadowTouchPoint.set(centerX, centerY);
    }

    public void onDrawShadow(Canvas canvas) {
        View view = getView();
        double rotationRad = Math.toRadians(view.getRotation());
        final int w = (int) (view.getWidth() * view.getScaleX());
        final int h = (int) (view.getHeight() * view.getScaleY());
        double s = Math.abs(Math.sin(rotationRad));
        double c = Math.abs(Math.cos(rotationRad));
        final int width = (int) (w * c + h * s);
        final int height = (int) (w * s + h * c);
        canvas.scale(view.getScaleX(), view.getScaleY(), width / 2, height / 2);
        canvas.rotate(view.getRotation(), width / 2, height / 2);
        canvas.translate((width - view.getWidth()) / 2, (height - view.getHeight()) / 2);
        super.onDrawShadow(canvas);
    }


}
