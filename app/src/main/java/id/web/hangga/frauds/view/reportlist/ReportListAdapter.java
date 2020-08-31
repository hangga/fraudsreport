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
import id.web.hangga.frauds.view.OnPrepareToDelete;
import id.web.hangga.frauds.view.PaginationViewHolder;
import id.web.hangga.frauds.view.SummaryHolder;

public class ReportListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int itemsPerPage;
    private List<Report> reportListPaged;
    private List<Report> reportList = new ArrayList<>();
    private Sumary sumary;
    private OnPrepareToDelete onPrepareToDelete;

    /**
     * Menggenerate item per halaman
     * Please, dont do this on real project
     * @param page
     */
    private void generatePage(int page){
        reportListPaged = new ArrayList<>();

        int start = ((page - 1) * itemsPerPage) + 1;
        int end = start + itemsPerPage;

        // add Summary
        reportListPaged.add(reportList.get(0));

        // Add item report paged
        for (int i = start; i < end; i++){
            try{
                reportListPaged.add(reportList.get(i));
            }catch (Exception ignored){}
        }

        // Add item view Pagination
        Report pagination = new Report();
        pagination.setType(Prop.TYPE_PAGINATION);
        reportListPaged.add(pagination);
    }

    /**
     * Banyaknya halaman
     * @return
     */
    int getPageCount(){
        int pageCount = reportList.size() / itemsPerPage;
        return reportList.size() % itemsPerPage > 0? pageCount + 1 : pageCount;
    }

    void setOnPrepareToDelete(OnPrepareToDelete onPrepareToDelete) {
        this.onPrepareToDelete = onPrepareToDelete;
    }

    ReportListAdapter(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    void setSumary(Sumary sumary) {
        this.sumary = sumary;
        notifyDataSetChanged();
    }

    void addReport(Report report){
        this.reportListPaged.add(1, report); // insert on index 1 a.k.a after summary
        this.reportList.add(1, report); // insert on index 1 a.k.a after summary

        Sumary newSummary = new Sumary();
        newSummary.setTotalKasus(sumary.getTotalKasus() + 1);
        newSummary.setTotalTelp(report.isNo_telp()?sumary.getTotalTelp() + 1:sumary.getTotalTelp());
        newSummary.setTotalRek(report.isNo_rek()? sumary.getTotalRek() + 1:sumary.getTotalRek());
        setSumary(newSummary);
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
        if (viewType == Prop.TYPE_SUMMARY) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_summary, parent, false);
            return new SummaryHolder(itemView);
        } else if (viewType == Prop.TYPE_PAGINATION){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_page, parent, false);
            return new PaginationViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_report, parent, false);
            return new ReportViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ReportViewHolder) {
            ((ReportViewHolder) holder).bind(reportListPaged.get(position), new OnPrepareToDelete() {
                @Override
                public void onPrepareToDelete(Report report) {
                    onPrepareToDelete.onPrepareToDelete(report);
                    reportList.remove(report);
                    reportListPaged.remove(report);
                    Sumary newSummary = new Sumary();
                    newSummary.setTotalKasus(sumary.getTotalKasus() - 1);
                    newSummary.setTotalTelp(report.isNo_telp()?sumary.getTotalTelp() - 1:sumary.getTotalTelp());
                    newSummary.setTotalRek(report.isNo_rek()? sumary.getTotalRek() - 1:sumary.getTotalRek());
                    setSumary(newSummary);
                    notifyDataSetChanged();
                }

                @Override
                public void onPrepareToDelete(Frauds frauds) {

                }
            });
        } else if (holder instanceof SummaryHolder) {
            ((SummaryHolder) holder).bind(sumary, SummaryHolder.SUMMARY_REPORTS);
        } else if (holder instanceof PaginationViewHolder){
            ((PaginationViewHolder)holder).bind(getPageCount(), page -> {
                generatePage(page);
                notifyDataSetChanged();
            });
        }
    }

    @Override
    public int getItemCount() {
        return reportListPaged.size();
    }

    public interface OnItemPageListener{
        void onPageListener(int page);
    }

}
