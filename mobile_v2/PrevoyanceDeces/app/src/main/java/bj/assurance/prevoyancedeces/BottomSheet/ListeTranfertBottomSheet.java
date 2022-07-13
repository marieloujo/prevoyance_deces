package bj.assurance.prevoyancedeces.BottomSheet;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.arthurivanets.bottomsheets.BaseBottomSheet;
import com.arthurivanets.bottomsheets.BottomSheet;
import com.arthurivanets.bottomsheets.config.BaseConfig;
import com.arthurivanets.bottomsheets.config.Config;

import androidx.annotation.NonNull;
import bj.assurance.prevoyancedeces.R;

public class ListeTranfertBottomSheet extends BaseBottomSheet {

    LinearLayout monCompte, autreMarchand;


    /**
     * The main constructor used for the initialization of the {@link }.
     *
     * @param hostActivity the activity the content view of which is to be used as a holder of the bottom sheet
     */
    public ListeTranfertBottomSheet(@NonNull Activity hostActivity) {
        this(hostActivity, new Config.Builder(hostActivity).build());
    }

    public ListeTranfertBottomSheet(@NonNull Activity hostActivity, @NonNull BaseConfig config) {
        super(hostActivity, config);
    }

    @NonNull
    @Override
    protected View onCreateSheetContentView(@NonNull Context context) {

        View view = LayoutInflater.from(context).inflate(R.layout.content_choice_transfert, this, false);


        monCompte = view.findViewById(R.id.monCompte);
        autreMarchand = view.findViewById(R.id.otherMarchand);

        monCompte.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheet transfert = new TransfertCommissionBottomSheet((Activity) context, true);
                transfert.show();
            }
        });

        autreMarchand.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheet transfert = new TransferToAnotherBottomSheet((Activity) context);
                transfert.show();
            }
        });


        return view;
    }
}
