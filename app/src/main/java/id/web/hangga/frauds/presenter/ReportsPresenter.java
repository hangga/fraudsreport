package id.web.hangga.frauds.presenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import id.web.hangga.frauds.model.Frauds;
import id.web.hangga.frauds.model.Report;
import id.web.hangga.frauds.repository.remote.ApiInterface;
import id.web.hangga.frauds.repository.remote.response.FraudItem;
import id.web.hangga.frauds.repository.remote.response.ReportItem;
import id.web.hangga.frauds.view.BaseView;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ReportsPresenter {

    private View view;
    private ApiInterface apiInterface;
    private List<Report> reports;
    private List<Frauds> frauds;

    public ReportsPresenter(View view, ApiInterface apiInterface){
        this.view = view;
        this.apiInterface = apiInterface;
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

    public void getReportDetil(int id){
        view.onProgress(true);
        apiInterface.getReportDetil(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<FraudItem>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<FraudItem> fraudItems) {
                        if (fraudItems.size() == 0){
                            view.onEmpty();
                        } else {
                            frauds = new ArrayList<>();
                            for (int i = 0; i < fraudItems.size(); i++) {
                                frauds.add(0, fraudItems.get(i).toFraudsParcel());
                            }
                            view.onReportDetil(frauds);
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
                            for (int i = 0; i < reportItems.size(); i++){
                                reports.add(0, reportItems.get(i).toReportparcel());
                            }
                            view.onGetAllDataReport(reports);
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
        void onGetAllDataReport(List<Report> reports);
        void onReportDetil(List<Frauds> frauds);
    }
}
