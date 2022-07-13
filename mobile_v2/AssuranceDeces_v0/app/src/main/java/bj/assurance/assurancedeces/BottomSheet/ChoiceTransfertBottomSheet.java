package bj.assurance.assurancedeces.BottomSheet;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.arthurivanets.bottomsheets.BaseBottomSheet;
import com.arthurivanets.bottomsheets.BottomSheet;
import com.arthurivanets.bottomsheets.config.BaseConfig;
import com.arthurivanets.bottomsheets.config.Config;

import androidx.annotation.NonNull;
import bj.assurance.assurancedeces.R;

public class ChoiceTransfertBottomSheet extends BaseBottomSheet {



    private LinearLayout monCompte, otherMarchand;



    public ChoiceTransfertBottomSheet(@NonNull Activity hostActivity) {
        this(hostActivity, new Config.Builder(hostActivity).build());
    }



    public ChoiceTransfertBottomSheet(@NonNull Activity hostActivity, @NonNull BaseConfig config) {
        super(hostActivity, config);
    }



    @NonNull
    @Override
    protected View onCreateSheetContentView(@NonNull Context context) {


        View view = LayoutInflater.from(context).inflate(R.layout.content_choice_transfert, this, false);


        init(view);

        return view;
    }



    private void init(View view) {

        findView(view);
        onClickListener();

    }



    private void findView(View view) {

        monCompte = view.findViewById(R.id.monCompte);
        otherMarchand = view.findViewById(R.id.otherMarchand);

    }



    private void onClickListener() {


        monCompte.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                BottomSheet bottomSheet = new TransferCommissiontoCreditVirtuelle((Activity) getContext());
                bottomSheet.show();

            }
        });


        otherMarchand.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                BottomSheet bottomSheet = new TransfertCommissiontoMarchand((Activity) getContext());
                bottomSheet.show();

            }
        });


    }

}
