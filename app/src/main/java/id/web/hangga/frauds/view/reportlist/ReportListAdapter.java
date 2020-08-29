package id.web.hangga.frauds.view.reportlist;

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
import id.web.hangga.frauds.util.Prop;

public class ReportListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Report> reportList = new ArrayList<>();
    private Sumary sumary;

    public ReportListAdapter() {
    }

    public void setSumary(Sumary sumary) {
        this.sumary = sumary;
        notifyDataSetChanged();
    }

    public void setReportList(List<Report> reportList) {
        this.reportList = reportList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return reportList.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Prop.TYPE_ITEM_REPORT) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_report, parent, false);
            return new ReportViewHolder(itemView);
        } else if (viewType == Prop.TYPE_SUMMARY) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_summary_report, parent, false);
            return new ReportSummaryHoder(itemView);
        } else
            return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ReportViewHolder) {
            ((ReportViewHolder) holder).bind(reportList.get(position));
        } else if (holder instanceof ReportSummaryHoder) {
            ((ReportSummaryHoder) holder).bind(sumary.getTotalRek(), sumary.getTotalTelp(), sumary.getTotalKasus());
        }
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

}
