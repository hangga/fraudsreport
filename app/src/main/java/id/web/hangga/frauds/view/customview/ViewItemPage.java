package id.web.hangga.frauds.view.customview;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Objects;

import id.web.hangga.frauds.R;

/**
 * Kelas untuk mencetak object item halaman 1,2,3 dst
 */
public class ViewItemPage extends RelativeLayout {

    private TextView txtPage;

    public ViewItemPage(Context context) {
        super(context);
        inflate(context);
    }

    public TextView getTxtPage() {
        return txtPage;
    }

    void inflate(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Objects.requireNonNull(layoutInflater).inflate(R.layout.layout_item_page, this);
        txtPage = findViewById(R.id.txtPage);
    }
}
