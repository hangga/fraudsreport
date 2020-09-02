package id.web.hangga.frauds.view.reportlist;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.hangga.frauds.R;
import id.web.hangga.frauds.model.Report;
import id.web.hangga.frauds.util.Prop;
import id.web.hangga.frauds.view.OnPrepareToDelete;
import id.web.hangga.frauds.view.postreport.PostReportActivity;
import id.web.hangga.frauds.view.reportdetil.ReportDetilActivity;

public class ReportViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.imgIcon)
    ImageView imgIcon;
    @BindView(R.id.txtJenis)
    TextView txtNUmber;
    @BindView(R.id.imgMore)
    ImageView imgMore;
    private View view;

    public ReportViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.view = itemView;
    }

    public void bind(Report report, OnPrepareToDelete onPrepareToDelete) {
        txtNUmber.setText(report.getNumber());
        if (report.isNo_rek()) {
            imgIcon.setBackgroundResource(R.drawable.ic_card_18dp);
        } else {
            imgIcon.setBackgroundResource(R.drawable.ic_phone_18dp);
        }
        view.setOnClickListener(view -> {
            if (view.getContext() instanceof ReportDetilActivity) return;
            Intent intent = new Intent(view.getContext(), ReportDetilActivity.class);
            intent.putExtra("report", report);
            view.getContext().startActivity(intent);
        });
        imgMore.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), imgMore);
            popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu_item, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.menuDelete) {
                    new AlertDialog.Builder(view.getContext())
                            .setTitle("Konfirmasi")
                            .setMessage("Hapus " + report.getNumber() + " ?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> onPrepareToDelete.onPrepareToDelete(report))
                            .setNegativeButton(android.R.string.no, null).show();
                } else if (menuItem.getItemId() == R.id.menuEdit) {
                    Intent intent = new Intent(view.getContext(), PostReportActivity.class);
                    intent.putExtra(Prop.PARAM_POST_TYPE, Prop.POST_UPDATE_REPORT);
                    intent.putExtra("report", report);
                    ((Activity) view.getContext()).startActivityForResult(intent, Prop.POST_UPDATE_REPORT);
                }
                return false;
            });
            popupMenu.show();
        });

        /**
         * Untuk animasi highlight color ketika insert data baru
         */
        if (report.isNew()) {
            int highlightColor = ContextCompat.getColor(itemView.getContext(), R.color.new_highlight);

            ObjectAnimator highlightNewInsert = ObjectAnimator
                    .ofInt(itemView, "backgroundColor", highlightColor, Color.WHITE)
                    .setDuration(5000);
            highlightNewInsert.setEvaluator(new ArgbEvaluator());
            highlightNewInsert.start();
            report.setNew(false);
        }
    }
}
