package id.web.hangga.frauds.view.customview;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class MoneyEditText extends TextInputEditText {

    String current = "";
    Long longval;

    public MoneyEditText(Context context) {
        super(context);
        init();
    }

    public MoneyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MoneyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public Long getLongval() {
        return longval;
    }

    void init() {
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (!s.toString().equals(current)) {
                    removeTextChangedListener(this);
                    try {
                        String originalString = s.toString();

                        int cur = getSelectionStart();

                        if (originalString.contains(",")) {
                            originalString = originalString.replaceAll(",", "");
                        }
                        longval = Long.parseLong(originalString);

                        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                        formatter.applyPattern("#,###,###,###");
                        String formattedString = formatter.format(longval);

                        setText(formattedString);
                        setSelection(cur);
                        //setSelection(Objects.requireNonNull(getText()).length());
                    } catch (NumberFormatException nfe) {
                    } catch (Exception nfe) {
                        setSelection(Objects.requireNonNull(getText()).length());
                        nfe.printStackTrace();
                    }
                    addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
    }
}
