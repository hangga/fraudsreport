package id.web.hangga.frauds.presenter;

import java.util.List;

import id.web.hangga.frauds.model.Frauds;
import id.web.hangga.frauds.model.Report;
import id.web.hangga.frauds.repository.remote.ApiInterface;
import id.web.hangga.frauds.repository.remote.RetrofitClient;
import id.web.hangga.frauds.repository.remote.response.FraudItem;
import id.web.hangga.frauds.view.BaseView;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class FraudsPresenter {
    private View view;
    private ApiInterface apiInterface;

    public FraudsPresenter(View view){
        this.view = view;
        this.apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
    }

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

    public void updateFraud(Frauds frauds){
        view.onProgress(true);
        apiInterface.updateFrauds(frauds.getReportId(),frauds.getId(), frauds.getJenis_penipuan(), String.valueOf(frauds.getJumlah_kerugian()),
                frauds.getKota_korban())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fraudItemSingleObserver);
    }

    public void createFraud(Frauds frauds){
        view.onProgress(true);
        apiInterface.newFrauds(frauds.getReportId(), frauds.getJenis_penipuan(), String.valueOf(frauds.getJumlah_kerugian()),
                frauds.getKota_korban())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fraudItemSingleObserver);
    }

    public void deleteFraud(Frauds frauds){
        view.onProgress(true);
        apiInterface.deleteFrauds(frauds.getReportId(), frauds.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<Void>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<Void> voidResponse) {
                        view.onFraudDeleted(true);
                        view.onProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onError(e.getMessage());
                    }
                });

    }

    public interface View extends BaseView {
        void onFraudResult(Frauds frauds);
        void onFraudDeleted(boolean isSuccess);
    }
}
