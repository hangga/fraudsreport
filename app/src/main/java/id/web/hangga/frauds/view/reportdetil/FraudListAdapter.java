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
import id.web.hangga.frauds.view.reportlist.ReportViewHolder;

public class FraudListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Sumary sumary;
    private Report report;

    public void setReport(Report report) {
        this.report = report;
        notifyDataSetChanged();
    }

    private List<Frauds> fraudsList = new ArrayList<>();

    public FraudListAdapter(){}

    public void setSumary(Sumary sumary) {
        this.sumary = sumary;
        notifyDataSetChanged();
    }

    public void setFraudsList(List<Frauds> fraudsList) {
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
            return new FraudViewHolder(itemView);
        } else if (viewType == Prop.TYPE_SUMMARY) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_summary_fraud,
                    parent, false);
            return new FraudSummaryHolder(itemView);
        } else
            return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ReportViewHolder) {
            ((ReportViewHolder) holder).bind(report);
        } else if (holder instanceof FraudViewHolder) {
            ((FraudViewHolder) holder).bind(fraudsList.get(position));
        } else if (holder instanceof FraudSummaryHolder) {
            ((FraudSummaryHolder) holder).bind(sumary.getTotalKasus(),
                    sumary.getTotalRugi(), sumary.getNewRugi());
        }
    }

    @Override
    public int getItemCount() {
        return fraudsList.size();
    }
}
