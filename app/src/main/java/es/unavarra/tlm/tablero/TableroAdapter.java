package es.unavarra.tlm.tablero;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TableroAdapter extends BaseAdapter{
    private final Context context;
    private int[] barco;
    private final int boardSize = 10;

    public TableroAdapter(Context context) {
        this.context = context;
    }
    public TableroAdapter(Context context, int[] barco) {

        this.context = context;
        this.barco = barco;
    }

    @Override
    public int getCount() {
        return boardSize * boardSize;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridViewItem = convertView;

        if (gridViewItem == null) {
            LayoutInflater inflater = LayoutInflater.from(this.context);
            gridViewItem = inflater.inflate(R.layout.item_grid, null);
        }

        TextView textView = gridViewItem.findViewById(R.id.cuadrado);

//        char row = (char) (position / 10 + 'A');
//        int column = position % 10 + 1;
//
//        textView.setText(row + " - " + column);

        textView.setText(String.valueOf(position));
        return textView;
    }

}
