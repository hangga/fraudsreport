package id.web.hangga.frauds.util;

import android.content.Context;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.skydoves.balloon.ArrowOrientation;
import com.skydoves.balloon.Balloon;
import com.skydoves.balloon.BalloonAnimation;

import org.json.JSONObject;

import id.web.hangga.frauds.R;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class Utils {
    private static String[] mMonthSimple = {"Jan", "Feb", "Mar", "Apr", "Mei", "Jun",
            "Jul", "Aug", "Sep", "Okt", "Nop", "Des"};
    private static String[] mMonth = {"Januari", "Februari", "Maret", "April", "Mei", "Juni",
            "Juli", "Agustus", "Sepember", "Oktober", "Nopember", "Desember"};

    public static String[] getmMonthSimple() {
        return mMonthSimple;
    }

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
                .setOnBalloonClickListener(view1 -> {
                })
                .setBalloonAnimation(BalloonAnimation.FADE)
                .build();
        balloon.show(view);
    }


    public static String reformatDate(String str) {
        try {
            String syear = str.substring(0, 4);
            String smonth = str.substring(5, 7).replace("0", "");
            int idxMonth = Integer.parseInt(smonth);
            String sday = str.substring(8, 10);
            String stime = str.substring(11, 16);
            return sday + " " + mMonth[idxMonth - 1] + " " + syear + ", " + stime;
        } catch (Exception e) {
            return str;
        }
    }

    public static String getHttpErrorMessage(Throwable throwable) {
        try {
            if (throwable instanceof HttpException) {
                ResponseBody body = ((HttpException) throwable).response().errorBody();
                JSONObject jsonObject = new JSONObject(body.string());
                return jsonObject.getString("message");
            } else {
                return throwable.getMessage();
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
