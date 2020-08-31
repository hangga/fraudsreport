package id.web.hangga.frauds.view.reportlist;

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
import id.web.hangga.frauds.presenter.ReportsListPresenter;
import id.web.hangga.frauds.util.Prop;
import id.web.hangga.frauds.view.postreport.PostReportActivity;

public class ReportListActivity extends AppCompatActivity implements ReportsListPresenter.View,
        FraudsPresenter.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    @BindView(R.id.recyclerMain)
    RecyclerView recyclerMain;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    ReportListAdapter reportListAdapter;
    ReportsListPresenter reportsListPresenter;
    FraudsPresenter fraudsPresenter;
    Frauds fraudsresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupactionbar();
        initrecycler();
        initaction();
    }

    void initaction() {
        fraudsPresenter = new FraudsPresenter(this);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(ReportListActivity.this, PostReportActivity.class);
            intent.putExtra(Prop.PARAM_POST_TYPE, Prop.POST_INSERT_REPORT);
            startActivityForResult(intent, Prop.POST_INSERT_REPORT);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            Log.d(Prop.APP_NAME, "Error create report:RESULT_OK");
            if (requestCode == Prop.POST_INSERT_REPORT) {
                Report report = data.getParcelableExtra("report");
                fraudsresult = data.getParcelableExtra("frauds");
                reportsListPresenter.createReport(report);
            } else if (requestCode == Prop.POST_UPDATE_REPORT) {
                Report report = data.getParcelableExtra("report");
                reportsListPresenter.updateReport(report);
            }
        }
    }

    void setupactionbar() {
        toolbar.setTitle(getString(R.string.report_list_title));
        toolbar.setSubtitle(getString(R.string.report_list_subtitle));
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(ReportListActivity.this,
                    R.color.colorPrimary));
        }
    }

    void initrecycler() {
        reportListAdapter = new ReportListAdapter(10);
        recyclerMain.setLayoutManager(new LinearLayoutManager(this));
        reportsListPresenter = new ReportsListPresenter(this);
        reportsListPresenter.getAllData();
        swiperefresh.setOnRefreshListener(() -> reportsListPresenter.getAllData());
    }

    @Override
    public void onReportResult(Report report, boolean isNew) {
        if (report != null) {
            report.setNew(isNew);
            if (isNew) {
                reportListAdapter.addReport(report);
                fraudsresult.setReportId(report.getId());
                fraudsPresenter.createFraud(fraudsresult);
            } else {
                reportsListPresenter.getAllData();
            }
        }
    }

    @Override
    public void onReportDeleted(Report report) {
        showMessage(report.getNumber() + getString(R.string.delete_success));
    }

    @Override
    public void onGetAllDataReport(List<Report> reports, Sumary sumary) {
        reportListAdapter.setReportList(reports);
        reportListAdapter.setSumary(sumary);
        reportListAdapter.setOnPrepareToDelete(new OnPrepareToDelete() {
            @Override
            public void onPrepareToDelete(Report report) {
                reportsListPresenter.deleteReport(report);
            }

            @Override
            public void onPrepareToDelete(Frauds frauds) {

            }
        });
        recyclerMain.setAdapter(reportListAdapter);
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
        showMessage(getString(R.string.not_found));
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFraudResult(Frauds frauds) {
        // not implemented in this activity
    }

    @Override
    public void onFraudDeleted(Frauds frauds) {
        // not implemented in this activity
    }
}
