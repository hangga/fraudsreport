package id.web.hangga.frauds.view.reportlist;

import id.web.hangga.frauds.model.Frauds;
import id.web.hangga.frauds.model.Report;

public interface OnPrepareToDelete {
    void onPrepareToDelete(Report report);
    void onPrepareToDelete(Frauds frauds);
}
