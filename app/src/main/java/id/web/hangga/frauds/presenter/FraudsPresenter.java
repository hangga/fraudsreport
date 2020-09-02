package id.web.hangga.frauds.presenter;

import id.web.hangga.frauds.model.Frauds;
import id.web.hangga.frauds.data.remote.ApiInterface;
import id.web.hangga.frauds.data.remote.RetrofitClient;
import id.web.hangga.frauds.data.remote.response.FraudItem;
import id.web.hangga.frauds.view.BaseViewInterface;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * FraudsPresenter.java adalah kelas presenter yang menangani main logic flow
 * yang berkaitan dengan data fraud
 */
public class FraudsPresenter {
    private ViewInterface view;
    private SingleObserver<FraudItem> fraudItemSingleObserver = new SingleObserver<FraudItem>() {
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
            view.onProgress(false);
        }
    };
    private ApiInterface apiInterface;

    /**
     * Method konstruktor FraudsPresenter
     * @param view interface yang akan diimplement
     */
    public FraudsPresenter(ViewInterface view) {
        this.view = view;
        this.apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
    }

    /**
     * Request Edit data fraud ke API
     * @param frauds Object frauds
     */
    public void updateFraud(Frauds frauds) {
        //view.onProgress(true);
        apiInterface.updateFrauds(frauds.getReportId(), frauds.getId(), frauds.getJenis_penipuan(), String.valueOf(frauds.getJumlah_kerugian()),
                frauds.getKota_korban())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fraudItemSingleObserver);
    }

    /**
     * Untuk membuat/create/insert frauds baru
     * @param frauds object frauds
     */
    public void createFraud(Frauds frauds) {
        view.onProgress(true);
        apiInterface.newFrauds(frauds.getReportId(), frauds.getJenis_penipuan(), String.valueOf(frauds.getJumlah_kerugian()),
                frauds.getKota_korban())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fraudItemSingleObserver);
    }

    /**
     * Untuk menghapus data frauds
     * @param frauds object frauds
     */
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

    /**
     * Interface ini akan diimplement oleh kelas berupa view/activity/fragment
     * yang menginject FraudsPresenter.java
     */
    public interface ViewInterface extends BaseViewInterface {
        /**
         * Interface method yang diimplement ketika presenter berhasil mengcreate/edit frauds
         * @param frauds balikan berupa object frauds
         */
        void onFraudResult(Frauds frauds);

        /**
         * Interface method yang diimplement ketika presenter berhasil menghapus frauds
         * @param frauds balikan berupa object frauds
         */
        void onFraudDeleted(Frauds frauds);
    }
}
