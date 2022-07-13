package bj.assurance.assurancedeces.BottomSheet;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.model.Utilisateur;


@SuppressLint("ValidFragment")
public class ActionBottomDialogFragment extends BottomSheetDialogFragment  {




    public static final String TAG = "ActionBottomDialog";


    private Utilisateur utilisateur;

    private LinearLayout phoneCall, mailCall, smsCall, close;


    @SuppressLint("ValidFragment")
    public ActionBottomDialogFragment(Utilisateur utilisateur) {

        this.utilisateur = utilisateur;

    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.content_call, container, false);


        init(view);


        return view;
    }





    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }









    private void init(View view) {

        findView(view);
        initValue();
        setClicklistener();

    }





    private void findView(View view) {


        phoneCall = view.findViewById(R.id.phone_call);
        mailCall = view.findViewById(R.id.mail_call);
        smsCall = view.findViewById(R.id.sms_call);
        close = view.findViewById(R.id.close);


    }




    @SuppressLint({"WrongConstant", "SetTextI18n"})
    private void initValue() {


    }




    private void setClicklistener() {


        phoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                call();

            }
        });


        mailCall.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("IntentReset")
            @Override
            public void onClick(View view) {

                   mail();

            }
        });




        smsCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sms();
            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });



    }










    private void call() {

        try {

            Uri u = Uri.parse("tel:" + utilisateur.getTelephone());

            Intent i = new Intent(Intent.ACTION_DIAL, u);


            try
            {
                // Launch the Phone app's dialer with a phone
                // number to dial a call.
                dismiss();
                getContext(). startActivity(i);
            }
            catch (SecurityException s)
            {
                // show() method display the toast with
                // exception message.
            }

        } catch (Exception e) {

        }

    }







    private void sms() {

        try {


            Intent intent = new Intent("android.intent.action.VIEW");

            /** creates an sms uri */
            Uri data = Uri.parse("sms:");

            /** Setting sms uri to the intent */
            intent.setData(data);
            intent.putExtra("address", utilisateur.getTelephone());
            dismiss();

            /** Initiates the SMS compose screen, because the activity contain ACTION_VIEW and sms uri */
            getContext().startActivity(intent);

        } catch (Exception e) {

        }

    }




    private void mail() {

        try {


            if (utilisateur.getEmail() == null || utilisateur.getEmail().equals("")) {

                try {

                    Toast.makeText(getContext(), "Ce utilisateur ne dispose pas d'adresse mail", Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{utilisateur.getEmail()});
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                intent.setType("text/plain");


                final PackageManager packageManager = getContext().getPackageManager();
                final List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 0);
                ResolveInfo best = null;
                for (final ResolveInfo info : resolveInfos) {
                    if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))
                        best = info;
                }
                if (best != null)
                    intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);


                dismiss();
                getContext().startActivity(intent);

            }

        } catch (Exception e) {

            try {

                Toast.makeText(getContext(), "Quelque chose s'est mal passée", Toast.LENGTH_LONG);

            } catch (Exception e1) {

                e.printStackTrace();

            }

        }

    }





}
