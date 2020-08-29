package id.web.hangga.frauds.view.reportlist;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.hangga.frauds.R;
import id.web.hangga.frauds.model.Report;
import id.web.hangga.frauds.view.reportdetil.ReportDetilActivity;

public class ReportViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.imgIcon)
    ImageView imgIcon;
    @BindView(R.id.txtJenis)
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
        view.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ReportDetilActivity.class);
            intent.putExtra("report", report);
            view.getContext().startActivity(intent);
        });
    }
}
