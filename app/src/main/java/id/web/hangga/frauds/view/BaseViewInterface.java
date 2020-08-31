package id.web.hangga.frauds.view;

/**
 * Kelas interface yang berisi method umum yang akan diimplement di setiap view/activity
 * Di trigger oleh Presenter
 */
public interface BaseViewInterface {
    void onProgress(boolean isShow);
    void onError(String errMessage);
    void onEmpty();
    void showMessage(String message);
}
