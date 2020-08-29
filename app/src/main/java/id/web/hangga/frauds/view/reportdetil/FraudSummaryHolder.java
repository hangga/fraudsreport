package id.web.hangga.frauds.view.reportdetil;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.hangga.frauds.R;

public class FraudSummaryHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.txtTotalKasus)
    TextView txtTotalKasus;
    @BindView(R.id.txtTotalRugi)
    TextView txtTotalRugi;
    @BindView(R.id.txtNewRugi)
    TextView txtNewRugi;
    DecimalFormat df = new DecimalFormat("#.##");

    public FraudSummaryHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void bind(int totalkasus, Double totalRugi, Double newRugi){
        txtTotalKasus.setText(String.valueOf(totalkasus));
        txtTotalRugi.setText("Rp."+df.format(totalRugi));
        txtNewRugi.setText("Rp."+df.format(newRugi));
    }
}
