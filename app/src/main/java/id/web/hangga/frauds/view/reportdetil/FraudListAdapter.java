package id.web.hangga.frauds.view.reportdetil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.web.hangga.frauds.R;
import id.web.hangga.frauds.model.Frauds;
import id.web.hangga.frauds.model.Report;
import id.web.hangga.frauds.model.Sumary;
import id.web.hangga.frauds.util.Prop;
import id.web.hangga.frauds.view.SummaryHolder;
import id.web.hangga.frauds.view.OnPrepareToDelete;
import id.web.hangga.frauds.view.reportlist.ReportViewHolder;

public class FraudListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Sumary sumary;
    private Report report;
    private OnPrepareToDelete onPrepareToDelete;
    private List<Frauds> fraudsList = new ArrayList<>();

    FraudListAdapter() {
    }

    void setOnPrepareToDelete(OnPrepareToDelete onPrepareToDelete) {
        this.onPrepareToDelete = onPrepareToDelete;
    }

    void update(Frauds frauds){
        frauds.setNew(true);
        boolean isUpdated = false;
        for (int i = 0; i < fraudsList.size(); i++){
            if (fraudsList.get(i).getId() == frauds.getId()){
                fraudsList.set(i, frauds);
                isUpdated = true;
                break;
            }
        }
        if (!isUpdated) addFrauds(frauds);
        notifyDataSetChanged();
    }

    void addFrauds(Frauds frauds){
        this.fraudsList.add(2, frauds);

        Sumary newSummary = new Sumary();
        newSummary.setTotalKasus(sumary.getTotalKasus() + 1);
        newSummary.setTotalRugi(sumary.getTotalRugi() + frauds.getJumlah_kerugian());
        newSummary.setNewRugi(frauds.getJumlah_kerugian());
        setSumary(newSummary);

        notifyDataSetChanged();
    }

    public void setReport(Report report) {
        this.report = report;
        notifyDataSetChanged();
    }

    void setSumary(Sumary sumary) {
        this.sumary = sumary;
        notifyDataSetChanged();
    }

    void setFraudsList(List<Frauds> fraudsList) {
        this.fraudsList = fraudsList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return fraudsList.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Prop.TYPE_ITEM_REPORT) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_report, parent, false);
            return new ReportViewHolder(itemView);
        } else if (viewType == Prop.TYPE_ITEM_FRAUD) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_fraud,
                    parent, false);
            return new FraudViewHolder(itemView, report);
        } else if (viewType == Prop.TYPE_SUMMARY) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_summary,
                    parent, false);
            return new SummaryHolder(itemView);
        } else
            return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ReportViewHolder) {
            ((ReportViewHolder) holder).bind(report, null);
        } else if (holder instanceof FraudViewHolder) {
            ((FraudViewHolder) holder).bind(fraudsList.get(position), new OnPrepareToDelete() {
                @Override
                public void onPrepareToDelete(Report report) {
                    onPrepareToDelete.onPrepareToDelete(report);
                }

                @Override
                public void onPrepareToDelete(Frauds frauds) {
                    if (frauds != null) {
                        onPrepareToDelete.onPrepareToDelete(frauds);
                        fraudsList.remove(frauds);
                        notifyDataSetChanged();
                    }
                }
            });
        } else if (holder instanceof SummaryHolder) {
            ((SummaryHolder) holder).bind(sumary, SummaryHolder.SUMMARY_FRAUDS);
        }
    }

    @Override
    public int getItemCount() {
        return fraudsList.size();
    }
}
