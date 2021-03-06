package bj.assurance.prevoyancedeces.fragment.client;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.activity.Main2Activity;
import bj.assurance.prevoyancedeces.model.Utilisateur;


@SuppressLint("ValidFragment")
public class DetailMarchand extends Fragment {

    private Utilisateur utilisateur;
    private TextView nomPrenom, adresseDomicile, mobile, adresseTelephonique;
    private ImageView contacter, appeler, mail;

    @SuppressLint("ValidFragment")
    public DetailMarchand(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_marchand, container, false);

        //Main2Activity.getTextTitle().setVisibility(View.INVISIBLE);
        System.out.println(utilisateur);
        init(view);
        bindData();
        setClickLister();

        return view;
    }

    public void init(View view) {
        nomPrenom = view.findViewById(R.id.nom_prenom_marchand);
        adresseDomicile = view.findViewById(R.id.adresse_marchand);
        mobile = view.findViewById(R.id.mobile_marchand);
        adresseTelephonique = view.findViewById(R.id.adresse_mail_marchand);

        contacter = view.findViewById(R.id.icon_contact_marchand);
        appeler = view.findViewById(R.id.icon_phone_marchand);
        mail = view.findViewById(R.id.icon_mail_marchand);
    }

    @SuppressLint("SetTextI18n")
    public void bindData() {
        nomPrenom.setText(utilisateur.getNom() + " " + utilisateur.getPrenom());
        adresseTelephonique.setText(utilisateur.getEmail());
        mobile.setText(utilisateur.getTelephone());
        adresseDomicile.setText(utilisateur.getAdresse());
    }

    public void setClickLister() {
        contacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new Discussion(Main2Activity.getUtilisateur().getId()), getResources().getString(R.string.discussion));
            }
        });

        appeler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(utilisateur.getTelephone()));
                startActivity(intent);
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, "");
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                intent.setType("text/plain");
                final PackageManager packageManager = getActivity().getPackageManager();
                final List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 0);
                ResolveInfo best = null;
                for (final ResolveInfo info : resolveInfos ) {
                    if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))
                        best = info;
                }
                if (best != null)
                    intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
                getContext().startActivity(intent);
            }
        });
    }

    private void replaceFragment(Fragment fragment, String titre){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();

        Main2Activity.getTextTitle().setText(titre);

    }

}
