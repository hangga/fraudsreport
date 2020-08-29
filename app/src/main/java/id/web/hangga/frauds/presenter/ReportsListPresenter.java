package id.web.hangga.frauds.presenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import id.web.hangga.frauds.model.Frauds;
import id.web.hangga.frauds.model.Report;
import id.web.hangga.frauds.model.Sumary;
import id.web.hangga.frauds.repository.remote.ApiInterface;
import id.web.hangga.frauds.repository.remote.RetrofitClient;
import id.web.hangga.frauds.repository.remote.response.FraudItem;
import id.web.hangga.frauds.repository.remote.response.ReportItem;
import id.web.hangga.frauds.view.BaseView;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ReportsListPresenter {

    private View view;
    private ApiInterface apiInterface;
    private List<Report> reports;
    private List<Frauds> frauds;
    private Sumary sumary;

    public ReportsListPresenter(View view){
        this.view = view;
        this.apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
    }

    SingleObserver<ReportItem> reportItemSingleObserver = new SingleObserver<ReportItem>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onSuccess(ReportItem reportItem) {
            if (reportItem != null){
                view.onReportResult(reportItem.toReportparcel());
            }
            view.onProgress(false);
        }

        @Override
        public void onError(Throwable e) {
            view.onError(e.getMessage());
        }
    };

    public void updateReport(Report report){
        view.onProgress(true);
        apiInterface.updateReport(report.getId(), report.getNumber(), report.isNo_telp(), report.isNo_rek())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reportItemSingleObserver);
    }

    public void createReport(Report report){
        view.onProgress(true);
        apiInterface.newReport(report.getNumber(), report.isNo_telp(), report.isNo_rek())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reportItemSingleObserver);
    }

    public void deleteReport(Report report){
        view.onProgress(true);
        apiInterface.deleteReport(report.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<Void>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<Void> voidResponse) {
                        view.onReportDeleted(true);
                        view.onProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onError(e.getMessage());
                    }
                });
    }


    public void getAllData(){
        view.onProgress(true);
        apiInterface.getAllReports()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ReportItem>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ReportItem> reportItems) {
                        if (reportItems.size() == 0){
                            view.onEmpty();
                        } else {
                            reports = new ArrayList<>();
                            Report summaryItem = new Report();
                            summaryItem.setType(Report.TYPE_SUMMARY);
                            reports.add(0, summaryItem);

                            int totalRek = 0;
                            int totalTelp = 0;
                            sumary = new Sumary();
                            for (int i = 0; i < reportItems.size(); i++){
                                Report report = reportItems.get(i).toReportparcel();
                                if (report.isNo_rek()) totalRek++;
                                if (report.isNo_telp()) totalTelp++;
                                reports.add(report);
                            }
                            sumary.setTotalRek(totalRek);
                            sumary.setTotalTelp(totalTelp);
                            sumary.setTotalKasus(reportItems.size());
                            view.onGetAllDataReport(reports, sumary);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        view.onProgress(false);
                    }
                });
    }

    public interface View extends BaseView{
        void onReportResult(Report report);
        void onReportDeleted(boolean isSuccess);
        void onGetAllDataReport(List<Report> reports, Sumary sumary);
    }
}
