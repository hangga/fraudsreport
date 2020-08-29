package id.web.hangga.frauds.presenter;

import java.util.ArrayList;
import java.util.List;

import id.web.hangga.frauds.model.Frauds;
import id.web.hangga.frauds.model.Report;
import id.web.hangga.frauds.model.Sumary;
import id.web.hangga.frauds.repository.remote.ApiInterface;
import id.web.hangga.frauds.repository.remote.RetrofitClient;
import id.web.hangga.frauds.repository.remote.response.FraudItem;
import id.web.hangga.frauds.view.BaseView;
import io.reactivex.Observer;
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
                        if (fraudItems.size() == 0){
                            view.onEmpty();
                        } else {
                            frauds = new ArrayList<>();
                            Frauds summaryItem = new Frauds();
                            summaryItem.setType(Frauds.TYPE_SUMMARY);
                            frauds.add(0, summaryItem);
                            Double totalRugi = 0.0;
                            Double newRugi = 0.0;
                            for (int i = 0; i < fraudItems.size(); i++) {
                                Frauds fraud = fraudItems.get(i).toFraudsParcel();
                                frauds.add(1, fraud);
                                totalRugi = totalRugi + fraud.getJumlah_kerugian();
                                newRugi = fraud.getJumlah_kerugian();
                            }
                            sumary = new Sumary();
                            sumary.setTotalKasus(fraudItems.size());
                            sumary.setTotalRugi(totalRugi);
                            sumary.setNewRugi(newRugi);
                            view.onReportDetil(frauds, sumary);
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
        void onReportDetil(List<Frauds> frauds, Sumary sumary);
    }
}
