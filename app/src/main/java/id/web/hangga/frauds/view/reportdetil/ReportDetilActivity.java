package id.web.hangga.frauds.view.reportdetil;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.hangga.frauds.R;
import id.web.hangga.frauds.model.Frauds;
import id.web.hangga.frauds.model.Report;
import id.web.hangga.frauds.model.Sumary;
import id.web.hangga.frauds.presenter.FraudsPresenter;
import id.web.hangga.frauds.presenter.ReportDetilPresenter;
import id.web.hangga.frauds.util.Prop;
import id.web.hangga.frauds.view.OnPrepareToDelete;
import id.web.hangga.frauds.view.postreport.PostReportActivity;

/**
 * Kelas untuk menampilkan data report detil beserta daftar penipuan(fraud list)
 * Kelas ini juga termasuk level view layer yg merupakan kelas end user yang berinteraksi langsung
 * dengan user
 */
public class ReportDetilActivity extends AppCompatActivity implements ReportDetilPresenter.ViewInterface,
        FraudsPresenter.ViewInterface {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    @BindView(R.id.recyclerMain)
    RecyclerView recyclerMain;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    ReportDetilPresenter reportDetilPresenter;
    FraudsPresenter fraudsPresenter;
    FraudListAdapter fraudListAdapter;
    Report report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detil);
        report = getIntent().getParcelableExtra("report");
        ButterKnife.bind(this);
        setupactionbar();
        initrecycler();
        initaction();
    }

    void initaction() {
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(ReportDetilActivity.this, PostReportActivity.class);
            intent.putExtra(Prop.PARAM_POST_TYPE, Prop.POST_INSERT_FRAUD);
            intent.putExtra("report", report);
            startActivityForResult(intent, Prop.POST_INSERT_FRAUD);
        });
    }

    void setupactionbar() {
        toolbar.setTitle(getString(R.string.report_detil));
        toolbar.setSubtitle(getString(R.string.fraud_list));
        toolbar.setTitleMarginStart(0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(ReportDetilActivity.this,
                    R.color.colorPrimary));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(Prop.APP_NAME, "Cek resultCode:resultCode:" + resultCode);
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == Prop.POST_INSERT_FRAUD) {
                Frauds frauds = data.getParcelableExtra("frauds");
                frauds.setReportId(report.getId());
                fraudsPresenter.createFraud(frauds);
            } else if (requestCode == Prop.POST_UPDATE_FRAUD) {
                Frauds frauds = data.getParcelableExtra("frauds");
                fraudsPresenter.updateFraud(frauds);
            } else if (requestCode == Prop.POST_UPDATE_REPORT) {
                Report report = data.getParcelableExtra("report");
                reportDetilPresenter.updateReport(report);
            }
        }
    }

    void initrecycler() {
        fraudsPresenter = new FraudsPresenter(this);
        fraudListAdapter = new FraudListAdapter();
        fraudListAdapter.setOnPrepareToDelete(new OnPrepareToDelete() {
            @Override
            public void onPrepareToDelete(Report report) {
                reportDetilPresenter.deleteReport(report);
            }

            @Override
            public void onPrepareToDelete(Frauds frauds) {
                fraudsPresenter.deleteFraud(frauds);
            }
        });
        recyclerMain.setLayoutManager(new LinearLayoutManager(this));
        reportDetilPresenter = new ReportDetilPresenter(this);
        reportDetilPresenter.getReportDetil(report.getId());
        swiperefresh.setOnRefreshListener(() -> reportDetilPresenter.getReportDetil(report.getId()));
    }


    @Override
    public void onProgress(boolean isShow) {
        swiperefresh.setRefreshing(isShow);
    }

    @Override
    public void onError(String errMessage) {
        showMessage(errMessage);
    }

    @Override
    public void onEmpty() {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReportResult(Report report) {
        //reportDetilPresenter.getReportDetil(report.getId());
    }

    @Override
    public void onReportDeleted(Report report) {
        finish();
    }

    @Override
    public void onReportDetil(List<Frauds> frauds, Sumary sumary) {
        fraudListAdapter.setFraudsList(frauds);
        fraudListAdapter.setSumary(sumary);
        fraudListAdapter.setReport(report);
        recyclerMain.setAdapter(fraudListAdapter);
    }

    @Override
    public void onFraudResult(Frauds frauds) {
        fraudListAdapter.update(frauds);
    }

    @Override
    public void onFraudDeleted(Frauds frauds) {
        reportDetilPresenter.getReportDetil(report.getId());
    }
}
