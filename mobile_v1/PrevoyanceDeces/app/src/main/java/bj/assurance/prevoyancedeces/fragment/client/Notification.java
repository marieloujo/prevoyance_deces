package bj.assurance.prevoyancedeces.fragment.client;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.model.ConversationUser;
import bj.assurance.prevoyancedeces.model.pagination.OutputPaginate;
import bj.assurance.prevoyancedeces.utils.AccessToken;
import bj.assurance.prevoyancedeces.utils.ApiError;
import bj.assurance.prevoyancedeces.utils.Utils;
import bj.assurance.prevoyancedeces.adapter.NotificationAdapter;
import bj.assurance.prevoyancedeces.model.Message;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.UserService;
import bj.assurance.prevoyancedeces.retrofit.TokenManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

@SuppressLint("ValidFragment")
public class Notification extends Fragment {

    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;
    private ProgressBar progressBar;
    /*private LinearLayout linearLayout;
    private RelativeLayout contentError;*/

    private Long id;

    @SuppressLint("ValidFragment")
    public Notification(Long id) {
        this.id = id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        init(view);
        getMessageofUser(TokenManager.getInstance(getActivity().
                getSharedPreferences("prefs", MODE_PRIVATE)).
                getToken(), id);

        return view;
    }

    @SuppressLint("WrongConstant")
    public void init(View view) {
        recyclerView = view.findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        progressBar = view.findViewById(R.id.main_progress);

        /*linearLayout = view.findViewById(R.id.no_data_layout);
        contentError = view.findViewById(R.id.content_error);*/
    }

    private void getMessageofUser(AccessToken accessToken, Long id) {

        Call<JsonArray> call;
        UserService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(UserService.class);
        call = service.getNotification(id);
        call.enqueue(new Callback<JsonArray>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                System.out.println(response.code());

                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    getResponseContrat(response.body());
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    progressBar.setVisibility(View.INVISIBLE);

                    /*((TextView) contentError.findViewById(R.id.error_text))
                            .setText("Auncune notification");
                    ((Button) contentError.findViewById(R.id.retry)).setVisibility(View.INVISIBLE);*/
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.w(TAG, "onFailure: " + t.getMessage());
                System.out.println(t.getMessage());

                /*((TextView) contentError.findViewById(R.id.error_text))
                        .setText("Auncun notification");
                ((Button) contentError.findViewById(R.id.retry)).setVisibility(View.INVISIBLE);*/
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
        List<ConversationUser> notifications = null;

        try {
            error = jsonObject.getAsJsonObject();
            messageError = error.get("message").getAsString();

        }catch (Exception ignored) {}

        try {
            sucess = jsonObject.getAsJsonObject();
            message = sucess.get("message").getAsString();
        } catch (Exception ignored) {}

        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<ConversationUser>>() {}.getType();

            notifications = gson.fromJson(jsonObject, listType);
            System.out.println("notification: " + notifications.toString());

            notificationAdapter = new NotificationAdapter(getContext(), notifications);
            recyclerView.setAdapter(notificationAdapter);

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}
