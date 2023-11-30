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
        textView.setText(String.valueOf(position));

        if (barco != null){
            if (isPositionInBarco(position)) {
                gridViewItem.setBackgroundColor(Color.YELLOW);
            }
        }

//        // 设置奇偶行、列的背景颜色
//        int row = position / boardSize;
//        int col = position % boardSize;
//
//        if ((row + col) % 2 == 0) {
//            imageView.setBackgroundColor(Color.WHITE);
//        } else {
//            imageView.setBackgroundColor(Color.WHITE);
//        }

        return textView;
    }


    // 判断当前位置是否在 barcoPositions 数组中
    private boolean isPositionInBarco(int position) {
        for (int barcoPosition : barco) {
            if (position == barcoPosition) {
                return true;
            }
        }
        return false;
    }
}
