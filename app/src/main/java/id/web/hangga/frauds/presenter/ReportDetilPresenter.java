package id.web.hangga.frauds.presenter;

import java.util.ArrayList;
import java.util.List;

import id.web.hangga.frauds.model.Frauds;
import id.web.hangga.frauds.model.Report;
import id.web.hangga.frauds.repository.remote.ApiInterface;
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

    public ReportDetilPresenter(View view){
        this.view = view;
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

    public interface View extends BaseView{
        void onReportDetil(List<Frauds> frauds);
    }
}
