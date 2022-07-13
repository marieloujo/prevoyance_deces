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
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.shagi.materialdatepicker.date.DatePickerFragmentDialog;

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
import bj.assurance.assurancedeces.activity.MarchandActivity;
import bj.assurance.assurancedeces.model.Commune;
import bj.assurance.assurancedeces.model.Departement;
import bj.assurance.assurancedeces.model.customModel.PhoneList;
import bj.assurance.assurancedeces.model.customModel.PhonePrefix;
import bj.assurance.assurancedeces.model.pagination.OutputPaginate;
import bj.assurance.assurancedeces.serviceImplementation.DepartementServiceImplementation;
import bj.assurance.assurancedeces.serviceImplementation.PhoneServiveImplementation;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import br.com.sapereaude.maskedEditText.MaskedEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ContratFormUtils {


    private Context context;
    private List<Commune> communes = new ArrayList<>();
    private List<Commune> communeList = new ArrayList<>();
    private List<Departement> departements = new ArrayList<>();
    private List<PhoneList> phoneLists = new ArrayList<>();
    private AlertDialog alertDialog;





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




    public ContratFormUtils(Context context) {

        this.context = context;

        communes.add(0,new Commune("Commune"));

        //communes.addAll(MarchandActivity.getCommunes());

        communes.add(new Commune("Autre"));

    }




    public void makeSexeSpinnerList(android.widget.Spinner spinner) {


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





    public void makeSmSpinnerList(android.widget.Spinner spinner) {

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






    public void makeCommuneList(final android.widget.Spinner spinner)  {


        getCommuneofMarchand(MarchandActivity.getMarchand().getUtilisateur().getCommune().getDepartement().getId(),spinner);


    }


    private void makeCommuneAdapter(final android.widget.Spinner spinner) {

        communes.add(0, new Commune("Commune"));
        communes.add(communes.size(), new Commune("Autre"));


        final ArrayAdapter<Commune> communeArrayAdapter = new ArrayAdapter<Commune>(
                context,R.layout.item_spinner,communes){
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





    public void makePhoneIdentityList(android.widget.Spinner spinner) {


        getPhoneNumberPrefixList(spinner);

    }

    private void makePhoneAdapyer(final android.widget.Spinner spinner) {

        String[] phoneIdentity = new String[phoneLists.size()];

        for (int i = 0; i < phoneLists.size(); i++) {

            phoneIdentity[i] = phoneLists.get(i).getCode();
            System.out.println(Arrays.toString(phoneIdentity));

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


        final ArrayAdapter<String> qualificationArrayAdapter = new ArrayAdapter<String>(
                context,R.layout.item_spinner,context.getResources().getStringArray(R.array.qualification)){


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
                //Commune selectedItemText = (Commune) parent.getItemAtPosition(position);

                /*if(position > 0){

                    if (selectedItemText.getNom().equals("Autre")) {



                    }
                        //openListDepartementDialog(spinner);
                }*/
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });




    }






    public void makeAnneeSpinnerList(Spinner spinner, Spinner spinner1) throws ParseException {


        List<List<String>> lists = getValue();




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





    public String getSelectedSexe (android.widget.Spinner spinner) {

        if (spinner.getSelectedItem().equals("Sexe"))

            return null;

        else return spinner.getSelectedItem().toString();


    }






    public Commune getSelectedCommune(android.widget.Spinner spinner) {

        Commune commune = new Commune();

        try {
/*
            for (int i = 0; i < MarchandActivity.getCommunes().size(); i++) {
                if (MarchandActivity.getCommunes().get(i).getNom().equals(spinner.getSelectedItem().toString())) {
                    commune = MarchandActivity.getCommunes().get(i);
                }
            }*/

        } catch (Exception e) {

            e.printStackTrace();

        }

        return commune;

    }






    public boolean verifTelephone(String telephone, Spinner spinner) {

        boolean isValidePhone = false;

        List<PhoneList> phoneLists = new ArrayList<>();

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







    private void openListDepartementDialog(final android.widget.Spinner spinner) {


        LayoutInflater inflater = ((FragmentActivity)context).getLayoutInflater();


        View alertLayout = inflater.inflate(R.layout.content_list_departement, null);

        ListView listView = alertLayout.findViewById(R.id.list_departement);


        ArrayAdapter<Departement> arrayAdapter = new ArrayAdapter<Departement>(context,
                android.R.layout.simple_list_item_1,
                departements);


        listView.setAdapter(arrayAdapter);


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

                getCommuneofDepartement(
                        departements.get(position).getId(),
                        spinner
                );

            }
        });



        alertDialog.show();
    }







    private void openCommuneDialog(final Spinner spinner) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Selectionner une commune");

        LayoutInflater inflater = ((FragmentActivity)context).getLayoutInflater();


        View alertLayout = inflater.inflate(R.layout.content_list_departement, null);

        ListView listView = alertLayout.findViewById(R.id.list_departement);




        ArrayAdapter<Commune> arrayAdapter = new ArrayAdapter<Commune>(context,
                android.R.layout.simple_list_item_1,
                communeList);

        listView.setAdapter(arrayAdapter);


        alert.setView(alertLayout);
        alert.setCancelable(false);

        alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        final AlertDialog dialog = alert.create();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ArrayAdapter<Commune> communeArrayAdapter;
                communeArrayAdapter = (ArrayAdapter<Commune>) spinner.getAdapter();


                communes.remove(communes.size()-1);

                communes.add(communes.size(), communeList.get(position));
                communeArrayAdapter.notifyDataSetChanged();

                communes.add(communes.size(), new Commune("Autre"));

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








    private void getCommuneofMarchand(Long idDepartement, final android.widget.Spinner spinner) {

        new DepartementServiceImplementation(

                TokenManager.getInstance(
                    ((FragmentActivity) context).getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()

        ).listCommunesByDepartement(idDepartement).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                if (response.isSuccessful()) {

                    OutputPaginate outputPaginate = new Gson().fromJson(
                            response.body(), OutputPaginate.class
                    );

                    Type listType = new TypeToken<List<Commune>>() {}.getType();
                    communes = new Gson().fromJson(new Gson().toJson(outputPaginate.getObjects()), listType);

                    System.out.println(communes);

                    makeCommuneAdapter(spinner);



                } else {

                    new CatchApiError(response.code()).catchError(((FragmentActivity) context).getApplicationContext());

                    try {

                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        System.out.println(jObjError.getJSONObject("errors").getString("message"));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                t.printStackTrace();

            }
        });

    }



    private void getPhoneNumberPrefixList(final android.widget.Spinner spinner) {

        new PhoneServiveImplementation(
                TokenManager.getInstance(
                    ((FragmentActivity) context).getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()
        ).getPhoneNumberListPrefix()
                .enqueue(new Callback<List<PhoneList>>() {


                    @Override
                    public void onResponse(Call<List<PhoneList>> call, Response<List<PhoneList>> response) {


                        if (response.isSuccessful()) {

                            phoneLists = response.body();

                            makePhoneAdapyer(spinner);

                        } else {

                            new CatchApiError(response.code()).catchError(((FragmentActivity) context).getApplicationContext());

                            try {

                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                System.out.println(jObjError.getJSONObject("errors").getString("message"));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }


                    }

                    @Override
                    public void onFailure(Call<List<PhoneList>> call, Throwable t) {

                        t.printStackTrace();

                    }

                });

    }





    private void getListDepartement() {

        new DepartementServiceImplementation(
                TokenManager.getInstance(
                    ((FragmentActivity) context).getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()
        )
                .listDepartement()
                .enqueue(new Callback<JsonObject>() {


                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                        if (response.isSuccessful()) {

                            Gson gson = new Gson();

                            Type listType = new TypeToken<List<Departement>>() {}.getType();

                            OutputPaginate outputPaginate = gson.fromJson(response.body(), OutputPaginate.class);

                            departements = gson.fromJson(
                                    gson.toJson(outputPaginate.getObjects()),
                                    listType
                            );



                            System.out.println(departements);


                        } else {

                            new CatchApiError(response.code());

                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                        t.printStackTrace();

                    }

                });

    }












    private void getCommuneofDepartement(Long id, final android.widget.Spinner spinner) {

        new DepartementServiceImplementation(

                TokenManager.getInstance(
                        ((FragmentActivity)context).getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()

        ).listCommunesByDepartement(id)
                .enqueue(new Callback<JsonObject>() {

                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        if (response.isSuccessful()) {


                            Gson gson = new Gson();

                            Type listType = new TypeToken<List<Commune>>() {}.getType();

                            OutputPaginate outputPaginate = gson.fromJson(response.body(), OutputPaginate.class);

                            communeList = gson.fromJson(
                                    gson.toJson(outputPaginate.getObjects()),
                                    listType
                            );
                            openCommuneDialog(spinner);


                        } else {

                            new CatchApiError(response.code());

                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                        t.printStackTrace();

                    }

                });

    }






    private List<List<String>> getValue() throws ParseException {


        List<String> listAnnee = new ArrayList<>();
        List<String> listSemestre = new ArrayList<>();


        String dateDebut = "2017-12-31 23:59:59";

        int dateTemp, dateActu;
        dateTemp = simpleDateFormat.parse(dateDebut).getYear() + 1900;
        dateActu = (new Date().getYear()) + 1900;
        date = simpleDateFormat.parse(dateDebut).getDay();
        String intervalle;

        //System.out.println((new Date().getYear()) + 1900);

        while (dateTemp + 1 <= dateActu) {
            Date date1 = dateFormat.parse(String.valueOf(dateTemp) + "-" + simpleDateFormat.parse(dateDebut).getMonth() + "-" +
                    simpleDateFormat.parse(dateDebut).getDay());
            Date date2 = dateFormat.parse(String.valueOf(dateTemp + 1) + "-" + simpleDateFormat.parse(dateDebut).getMonth() + "-" +
                    simpleDateFormat.parse(dateDebut).getDay());

            System.out.println(date1 + " " + date2);

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








}
