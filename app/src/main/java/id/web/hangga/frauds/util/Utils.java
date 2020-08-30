package id.web.hangga.frauds.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.core.content.ContextCompat;

import com.skydoves.balloon.ArrowOrientation;
import com.skydoves.balloon.Balloon;
import com.skydoves.balloon.BalloonAnimation;
import com.skydoves.balloon.OnBalloonClickListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import id.web.hangga.frauds.R;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class Utils {
    public static String[] getmMonthSimple() {
        return mMonthSimple;
    }

    static String[] mMonthSimple = {"Jan", "Feb", "Mar", "Apr", "Mei", "Jun",
            "Jul", "Aug", "Sep", "Okt", "Nop", "Des"};

    static String[] mMonth = {"Januari", "Februari", "Maret", "April", "Mei", "Juni",
            "Juli", "Agustus", "Sepember", "Oktober", "Nopember", "Desember"};

    public static void showBallon(Context context, View view, String message) {
        Balloon balloon = new Balloon.Builder(context)
                .setArrowSize(10)
                .setArrowOrientation(ArrowOrientation.BOTTOM)
                .setArrowVisible(true)
                .setAutoDismissDuration(1000L)
                .setWidthRatio(0.9f)
                .setHeight(65)
                .setTextSize(15f)
                .setArrowPosition(0.62f)
                .setCornerRadius(4f)
                .setAlpha(0.9f)
                .setText(message)
                .setTextColor(ContextCompat.getColor(context, R.color.white))
                .setBackgroundColor(ContextCompat.getColor(context, R.color.red_material))
                .setOnBalloonClickListener(new OnBalloonClickListener() {
                    @Override
                    public void onBalloonClick(@NotNull View view) {
                    }
                })
                .setBalloonAnimation(BalloonAnimation.FADE)
                //.setLifecycleOwner(lifecycleOwner)
                .build();
        balloon.show(view);
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static RequestBody toRequestBody(String str) {
        if (str == null) return null;
        //RequestBody.create();
        return RequestBody.create(str, MediaType.parse("text/plain"));
    }

    public static File createImageFile(Context context) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "TPG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        return mFile;
    }

    public static String getRealPathFromUri(Uri contentUri, Context context) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static String formatRupiah(int price) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format((double) price).replace("Rp","Rp. ");
    }

    public static boolean checkPermission(Context context, String permission) {
        int result = ContextCompat.checkSelfPermission(context, permission);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }


    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
            }
        }
    }



    public static boolean isEmailValid(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return (email.matches(emailPattern) && email.length() > 0);
    }



    public static String[] getmMonth() {
        return mMonth;
    }

    /*
        Reformate date from mysql format to custom sakkarepku format
        2020-07-23 hh:ii:ss
        */

    /**
     * Get Day from Date in MySQL format
     * @param str
     * @return
     */
    public static int getDay(String str){
        return Integer.parseInt(str.substring(8, 10).replace("0",""));
    }

    public static int getMonth(String str){
        return Integer.parseInt(str.substring(5, 7).replace("0", ""));
    }

    public static int getYear(String str){
        return Integer.parseInt(str.substring(0, 4));
    }

    public static String reformateDateSuperSimple(String str){
        try{
            String syear = str.substring(2, 4);
            String smonth = str.substring(5, 7);
            String sday = str.substring(8, 10);
            return sday + "/" + smonth + "/" + syear;
        }catch (Exception e){
            return str;
        }
    }

    public static String reformatDateSimple(String str) {
        try{
            String syear = str.substring(0, 4);
            String smonth = str.substring(5, 7).replace("0", "");
            int idxMonth = Integer.parseInt(smonth);
            String sday = str.substring(8, 10);
            //String stime = str.substring(11, 16);
            return sday + " " + mMonthSimple[idxMonth-1] + " " + syear;
        }catch (Exception e){
            return str;
        }
    }

    public static String reformatDate(String str) {
        try{
            String syear = str.substring(0, 4);
            String smonth = str.substring(5, 7).replace("0", "");
            int idxMonth = Integer.parseInt(smonth);
            String sday = str.substring(8, 10);
            String stime = str.substring(11, 16);
            return sday + " " + mMonth[idxMonth-1] + " " + syear + ", " + stime;
        }catch (Exception e){
            return str;
        }
    }

    public static  String getHttpErrorMessage(Throwable throwable){
        try{
            if (throwable instanceof HttpException){
                ResponseBody body = ((HttpException) throwable).response().errorBody();
                JSONObject jsonObject = new JSONObject(body.string());
                return jsonObject.getString("message");
            } else {
                return throwable.getMessage();
            }
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public static String getErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            return jsonObject.getString("message");
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
