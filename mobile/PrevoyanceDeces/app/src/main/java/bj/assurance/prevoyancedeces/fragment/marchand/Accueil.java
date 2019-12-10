package bj.assurance.prevoyancedeces.fragment.marchand;


import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arthurivanets.bottomsheets.BottomSheet;
import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kinda.alert.KAlertDialog;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.SimpleCustomBottomSheet;
import bj.assurance.prevoyancedeces.model.Compte;
import bj.assurance.prevoyancedeces.model.Utilisateur;
import bj.assurance.prevoyancedeces.model.pagination.OutputPaginate;
import bj.assurance.prevoyancedeces.utils.AccessToken;
import bj.assurance.prevoyancedeces.utils.ApiError;
import bj.assurance.prevoyancedeces.utils.Utils;
import bj.assurance.prevoyancedeces.activity.MarchandMainActivity;
import bj.assurance.prevoyancedeces.adapter.TransactionAdater;
import bj.assurance.prevoyancedeces.model.Contrat;
import bj.assurance.prevoyancedeces.model.Marchand;
import bj.assurance.prevoyancedeces.model.Portefeuille;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.MarchandService;
import bj.assurance.prevoyancedeces.retrofit.TokenManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;


public class Accueil extends Fragment {


    private SeekBar seekBar;
    private TextView textView, matricule, nomPrenom, creditVirtuelleActuelle, creditVirtuelleRecharger, gotoTransaction, errorText, nodataText;
    private FloatingActionButton floatingActionButton;

    private RecyclerView recyclerView;
    private TransactionAdater transactionAdater;

    private Button button;

    private int FIRST_PAGE = 1;
    private int CURRENT_PAGE, LAST_PAGE;

    private RelativeLayout contentMain, contentError;
    private LinearLayout linearLayout;
    private ProgressBar progressBar;


    public Accueil() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint({"ClickableViewAccessibility", "RestrictedApi"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_accueil_marchand, container, false);


        try {
            init(view);
            setClickListener();

            bindData(MarchandMainActivity.getMarchand());

            getTansactions(TokenManager.getInstance(getActivity().
                    getSharedPreferences("prefs", MODE_PRIVATE)).
                    getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @SuppressLint("WrongConstant")
    public void init(View view) {
        seekBar = view.findViewById(R.id.simpleSeekBar);
        textView = view.findViewById(R.id.view_historique);
        floatingActionButton = view.findViewById(R.id.floatingAdd);
        matricule = view.findViewById(R.id.matricule_marchand);
        nomPrenom = view.findViewById(R.id.nom_prenom_marchand);
        creditVirtuelleActuelle = view.findViewById(R.id.credit_virtuelle_actuel);
        creditVirtuelleRecharger = view.findViewById(R.id.credit_virtuel_depart);
        recyclerView = view.findViewById(R.id.recycler);
        gotoTransaction = view.findViewById(R.id.view_all_transaction);
        contentMain = view.findViewById(R.id.content_main);
        contentError = view.findViewById(R.id.content_error);
        progressBar = view.findViewById(R.id.scroll_progress);
        errorText = view.findViewById(R.id.error_text);
        button = view.findViewById(R.id.retry);
        linearLayout = view.findViewById(R.id.no_data_layout);
        nodataText = view.findViewById(R.id.no_data);

        contentError.setVisibility(View.INVISIBLE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    @SuppressLint("ClickableViewAccessibility")
    private void setClickListener() {
        seekBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main_marchand, new Historique()).commit();

                MarchandMainActivity.getTitleFrame().setText(getContext().getResources().getString(R.string.historique));
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheet bottomSheet = new SimpleCustomBottomSheet(getActivity());
                bottomSheet.show();
            }
        });

        gotoTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main_marchand, new Transactions()).commit();

                MarchandMainActivity.getTitleFrame().setText(getContext().getResources().getString(R.string.transactions));
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void bindData(Marchand marchand) {

        nomPrenom.setText(MarchandMainActivity.getUtilisateur().getNom()+ " " + MarchandMainActivity.getUtilisateur().getPrenom());
        matricule.setText(marchand.getMatricule());
        creditVirtuelleActuelle.setText(marchand.getCreditVirtuel() + " fcfa");

        getCreditVirtuelle(
                TokenManager.getInstance(getActivity().
                        getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()
        );

    }

    private void handleErrors(ResponseBody response) {

        ApiError apiError = Utils.converErrors(response);

    }

    private void getCreditVirtuelle(AccessToken accessToken) {
        Call<JsonObject> call;
        MarchandService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(MarchandService.class);
        call = service.getCompte(MarchandMainActivity.getMarchand().getId());
        call.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    getResponseCreditVirtuelle(response.body());

                } else {
                    catchApiErorCredit(response);
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                //Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getTansactions(AccessToken accessToken) {
       Call<JsonObject> call;

        MarchandService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(MarchandService.class);

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        call = service.getTransactionbyDate(MarchandMainActivity.getMarchand().getId(), new Date());
        call.enqueue(new Callback<JsonObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    getResponseTransaction(response.body());
                    progressBar.setVisibility(View.INVISIBLE);

                } else {
                    catchApiErorCredit(response);
                    linearLayout.setVisibility(View.INVISIBLE);
                    contentError.setVisibility(View.VISIBLE);
                    contentMain.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());

                linearLayout.setVisibility(View.INVISIBLE);
                contentError.setVisibility(View.VISIBLE);
                contentMain.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void catchApiErorCredit(Response<JsonObject> response) {
        switch (response.code()) {
            case 400:  // Bad Request La syntaxe de la requête est erronée.
                errorText.setText("Une erreur s'est produite lors de l'envoie des donnée veuillez réessayer ultérieurement");
                Log.w(TAG, "400: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                contentError.setVisibility(View.VISIBLE);
                break;

            case 401:  // Unauthorized  Une authentification est nécessaire pour accéder à la ressource.
                errorText.setText("Vous n'etes pas autorisé à acceder à cette ressource");
                Log.w(TAG, "401: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                contentError.setVisibility(View.VISIBLE);
                break;

            case 403:  // Forbidden
                Log.w(TAG, "403: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                contentError.setVisibility(View.VISIBLE);
                break;

            case 404: // Not Found Ressource non trouvée.
                errorText.setText("La ressource demandé est indisponible");
                Log.w(TAG, "404: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                contentError.setVisibility(View.VISIBLE);
                break;

            case 405: //  Method Not Allowed Méthode de requête non autorisée.
                errorText.setText("Une erreur s'est produite lors de l'envoie des donnée veuillez réessayer ultérieurement");
                Log.w(TAG, "405: " + response.errorBody().source());
                handleErrors(response.errorBody());progressBar.setVisibility(View.INVISIBLE);
                contentError.setVisibility(View.VISIBLE);
                break;

            case 406:  //  Not Acceptable La ressource demandée n'est pas disponible dans un format qui respecterait les en-têtes « Accept » de la requête.
                errorText.setText("Une erreur s'est produite lors de l'envoie des donnée veuillez réessayer ultérieurement");
                Log.w(TAG, "406: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                contentError.setVisibility(View.VISIBLE);
                break;

            case 408:   //   Request Time-out Temps d’attente d’une requête du client, écoulé côté serveur. D'après les spécifications HTTP : « Le client n'a pas produit de requête dans le délai que le serveur était prêt à attendre. Le client PEUT répéter la demande sans modifications à tout moment ultérieur »8.
                button.setText("L'envoie des données à pris trop de temps.Veuillez réessayer ultérieurement");
                Log.w(TAG, "408: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                contentError.setVisibility(View.VISIBLE);
                break;

            case 422:  // Unprocessable entity	WebDAV : L’entité fournie avec la requête est incompréhensible ou incomplète.
                errorText.setText("Nom d'utilisateur ou mot de passe incorrect");
                Log.w(TAG, "422: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                contentError.setVisibility(View.VISIBLE);
                break;

            case 424:  //  Method failure Une méthode de la transaction a échoué.
                errorText.setText("Une erreur s'est produite lors de l'envoie des données, veuillez réessayer ultérieurement");
                Log.w(TAG, "424 " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                contentError.setVisibility(View.VISIBLE);
                break;

            case 429: //  Too Many Requests Le client a émis trop de requêtes dans un délai donné.
                errorText.setText("Vous avez lançer trop de requêtes");
                Log.w(TAG, "429: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                contentError.setVisibility(View.VISIBLE);
                break;

            case 444:  // No Response Indique que le serveur n'a retourné aucune information vers le client et a fermé la connexion.
                errorText.setText("Aucune reponse retourné");
                Log.w(TAG, "444: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                contentError.setVisibility(View.VISIBLE);
                break;

            case 500:  // Internal Server Error Erreur interne du serveur.
                errorText.setText("Erreur interne au niveau du seveur, veuillez réessayer ultérieurement");
                Log.w(TAG, "500: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                contentError.setVisibility(View.VISIBLE);
                break;


            case 498:  // Client Closed Request	Le client a fermé la connexion avant de recevoir la réponse. Cette erreur se produit quand le traitement est trop long côté serveur16.
                errorText.setText("Session expiré");
                Log.w(TAG, "422: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                contentError.setVisibility(View.VISIBLE);
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void catchApiErorTransaction(Response<OutputPaginate> response) {
        switch (response.code()) {
            case 400:  // Bad Request La syntaxe de la requête est erronée.
                errorText.setText("Une erreur s'est produite lors de l'envoie des donnée veuillez réessayer ultérieurement");
                Log.w(TAG, "400: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                contentError.setVisibility(View.VISIBLE);
                break;

            case 401:  // Unauthorized  Une authentification est nécessaire pour accéder à la ressource.
                errorText.setText("Vous n'etes pas autorisé à acceder à cette ressource");
                Log.w(TAG, "401: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                contentError.setVisibility(View.VISIBLE);
                break;

            case 403:  // Forbidden
                Log.w(TAG, "403: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                contentError.setVisibility(View.VISIBLE);
                break;

            case 404: // Not Found Ressource non trouvée.
                errorText.setText("La ressource demandé est indisponible");
                Log.w(TAG, "404: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                contentError.setVisibility(View.VISIBLE);
                break;

            case 405: //  Method Not Allowed Méthode de requête non autorisée.
                errorText.setText("Une erreur s'est produite lors de l'envoie des donnée veuillez réessayer ultérieurement");
                Log.w(TAG, "405: " + response.errorBody().source());
                handleErrors(response.errorBody());progressBar.setVisibility(View.INVISIBLE);
                contentError.setVisibility(View.VISIBLE);
                break;

            case 406:  //  Not Acceptable La ressource demandée n'est pas disponible dans un format qui respecterait les en-têtes « Accept » de la requête.
                errorText.setText("Une erreur s'est produite lors de l'envoie des donnée veuillez réessayer ultérieurement");
                Log.w(TAG, "406: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                contentError.setVisibility(View.VISIBLE);
                break;

            case 408:   //   Request Time-out Temps d’attente d’une requête du client, écoulé côté serveur. D'après les spécifications HTTP : « Le client n'a pas produit de requête dans le délai que le serveur était prêt à attendre. Le client PEUT répéter la demande sans modifications à tout moment ultérieur »8.
                button.setText("L'envoie des données à pris trop de temps.Veuillez réessayer ultérieurement");
                Log.w(TAG, "408: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                contentError.setVisibility(View.VISIBLE);
                break;

            case 422:  // Unprocessable entity	WebDAV : L’entité fournie avec la requête est incompréhensible ou incomplète.
                errorText.setText("Nom d'utilisateur ou mot de passe incorrect");
                Log.w(TAG, "422: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                contentError.setVisibility(View.VISIBLE);
                break;

            case 424:  //  Method failure Une méthode de la transaction a échoué.
                errorText.setText("Une erreur s'est produite lors de l'envoie des données, veuillez réessayer ultérieurement");
                Log.w(TAG, "424 " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                contentError.setVisibility(View.VISIBLE);
                break;

            case 429: //  Too Many Requests Le client a émis trop de requêtes dans un délai donné.
                errorText.setText("Vous avez lançer trop de requêtes");
                Log.w(TAG, "429: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                contentError.setVisibility(View.VISIBLE);
                break;

            case 444:  // No Response Indique que le serveur n'a retourné aucune information vers le client et a fermé la connexion.
                errorText.setText("Aucune reponse retourné");
                Log.w(TAG, "444: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                contentError.setVisibility(View.VISIBLE);
                break;

            case 500:  // Internal Server Error Erreur interne du serveur.
                errorText.setText("Erreur interne au niveau du seveur, veuillez réessayer ultérieurement");
                Log.w(TAG, "500: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                contentError.setVisibility(View.VISIBLE);
                break;


            case 498:  // Client Closed Request	Le client a fermé la connexion avant de recevoir la réponse. Cette erreur se produit quand le traitement est trop long côté serveur16.
                errorText.setText("Session expiré");
                Log.w(TAG, "422: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                contentError.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void getResponseTransaction(JsonObject jsonObject) {
        JsonObject error = null, sucess = null;
        String messageError = null, message = null;
        OutputPaginate outputPaginate = null;
        List<Portefeuille> portefeuilles = null;

        TextView errorText = contentError.findViewById(R.id.error_text);
        TextView nodata = linearLayout.findViewById(R.id.no_data);

        try {
            error = jsonObject.getAsJsonObject("errors");
            messageError = error.get("message").getAsString();
            errorText.setText(messageError);
            //layoutConnexionLose.setVisibility(View.INVISIBLE);
            linearLayout.setVisibility(View.INVISIBLE);
            contentError.setVisibility(View.VISIBLE);
            contentMain.setVisibility(View.INVISIBLE);
        }catch (Exception ignored) {}

        try {
            sucess = jsonObject.getAsJsonObject("success");
            message = sucess.get("message").getAsString();
            nodata.setText(message);
            //layoutConnexionLose.setVisibility(View.INVISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
            contentError.setVisibility(View.INVISIBLE);
            contentMain.setVisibility(View.INVISIBLE);
        } catch (Exception ignored) {}

        try {
            outputPaginate = new Gson().fromJson(jsonObject, OutputPaginate.class);
        }catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Portefeuille>>() {}.getType();

            assert outputPaginate != null;
            portefeuilles = gson.fromJson(gson.toJson(outputPaginate.getObjects()), listType);
            transactionAdater = new TransactionAdater(getContext(), portefeuilles);
            recyclerView.setAdapter(transactionAdater);

            linearLayout.setVisibility(View.INVISIBLE);
            contentError.setVisibility(View.INVISIBLE);
            contentMain.setVisibility(View.VISIBLE);

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    private void getResponseCreditVirtuelle(JsonObject jsonObject) {
        JsonObject error = null, sucess = null;
        String messageError = null, message = null;

        try {
            error = jsonObject.getAsJsonObject("errors");
            messageError = error.get("message").getAsString();
            errorText.setText(messageError);
        }catch (Exception ignored) {}

        try {
            sucess = jsonObject.getAsJsonObject("success");
            message = sucess.get("message").getAsString();
        } catch (Exception ignored) {}

        try {
            Compte compte = new Gson().fromJson(sucess.get("data"), Compte.class);
            System.out.println(compte);
            creditVirtuelleRecharger.setText(compte.getMontant() + " fcfa");

            Float pourcentage = (Float.valueOf(MarchandMainActivity.getMarchand().getCreditVirtuel()) / Float.valueOf(compte.getMontant()))
                    * 100 ;

            System.out.println("pourcentage: " + pourcentage + "\n" + "getCreditVirtuelActuelle: " + Float.valueOf(MarchandMainActivity.
                    getMarchand().getCreditVirtuel()) + "\n" + " Recharge:  " + Float.valueOf(compte.getMontant()) + "\n" +
                    "division" + ((Integer.valueOf(MarchandMainActivity.
                    getMarchand().getCreditVirtuel())) - (Integer.valueOf(compte.getMontant()))));


            seekBar.setProgress(Integer.valueOf(String.valueOf(pourcentage)));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
