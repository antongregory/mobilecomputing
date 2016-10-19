package museum.findit.com.myapplication.view.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import museum.findit.com.myapplication.R;

public class QuizActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


    }

    public void showDescription(View vew){

        String descriptionExp = "A course or document may need to use an image which would need a fairly long description to properly describe the content. In this case the recommendation is to include a short summary in the ALT tag which points to or links blind users to a long text description which fully explains the image.\n" +
                "\n" +
                "In most cases, the long description should be available to all users. This long description will not only assist blind users but also sighted users who may not understand a complex image.\n" +
                "\n" +
                "The examples below will provide examples of long description and how to place them to benefit the most users.";

        new AlertDialog.Builder(this)
                .setTitle("Item Name")
                .setMessage(descriptionExp)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })

                .setIcon(android.R.drawable.ic_dialog_info)
                .show();

    }
}
