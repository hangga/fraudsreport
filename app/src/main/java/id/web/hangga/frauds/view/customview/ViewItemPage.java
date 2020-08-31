package id.web.hangga.frauds.view.customview;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Objects;

import id.web.hangga.frauds.R;

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
