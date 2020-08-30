package id.web.hangga.frauds.presenter;

import id.web.hangga.frauds.model.Frauds;
import id.web.hangga.frauds.data.remote.ApiInterface;
import id.web.hangga.frauds.data.remote.RetrofitClient;
import id.web.hangga.frauds.data.remote.response.FraudItem;
import id.web.hangga.frauds.view.BaseView;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FraudsPresenter {
    private View view;
    SingleObserver<FraudItem> fraudItemSingleObserver = new SingleObserver<FraudItem>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onSuccess(FraudItem fraudItem) {
            view.onFraudResult(fraudItem.toFraudsParcel());
            view.onProgress(false);
        }

        @Override
        public void onError(Throwable e) {
            view.onError(e.getMessage());
        }
    };
    private ApiInterface apiInterface;

    public FraudsPresenter(View view) {
        this.view = view;
        this.apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
    }

    public void updateFraud(Frauds frauds) {
        view.onProgress(true);
        apiInterface.updateFrauds(frauds.getReportId(), frauds.getId(), frauds.getJenis_penipuan(), String.valueOf(frauds.getJumlah_kerugian()),
                frauds.getKota_korban())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fraudItemSingleObserver);
    }

    public void createFraud(Frauds frauds) {
        view.onProgress(true);
        apiInterface.newFrauds(frauds.getReportId(), frauds.getJenis_penipuan(), String.valueOf(frauds.getJumlah_kerugian()),
                frauds.getKota_korban())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fraudItemSingleObserver);
    }

    public void deleteFraud(Frauds frauds) {
        view.onProgress(true);
        apiInterface.deleteFrauds(frauds.getReportId(), frauds.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<FraudItem>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(FraudItem fraudItem) {
                        view.onFraudDeleted(fraudItem.toFraudsParcel());
                        view.onProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onError(e.getMessage());
                        view.onProgress(false);
                    }
                });

    }

    public interface View extends BaseView {
        void onFraudResult(Frauds frauds);
        void onFraudDeleted(Frauds frauds);
    }
}
