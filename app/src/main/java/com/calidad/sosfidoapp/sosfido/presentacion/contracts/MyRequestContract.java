package com.calidad.sosfidoapp.sosfido.presentacion.contracts;

import com.calidad.sosfidoapp.sosfido.data.entities.MyRequestEntity;


import java.util.List;

/**
 * Created by jairbarzola on 10/11/17.
 */

public interface MyRequestContract {
    interface View{
        void initRecyclerView(List<MyRequestEntity> requestsEntityList);
        void showSwipeLayout();
        void hideSWipeLayout();
        void showEmpty();
        void hideEmpty();
    }
    interface Presenter{
        void start();
    }
}
