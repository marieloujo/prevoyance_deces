package bj.assurance.assurancedeces.recyclerViewHolder;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;
import br.com.sapereaude.maskedEditText.MaskedEditText;



public class ListBeneficiaireViewHolder extends RecyclerView.ViewHolder {


    
    private TextView tvNom, tvPrenoms, tvEmail, tvAdresse, tvSituationMatrimoniale, tvSexe, tvDateNaissance,
            tvProfession, tvEmployeur, tvCommune, tvTelephone, tvQualification;

    private EditText etNom, etPrenoms, etAdresse, etProfession, etEmployeur, etDateNaissance, etEmail;

    @SuppressLint("StaticFieldLeak")
    static private Spinner etSituationMatrimoniale, etSexe, etCommune, phoneIdentity, etQualification;
    static private MaskedEditText etTelephone;
    
    
    
    
    public ListBeneficiaireViewHolder(@NonNull View itemView) {
        super(itemView);

        findView(itemView);

    }



    private void findView(View view) {

        etNom = view.findViewById(R.id.etNomBeneficiaire);
        etPrenoms = view.findViewById(R.id.etPrenomBeneficiaire);
        etEmail = view.findViewById(R.id.et_email);
        etAdresse = view.findViewById(R.id.etAdresseBeneficiaire);
        etSituationMatrimoniale = view.findViewById(R.id.etSituationMatrimonialeBeneficiaire);
        etSexe = view.findViewById(R.id.etSexeBeneficiaire);
        etDateNaissance = view.findViewById(R.id.dateEditText);
        etEmployeur = view.findViewById(R.id.etEmployeur);
        etProfession = view.findViewById(R.id.etProfession);
        etTelephone = view.findViewById(R.id.etTelephoneBeneficiaire);
        etCommune = view.findViewById(R.id.etCommuneBeneficiaire);
        phoneIdentity = view.findViewById(R.id.phoneIdentity);
        etQualification = view.findViewById(R.id.etQualification);


        tvNom = view.findViewById(R.id.tvNomBeneficiaire);
        tvPrenoms = view.findViewById(R.id.tvPrenomBeneficiaire);
        tvEmail = view.findViewById(R.id.tvEmailBeneficiaire);
        tvAdresse = view.findViewById(R.id.tvAdresseDomicileBeneficiaire);
        tvSituationMatrimoniale = view.findViewById(R.id.tvSituationMatrimonialeBeneficiaire);
        tvSexe = view.findViewById(R.id.tvetSexeBeneficiaire);
        tvDateNaissance = view.findViewById(R.id.tvDateNaissanceBeneficiaire);
        tvCommune = view.findViewById(R.id.tvCommuneBeneficiaire);
        tvProfession = view.findViewById(R.id.tvProfessionBeneficiaire);
        tvEmployeur = view.findViewById(R.id.tvEmployeurBeneficiaire);
        tvTelephone = view.findViewById(R.id.tvTelephoneBeneficiaire);
        tvQualification = view.findViewById(R.id.tvQualification);

    }






    public TextView getTvNom() {
        return tvNom;
    }

    public void setTvNom(TextView tvNom) {
        this.tvNom = tvNom;
    }


    public TextView getTvPrenoms() {
        return tvPrenoms;
    }

    public void setTvPrenoms(TextView tvPrenoms) {
        this.tvPrenoms = tvPrenoms;
    }

    public TextView getTvEmail() {
        return tvEmail;
    }

    public void setTvEmail(TextView tvEmail) {
        this.tvEmail = tvEmail;
    }

    public TextView getTvAdresse() {
        return tvAdresse;
    }

    public void setTvAdresse(TextView tvAdresse) {
        this.tvAdresse = tvAdresse;
    }

    public TextView getTvSituationMatrimoniale() {
        return tvSituationMatrimoniale;
    }

    public void setTvSituationMatrimoniale(TextView tvSituationMatrimoniale) {
        this.tvSituationMatrimoniale = tvSituationMatrimoniale;
    }

    public TextView getTvSexe() {
        return tvSexe;
    }

    public void setTvSexe(TextView tvSexe) {
        this.tvSexe = tvSexe;
    }

    public TextView getTvDateNaissance() {
        return tvDateNaissance;
    }

    public void setTvDateNaissance(TextView tvDateNaissance) {
        this.tvDateNaissance = tvDateNaissance;
    }

    public TextView getTvProfession() {
        return tvProfession;
    }

    public void setTvProfession(TextView tvProfession) {
        this.tvProfession = tvProfession;
    }

    public TextView getTvEmployeur() {
        return tvEmployeur;
    }

    public void setTvEmployeur(TextView tvEmployeur) {
        this.tvEmployeur = tvEmployeur;
    }

    public TextView getTvCommune() {
        return tvCommune;
    }

    public void setTvCommune(TextView tvCommune) {
        this.tvCommune = tvCommune;
    }

    public TextView getTvTelephone() {
        return tvTelephone;
    }

    public void setTvTelephone(TextView tvTelephone) {
        this.tvTelephone = tvTelephone;
    }

    public TextView getTvQualification() {
        return tvQualification;
    }

    public void setTvQualification(TextView tvQualification) {
        this.tvQualification = tvQualification;
    }

    public EditText getEtNom() {
        return etNom;
    }

    public void setEtNom(EditText etNom) {
        this.etNom = etNom;
    }

    public EditText getEtPrenoms() {
        return etPrenoms;
    }

    public void setEtPrenoms(EditText etPrenoms) {
        this.etPrenoms = etPrenoms;
    }

    public EditText getEtAdresse() {
        return etAdresse;
    }

    public void setEtAdresse(EditText etAdresse) {
        this.etAdresse = etAdresse;
    }

    public EditText getEtProfession() {
        return etProfession;
    }

    public void setEtProfession(EditText etProfession) {
        this.etProfession = etProfession;
    }

    public EditText getEtEmployeur() {
        return etEmployeur;
    }

    public void setEtEmployeur(EditText etEmployeur) {
        this.etEmployeur = etEmployeur;
    }

    public EditText getEtDateNaissance() {
        return etDateNaissance;
    }

    public void setEtDateNaissance(EditText etDateNaissance) {
        this.etDateNaissance = etDateNaissance;
    }

    public EditText getEtEmail() {
        return etEmail;
    }

    public void setEtEmail(EditText etEmail) {
        this.etEmail = etEmail;
    }

    public Spinner getEtSituationMatrimoniale() {
        return etSituationMatrimoniale;
    }

    public void setEtSituationMatrimoniale(Spinner etSituationMatrimoniale) {
        ListBeneficiaireViewHolder.etSituationMatrimoniale = etSituationMatrimoniale;
    }

    public Spinner getEtSexe() {
        return etSexe;
    }

    public void setEtSexe(Spinner etSexe) {
        ListBeneficiaireViewHolder.etSexe = etSexe;
    }

    public Spinner getEtCommune() {
        return etCommune;
    }

    public void setEtCommune(Spinner etCommune) {
        ListBeneficiaireViewHolder.etCommune = etCommune;
    }

    public Spinner getPhoneIdentity() {
        return phoneIdentity;
    }

    public void setPhoneIdentity(Spinner phoneIdentity) {
        ListBeneficiaireViewHolder.phoneIdentity = phoneIdentity;
    }

    public Spinner getEtQualification() {
        return etQualification;
    }

    public void setEtQualification(Spinner etQualification) {
        ListBeneficiaireViewHolder.etQualification = etQualification;
    }

    public MaskedEditText getEtTelephone() {
        return etTelephone;
    }

    public void setEtTelephone(MaskedEditText etTelephone) {
        ListBeneficiaireViewHolder.etTelephone = etTelephone;
    }
}
