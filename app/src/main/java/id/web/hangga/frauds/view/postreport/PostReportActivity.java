package id.web.hangga.frauds.view.postreport;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.hangga.frauds.R;
import id.web.hangga.frauds.model.Frauds;
import id.web.hangga.frauds.model.Report;
import id.web.hangga.frauds.util.Prop;
import id.web.hangga.frauds.util.Utils;
import id.web.hangga.frauds.view.customview.MoneyEditText;

public class PostReportActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.linMain)
    LinearLayout linMain;
    @BindView(R.id.txtLabelNomor)
    TextView txtLabelNomor;
    @BindView(R.id.rgNumberType)
    RadioGroup rgNumberType;
    @BindView(R.id.rbRek)
    RadioButton rbRek;
    @BindView(R.id.rbPhone)
    RadioButton rbPhone;
    @BindView(R.id.edtNomor)
    EditText edtNomor;
    @BindView(R.id.edtJenis)
    EditText edtJenis;
    @BindView(R.id.edtKerugian)
    MoneyEditText edtKerugian;
    @BindView(R.id.edtKota)
    EditText edtKota;

    @BindView(R.id.txtKOta)
    TextView txtKOta;
    @BindView(R.id.txtKerugian)
    TextView txtKerugian;
    @BindView(R.id.txtJenis)
    TextView txtJenis;

    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    int postType = 0;
    Report report = new Report();
    Frauds frauds = new Frauds();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_report);
        ButterKnife.bind(this);

        postType = getIntent().getIntExtra(Prop.PARAM_POST_TYPE, 0);

        switch (postType) {
            case Prop.POST_TYPE_UPDATE_REPORT:
                uiEditReportMOde();
                break;

            case Prop.POST_TYPE_UPDATE_FRAUD:
                uiEditFraud();
                break;
        }

        setupactionbar();
        initAction();
    }

    void uiEditFraud() {
        frauds = getIntent().getParcelableExtra("frauds");
        edtKota.setText(frauds.getKota_korban());
        edtJenis.setText(frauds.getJenis_penipuan());
        edtKerugian.setText(frauds.getJumlah_kerugian().toString());
        rgNumberType.setVisibility(View.GONE);
        txtLabelNomor.setVisibility(View.GONE);
        edtNomor.setVisibility(View.GONE);
    }

    void uiEditReportMOde() {
        toolbar.setTitle(R.string.edit_report);
        report = getIntent().getParcelableExtra("report");
        edtNomor.setText(report.getNumber());
        rbRek.setChecked(report.isNo_rek());
        rbPhone.setChecked(report.isNo_telp());
        txtLabelNomor.setText(report.isNo_rek()? getString(R.string.nomor_rek):getString(R.string.nomor_telp));
        txtJenis.setVisibility(View.GONE);
        edtJenis.setVisibility(View.GONE);
        txtKerugian.setVisibility(View.GONE);
        edtKerugian.setVisibility(View.GONE);
        txtKOta.setVisibility(View.GONE);
        edtKota.setVisibility(View.GONE);
    }

    void initAction() {
        rbPhone.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                txtLabelNomor.setText(R.string.nomor_telp);
                edtNomor.setHint(R.string.nomor_telp);
            }
        });
        rbRek.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                txtLabelNomor.setText(R.string.nomor_rek);
                edtNomor.setHint(R.string.nomor_rek);
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = true;
                for (int i = 0; i < linMain.getChildCount(); i++) {
                    View chid = linMain.getChildAt(i);
                    if (chid instanceof EditText) {
                        if (chid.getVisibility() == View.VISIBLE &&
                                ((EditText) chid).getText().toString().trim().length() == 0) {
                            isValid = false;
                            linMain.scrollTo(chid.getScrollX(), 0);
                            chid.requestFocus();
                            Utils.showBallon(PostReportActivity.this, chid,
                                    ((EditText) chid).getHint().toString());
                            break;
                        }
                    }
                }

                if (isValid) {

                    switch (postType) {
                        case Prop.POST_TYPE_INSERT_REPORT:
                            //uiEditReportMOde();
                            break;

                        case Prop.POST_TYPE_UPDATE_REPORT:
                            //uiEditReportMOde();
                            break;

                        case Prop.POST_TYPE_UPDATE_FRAUD:
                            //uiEditFraud();
                            break;
                    }

                    report.setNumber(edtNomor.getText().toString().trim());
                    report.setNo_telp(rbPhone.isChecked());
                    report.setNo_rek(rbRek.isChecked());

                    frauds.setKota_korban(edtKota.getText()!=null?edtKota.getText().toString():"");
                    frauds.setJenis_penipuan(edtJenis.getText() != null? edtJenis.getText().toString():"");
                    frauds.setJumlah_kerugian(edtKerugian.getLongval() != null? edtKerugian.getLongval().toString() :"");

                    Intent intent = new Intent();
                    intent.putExtra("report", report);
                    intent.putExtra("frauds", frauds);
                    setResult(postType, intent);
                    finish();
                }
            }
        });
    }

    void setupactionbar() {
        toolbar.setTitle(R.string.post_report);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(PostReportActivity.this,
                    R.color.colorPrimary));
        }
    }
}
