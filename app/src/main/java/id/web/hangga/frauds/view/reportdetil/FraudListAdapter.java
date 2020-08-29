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
import id.web.hangga.frauds.model.Sumary;

public class FraudListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_SUMMARY = 0;
    private static final int TYPE_ITEM_FRAUD = 1;
    private static final int TYPE_PAGI = 2;
    private Sumary sumary;
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
        if (viewType == TYPE_ITEM_FRAUD) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_fraud,
                    parent, false);
            return new FraudViewHolder(itemView);
        } else if (viewType == TYPE_SUMMARY) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_summary_fraud,
                    parent, false);
            return new FraudSummaryHolder(itemView);
        } else
            return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FraudViewHolder) {
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
