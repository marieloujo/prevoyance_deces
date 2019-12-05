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
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kinda.alert.KAlertDialog;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.fragment.app.FragmentManager;
import bj.assurance.prevoyancedeces.R;
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

    private ProgressBar progressBar;
    private ScrollView scrollView;

    static private List<Client> clients = new ArrayList<>();

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


        init(view);
        getClient(
                TokenManager.getInstance(getActivity().getSharedPreferences("prefs", MODE_PRIVATE)).getToken()
        );
        floatingButtonaddClient();

        System.out.println("\n\n\n");
        System.out.println("\n\n\n");
        System.out.println("\n\n\n");
        System.out.println(MarchandMainActivity.getMarchand());
        System.out.println("\n\n\n");
        System.out.println("\n\n\n");
        System.out.println("\n\n\n");
        System.out.println("\n\n\n");

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void init(View view) {
        mExpandingList = view.findViewById(R.id.expanding_list_main);
        floatingActionButton = view.findViewById(R.id.floatingAdd);
        scrollView = view.findViewById(R.id.scrollview);
        progressBar = view.findViewById(R.id.main_progress);


        scrollView.getViewTreeObserver()
                .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (scrollView.getChildAt(0).getBottom()
                                <= (scrollView.getHeight() + scrollView.getScrollY())) {
                            progressBar.setVisibility(View.VISIBLE);
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

    private void createItems(List<Client> clients, int position) {

        for (int i = position; i < clients.size(); i++) {
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
            item.setIndicatorIconRes(R.drawable.ic_location_black);
            //It is possible to get any view inside the inflated layout. Let's set the text in the item

            try {
                ((TextView) item.findViewById(R.id.nom_prenom_marchand)).setText(client.getUtilisateur().getNom() + " " +
                        client.getUtilisateur().getPrenom());

                ((TextView) item.findViewById(R.id.numero_marchand)).setText(client.getUtilisateur().getTelephone());
            } catch (Exception e) {}

            try {
                getContrats(TokenManager.getInstance(getActivity()
                        .getSharedPreferences("prefs", MODE_PRIVATE))
                        .getToken(), client.getId(), item);
            } catch (Exception e) {

            }


        }
    }

    @SuppressLint("SetTextI18n")
    private void addSubItem(List<Contrat> contrats, ExpandingItem view){
        view.createSubItems(contrats.size());

        System.out.println("dsbus\n\n\n\n");
        System.out.println("dsbus\n\n\n\n");
        System.out.println("dsbus\n\n\n\n");
        System.out.println(contrats.size());
        System.out.println("dsbus\n\n\n\n");
        System.out.println("dsbus\n\n\n\n");
        System.out.println("dsbus\n\n\n\n");
        System.out.println(contrats.toString());
        System.out.println("dsbus\n\n\n\n");
        System.out.println("dsbus\n\n\n\n");
        System.out.println("dsbus\n\n\n\n");
        System.out.println("dsbus\n\n\n\n");

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

        Call<OutputPaginate> call;
        MarchandService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(MarchandService.class);
        call = service.getClients(MarchandMainActivity.getMarchand().getId(), FIRST_PAGE);
        call.enqueue(new Callback<OutputPaginate>() {
            @Override
            public void onResponse(Call<OutputPaginate> call, Response<OutputPaginate> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    System.out.println(response.body());

                        try {
                            CURRENT_PAGE = response.body().getMetaPaginate().getCurrentPage();
                            LAST_PAGE = response.body().getMetaPaginate().getLastPage();
                        } catch (Exception e) {}

                        Gson gson = new Gson();
                        Type listType = new TypeToken<ArrayList<Client>>(){}.getType();

                        clients = gson.fromJson(
                                gson.toJson(response.body().getObjects()), listType);

                        createItems(clients, 0);
                        progressBar.setVisibility(View.INVISIBLE);

                } else {
                    if (response.code() == 422) {
                        System.out.println(response.errorBody().source());
                        handleErrors(response.errorBody());
                    }
                    if (response.code() == 401) {
                        ApiError apiError = Utils.converErrors(response.errorBody());
                        Toast.makeText(getContext(), apiError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFailure(Call<OutputPaginate> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                //Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }


    private void getContrats(AccessToken accessToken, Long idClient, ExpandingItem view) {
        Call<OutputPaginate> call;
        MarchandService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(MarchandService.class);
        call = service.getConrattoClient(MarchandMainActivity.getMarchand().getId(), idClient, 1);

        call.enqueue(new Callback<OutputPaginate>() {
            @Override
            public void onResponse(Call<OutputPaginate> call, Response<OutputPaginate> response) {
                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    System.out.println(response.body());

                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<Contrat>>(){}.getType();

                    List<Contrat> contrats = gson.fromJson(
                            gson.toJson(response.body().getObjects()), listType);

                    addSubItem(contrats, view);

                } else {
                    if (response.code() == 422) {
                        System.out.println(response.errorBody().source());
                        handleErrors(response.errorBody());
                    }
                    if (response.code() == 401) {
                        ApiError apiError = Utils.converErrors(response.errorBody());
                        Toast.makeText(getContext(), apiError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OutputPaginate> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());

                //Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }


    private void handleErrors(ResponseBody response) {

        ApiError apiError = Utils.converErrors(response);

    }


    private void getNextClient(AccessToken accessToken, int page) {
        Call<OutputPaginate> call;
        MarchandService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(MarchandService.class);
        call = service.getClients(MarchandMainActivity.getMarchand().getId(), page);
        call.enqueue(new Callback<OutputPaginate>() {
            @Override
            public void onResponse(Call<OutputPaginate> call, Response<OutputPaginate> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    System.out.println(response.body());

                    CURRENT_PAGE = response.body().getMetaPaginate().getCurrentPage();
                    LAST_PAGE = response.body().getMetaPaginate().getLastPage();

                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<Client>>(){}.getType();

                    int size = clients.size();

                    clients.addAll(gson.fromJson(
                            gson.toJson(response.body().getObjects()), listType));

                    createItems(clients, size);
                    progressBar.setVisibility(View.INVISIBLE);

                } else {
                    if (response.code() == 422) {
                        System.out.println(response.errorBody().source());
                        handleErrors(response.errorBody());
                    }
                    if (response.code() == 401) {
                        ApiError apiError = Utils.converErrors(response.errorBody());
                        Toast.makeText(getContext(), apiError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<OutputPaginate> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                //Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }









}
