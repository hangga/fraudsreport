package id.web.hangga.frauds.view.reportlist;

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

public class ReportListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Report> reportList = new ArrayList<>();
    private Sumary sumary;
    private OnPrepareToDelete onPrepareToDelete;

    void setOnPrepareToDelete(OnPrepareToDelete onPrepareToDelete) {
        this.onPrepareToDelete = onPrepareToDelete;
    }

    ReportListAdapter() {
    }

    void setSumary(Sumary sumary) {
        this.sumary = sumary;
        notifyDataSetChanged();
    }

    void addReport(Report report){
        this.reportList.add(1, report); // insert on index 1 a.k.a after summary
        notifyDataSetChanged();
    }

    void setReportList(List<Report> reportList) {
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
            ((ReportViewHolder) holder).bind(reportList.get(position), new OnPrepareToDelete() {
                @Override
                public void onPrepareToDelete(Report report) {
                    onPrepareToDelete.onPrepareToDelete(report);
                    reportList.remove(report);
                    sumary.setTotalKasus(reportList.size());
                    if (report.isNo_telp()) sumary.setTotalTelp(sumary.getTotalTelp() - 1);
                    if (report.isNo_rek()) sumary.setTotalRek(sumary.getTotalRek() - 1);
                    notifyDataSetChanged();
                }

                @Override
                public void onPrepareToDelete(Frauds frauds) {

                }
            });
        } else if (holder instanceof ReportSummaryHoder) {
            ((ReportSummaryHoder) holder).bind(sumary.getTotalRek(), sumary.getTotalTelp(), sumary.getTotalKasus());
        }
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

}
