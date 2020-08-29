package id.web.hangga.frauds.view.reportlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.web.hangga.frauds.R;
import id.web.hangga.frauds.model.Report;
import id.web.hangga.frauds.model.Sumary;

public class ReportListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_SUMMARY = 0;
    private static final int TYPE_ITEM_REPORT = 1;
    private static final int TYPE_PAGI = 2;
    private List<Report> reportList = new ArrayList<>();
    private Context context;
    private Sumary sumary;

    public ReportListAdapter(Context context) {
        this.context = context;
    }

    public void setSumary(Sumary sumary) {
        this.sumary = sumary;
        notifyDataSetChanged();
    }

    public void setReportList(List<Report> reportList) {
        this.reportList = reportList;
        notifyDataSetChanged();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return reportList.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM_REPORT) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_report, parent, false);
            return new ReportViewHolder(itemView);
        } else if (viewType == TYPE_SUMMARY) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_summary, parent, false);
            return new SummaryViewHoder(itemView);
        } else
            return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ReportViewHolder) {
            ((ReportViewHolder) holder).bind(reportList.get(position));
        } else if (holder instanceof SummaryViewHoder) {
            ((SummaryViewHoder) holder).bind(sumary.getTotalRek(), sumary.getTotalTelp(), sumary.getTotalRugi(), sumary.getNewRugi());
        }
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

}
