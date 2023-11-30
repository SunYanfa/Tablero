package es.unavarra.tlm.tablero;

import android.view.DragEvent;
import android.view.View;
import android.widget.GridView;

public class gridDragListener implements View.OnDragListener {
    private final GridView gridView;

    public gridDragListener(GridView gridView) {
        this.gridView = gridView;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        switch (action) {
            case DragEvent.ACTION_DROP:
                // Handle the drop event here
                // Retrieve the dragged item using event.getLocalState()
                View draggedView = (View) event.getLocalState();
                // Retrieve the position where the item is dropped
                int dropPosition = gridView.pointToPosition((int) event.getX(), (int) event.getY());
                // Perform any necessary actions with the dropped item and position
                // ...

                // Make the dragged view visible again
                draggedView.setVisibility(View.VISIBLE);
                break;
            // Handle other drag events if needed
            // ...

        }
        return true;
    }
}
