package id.web.hangga.frauds.repository.remote;

import java.util.List;

import id.web.hangga.frauds.repository.remote.response.FraudItem;
import io.reactivex.Observable;

import id.web.hangga.frauds.repository.remote.response.ReportItem;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {

    /**
     * Get All Report
     * @return
     */
    @GET("reports")
    Observable<List<ReportItem>> getAllReports();

    /**
     * get Detil Report and FraudItem list
     * @param id
     * @return
     */
    @GET("reports/{id}/frauds")
    Observable<List<FraudItem>> getReportDetil(@Path("id") int id);

    /**
     * Create New Report
     * @param number
     * @param no_telp
     * @param no_rek
     * @return
     */
    @FormUrlEncoded
    @POST("reports")
    Single<ReportItem> newReport(@Field("number") String number,
                                  @Field("no_telp") boolean no_telp,
                                  @Field("no_rek") boolean no_rek);

    /**
     * Create New FraudItem
     * @param report_id
     * @param jenis_penipuan
     * @param jumlah_kerugian
     * @param kota_korban
     * @return
     */
    @FormUrlEncoded
    @POST("reports/{id}/frauds")
    Single<FraudItem> newFrauds(@Path("id") int report_id,
                                 @Field("jenis_penipuan") String jenis_penipuan,
                                 @Field("jumlah_kerugian") String jumlah_kerugian,
                                 @Field("kota_korban") String kota_korban);

    /**
     * Update data Report
     * @param id
     * @param number
     * @param no_telp
     * @param no_rek
     * @return
     */
    @FormUrlEncoded
    @PUT("reports/{id}")
    Single<ReportItem> updateReport(@Path("id") int id,
                                    @Field("number") String number,
                                    @Field("no_telp") boolean no_telp,
                                    @Field("no_rek") boolean no_rek);

    /**
     * Update data FraudItem
     * @param report_id
     * @param fraud_id
     * @param jenis_penipuan
     * @param jumlah_kerugian
     * @param kota_korban
     * @return
     */
    @FormUrlEncoded
    @PUT("reports/{report_id}/frauds/{fraud_id}")
    Single<FraudItem> updateFrauds(@Path("report_id") int report_id,
                                    @Path("fraud_id") int fraud_id,
                                    @Field("jenis_penipuan") String jenis_penipuan,
                                    @Field("jumlah_kerugian") String jumlah_kerugian,
                                    @Field("kota_korban") String kota_korban);

    /**
     * Delete report
     * @param id
     * @return
     */
    @DELETE("reports/{id}")
    Single<ReportItem> deleteReport(@Path("id") int id);

    /**
     * Delete FraudItem
     * @param report_id
     * @param fraud_id
     * @return
     */
    @DELETE("reports/{report_id}/frauds/{fraud_id}")
    Single<FraudItem> deleteFrauds(@Path("report_id") int report_id,
                                    @Path("fraud_id") int fraud_id);
}
