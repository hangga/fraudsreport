package id.web.hangga.frauds.view.reportdetil;

import android.app.Activity;
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

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.hangga.frauds.R;
import id.web.hangga.frauds.model.Frauds;
import id.web.hangga.frauds.util.Prop;
import id.web.hangga.frauds.util.Utils;
import id.web.hangga.frauds.view.postreport.PostReportActivity;
import id.web.hangga.frauds.view.reportlist.OnPrepareToDelete;

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
    @BindView(R.id.imgMore)
    ImageView imgMore;

    public FraudViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.view = itemView;
    }

    void bind(Frauds frauds, OnPrepareToDelete onPrepareToDelete){
        txtDate.setText(Utils.reformatDate(frauds.getCreatedAt()));
        txtJenis.setText(frauds.getJenis_penipuan());
        txtKerugian.setText(String.valueOf(frauds.getJumlah_kerugian()));
        txtKOta.setText(frauds.getKota_korban());
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
                                    .setMessage("Hapus "+frauds.getJenis_penipuan()+" ?")
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            onPrepareToDelete.onPrepareToDelete(frauds);
                                        }})
                                    .setNegativeButton(android.R.string.no, null).show();
                        } else if (menuItem.getItemId() == R.id.menuEdit){
                            Intent intent = new Intent(view.getContext(), PostReportActivity.class);
                            intent.putExtra(Prop.PARAM_POST_TYPE, Prop.POST_TYPE_UPDATE_FRAUD);
                            intent.putExtra("frauds", frauds);
                            ((Activity)view.getContext()).startActivityForResult(intent, Prop.POST_TYPE_UPDATE_FRAUD);
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }
}
