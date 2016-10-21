package museum.findit.com.myapplication.model;

/**
 * Created by antongregory on 19/10/2016.
 */

public interface Manager {

    /*
        Handles the actions corresponding to an item

     */

    public boolean checkNextItem();
    public ItemModel getItem();
    public void saveItems();
    public void saveScore(int items);






}
