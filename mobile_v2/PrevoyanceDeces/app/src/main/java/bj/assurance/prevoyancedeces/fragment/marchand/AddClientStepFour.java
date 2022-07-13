package bj.assurance.prevoyancedeces.fragment.marchand;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;
import com.kinda.alert.KAlertDialog;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.activity.MarchandMainActivity;
import bj.assurance.prevoyancedeces.adapter.FileListAdapter;
import bj.assurance.prevoyancedeces.fragment.client.Marchands;
import bj.assurance.prevoyancedeces.model.Contrat;
import bj.assurance.prevoyancedeces.model.Document;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.ClientService;
import bj.assurance.prevoyancedeces.utils.AccessToken;
import bj.assurance.prevoyancedeces.utils.ApiError;
import bj.assurance.prevoyancedeces.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static androidx.media.MediaBrowserServiceCompat.RESULT_OK;


public class AddClientStepFour extends Fragment {

    private Button next, cancel;
    private TextView jointFile;

    private final static int FILE_REQUEST_CODE = 1;
    private ArrayList<MediaFile> mediaFiles = new ArrayList<>();

    private RecyclerView recyclerView;
    private FileListAdapter fileListAdapter;

    public AddClientStepFour() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_addclient_stepfour, container, false);

        init(view);
        setOnclickListener();


        return view;
    }

    @SuppressLint("WrongConstant")
    public void init(View view) {
        cancel = view.findViewById(R.id.annuler);
        next = view.findViewById(R.id.suivant);
        jointFile = view.findViewById(R.id.joint_file);

        recyclerView = view.findViewById(R.id.recycler);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 3);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        fileListAdapter = new FileListAdapter(mediaFiles);
        recyclerView.setAdapter(fileListAdapter);
    }

    private void setOnclickListener() {
        jointFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FilePickerActivity.class);
                intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                        .setCheckPermission(true)
                        .setSelectedMediaFiles(mediaFiles)
                        .enableImageCapture(true)
                        .setShowVideos(false)
                        .setSkipZeroSizeFiles(true)
                        .setMaxSelection(10)
                        .build());
                startActivityForResult(intent, FILE_REQUEST_CODE);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaFiles.isEmpty()) {

                    replaceFraglent(new AddClientStepThree());

                } else {
                    new KAlertDialog(getContext(), KAlertDialog.WARNING_TYPE)
                        .setTitleText("Confirmation")
                        .setContentText("Voulez vous vraiment annuler ?? les fichiers sélectionnées seront effacées.")
                        .setConfirmText("Oui")
                        .setCancelText("Non")
                        .showCancelButton(true)
                        .setCancelClickListener(new KAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(KAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .setConfirmClickListener(new KAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(KAlertDialog sDialog) {
                                sDialog.dismiss();
                                replaceFraglent(new AddClientStepThree(true));
                            }
                        })
                        .show();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Document> documents = new ArrayList<>();

                for (int i =0; i < mediaFiles.size(); i++) {
                    documents.add(new Document(convertTobase_64(mediaFiles.get(i).getUri())));
                }

                //MarchandMainActivity.getContrat().setDocuments(documents);

                //System.out.println(MarchandMainActivity.getContrat().getDocuments().get(MarchandMainActivity.getContrat().getDocuments().size() -1));
                replaceFraglent(new AddClient());
            }
        });

    }

    private void replaceFraglent(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main_marchand, fragment).commit();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_REQUEST_CODE) {
            mediaFiles.clear();
            mediaFiles.addAll(data.<MediaFile>getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES));
            System.out.println("files " + mediaFiles.toString());
            fileListAdapter.notifyDataSetChanged();
        }
    }


    private String convertTobase_64(Uri uri) {


        String encodeString = "";

        try {
            Bitmap  mBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);

            String extension = uri.getPath();

            encodeString = "data:image/" + extension + ";base64,";

            try
            {
                ByteArrayOutputStream baos = null;
                byte[] baat = null;

                baos = new ByteArrayOutputStream();
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                baat = baos.toByteArray();
                encodeString += Base64.encodeToString(baat, Base64.DEFAULT);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return  encodeString;
    }


}
