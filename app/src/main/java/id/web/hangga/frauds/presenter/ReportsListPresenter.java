package id.web.hangga.frauds.presenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import id.web.hangga.frauds.data.remote.ApiInterface;
import id.web.hangga.frauds.data.remote.RetrofitClient;
import id.web.hangga.frauds.data.remote.response.ReportItem;
import id.web.hangga.frauds.model.Report;
import id.web.hangga.frauds.model.Sumary;
import id.web.hangga.frauds.util.Prop;
import id.web.hangga.frauds.view.BaseViewInterface;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Kelas presenter yang menangani logic flow dan request data report
 */
public class ReportsListPresenter {

    private ViewInterface view;
    private ApiInterface apiInterface;
    private List<Report> reports;
    private Sumary sumary;

    public ReportsListPresenter(ViewInterface view) {
        this.view = view;
        this.apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
    }

    /**
     * Untuk mengupdate data report
     * @param report balikan berupa object report
     */
    public void updateReport(Report report) {
        view.onProgress(true);
        apiInterface.updateReport(report.getId(), report.getNumber(), report.isNo_telp(), report.isNo_rek())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ReportItem>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ReportItem reportItem) {
                        if (reportItem != null) {
                            view.onReportResult(reportItem.toReportparcel(), false);
                        }
                        view.onProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onError(e.getMessage());
                        view.onProgress(false);
                    }
                });
    }

    /**
     * Untuk mengreate/menambah report baru
     * @param report balikan berupa object report
     */
    public void createReport(Report report) {
        view.onProgress(true);
        apiInterface.newReport(report.getNumber(), report.isNo_telp(), report.isNo_rek())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ReportItem>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ReportItem reportItem) {
                        if (reportItem != null) {
                            view.onReportResult(reportItem.toReportparcel(), true);
                        }
                        view.onProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onError(e.getMessage());
                        view.onProgress(false);
                    }
                });
    }

    /**
     * Untuk menghapus report
     * @param report balikan berupa object report
     */
    public void deleteReport(Report report) {
        view.onProgress(true);
        apiInterface.deleteReport(report.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ReportItem>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ReportItem reportItem) {
                        view.onReportDeleted(reportItem.toReportparcel());
                        view.onProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onError(e.getMessage());
                        view.onProgress(false);
                    }
                });
    }


    /**
     * Untuk mendapatkan semua data report
     * Balikan berupa List<Report>
     */
    public void getAllData() {
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
                        if (reportItems.size() == 0) {
                            view.onEmpty();
                        } else {
                            reports = new ArrayList<>();
                            Report summaryItem = new Report();
                            summaryItem.setType(Prop.TYPE_SUMMARY);
                            reports.add(0, summaryItem);

                            int totalRek = 0;
                            int totalTelp = 0;
                            sumary = new Sumary();
                            for (int i = 0; i < reportItems.size(); i++) {
                                Report report = reportItems.get(i).toReportparcel();
                                if (report.isNo_rek()) totalRek++;
                                if (report.isNo_telp()) totalTelp++;
                                reports.add(1, report);
                            }
                            sumary.setTotalRek(totalRek);
                            sumary.setTotalTelp(totalTelp);
                            sumary.setTotalKasus(reportItems.size());
                            view.onGetAllDataReport(reports, sumary);
                        }
                        view.onProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onError(e.getMessage());
                        view.onProgress(false);
                    }

                    @Override
                    public void onComplete() {
                        view.onProgress(false);
                    }
                });
    }

    /**
     * Interface ini akan diimplement oleh kelas berupa view/activity/fragment
     * yang menginject ReportsListPresenter.java
     */
    public interface ViewInterface extends BaseViewInterface {
        /**
         * Interface method yang diimplement ketika presenter berhasil memcreate/update report
         * @param report balikan berupa object report
         * @param isNew balikan berupa nilai boolean true(jika berupa data baru/insert) atau false(bukan data baru/update)
         */
        void onReportResult(Report report, boolean isNew);

        /**
         * Interface method yang diimplement ketika presenter berhasil menghapus report
         * @param report balikan berupa object report
         */
        void onReportDeleted(Report report);

        /**
         * Interface method yang diimplement ketika presenter berhasil mendapatkan data report
         * @param reports balikan berupa List report
         * @param sumary balikan berupa summary
         */
        void onGetAllDataReport(List<Report> reports, Sumary sumary);
    }
}
