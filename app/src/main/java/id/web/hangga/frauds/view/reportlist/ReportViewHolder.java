package id.web.hangga.frauds.view.reportlist;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.hangga.frauds.R;
import id.web.hangga.frauds.model.Report;
import id.web.hangga.frauds.util.Utils;

public class ReportViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.imgIcon)
    ImageView imgIcon;
    @BindView(R.id.txtNUmber)
    TextView txtNUmber;
    View view;

    ReportViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.view = itemView;
    }

    void bind(Report report){
        txtNUmber.setText(report.getNumber());
        if (report.isNo_rek()){
            imgIcon.setBackgroundResource(R.drawable.ic_bank_24dp);
        } else {
            imgIcon.setBackgroundResource(R.drawable.ic_contact_phone_24dp);
        }
    }
}
