package id.web.hangga.frauds.view.reportdetil;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.hangga.frauds.R;
import id.web.hangga.frauds.model.Frauds;
import id.web.hangga.frauds.util.Utils;

public class FraudViewHolder extends RecyclerView.ViewHolder {

    View view;
    @BindView(R.id.txtDate)
    TextView txtDate;
    @BindView(R.id.txtJenis)
    TextView txtJenis;
    @BindView(R.id.txtKerugian)
    TextView txtKerugian;
    @BindView(R.id.txtKOta)
    TextView txtKOta;

    public FraudViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.view = itemView;
    }

    void bind(Frauds frauds){
        txtDate.setText(Utils.reformatDate(frauds.getCreatedAt()));
        txtJenis.setText(frauds.getJenis_penipuan());
        txtKerugian.setText(String.valueOf(frauds.getJumlah_kerugian()));
        txtKOta.setText(frauds.getKota_korban());
    }
}
