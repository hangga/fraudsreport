package id.web.hangga.frauds.view.reportdetil;

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
import id.web.hangga.frauds.model.Frauds;
import id.web.hangga.frauds.model.Report;
import id.web.hangga.frauds.util.Prop;
import id.web.hangga.frauds.util.Utils;
import id.web.hangga.frauds.view.postreport.PostReportActivity;
import id.web.hangga.frauds.view.OnPrepareToDelete;

class FraudViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txtDate)
    TextView txtDate;
    @BindView(R.id.txtJenis)
    TextView txtJenis;
    @BindView(R.id.txtKerugian)
    TextView txtKerugian;
    @BindView(R.id.txtKOta)
    TextView txtKOta;
    @BindView(R.id.imgMore)
    ImageView imgMore;
    private Report report;

    FraudViewHolder(@NonNull View itemView, Report report) {
        super(itemView);
        this.report = report;
        ButterKnife.bind(this, itemView);
    }

    void bind(Frauds frauds, OnPrepareToDelete onPrepareToDelete) {
        txtDate.setText(Utils.reformatDate(frauds.getCreatedAt()));
        txtJenis.setText(frauds.getJenis_penipuan());
        txtKerugian.setText("Rp." + String.valueOf(frauds.getJumlah_kerugian()));
        txtKOta.setText(frauds.getKota_korban());
        imgMore.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), imgMore);
            popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu_item, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.menuDelete) {
                    new AlertDialog.Builder(view.getContext())
                            .setTitle("Konfirmasi")
                            .setMessage("Hapus " + frauds.getJenis_penipuan() + " ?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes,
                                    (dialog, whichButton) -> onPrepareToDelete.onPrepareToDelete(frauds))
                            .setNegativeButton(android.R.string.no, null).show();
                } else if (menuItem.getItemId() == R.id.menuEdit) {
                    Intent intent = new Intent(view.getContext(), PostReportActivity.class);
                    intent.putExtra(Prop.PARAM_POST_TYPE, Prop.POST_UPDATE_FRAUD);
                    intent.putExtra("report", report);
                    intent.putExtra("frauds", frauds);
                    ((Activity) view.getContext()).startActivityForResult(intent, Prop.POST_UPDATE_FRAUD);
                }
                return false;
            });
            popupMenu.show();
        });
        if (frauds.isNew()){
            int highlightColor = ContextCompat.getColor(itemView.getContext(), R.color.colorAccent);

            ObjectAnimator highlightNewInsert = ObjectAnimator
                    .ofInt(itemView, "backgroundColor", highlightColor, Color.WHITE)
                    .setDuration(5000);
            highlightNewInsert.setEvaluator(new ArgbEvaluator());
            highlightNewInsert.start();
            frauds.setNew(false);
        }
    }
}
