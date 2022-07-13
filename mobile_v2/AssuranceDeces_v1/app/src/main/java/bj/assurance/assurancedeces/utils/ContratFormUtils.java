package bj.assurance.assurancedeces.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.model.Commune;
import bj.assurance.assurancedeces.model.Departement;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.model.customModel.PhoneList;
import bj.assurance.assurancedeces.model.customModel.PhonePrefix;
import bj.assurance.assurancedeces.utils.json.ReadExternalJson;





public class ContratFormUtils {


    private Context context;
    private List<Commune> communes = new ArrayList<>();
    private List<Commune> communeList = new ArrayList<>();
    private List<Departement> departements = new ArrayList<>();
    private List<PhoneList> phoneLists = new ArrayList<>();
    private AlertDialog alertDialog;

    private List<String> qualificationList = new ArrayList<>();

    private ArrayAdapter<Departement> arrayAdapter;





    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    @SuppressLint("SimpleDateFormat")
    private
    SimpleDateFormat format = new SimpleDateFormat("MMM yyyy", Locale.FRANCE);


    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat formatDate = new SimpleDateFormat("dd MMM", Locale.FRANCE);




    private int date;

    private ListView listView, listViewCommune;
    private ProgressBar progressBar;
    private TextView errorText;

    private Long idDepartement;





    public ContratFormUtils(Context context, Long idDepartement) {

        this.context = context;
        this.idDepartement = idDepartement;


    }




    public void makeSexeSpinnerList(Spinner spinner) {


        final ArrayAdapter<String> sexeArrayAdapter = new ArrayAdapter<String>(
                context,R.layout.item_spinner,context.getResources().getStringArray(R.array.sexe)){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };


        sexeArrayAdapter.setDropDownViewResource(R.layout.item_spinner);
        spinner.setAdapter(sexeArrayAdapter);


    }





    public void makeSmSpinnerList(Spinner spinner) {

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                context,R.layout.item_spinner,context.getResources().getStringArray(R.array.situation_matrimoniale)){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };


        spinnerArrayAdapter.setDropDownViewResource(R.layout.item_spinner);
        spinner.setAdapter(spinnerArrayAdapter);


    }






    public void makeCommuneList(final Spinner spinner)  {


        try {

            getCommuneofMarchand(idDepartement,spinner);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    private void makeCommuneAdapter(final Spinner spinner) {

        communes.add(0, new Commune("Commune"));
        communes.add(communes.size(), new Commune("Autre"));


        final ArrayAdapter<Commune> communeArrayAdapter = new ArrayAdapter<Commune>(
                context,R.layout.item_spinner,communes){
            @Override
            public boolean isEnabled(int position){
                if(position == 0) {
                    return false;
                } else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };


        communeArrayAdapter.setDropDownViewResource(R.layout.item_spinner);

        spinner.setAdapter(communeArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Commune selectedItemText = (Commune) parent.getItemAtPosition(position);

                if(position > 0){

                    if (selectedItemText.getNom().equals("Autre"))
                        openListDepartementDialog(spinner);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }





    public void makePhoneIdentityList(Spinner spinner) {


        try {
            getPhoneNumberPrefixList(spinner);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void makePhoneAdapyer(final Spinner spinner) {

        String[] phoneIdentity = new String[phoneLists.size()];

        for (int i = 0; i < phoneLists.size(); i++) {

            phoneIdentity[i] = phoneLists.get(i).getCode();

        }


        final ArrayAdapter<String> phoneIdentityArrayAdapter = new ArrayAdapter<String>(
                context,R.layout.item_spinner,phoneIdentity) {

            @Override
            public boolean isEnabled(int position) {

                return true;
            }


            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {

                View view = super.getDropDownView(position, convertView, parent);

                return view;

            }
        };


        phoneIdentityArrayAdapter.setDropDownViewResource(R.layout.item_spinner);

        spinner.setAdapter(phoneIdentityArrayAdapter);


    }





    public void makeQualificationList(final Spinner spinner) {

        String[] strings = context.getResources().getStringArray(R.array.qualification);

        qualificationList.addAll(Arrays.asList(strings));

        final ArrayAdapter<String> qualificationArrayAdapter = new ArrayAdapter<String>(
                context,R.layout.item_spinner,qualificationList){


            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }


            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }

        };


        qualificationArrayAdapter.setDropDownViewResource(R.layout.item_spinner);
        spinner.setAdapter(qualificationArrayAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                if(position > 0){

                    if (selectedItemText.equals("Autre")) {

                        openDialogToAddQualifiacation(spinner);


                    }

                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });




    }






    public void makeAnneeSpinnerList(Spinner spinner, Spinner spinner1, String dateDebut) throws ParseException {


        List<List<String>> lists = getValue(dateDebut);




        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                context,R.layout.item_spinner, lists.get(0)) {

            @Override
            public boolean isEnabled(int position){
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent)

            {
                View view = super.getDropDownView(position, convertView, parent);
                return view;
            }
        };



        spinnerArrayAdapter.setDropDownViewResource(R.layout.item_spinner);
        spinner.setAdapter(spinnerArrayAdapter);








        final ArrayAdapter<String> sexeArrayAdapter = new ArrayAdapter<String>(
                context,R.layout.item_spinner, lists.get(1))

        {

            @Override
            public boolean isEnabled(int position){
                return true;
            }


            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                return view;
            }


        };




        sexeArrayAdapter.setDropDownViewResource(R.layout.item_spinner);
        spinner1.setAdapter(sexeArrayAdapter);





    }





    public String getSelectedSexe (Spinner spinner) {

        if (spinner.getSelectedItem().equals("Sexe"))

            return null;

        else return spinner.getSelectedItem().toString();


    }






    public Commune getSelectedCommune(Spinner spinner) {

        Commune commune = new Commune();

        try {


            if (spinner.getSelectedItem().toString().equals("Autre") || spinner.getSelectedItem().toString().equals("Commune")) {

                commune = null;

            } else {

                for (int i = 0; i < communes.size(); i++) {


                    if (communes.get(i).getNom().equals(spinner.getSelectedItem().toString())) {

                        commune = communes.get(i);
                    }
                }

            }




        } catch (Exception e) {

            e.printStackTrace();

        }


        return commune;

    }






    public boolean verifTelephone(String telephone, Spinner spinner) {

        boolean isValidePhone = false;

        //List<PhoneList> phoneLists = new ArrayList<>();

        List<PhonePrefix> phonePrefixes = new ArrayList<>();


        for (int i = 0; i < phoneLists.size(); i ++) {

            if (spinner.getSelectedItem().toString().equals(phoneLists.get(i).getCode())) {

                phonePrefixes = phoneLists.get(i).getPhonePrefix();

            }

        }



        for (int i = 0; i < phonePrefixes.size(); i++) {

            if (phonePrefixes.get(i).getNum().equals(telephone.substring(0,2))) {

                isValidePhone = true;

            }
        }


        return isValidePhone;


    }







    private void openListDepartementDialog(final Spinner spinner) {


        LayoutInflater inflater = ((FragmentActivity)context).getLayoutInflater();


        View alertLayout = inflater.inflate(R.layout.content_list_departement, null);

        listView = alertLayout.findViewById(R.id.list_departement);
        progressBar = alertLayout.findViewById(R.id.main_progress);
        errorText = alertLayout.findViewById(R.id.error_text);
        errorText.setText("");


        try {

            getListDepartement();

        } catch (JSONException e) {
            e.printStackTrace();
        }


        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Selectionner un département");
        alert.setView(alertLayout);
        alert.setCancelable(false);

        alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog = alert.create();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                openCommuneDialog(spinner, departements.get(position).getId());

                /*getCommuneofDepartement(
                        departements.get(position).getId(),
                        spinner
                );*/

            }
        });



        alertDialog.show();
    }







    private void openCommuneDialog(final Spinner spinner, Long id) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Selectionner une commune");

        LayoutInflater inflater = ((FragmentActivity)context).getLayoutInflater();


        View alertLayout = inflater.inflate(R.layout.content_list_departement, null);

        listViewCommune = alertLayout.findViewById(R.id.list_departement);
        progressBar = alertLayout.findViewById(R.id.main_progress);
        errorText = alertLayout.findViewById(R.id.error_text);
        errorText.setText("");


        try {
            getCommuneofDepartement( id);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        alert.setView(alertLayout);
        alert.setCancelable(false);

        alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        final AlertDialog dialog = alert.create();


        listViewCommune.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ArrayAdapter<Commune> communeArrayAdapter;
                communeArrayAdapter = (ArrayAdapter<Commune>) spinner.getAdapter();


                boolean isOlder = false;
                for (int i = 0; i < communes.size(); i ++) {

                    if (communes.get(i).getNom().equals(communeList.get(position).getNom())) {

                        isOlder = true;
                        break;

                    }

                }

                if (!isOlder) {

                    communes.remove(communes.size()-1);

                    communes.add(communes.size(), communeList.get(position));
                    communeArrayAdapter.notifyDataSetChanged();

                    communes.add(communes.size(), new Commune("Autre"));

                }



                for(int i= 0; i < spinner.getAdapter().getCount(); i++) {

                    if(spinner.getAdapter().getItem(i).toString().contains(communeList.get(position).getNom())) {

                        spinner.setSelection(i);

                    }
                }

                dialog.dismiss();
                alertDialog.dismiss();

            }
        });



        dialog.show();
    }








    private void getCommuneofMarchand(Long idDepartement, final Spinner spinner) throws JSONException {


        String string = new ReadExternalJson().getJSONData(
                context, "communes.json");

        Type listType = new TypeToken<List<Commune>>() {}.getType();
        JSONObject jsonObject = new JSONObject(string);


        List<Commune> communes = new Gson().fromJson(
                jsonObject.getString("data"), listType);



        for (int i = 0; i < communes.size(); i++) {

            if (communes.get(i).getDepartement().getId().equals(idDepartement)) {

                this.communes.add(communes.get(i));
            }

        }



        makeCommuneAdapter(spinner);



    }



    private void getPhoneNumberPrefixList(final Spinner spinner) throws JSONException {



        String string = new ReadExternalJson().getJSONData(
                context, "phonelist.json");


        Type listType = new TypeToken<List<PhoneList>>() {}.getType();

        JSONObject jsonObject = new JSONObject(string);

        phoneLists =  new Gson().fromJson(
                jsonObject.getString("data"), listType);


        makePhoneAdapyer(spinner);


    }





    private void getListDepartement() throws JSONException {



        String string = new ReadExternalJson().getJSONData(
                context, "departements.json");

        Type listType = new TypeToken<List<Departement>>() {}.getType();
        JSONObject jsonObject = new JSONObject(string);


        departements = new Gson().fromJson(
                jsonObject.getString("data"), listType);


        for ( int i = 0; i < departements.size(); i++) {

            if (departements.get(i).getId().equals(idDepartement)) {

                try {

                    departements.remove(departements.get(i));

                } catch (Exception e) { e.printStackTrace(); }


            }

        }

        arrayAdapter = new ArrayAdapter<Departement>(context,
                android.R.layout.simple_list_item_1,
                departements);

        listView.setAdapter(arrayAdapter);
        progressBar.setVisibility(View.INVISIBLE);
        errorText.setText("");





    }












    private void getCommuneofDepartement(final Long id) throws JSONException {



        String string = new ReadExternalJson().getJSONData(
                context, "communes.json");

        Type listType = new TypeToken<List<Commune>>() {}.getType();
        JSONObject jsonObject = new JSONObject(string);


        List<Commune> communes = new Gson().fromJson(
                jsonObject.getString("data"), listType);


        communeList.clear();
        for (int i = 0; i < communes.size(); i++) {

            if (communes.get(i).getDepartement().getId().equals(id)) {

                this.communeList.add(communes.get(i));
            }

        }


        ArrayAdapter<Commune> arrayAdapter = new ArrayAdapter<Commune>(context,
                android.R.layout.simple_list_item_1,
                communeList);

        listViewCommune.setAdapter(arrayAdapter);

        progressBar.setVisibility(View.INVISIBLE);
        errorText.setText("");



    }










    private void openDialogToAddQualifiacation(final Spinner spinner) {

        LayoutInflater inflater = ((FragmentActivity)context).getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.add_qualification, null);

        final EditText textView = alertLayout.findViewById(R.id.etQualificationAdd);
        final TextView textView1 = alertLayout.findViewById(R.id.tvError);


        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Ajouter une qualification");
        alert.setView(alertLayout);
        alert.setCancelable(false);

        alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });



        alert.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (textView.getText().toString().isEmpty()) {


                    textView1.setVisibility(View.VISIBLE);


                } else {


                    ArrayAdapter<String> qualifiactionArrayAdapter = (ArrayAdapter<String>) spinner.getAdapter();

                    qualificationList.remove(qualificationList.size()-1);

                    qualificationList.add(qualificationList.size(), textView.getText().toString());
                    qualifiactionArrayAdapter.notifyDataSetChanged();

                    qualificationList.add(qualificationList.size(), "Autre");




                    for(int i= 0; i < spinner.getAdapter().getCount(); i++) {

                        if(spinner.getAdapter().getItem(i).toString().contains(textView.getText().toString())) {

                            spinner.setSelection(i);

                        }
                    }

                    dialog.dismiss();

                }
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();


    }









    private List<List<String>> getValue(String dateDebut) throws ParseException {


        List<String> listAnnee = new ArrayList<>();
        List<String> listSemestre = new ArrayList<>();


        //String dateDebut = MarchandActivity.getMarchand().getDateCreation().toString(); //"2017-12-31 23:59:59";

        int dateTemp, dateActu;
        dateTemp = simpleDateFormat.parse(dateDebut).getYear() + 1900;
        dateActu = (new Date().getYear()) + 1900;
        date = simpleDateFormat.parse(dateDebut).getDay();
        String intervalle;

        while (dateTemp + 1 <= dateActu) {
            Date date1 = dateFormat.parse(String.valueOf(dateTemp) + "-" + simpleDateFormat.parse(dateDebut).getMonth() + "-" +
                    simpleDateFormat.parse(dateDebut).getDay());
            Date date2 = dateFormat.parse(String.valueOf(dateTemp + 1) + "-" + simpleDateFormat.parse(dateDebut).getMonth() + "-" +
                    simpleDateFormat.parse(dateDebut).getDay());

            intervalle = format.format(date1) + "  –   " + format.format(date2);
            listAnnee.add(intervalle);
            dateTemp = dateTemp + 1;
        }

        Date lasteDayofIntervalle = dateFormat.parse(String.valueOf(dateTemp) + "-" + simpleDateFormat.parse(dateDebut).getMonth() + "-" +
                simpleDateFormat.parse(dateDebut).getDay());
        Date newDate = dateFormat.parse(dateFormat.format(new Date()));

        if (lasteDayofIntervalle.compareTo(newDate) < 0) {
            listAnnee.add(format.format(lasteDayofIntervalle) + "  –  " + format.format( dateFormat.parse(String.valueOf(dateTemp + 1) + "-" + simpleDateFormat.parse(dateDebut).getMonth() + "-" +
                    simpleDateFormat.parse(dateDebut).getDay())));
        }

        listSemestre.add(formatDate.format(lasteDayofIntervalle) + "  –  " + formatDate.format(addMonth(lasteDayofIntervalle, 6)));

        listSemestre.add(formatDate.format(addMonth(lasteDayofIntervalle, 6)) + "  –  " + formatDate.format(addMonth(lasteDayofIntervalle, 12)));


        Collections.reverse(listAnnee);


        List<List<String>> lists = new ArrayList<>();

        lists.add(listAnnee);
        lists.add(listSemestre);



        return lists;


    }








    private static Date addMonth(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, i);
        return cal.getTime();
    }







    public void resetSpiner(Utilisateur utilisateur, Spinner etSexe, Spinner etCommune, Spinner etSituationMatrimoniale) {


        try {
            for(int i= 0; i < etSexe.getAdapter().getCount(); i++)
            {
                if(etSexe.getAdapter().getItem(i).toString().contains(utilisateur.getSexe()))
                {
                    etSexe.setSelection(i);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            boolean isContain = false;
            for(int i= 0; i < etCommune.getAdapter().getCount(); i++)
            {
                if(etCommune.getAdapter().getItem(i).toString().contains(utilisateur.getCommune().getNom()))
                {
                    etCommune.setSelection(i);
                    isContain = true;

                }
            }


            if (!isContain) {

                communes.add(utilisateur.getCommune());
                try {

                    (( ArrayAdapter<Commune>) etCommune.getAdapter()).notifyDataSetChanged();


                    for (int j = 0; j < etCommune.getAdapter().getCount(); j++) {

                        if(etCommune.getAdapter().getItem(j).toString().contains(utilisateur.getCommune().getNom()))
                        {
                            etCommune.setSelection(j);

                        }

                    }

                } catch (Exception e) {}

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            for(int i= 0; i < etSituationMatrimoniale.getAdapter().getCount(); i++)
            {

                if (utilisateur.getSituationMatrimoniale() != null)

                    if(etSituationMatrimoniale.getAdapter().getItem(i).toString().contains(utilisateur.getSituationMatrimoniale()))
                    {
                        etSituationMatrimoniale.setSelection(i);
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
