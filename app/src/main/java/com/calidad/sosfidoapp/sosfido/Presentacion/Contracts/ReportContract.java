package com.calidad.sosfidoapp.sosfido.Presentacion.Contracts;

import com.calidad.sosfidoapp.sosfido.Data.Entities.ResponseReport;

import java.util.List;

/**
 * Created by jairbarzola on 31/10/17.
 */

public interface ReportContract {

    //interfaces para el modelo Vista-Presentador
    interface View {
        void setInitRecycler(List<ResponseReport.ReportList> reportListsAbandoned, List<ResponseReport.ReportListMissing> reportListsMissing,
                             List<ResponseReport.ReportListAdoption> reportListAdoption);
        void showSwipeLayout();
        void hideSWipeLayout();
        void showEmpty();
        void hideEmpty();
        void setMessageError(String error);
    }
    interface Presenter {
        void start();
    }
}
