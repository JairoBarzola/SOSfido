package com.calidad.sosfidoapp.sosfido.Presentacion.Contracts;

import com.calidad.sosfidoapp.sosfido.Data.Entities.PersonEntity;
import com.calidad.sosfidoapp.sosfido.Data.Entities.ResponseReport;

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
        void getReportsPoints(List<ResponseReport.ReportList> reportLists);
    }
    interface Presenter {
        void loadReports();
    }
}
