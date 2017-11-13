package com.calidad.sosfidoapp.sosfido.presentacion.contracts;

import com.calidad.sosfidoapp.sosfido.data.entities.MyProposalAdoptionsEntity;
import com.calidad.sosfidoapp.sosfido.data.entities.ResponseReport;

import java.util.List;

/**
 * Created by jairbarzola on 10/11/17.
 */

public interface ProposalAdoptionsContract {
    //my proposal adoptions
    interface View{
        void initRecyclerView(List<MyProposalAdoptionsEntity> reportlist);
        void showSwipeLayout();

        void hideSWipeLayout();

        void showEmpty();

        void hideEmpty();
    }
    interface Presenter{
        void start();
    }
}
