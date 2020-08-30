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

    int itemPerPage = 10;

    private List<Report> reportList = new ArrayList<>();
    private Sumary sumary;
    private OnPrepareToDelete onPrepareToDelete;


    private List<Report> reportListPaged;

    /**
     * Menggenerate item per halaman
     * Pleade, dont do this on real project
     * @param page
     */
    public void generatePage(int page){
        reportListPaged = new ArrayList<>();

        int start = ((page - 1) * itemPerPage) + 1;
        int end = start + itemPerPage;

        reportListPaged.add(reportList.get(0)); // add Summary
        for (int i = start; i < end; i++){
            try{
                reportListPaged.add(reportList.get(i)); // Add item report
            }catch (Exception e){}
        }
        Report pagination = new Report();
        pagination.setType(Prop.TYPE_PAGINATION);
        reportListPaged.add(pagination); // Add pagination
    }

    /**
     * Banyaknya halaman
     * @return
     */
    int getPageCount(){
        int pageCount = reportList.size() / itemPerPage;
        return reportList.size() % itemPerPage > 0? pageCount + 1 : pageCount;
    }

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
        generatePage(1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        //return reportList.get(position).getType();
        return reportListPaged.get(position).getType();
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
        } else if (viewType == Prop.TYPE_PAGINATION){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_page, parent, false);
            return new PageViewHolder(itemView);
        } else
            return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ReportViewHolder) {
            //((ReportViewHolder) holder).bind(reportList.get(position), new OnPrepareToDelete() {
            ((ReportViewHolder) holder).bind(reportListPaged.get(position), new OnPrepareToDelete() {
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
        } else if (holder instanceof PageViewHolder){
            ((PageViewHolder)holder).bind(getPageCount(), new OnItemPageListener() {
                @Override
                public void onPageListener(int page) {
                    generatePage(page);
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        //return reportList.size();
        return reportListPaged.size();
    }

    public interface OnItemPageListener{
        void onPageListener(int page);
    }

}
