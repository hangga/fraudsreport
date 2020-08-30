package id.web.hangga.frauds.view.reportlist;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.hangga.frauds.R;

class PageViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.linPaging)
    LinearLayout linPaging;
    private View view;

    private List<ViewItemPage> itemPageList = new ArrayList<>();

    PageViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.view = itemView;
    }

    private void reset() {
        for (int i = 0; i < itemPageList.size(); i++) {
            itemPageList.get(i).setBackgroundResource(R.drawable.circle_black);
        }
    }

    void bind(int pageCount, ReportListAdapter.OnItemPageListener onItemPageListener) {
        if (linPaging.getChildCount() == 0) {
            for (int i = 0; i < pageCount; i++) {
                ViewItemPage viewItemPage = new ViewItemPage(view.getContext());
                viewItemPage.getTxtPage().setText(String.valueOf(i + 1));
                if (i == 0) {
                    viewItemPage.setBackgroundResource(R.drawable.circle_yellow);
                } else {
                    viewItemPage.setBackgroundResource(R.drawable.circle_black);
                }
                int finali = i;
                viewItemPage.setOnClickListener(view -> {
                    reset();
                    viewItemPage.setBackgroundResource(R.drawable.circle_yellow);
                    onItemPageListener.onPageListener(finali + 1);
                });
                itemPageList.add(viewItemPage);
                linPaging.addView(viewItemPage);
            }
        }
    }
}
