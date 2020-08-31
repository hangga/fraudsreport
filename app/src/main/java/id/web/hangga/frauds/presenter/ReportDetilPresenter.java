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
import id.web.hangga.frauds.view.BaseView;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ReportDetilPresenter {
    private View view;
    private ApiInterface apiInterface;
    private List<Frauds> frauds;

    private Sumary sumary;

    public ReportDetilPresenter(View view){
        this.view = view;
        this.apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
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

    public interface View extends BaseView{
        void onReportResult(Report report);
        void onReportDeleted(Report report);
        void onReportDetil(List<Frauds> frauds, Sumary sumary);
    }
}
