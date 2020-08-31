package id.web.hangga.frauds.view;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.hangga.frauds.R;
import id.web.hangga.frauds.model.Sumary;

public class SummaryHolder extends RecyclerView.ViewHolder {

    public static final int SUMMARY_REPORTS = 1;
    public static final int SUMMARY_FRAUDS = 2;

    @BindView(R.id.txtSummaryOne)
    TextView txtSummaryOne;
    @BindView(R.id.txtSummaryTwo)
    TextView txtSummaryTwo;
    @BindView(R.id.txtSummaryThree)
    TextView txtSummaryThree;

    @BindView(R.id.txtSubOne)
    TextView txtSubOne;
    @BindView(R.id.txtSubTwo)
    TextView txtSubTwo;
    @BindView(R.id.txtSubThree)
    TextView txtSubThree;

    DecimalFormat df = new DecimalFormat("#.##");

    public SummaryHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Sumary sumary, int sumTYpe){
        if (sumTYpe == SUMMARY_FRAUDS){
            txtSummaryOne.setText(String.valueOf(sumary.getTotalKasus()));
            txtSummaryTwo.setText("Rp."+df.format(sumary.getTotalRugi()));
            txtSummaryThree.setText("Rp."+df.format(sumary.getNewRugi()));
            txtSubOne.setText(R.string.summary_kasus);
            txtSubTwo.setText(R.string.summary_rugi);
            txtSubThree.setText(R.string.summary_new_rugi);
        } else {
            txtSummaryOne.setText(String.valueOf(sumary.getTotalKasus()));
            txtSummaryTwo.setText(String.valueOf(sumary.getTotalRek()));
            txtSummaryThree.setText(String.valueOf(sumary.getTotalTelp()));
            txtSubOne.setText(R.string.summary_kasus);
            txtSubTwo.setText(R.string.summary_norek);
            txtSubThree.setText(R.string.summary_notelp);
        }
    }
}
