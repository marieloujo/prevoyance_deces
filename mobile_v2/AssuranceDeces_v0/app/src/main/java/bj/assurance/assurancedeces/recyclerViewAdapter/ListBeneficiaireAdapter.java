package bj.assurance.assurancedeces.recyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.shagi.materialdatepicker.date.DatePickerFragmentDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.recyclerViewHolder.ListBeneficiaireViewHolder;
import bj.assurance.assurancedeces.utils.ContratFormUtils;


public class ListBeneficiaireAdapter extends RecyclerView.Adapter<ListBeneficiaireViewHolder> {


    private Context context;
    private List<String> strings;

    private DatePickerFragmentDialog datePickerFragmentDialog;

    private ContratFormUtils contratFormUtils;




    public ListBeneficiaireAdapter(Context context, List<String> strings) {
        this.context = context;
        this.strings = strings;

        contratFormUtils = new ContratFormUtils(context);

    }




    @NonNull
    @Override
    public ListBeneficiaireViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ListBeneficiaireViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_form_beneficier, parent, false)
        );

    }




    @Override
    public void onBindViewHolder(@NonNull ListBeneficiaireViewHolder holder, int position) {


        contratFormUtils.makeCommuneList(holder.getEtCommune());
        contratFormUtils.makePhoneIdentityList(holder.getPhoneIdentity());
        contratFormUtils.makeSexeSpinnerList(holder.getEtSexe());
        contratFormUtils.makeSmSpinnerList(holder.getEtSituationMatrimoniale());
        contratFormUtils.makeQualificationList(holder.getEtQualification());

        makeDatePicker(holder.getEtDateNaissance());



        holder.getEtDateNaissance().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                datePickerFragmentDialog.show(((FragmentActivity)context).getSupportFragmentManager(), "tag");

            }
        });


    }



    @Override
    public int getItemCount() {
        return strings.size();
    }




    private void makeDatePicker(final EditText editText) {


        DatePickerFragmentDialog.OnDateSetListener onDateSetListener = new DatePickerFragmentDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerFragmentDialog view, int year, int monthOfYear, int dayOfMonth) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);

                SimpleDateFormat simpleDateFormat = new  SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);
                simpleDateFormat.format(calendar.getTime());

                editText.setText(simpleDateFormat.format(calendar.getTime()));

                datePickerFragmentDialog.dismiss();

            }
        };

        datePickerFragmentDialog = DatePickerFragmentDialog.newInstance(onDateSetListener, 1999,11, 4);


        int minYear = (new Date().getYear() - 74) + 1900 ;
        int maxYear = (new Date().getYear() - 18) + 1900 ;


        datePickerFragmentDialog.setYearRange(minYear, maxYear);

    }




}
