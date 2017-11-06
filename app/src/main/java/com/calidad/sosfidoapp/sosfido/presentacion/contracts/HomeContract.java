package com.calidad.sosfidoapp.sosfido.presentacion.contracts;

import com.calidad.sosfidoapp.sosfido.data.entities.ResponseReport;

import java.util.List;

/**
 * Created by jairbarzola on 2/10/17.
 */

public interface HomeContract {
    //interfaces para el modelo Vista-Presentador
    interface View {
        void setLoadingIndicator(boolean active);

        void setMessageError(String error);

        void setDialogMessage(String message);

        void getReportsPoints(List<ResponseReport.ReportList> reportListsAbandoned, List<ResponseReport.ReportListMissing> reportListsMissing,
                              List<ResponseReport.ReportListAdoption> reportListAdoption);
    }

    interface Presenter {
        void loadReports();
    }
}
