package bj.assurance.prevoyancedeces.fragment.marchand;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kinda.alert.KAlertDialog;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.fragment.app.FragmentManager;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.adapter.ListeSouscriptionAdpter;
import bj.assurance.prevoyancedeces.model.Portefeuille;
import bj.assurance.prevoyancedeces.model.pagination.OutputPaginate;
import bj.assurance.prevoyancedeces.utils.AccessToken;
import bj.assurance.prevoyancedeces.utils.ApiError;
import bj.assurance.prevoyancedeces.utils.Utils;
import bj.assurance.prevoyancedeces.activity.MarchandMainActivity;
import bj.assurance.prevoyancedeces.model.Client;
import bj.assurance.prevoyancedeces.model.Contrat;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.MarchandService;
import bj.assurance.prevoyancedeces.retrofit.TokenManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;


public class ListeClients extends Fragment {

    ExpandingList mExpandingList;
    private FloatingActionButton floatingActionButton;

    private int FIRST_PAGE = 1;
    private int CURRENT_PAGE, LAST_PAGE;

    private ProgressBar progressBar, progressBar1;
    private ScrollView scrollView;

    static private List<Client> clients = new ArrayList<>();
    private TextView errorText;
    private RelativeLayout contentError, layoutConnexionLose;
    private LinearLayout noData;
    private Button retry;

    int size = 0;

    public ListeClients() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_liste_clients, container, false);

        try {

            init(view);
            getClient(
                    TokenManager.getInstance(getActivity().getSharedPreferences("prefs", MODE_PRIVATE)).getToken()
            );
            floatingButtonaddClient();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void init(View view) {
        mExpandingList = view.findViewById(R.id.expanding_list_main);
        floatingActionButton = view.findViewById(R.id.floatingAdd);
        scrollView = view.findViewById(R.id.scrollview);
        progressBar = view.findViewById(R.id.main_progress);
        progressBar1 = view.findViewById(R.id.scroll_progress);
        errorText = view.findViewById(R.id.error_text);
        contentError = view.findViewById(R.id.content_error);
        retry = view.findViewById(R.id.retry);
        noData = view.findViewById(R.id.no_data_layout);
        layoutConnexionLose = view.findViewById(R.id.no_connection);
        contentError.setVisibility(View.INVISIBLE);

        scrollView.getViewTreeObserver()
                .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (scrollView.getChildAt(0).getBottom()
                                <= (scrollView.getHeight() + scrollView.getScrollY())) {
                            //progressBar1.setVisibility(View.VISIBLE);
                            if (CURRENT_PAGE + 1 > LAST_PAGE ) {
                                return;
                            } else {

                                CURRENT_PAGE += 1;

                                getNextClient(
                                        TokenManager.getInstance(getActivity()
                                                .getSharedPreferences("prefs", MODE_PRIVATE))
                                                .getToken(), CURRENT_PAGE
                                );
                            }
                        } else {
                            //scroll view is not at bottom
                        }
                    }
                });

    }

    private void createItems(List<Client> clients) {

        for (int i = 0; i < clients.size(); i++) {
            addItem(clients.get(i));
        }
    }

    @SuppressLint("SetTextI18n")
    private void addItem(Client client) {
        //Let's create an item with R.layout.expanding_layout
        final ExpandingItem item = mExpandingList.createNewItem(R.layout.expanding_layout_client);

        //If item creation is successful, let's configure it
        if (item != null) {
            item.setIndicatorColorRes(R.color.gery_inactive);
            //It is possible to get any view inside the inflated layout. Let's set the text in the item

            try {
                ((TextView) item.findViewById(R.id.nom_prenom_marchand)).setText(client.getUtilisateur().getNom() + " " +
                        client.getUtilisateur().getPrenom());

                ((TextView) item.findViewById(R.id.numero_marchand)).setText(client.getUtilisateur().getTelephone());
            } catch (Exception ignored) {}

            try {
                getContrats(TokenManager.getInstance(getActivity()
                        .getSharedPreferences("prefs", MODE_PRIVATE))
                        .getToken(), client.getId(), item);
            } catch (Exception ignored) {

            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void addSubItem(List<Contrat> contrats, ExpandingItem view){
        view.createSubItems(contrats.size());

        for (int i = size; i < contrats.size(); i++) {
                View subItemZero = view.getSubItemView(i);

            try {
                ((TextView) subItemZero.findViewById(R.id.nom_assure)).setText(contrats.get(i).getAssurer().getUtilisateur().getNom());

                ((TextView) subItemZero.findViewById(R.id.prenom_assure)).setText(contrats.get(i).getAssurer().getUtilisateur().getPrenom());

                ((TextView) subItemZero.findViewById(R.id.reference_contat)).setText(contrats.get(i).getNumero());


                if(contrats.get(i).isFin())
                    ((TextView) subItemZero.findViewById(R.id.contrat_actif)).setText(getResources().getString(R.string.active));
                else  {
                    TextView textView =  subItemZero.findViewById(R.id.contrat_actif);
                    textView.setText("expiré");

                    for (Drawable drawable : textView.getCompoundDrawables()) {
                        if (drawable != null) {
                            drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.red_active),PorterDuff.Mode.SRC_IN));
                        }
                    }
                }

                Integer solde = 0;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
                Date lastePaiement = simpleDateFormat.parse(contrats.get(i).getTransactions().get(0).getCreatedAt());

                for (int j = 0; j < contrats.get(i).getTransactions().size(); j++) {
                    solde += Integer.valueOf(contrats.get(i).getTransactions().get(j).getMontant());

                    Date date2 = simpleDateFormat.parse(contrats.get(i).getTransactions().get(j).getCreatedAt());

                    if (lastePaiement.compareTo(date2) > 0 ) {
                        lastePaiement = lastePaiement;
                    } else if (lastePaiement.compareTo(date2) < 0) {
                        lastePaiement = date2;
                    } else if (lastePaiement.compareTo(date2) == 0) {
                        lastePaiement = date2;
                    }
                }
                ((TextView) subItemZero.findViewById(R.id.portefeuil_assurance)).setText(solde+ " fcfa");
                ((TextView) subItemZero.findViewById(R.id.date_dernier_paiment)).setText(simpleDateFormat.format(lastePaiement));

                int monthsBetween = new Date().getMonth() - simpleDateFormat.parse(contrats.get(i).getDateEffet()).getMonth();

                int montant = (monthsBetween * 1000) - solde;

                if (montant > 0) {
                    ((TextView) subItemZero.findViewById(R.id.status_paiment)).setText("-" + montant + " fcfa");
                    ((TextView) subItemZero.findViewById(R.id.status_paiment)).setTextColor(getResources().getColor(R.color.red_active));
                } else if (montant < 0) {
                    ((TextView) subItemZero.findViewById(R.id.status_paiment)).setText("avance de " + montant/1000 + "mois");
                    ((TextView) subItemZero.findViewById(R.id.status_paiment)).setTextColor(getResources().getColor(R.color.black));
                } else if (montant == 0) {
                    ((TextView) subItemZero.findViewById(R.id.status_paiment)).setTextColor(getResources().getColor(R.color.green_active));
                    ((TextView) subItemZero.findViewById(R.id.status_paiment)).setText("En règle");
                }

            } catch (Exception e) {
                System.out.println(e.getCause());
            }

        }
    }

    private void floatingButtonaddClient() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new AddClientStepOne());
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main_marchand, fragment).commit();
    }

    private void getClient(AccessToken accessToken) {
        Call<JsonObject> call;
        MarchandService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(MarchandService.class);
        call = service.getClients(MarchandMainActivity.getMarchand().getId(), FIRST_PAGE);
        call.enqueue(new Callback<JsonObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    getResponseClient(response.body());
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    layoutConnexionLose.setVisibility(View.INVISIBLE);
                    noData.setVisibility(View.INVISIBLE);
                    contentError.setVisibility(View.VISIBLE);


                    ((TextView) contentError.findViewById(R.id.error_text)).setText("Une erreur s'est produite lors dela récupération des clents");

                    progressBar.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                layoutConnexionLose.setVisibility(View.INVISIBLE);
                noData.setVisibility(View.INVISIBLE);
                contentError.setVisibility(View.VISIBLE);

                ((TextView) contentError.findViewById(R.id.error_text)).setText("Une erreur s'est produite lors dela récupération des clents");
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void getContrats(AccessToken accessToken, Long idClient, ExpandingItem view) {
        Call<JsonObject> call;
        MarchandService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(MarchandService.class);
        call = service.getConrattoClient(MarchandMainActivity.getMarchand().getId(), idClient, 1);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    System.out.println(response.body());

                    getResponseContrat(response.body(), view);

                } else {
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());

                //Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void handleErrors(ResponseBody response) {

        ApiError apiError = Utils.converErrors(response);

    }

    private void getNextClient(AccessToken accessToken, int page) {
        Call<JsonObject> call;
        MarchandService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(MarchandService.class);
        call = service.getClients(MarchandMainActivity.getMarchand().getId(), page);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    System.out.println(response.body());

                    /*CURRENT_PAGE = response.body().getMetaPaginate().getCurrentPage();
                    LAST_PAGE = response.body().getMetaPaginate().getLastPage();

                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<Client>>(){}.getType();

                    int size = clients.size();

                    clients.addAll(gson.fromJson(
                            gson.toJson(response.body().getObjects()), listType));

                    createItems(clients, size);*/
                    progressBar.setVisibility(View.INVISIBLE);

                } else {
                    //catchApiEror(response);
                    progressBar.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                //Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void catchApiEror(Response<OutputPaginate> response) {

        switch (response.code()) {
            case 400:  // Bad Request La syntaxe de la requête est erronée.
                contentError.setVisibility(View.VISIBLE);
                errorText.setText("Une erreur s'est produite lors de l'envoie des données veuillez réessayer ultérieurement");
                Log.w(TAG, "400: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                break;

            case 401:  // Unauthorized  Une authentification est nécessaire pour accéder à la ressource
                contentError.setVisibility(View.VISIBLE);
                errorText.setText("Vous n'êtes pas autorisé à acceder à cette resource");
                retry.setVisibility(View.INVISIBLE);
                Log.w(TAG, "401: " + response.errorBody().byteStream());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                break;

            case 403:  // Forbidden
                contentError.setVisibility(View.VISIBLE);
                errorText.setText("");
                retry.setVisibility(View.INVISIBLE);
                Log.w(TAG, "403: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                break;

            case 404: // Not Found Ressource non trouvée.
                contentError.setVisibility(View.VISIBLE);
                errorText.setText("Vous n'etes pas autorisé à acceder à cette ressource");
                Log.w(TAG, "422: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                break;

            case 405: //  Method Not Allowed Méthode de requête non autorisée.
                contentError.setVisibility(View.VISIBLE);
                errorText.setText("Une erreur s'est produite lors de la récupération des données veuillez réessayer ultérieurement");
                Log.w(TAG, "405: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                break;

            case 406:  //  Not Acceptable La ressource demandée n'est pas disponible dans un format qui respecterait les en-têtes « Accept » de la requête.
                contentError.setVisibility(View.VISIBLE);
                errorText.setText("Une erreur s'est produite lors de la récupération des données veuillez réessayer ultérieurement");
                Log.w(TAG, "406: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                break;

            case 408:   //   Request Time-out Temps d’attente d’une requête du client, écoulé côté serveur. D'après les spécifications HTTP : « Le client n'a pas produit de requête dans le délai que le serveur était prêt à attendre. Le client PEUT répéter la demande sans modifications à tout moment ultérieur »8.
                contentError.setVisibility(View.VISIBLE);
                errorText.setText("La récupération des données veuillez réessayer ultérieurement");
                Log.w(TAG, "408: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                break;

            case 422:  // Unprocessable entity	WebDAV : L’entité fournie avec la requête est incompréhensible ou incomplète.
                contentError.setVisibility(View.VISIBLE);
                errorText.setText("Une erreur s'est produite lors de la récupération des données veuillez réessayer ultérieurement");
                Log.w(TAG, "422: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                break;

            case 424:  //  Method failure Une méthode de la transaction a échoué.
                contentError.setVisibility(View.VISIBLE);
                errorText.setText("Une erreur s'est produite lors de la récupération des données veuillez réessayer ultérieurement");
                Log.w(TAG, "424: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                break;

            case 429: //  Too Many Requests Le client a émis trop de requêtes dans un délai donné.
                contentError.setVisibility(View.VISIBLE);
                errorText.setText("Une erreur s'est produite lors de la récupération des données veuillez réessayer ultérieurement");
                Log.w(TAG, "429: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                break;

            case 444:  // No Response Indique que le serveur n'a retourné aucune information vers le client et a fermé la connexion.
                contentError.setVisibility(View.VISIBLE);
                errorText.setText("Une erreur s'est produite lors de la récupération des données veuillez réessayer ultérieurement");
                Log.w(TAG, "444: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                break;

            case 500:  // Internal Server Error Erreur interne du serveur.
                contentError.setVisibility(View.VISIBLE);
                errorText.setText("Erreur interne au niveau du seveur, veuillez réessayer ultérieurement");
                Log.w(TAG, "500: " + response.errorBody().source());
                handleErrors(response.errorBody());
                progressBar.setVisibility(View.INVISIBLE);
                break;

                /*
            case 498:  // Client Closed Request	Le client a fermé la connexion avant de recevoir la réponse. Cette erreur se produit quand le traitement est trop long côté serveur16.
                error.setText("Une erreur s'est produite lors de l'envoie des donnée veuillez réessayer ultérieurement");
                Log.w(TAG, "422: " + response.errorBody().source());
                handleErrors(response.errorBody());
                pDialog.dismiss();
                break;*/
        }
    }


    private void getResponseClient(JsonObject jsonObject) {
        JsonObject error = null, sucess = null;
        String messageError = null, message = null;
        OutputPaginate outputPaginate = null;

        TextView errorText = contentError.findViewById(R.id.error_text);
        TextView nodata = noData.findViewById(R.id.no_data);

        try {
            error = jsonObject.getAsJsonObject("errors");
            messageError = error.get("message").getAsString();
            errorText.setText(messageError);
            layoutConnexionLose.setVisibility(View.INVISIBLE);
            noData.setVisibility(View.INVISIBLE);
            contentError.setVisibility(View.VISIBLE);
        }catch (Exception ignored) {}

        try {
            sucess = jsonObject.getAsJsonObject("success");
            message = sucess.get("message").getAsString();
            nodata.setText(message);
            layoutConnexionLose.setVisibility(View.INVISIBLE);
            noData.setVisibility(View.VISIBLE);
            contentError.setVisibility(View.INVISIBLE);
        } catch (Exception ignored) {}

        try {
            outputPaginate = new Gson().fromJson(jsonObject, OutputPaginate.class);
        } catch (Exception e) {
            e.printStackTrace();}

        try {
            try {
                CURRENT_PAGE = outputPaginate.getMetaPaginate().getCurrentPage();
                LAST_PAGE = outputPaginate.getMetaPaginate().getLastPage();
            } catch (Exception e) {}

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Client>>(){}.getType();

            clients = gson.fromJson(gson.toJson(outputPaginate.getObjects()), listType);
            System.out.println("clients" + clients);
            createItems(clients);
            progressBar.setVisibility(View.INVISIBLE);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getResponseContrat(JsonObject jsonObject, ExpandingItem view) {
        JsonObject error = null, sucess = null;
        String messageError = null, message = null;
        OutputPaginate outputPaginate = null;
        String string = null, s = null;
        List<Contrat> contrats = null;

        TextView errorText = contentError.findViewById(R.id.error_text);
        TextView nodata = noData.findViewById(R.id.no_data);

        try {
            error = jsonObject.getAsJsonObject("errors");
            messageError = error.get("message").getAsString();
            errorText.setText(messageError);
            layoutConnexionLose.setVisibility(View.INVISIBLE);
            noData.setVisibility(View.INVISIBLE);
            contentError.setVisibility(View.VISIBLE);
        }catch (Exception ignored) {}

        try {
            sucess = jsonObject.getAsJsonObject("success");
            message = sucess.get("message").getAsString();
            nodata.setText(message);
            layoutConnexionLose.setVisibility(View.INVISIBLE);
            nodata.setVisibility(View.VISIBLE);
            contentError.setVisibility(View.INVISIBLE);
        } catch (Exception ignored) {}

        try {
            outputPaginate = new Gson().fromJson(jsonObject, OutputPaginate.class);
        } catch (Exception ignored) {
        }

        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Contrat>>() {}.getType();

            assert outputPaginate != null;
            contrats = gson.fromJson( gson.toJson(outputPaginate.getObjects()), listType);

            addSubItem(contrats, view);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }


}
