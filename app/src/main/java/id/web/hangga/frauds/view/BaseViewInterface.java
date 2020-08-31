package id.web.hangga.frauds.view;

public interface BaseViewInterface {
    void onProgress(boolean isShow);
    void onError(String errMessage);
    void onEmpty();
    void showMessage(String message);
}
