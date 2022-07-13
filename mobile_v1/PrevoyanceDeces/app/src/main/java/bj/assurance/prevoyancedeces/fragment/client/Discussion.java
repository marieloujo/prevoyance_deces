package bj.assurance.prevoyancedeces.fragment.client;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kinda.alert.KAlertDialog;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.adapter.ListeSouscriptionAdpter;
import bj.assurance.prevoyancedeces.model.Contrat;
import bj.assurance.prevoyancedeces.model.ConversationUser;
import bj.assurance.prevoyancedeces.model.pagination.OutputPaginate;
import bj.assurance.prevoyancedeces.retrofit.TokenManager;
import bj.assurance.prevoyancedeces.utils.AccessToken;
import bj.assurance.prevoyancedeces.utils.ApiError;
import bj.assurance.prevoyancedeces.utils.Utils;
import bj.assurance.prevoyancedeces.adapter.DiscussionAdapter;
import bj.assurance.prevoyancedeces.model.Message;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.UserService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

@SuppressLint("ValidFragment")
public class Discussion extends Fragment {

    private RecyclerView recyclerView;
    private DiscussionAdapter discussionAdapter;
    private LinearLayout linearLayout;
    private TextView textView;

    private Long id;

    @SuppressLint("ValidFragment")
    public Discussion(Long id) {
        this.id = id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //MarchandMainActivity.getFloatingActionButton().setVisibility(View.INVISIBLE);

        View view = inflater.inflate(R.layout.fragment_discussion, container, false);

        init(view);
        getMessageofUser(TokenManager.getInstance(getActivity().
                getSharedPreferences("prefs", MODE_PRIVATE)).
                getToken());

        return view;
    }

    @SuppressLint({"WrongConstant", "SetTextI18n"})
    public void init(View view) {
        recyclerView = view.findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setVisibility(View.VISIBLE);

        linearLayout = view.findViewById(R.id.no_data_layout);
        textView = view.findViewById(R.id.no_data);

        linearLayout.setVisibility(View.INVISIBLE);
        textView.setText("Vous n'avez encore aucune discussion en cours");
    }

    public void getMessageofUser(AccessToken accessToken) {

        Call<JsonArray> call;
        UserService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(UserService.class);
        call = service.getMessageofUser(id);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                Log.w(TAG, "onresponse: " + response.body());
                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    getResponseContrat(response.body());
                } else {
                    Log.w(TAG, "onError: " + response.code());
                }

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void handleErrors(ResponseBody response) {

        ApiError apiError = Utils.converErrors(response);

    }

    private void getResponseContrat(JsonArray jsonObject) {
        JsonObject error = null, sucess = null;
        String messageError = null, message = null;
        OutputPaginate outputPaginate = null;
        String string = null, s = null;
        List<ConversationUser> conversationUsers = null;

        try {
            error = jsonObject.getAsJsonObject();
            messageError = error.get("message").getAsString();
            Toast.makeText(getContext(), messageError, Toast.LENGTH_LONG).show();
        }catch (Exception ignored) {}

        try {
            Toast.makeText(getContext(), messageError, Toast.LENGTH_LONG).show();
            sucess = jsonObject.getAsJsonObject();
            message = sucess.get("message").getAsString();
        } catch (Exception ignored) {}

        try {
            Type listType = new TypeToken<List<ConversationUser>>() {}.getType();
            conversationUsers = new Gson().fromJson(jsonObject, listType);
            discussionAdapter = new DiscussionAdapter(getContext(), conversationUsers);
            recyclerView.setAdapter(discussionAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        }

        /*try {
            outputPaginate = new Gson().fromJson(jsonObject, OutputPaginate.class);
        } catch (Exception ignored) {
        }

        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Contrat>>() {}.getType();

            assert outputPaginate != null;
            string = gson.toJson(outputPaginate.getObjects());
            conversationUsers = gson.fromJson(string, listType);
        }catch (Exception e) {
            e.printStackTrace();
        }*/

}
