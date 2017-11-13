package com.calidad.sosfidoapp.sosfido.presentacion.contracts;

import com.calidad.sosfidoapp.sosfido.data.entities.RequestsEntity;

import java.util.List;

/**
 * Created by jairbarzola on 13/11/17.
 */

public interface RequestContract {
    interface View{
        void initRecyclerView(List<RequestsEntity> requestsEntityList);
        void showSwipeLayout();

        void hideSWipeLayout();

        void showEmpty();

        void hideEmpty();
    }
    interface Presenter{
        void start(String id);
    }
}
