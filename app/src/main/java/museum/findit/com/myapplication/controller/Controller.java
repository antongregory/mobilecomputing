package museum.findit.com.myapplication.controller;

import java.util.List;

import museum.findit.com.myapplication.model.ItemModel;

/**
 * Created by antongregory on 05/10/2016.
 */

public class Controller {

    private ItemLoadCallbackListener mListener;

    public Controller(ItemLoadCallbackListener listener) {
        mListener = listener;
       //use model manager
    }




    public interface ItemLoadCallbackListener {

        void onFetchStart();
        void onFetchProgress(ItemModel flower);
        void onFetchProgress(List<ItemModel> flowerList);
        void onFetchComplete();
        void onFetchFailed();
    }


}
