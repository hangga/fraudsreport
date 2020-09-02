package id.web.hangga.frauds.data.remote;

import java.util.List;

import id.web.hangga.frauds.data.remote.response.FraudItem;
import io.reactivex.Observable;

import id.web.hangga.frauds.data.remote.response.ReportItem;
import io.reactivex.Single;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Interface yang dibutuhkan saat Retrofit mengcreate instance. Setiap method-method dalam interface
 * ini mendefinisikan endpoint-endpoint yang digunakan dalam project menggunakan Anotasi Spesial
 * untuk melakukan encode parameter dan request method
 *
 */
public interface ApiInterface {

    /**
     * API Interface method yang diimplement saat retrofit berhasil medapatkan data report
     * @return balikan berupa List<ReportItem>
     */
    @GET("reports")
    Observable<List<ReportItem>> getAllReports();

    /**
     * API Interface method yang diimplement saat retrofit berhasil medapatkan data Detil Report
     * dan FraudItem list
     * @param id parameter berupa report id
     * @return balikan berupa List<FraudItem>
     */
    @GET("reports/{id}/frauds")
    Observable<List<FraudItem>> getReportDetil(@Path("id") int id);

    /**
     * API Interface method yang diimplement saat retrofit berhasil mengcreate data Report
     * @param number nomor rekening atau telephon
     * @param no_telp true jika berupa no telp
     * @param no_rek true jika berupa no rek
     * @return
     */
    @FormUrlEncoded
    @POST("reports")
    Single<ReportItem> newReport(@Field("number") String number,
                                  @Field("no_telp") boolean no_telp,
                                  @Field("no_rek") boolean no_rek);

    /**
     * API Interface method yang diimplement saat retrofit berhasil mencreate data Fraud
     * @param report_id parameter berupa report id
     * @param jenis_penipuan jenis penipuan
     * @param jumlah_kerugian jumlah kerugian
     * @param kota_korban kota korban
     * @return balikam berupa FraudItem
     */
    @FormUrlEncoded
    @POST("reports/{id}/frauds")
    Single<FraudItem> newFrauds(@Path("id") int report_id,
                                 @Field("jenis_penipuan") String jenis_penipuan,
                                 @Field("jumlah_kerugian") String jumlah_kerugian,
                                 @Field("kota_korban") String kota_korban);

    /**
     * API Interface method yang diimplement saat retrofit berhasil mengupdate data Report
     * @param id
     * @param number
     * @param no_telp
     * @param no_rek
     * @return  balikan berupa ReportItem
     */
    @FormUrlEncoded
    @PUT("reports/{id}")
    Single<ReportItem> updateReport(@Path("id") int id,
                                    @Field("number") String number,
                                    @Field("no_telp") boolean no_telp,
                                    @Field("no_rek") boolean no_rek);

    /**
     * API Interface method yang diimplement saat retrofit berhasil mengupdate data Fraud
     * @param report_id
     * @param fraud_id
     * @param jenis_penipuan
     * @param jumlah_kerugian
     * @param kota_korban
     * @return balikan berupa FraudItem
     */
    @FormUrlEncoded
    @PUT("reports/{report_id}/frauds/{fraud_id}")
    Single<FraudItem> updateFrauds(@Path("report_id") int report_id,
                                    @Path("fraud_id") int fraud_id,
                                    @Field("jenis_penipuan") String jenis_penipuan,
                                    @Field("jumlah_kerugian") String jumlah_kerugian,
                                    @Field("kota_korban") String kota_korban);

    /**
     * API Interface method yang diimplement saat retrofit berhasil menghapus report
     * @param id parameter berupa report_id
     * @return balikan berupa ReportItem
     */
    @DELETE("reports/{id}")
    Single<ReportItem> deleteReport(@Path("id") int id);

    /**
     * API Interface method yang diimplement saat retrofit berhasil menghapus FraudItem
     * @param report_id parameter berupa report_id
     * @param fraud_id parameter berupa fraud_id
     * @return balikan berupa FraudItem
     */
    @DELETE("reports/{report_id}/frauds/{fraud_id}")
    Single<FraudItem> deleteFrauds(@Path("report_id") int report_id,
                                    @Path("fraud_id") int fraud_id);
}
