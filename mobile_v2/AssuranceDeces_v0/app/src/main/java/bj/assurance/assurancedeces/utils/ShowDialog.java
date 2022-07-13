package bj.assurance.assurancedeces.utils;


import android.content.Context;
import android.graphics.Color;

import com.labters.lottiealertdialoglibrary.ClickListener;
import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;
import org.jetbrains.annotations.NotNull;




public class ShowDialog {


    private Context context;
    private LottieAlertDialog alertDialog ;


    public ShowDialog(Context context) {
        this.context = context;
    }




    public void showLoaddingDialog(String title, String description) {


        alertDialog =
                new LottieAlertDialog.Builder(context,DialogTypes.TYPE_LOADING)
                .setTitle(title)
                .setDescription(description)
                .build();
                alertDialog.setCancelable(false);

        try {

            alertDialog.show();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }




    public void showWarningDialog(String title, String desciption) {


        alertDialog = new LottieAlertDialog.Builder(context,DialogTypes.TYPE_WARNING)
                .setTitle(title)
                .setDescription(desciption)
                .setPositiveText("D'accord")
                .setPositiveButtonColor(Color.parseColor("#3d000000"))
                .setPositiveTextColor(Color.parseColor("#bb000000"))
                .setPositiveListener(new ClickListener() {
                    @Override
                    public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                        lottieAlertDialog.dismiss();
                    }
                })
                .build();

        alertDialog.setCancelable(false);
        alertDialog.show();
        // Error View


    }





    public LottieAlertDialog.Builder showQuestionDialog(String title, String desciption) {


        return new LottieAlertDialog.Builder(context,DialogTypes.TYPE_QUESTION)
            .setTitle(title)
            .setDescription(desciption)
            .setPositiveText("Oui")
            .setNegativeText("Non")
            .setPositiveButtonColor(Color.parseColor("#f44242"))
            .setPositiveTextColor(Color.parseColor("#ffeaea"))
            .setNegativeButtonColor(Color.parseColor("#ffbb00"))
            .setNegativeTextColor(Color.parseColor("#0a0906"));
                // Error View


    }



    public LottieAlertDialog.Builder showErrorDialog(String title, String description, String buttonText) {

         return new LottieAlertDialog.Builder(context,DialogTypes.TYPE_ERROR)
                .setTitle(title)
                .setDescription(description)
                .setPositiveText(buttonText)
                .setPositiveButtonColor(Color.parseColor("#3d000000"))
                .setPositiveTextColor(Color.parseColor("#bb000000"));

    }






    public LottieAlertDialog.Builder showNoInternetDialog() {

        return new LottieAlertDialog.Builder(context,DialogTypes.TYPE_ERROR)
                .setTitle("Aucune connexion internet")
                .setDescription("Vous naviguez en mode non connecté, vérifier votre connexion internet")
                .setPositiveText("Réesayer")
                .setPositiveButtonColor(Color.parseColor("#3d000000"))
                .setPositiveTextColor(Color.parseColor("#bb000000"));

    }




    public LottieAlertDialog.Builder showSuccessDialog(String title, String description) {

        return new LottieAlertDialog.Builder(context,DialogTypes.TYPE_SUCCESS)
                .setTitle(title)
                .setDescription(description)
                .setPositiveText("Ok")
                .setPositiveButtonColor(Color.parseColor("#3d000000"))
                .setPositiveTextColor(Color.parseColor("#bb000000"));

    }




    public LottieAlertDialog getAlertDialog() {
        return alertDialog;
    }



    public void setAlertDialog(LottieAlertDialog alertDialog) {
        this.alertDialog = alertDialog;
    }




}
