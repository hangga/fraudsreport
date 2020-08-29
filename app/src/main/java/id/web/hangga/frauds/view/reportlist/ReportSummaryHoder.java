package id.web.hangga.frauds.view.reportlist;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.hangga.frauds.R;

public class ReportSummaryHoder extends RecyclerView.ViewHolder{
    @BindView(R.id.txtTotalKasus)
    TextView txtTotalKasus;
    @BindView(R.id.txtTotalTelp)
    TextView txtTotalTelp;
    @BindView(R.id.txtTotalBank)
    TextView txtTotalBank;

    ReportSummaryHoder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
    void bind(int totalRek, int totalTelp, int totalKasus){
        txtTotalKasus.setText(String.valueOf(totalKasus));
        txtTotalBank.setText(String.valueOf(totalRek));
        txtTotalTelp.setText(String.valueOf(totalTelp));
    }
}
