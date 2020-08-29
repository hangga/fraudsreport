package id.web.hangga.frauds.view.reportlist;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.hangga.frauds.R;

public class SummaryViewHoder extends RecyclerView.ViewHolder{
    @BindView(R.id.txtTotalRek)
    TextView txtTotalRek;
    @BindView(R.id.txtTotalTelp)
    TextView txtTotalTelp;
    @BindView(R.id.txtTotalRugi)
    TextView txtTotalRugi;
    @BindView(R.id.txtNewRugi)
    TextView txtNewRugi;
    View view;

    SummaryViewHoder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.view = itemView;
    }
    void bind(int totalRek, int totalTelp, Double totalRugi, Double newRugi){
        txtNewRugi.setText("Kerugian terbaru : "+String.valueOf(newRugi));
        txtTotalRugi.setText("Total kerugian yang dilaporkan: "+String.valueOf(totalRugi));
        txtTotalRek.setText("Total Rekening yang dilaporkan : "+String.valueOf(totalRek));
        txtTotalTelp.setText("Total No.telp yang dilaporkan : "+String.valueOf(totalTelp));
    }
}
