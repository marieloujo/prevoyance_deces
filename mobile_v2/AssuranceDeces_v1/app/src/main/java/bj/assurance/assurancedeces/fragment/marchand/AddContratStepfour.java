package bj.assurance.assurancedeces.fragment.marchand;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.MarchandActivity;
import bj.assurance.assurancedeces.model.Document;
import bj.assurance.assurancedeces.recyclerViewAdapter.FileListAdapter;
import bj.assurance.assurancedeces.utils.ShowDialog;
import gun0912.tedimagepicker.builder.TedImagePicker;
import gun0912.tedimagepicker.builder.listener.OnMultiSelectedListener;
import gun0912.tedimagepicker.builder.type.MediaType;


public class AddContratStepfour extends Fragment {


    private TextView jointFile;
    private RecyclerView recyclerView;

    private Button prev, next;

    private List<Uri> uris = new ArrayList<>();


    private ShowDialog showDialog ;

    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int READ_STORAGE_CODE = 1;



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

        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 1);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        showDialog = new ShowDialog(getContext());

    }




    private void onClickListner() {

        jointFile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {


                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {


                    if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()),
                            Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED ) {


                        satartImahePicker();


                    } else {


                        ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);


                    }

                } else {

                    ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE_CODE);

                }

            }
        });


        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((FragmentActivity) getContext()).onBackPressed();

            }
        });



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (uris.isEmpty()) {

                    showDialog.getAlertDialog().dismiss();
                    showDialog.showWarningDialog("", "Veuillez ajouter des pièces");

                } else {

                    try {

                        List<Document> documents = convertTobase_64(uris);
                        MarchandActivity.getContrat().setDocuments(documents);

                        replaceFragment(new AddContratStepfive(), "Enregistrement de contrat");

                    } catch (Exception e) {

                        e.printStackTrace();

                    }
                }

            }
        });


    }





    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void satartImahePicker() {

        TedImagePicker.with(Objects.requireNonNull(getContext()))
                .mediaType(MediaType.IMAGE)
                .title("Choisir une image")
                .buttonText("Valider")
                .min(1, "Vous devez choisir au moins une image")
                .max(5, "Vous ne pouvez pas choisir plus de cinq image")
                .startMultiImage(new OnMultiSelectedListener() {
                    @Override
                    public void onSelected(@NotNull List<? extends Uri> uriList) {

                        uris = (List<Uri>) uriList;

                        FileListAdapter fileListAdapter = new FileListAdapter(uris);
                        recyclerView.setAdapter(fileListAdapter);
                    }
                });

    }





    private void replaceFragment(Fragment fragment, String title) {


        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).addToBackStack(null).commit();


        MarchandActivity.getFrameTitle().setText(title);

    }




    private void compressedImage(List<Uri> uriList) {



        for (int i = 0; i < uriList.size(); i++) {


            File f = new File(uriList.get(i).getPath());
            double fileSize = 0.0;
            fileSize = (double) f.length();


            if (fileSize < 1024 || (fileSize > 1024 && fileSize < (1024 * 1024))) {

                uris.add(uriList.get(i));

            } else {

                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                    Bitmap mBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uriList.get(i));
                    mBitmap.compress(Bitmap.CompressFormat.PNG, 80,byteArrayOutputStream);

                    byte[] BYTE = byteArrayOutputStream.toByteArray();
                    Bitmap bitmap2 = BitmapFactory.decodeByteArray(BYTE,0,BYTE.length);


                    String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmap2, "Title", null);
                    uris.add(Uri.parse(path));


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        }



    }





    private List<Document> convertTobase_64(List<Uri> uris) {

        List<Document> documents = new ArrayList<>();

        String encodeString = "";

        for (int i = 0; i < uris.size(); i++) {

            try {
                Bitmap mBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uris.get(i));

                String extension = uris.get(i).toString().substring(uris.get(i).toString().lastIndexOf(".") + 1);

                encodeString = "data:image/" + extension + ";base64,";

                try
                {
                    ByteArrayOutputStream baos = null;
                    byte[] baat = null;

                    baos = new ByteArrayOutputStream();
                    mBitmap.compress(Bitmap.CompressFormat.JPEG, 40, baos);
                    baat = baos.toByteArray();
                    encodeString += Base64.encodeToString(baat, Base64.DEFAULT);

                    documents.add(new Document(encodeString));

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return  documents;
    }




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == MY_CAMERA_REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                try {

                    satartImahePicker();

                } catch (Exception ignored) {}

            } else {

                try {

                    Toast.makeText(getContext(), "Permission non accordé", Toast.LENGTH_LONG).show();

                } catch (Exception ignored) {}

            }

        } else {

            if (requestCode == READ_STORAGE_CODE) {


                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    try {

                        Toast.makeText(getContext(), "Permission accordé", Toast.LENGTH_LONG).show();

                    } catch (Exception ignored) {}

                } else {

                    try {

                        Toast.makeText(getContext(), "Permission non accordé", Toast.LENGTH_LONG).show();
                    } catch (Exception ignored) {}

                }

            }

        }

    }



}
