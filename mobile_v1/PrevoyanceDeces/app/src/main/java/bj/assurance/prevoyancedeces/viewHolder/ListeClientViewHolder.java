package bj.assurance.prevoyancedeces.viewHolder;

import android.view.View;
import android.widget.TextView;

import com.skydoves.expandablelayout.ExpandableLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;

public class ListeClientViewHolder extends RecyclerView.ViewHolder {

    private ExpandableLayout expandableLayout;
    private View parent, child;
    private TextView nomPrenomClient, telephoneClient, referenceContat, nomAssure, prenomAssure, statusPaiment, dateDernierPaiment,
            portefeuilAssurance;

    public ListeClientViewHolder(@NonNull View itemView) {
        super(itemView);

        expandableLayout = itemView.findViewById(R.id.expandable);
        parent = expandableLayout.getParentLayout();

        nomPrenomClient = parent.findViewById(R.id.nom_prenom_marchand);
        telephoneClient = parent.findViewById(R.id.numero_marchand);

        child = expandableLayout.getSecondLayout();

        nomAssure = child.findViewById(R.id.nom_assure);
        referenceContat = parent.findViewById(R.id.reference_contat);
        prenomAssure = parent.findViewById(R.id.prenom_assure);
        statusPaiment = parent.findViewById(R.id.status_paiment);
        dateDernierPaiment = child.findViewById(R.id.date_dernier_paiment);
        portefeuilAssurance = child.findViewById(R.id.portefeuil_assurance);

    }

    public ExpandableLayout getExpandableLayout() {
        return expandableLayout;
    }

    public void setExpandableLayout(ExpandableLayout expandableLayout) {
        this.expandableLayout = expandableLayout;
    }

    public View getParent() {
        return parent;
    }

    public void setParent(View parent) {
        this.parent = parent;
    }

    public View getChild() {
        return child;
    }

    public void setChild(View child) {
        this.child = child;
    }

    public TextView getNomPrenomClient() {
        return nomPrenomClient;
    }

    public void setNomPrenomClient(TextView nomPrenomClient) {
        this.nomPrenomClient = nomPrenomClient;
    }

    public TextView getTelephoneClient() {
        return telephoneClient;
    }

    public void setTelephoneClient(TextView telephoneClient) {
        this.telephoneClient = telephoneClient;
    }

    public TextView getReferenceContat() {
        return referenceContat;
    }

    public void setReferenceContat(TextView referenceContat) {
        this.referenceContat = referenceContat;
    }

    public TextView getNomAssure() {
        return nomAssure;
    }

    public void setNomAssure(TextView nomAssure) {
        this.nomAssure = nomAssure;
    }

    public TextView getPrenomAssure() {
        return prenomAssure;
    }

    public void setPrenomAssure(TextView prenomAssure) {
        this.prenomAssure = prenomAssure;
    }

    public TextView getStatusPaiment() {
        return statusPaiment;
    }

    public void setStatusPaiment(TextView statusPaiment) {
        this.statusPaiment = statusPaiment;
    }

    public TextView getDateDernierPaiment() {
        return dateDernierPaiment;
    }

    public void setDateDernierPaiment(TextView dateDernierPaiment) {
        this.dateDernierPaiment = dateDernierPaiment;
    }

    public TextView getPortefeuilAssurance() {
        return portefeuilAssurance;
    }

    public void setPortefeuilAssurance(TextView portefeuilAssurance) {
        this.portefeuilAssurance = portefeuilAssurance;
    }
}
