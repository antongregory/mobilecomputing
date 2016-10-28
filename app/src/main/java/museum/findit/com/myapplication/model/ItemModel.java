package museum.findit.com.myapplication.model;

import java.util.ArrayList;

/**
 * Created by antongregory on 13/10/2016.
 */

public class ItemModel {


    String image_url;
    String description;

    ArrayList<Question> questions;
    public ItemModel(){
        questions=new ArrayList<>();
    }

    String barcodeId;

    public String getOrderAndCount() {
        return orderAndCount;
    }

    public void setOrderAndCount(String orderAndCount) {
        this.orderAndCount = orderAndCount;
    }

    String orderAndCount;



    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getBarcodeId() {
        return barcodeId;
    }

    public void setBarcodeId(String barcodeId) {
        this.barcodeId = barcodeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }




}
