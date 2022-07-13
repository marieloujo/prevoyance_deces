package bj.assurance.assurancedeces.fragment.marchand;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.MarchandActivity;
import bj.assurance.assurancedeces.model.Document;
import bj.assurance.assurancedeces.model.Marchand;
import bj.assurance.assurancedeces.recyclerViewAdapter.FileListAdapter;
import gun0912.tedimagepicker.builder.TedImagePicker;
import gun0912.tedimagepicker.builder.listener.OnMultiSelectedListener;
import gun0912.tedimagepicker.builder.type.MediaType;


public class AddContratStepfour extends Fragment {


    private TextView jointFile;
    private RecyclerView recyclerView;

    private Button prev, next;

    private List<Uri> uris = new ArrayList<>();


    public AddContratStepfour() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_contrat_stepfour, container, false);


        init(view);


        return view;
    }





    private void init (View view) {


        findView(view);
        initValue();
        onClickListner();

    }



    private void  findView(View view) {

        jointFile = view.findViewById(R.id.joint_file);
        recyclerView = view.findViewById(R.id.recycler);


        prev = view.findViewById(R.id.annuler);
        next = view.findViewById(R.id.suivant);

    }




    @SuppressLint("WrongConstant")
    private void initValue() {

        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 3);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

    }




    private void onClickListner() {

        jointFile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

                TedImagePicker.with(Objects.requireNonNull(getContext()))
                        .mediaType(MediaType.IMAGE)
                        .title("Choisir une image")
                        .buttonText("Valider")
                        .startMultiImage(new OnMultiSelectedListener() {
                            @Override
                            public void onSelected(@NotNull List<? extends Uri> uriList) {

                                uris = (List<Uri>) uriList;

                                FileListAdapter fileListAdapter = new FileListAdapter(uris);
                                recyclerView.setAdapter(fileListAdapter);
                            }
                        });

            }
        });


        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<Document> documents = new ArrayList<>();

                for (Uri uri : uris) {

                    documents.add(new Document(
                            convertTobase_64(uri)
                    ));
                }

                MarchandActivity.getContrat().setDocuments(documents);

                replaceFragment(new AddContratStepfive(), "Enregistrement de contrat");


            }
        });


    }


    private void replaceFragment(Fragment fragment, String title) {


        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();


        MarchandActivity.getFrameTitle().setText(title);

    }




    private String convertTobase_64(Uri uri) {


        String encodeString = "";

        try {
            Bitmap mBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);

            String extension = uri.toString().substring(uri.toString().lastIndexOf(".") + 1);

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
