package id.web.hangga.frauds.presenter;

import java.util.ArrayList;
import java.util.List;

import id.web.hangga.frauds.model.Frauds;
import id.web.hangga.frauds.model.Report;
import id.web.hangga.frauds.model.Sumary;
import id.web.hangga.frauds.data.remote.ApiInterface;
import id.web.hangga.frauds.data.remote.RetrofitClient;
import id.web.hangga.frauds.data.remote.response.FraudItem;
import id.web.hangga.frauds.data.remote.response.ReportItem;
import id.web.hangga.frauds.util.Prop;
import id.web.hangga.frauds.view.BaseViewInterface;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Kelas ini merupakan presenter yang menangani logic flow detil report
 * Termasuk didalamnya request data report detil dan data fraudlist
 */
public class ReportDetilPresenter {
    private ViewInterface view;
    private ApiInterface apiInterface;
    private List<Frauds> frauds;

    private Sumary sumary;

    public ReportDetilPresenter(ViewInterface view){
        this.view = view;
        this.apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
    }

    /**
     * Untuk mendapatkan data Report Detil
     * @param id report id
     */
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
                        frauds = new ArrayList<>();

                        //add Summary in index 0
                        Frauds summaryItem = new Frauds();
                        summaryItem.setType(Prop.TYPE_SUMMARY);
                        frauds.add(0, summaryItem);
                        Double totalRugi = 0.0;
                        Double newRugi = 0.0;

                        //add Report item in index 1
                        Frauds reportItem = new Frauds();
                        reportItem.setType(Prop.TYPE_ITEM_REPORT);
                        frauds.add(1, reportItem);

                        //add Frauds start in index 2
                        for (int i = 0; i < fraudItems.size(); i++) {
                            Frauds fraud = fraudItems.get(i).toFraudsParcel();
                            frauds.add(fraud);
                            totalRugi = totalRugi + fraud.getJumlah_kerugian();
                            newRugi = fraud.getJumlah_kerugian();
                        }

                        sumary = new Sumary();
                        sumary.setTotalKasus(fraudItems.size());
                        sumary.setTotalRugi(totalRugi);
                        sumary.setNewRugi(newRugi);
                        view.onReportDetil(frauds, sumary);
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
     * Untuk menghapus report
     * @param report parameter berupa object report
     */
    public void deleteReport(Report report){
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
     * Untuk mengupdate report
     * @param report parameter berupa object report
     */
    public void updateReport(Report report){
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
                        if (reportItem != null){
                            view.onReportResult(reportItem.toReportparcel());
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
     * Interface ini akan diimplement oleh kelas berupa view/activity/fragment
     * yang menginject ReportDetilPresenter.java
     */
    public interface ViewInterface extends BaseViewInterface {
        /**
         * Interface method yg dipanggil ketika report berhasil diedit atau di insert
         * @param report balikan berupa object report
         */
        void onReportResult(Report report);

        /**
         * Interface method yg dipanggil ketika report berhasil dihapus
         * @param report balikan berupa object report
         */
        void onReportDeleted(Report report);

        /**
         * Interface method yang diimplement ketika presenter berhasil mendapatkan data report, fraud list
         * dan menghitung summary.
         * @param frauds balika berupa List frauds
         * @param sumary balikan berupa summary
         */
        void onReportDetil(List<Frauds> frauds, Sumary sumary);
    }
}
