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

    private static final int ITEM_PERPAGE = 10;

    private List<Report> reportList = new ArrayList<>();
    private Sumary sumary;
    private OnPrepareToDelete onPrepareToDelete;


    private List<Report> reportListPaged;

    /**
     * Menggenerate item per halaman
     * Pleade, dont do this on real project
     * @param page
     */
    private int selectedPage = 1;
    private void generatePage(int page){
        selectedPage = page;
        reportListPaged = new ArrayList<>();

        int start = ((page - 1) * ITEM_PERPAGE) + 1;
        int end = start + ITEM_PERPAGE;

        // add Summary
        reportListPaged.add(reportList.get(0));

        // Add item report paged
        for (int i = start; i < end; i++){
            try{
                reportListPaged.add(reportList.get(i));
            }catch (Exception e){}
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
        int pageCount = reportList.size() / ITEM_PERPAGE;
        return reportList.size() % ITEM_PERPAGE > 0? pageCount + 1 : pageCount;
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
        this.reportListPaged.add(1, report); // insert on index 1 a.k.a after summary
        this.reportList.add(1, report); // insert on index 1 a.k.a after summary

        Sumary newSummary = new Sumary();
        newSummary.setTotalKasus(sumary.getTotalKasus() + 1);
        newSummary.setTotalTelp(report.isNo_telp()?sumary.getTotalTelp() + 1:sumary.getTotalTelp());
        newSummary.setTotalRek(report.isNo_rek()? sumary.getTotalRek() + 1:sumary.getTotalRek());
        setSumary(newSummary);
        notifyDataSetChanged();

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
        } else if (holder instanceof ReportSummaryHoder) {
            ((ReportSummaryHoder) holder).bind(sumary.getTotalRek(), sumary.getTotalTelp(), sumary.getTotalKasus());
        } else if (holder instanceof PageViewHolder){
            ((PageViewHolder)holder).bind(getPageCount(), page -> {
                generatePage(page);
                notifyDataSetChanged();
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
