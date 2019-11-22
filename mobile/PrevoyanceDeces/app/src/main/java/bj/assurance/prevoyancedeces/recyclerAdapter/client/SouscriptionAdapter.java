package bj.assurance.prevoyancedeces.recyclerAdapter.client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.viewHolder.client.SouscriptionViewHolder;

public class SouscriptionAdapter extends RecyclerView.Adapter<SouscriptionViewHolder> {

    private Context context;

    @NonNull
    @Override
    public SouscriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SouscriptionViewHolder(LayoutInflater.from(context).inflate(R.layout.item_souscription, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SouscriptionViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
