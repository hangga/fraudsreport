package id.web.hangga.frauds.view.reportlist;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
    @BindView(R.id.imgMore)
    ImageView imgMore;
    View view;

    public ReportViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.view = itemView;
    }

    public void bind(Report report, OnPrepareToDelete onPrepareToDelete){
        txtNUmber.setText(report.getNumber());
        if (report.isNo_rek()){
            imgIcon.setBackgroundResource(R.mipmap.ic_bank_bg);
        } else {
            imgIcon.setBackgroundResource(R.mipmap.ic_phone_bg);
        }
        view.setOnClickListener(view -> {
            if (view.getContext() instanceof ReportDetilActivity) return;
            Intent intent = new Intent(view.getContext(), ReportDetilActivity.class);
            intent.putExtra("report", report);
            view.getContext().startActivity(intent);
        });
        imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), imgMore);
                popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu_item, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.menuDelete){
                            new AlertDialog.Builder(view.getContext())
                                    .setTitle("Konfirmasi")
                                    .setMessage("Hapus "+report.getNumber()+" ?")
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            onPrepareToDelete.onPrepareToDelete(report);
                                        }})
                                    .setNegativeButton(android.R.string.no, null).show();
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }
}
