package bj.assurance.assurancedeces.recyclerViewAdapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;



public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.ViewHolder> {

    private List<Uri> mediaFiles = new ArrayList<>();



    public FileListAdapter(List<Uri> mediaFiles) {
        this.mediaFiles = mediaFiles;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_file, parent, false);


        return new ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Uri mediaFile = mediaFiles.get(position);
        Context context = holder.itemView.getContext();

        Glide.with(context)
                .load(mediaFile)
                .into(holder.fileThumbnail);


        /*if (mediaFile.getMediaType() == MediaFile.TYPE_IMAGE
                || mediaFile.getMediaType() == MediaFile.TYPE_VIDEO) {
            Glide.with(context)
                    .load(mediaFile.getUri())
                    .into(holder.fileThumbnail);
        } else if (mediaFile.getMediaType() == MediaFile.TYPE_AUDIO) {
            Glide.with(context)
                    .load(mediaFile.getThumbnail())
                    .placeholder(R.drawable.ic_audio)
                    .into(holder.fileThumbnail);
        } else {
            holder.fileThumbnail.setImageResource(R.drawable.ic_file);
        }*/

    }

    @Override
    public int getItemCount() {
        return mediaFiles.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView fileThumbnail;

        ViewHolder(View view) {
            super(view);
            fileThumbnail = view.findViewById(R.id.file_thumbnail);
        }
    }

}
