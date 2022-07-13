package bj.assurance.assurancedeces.recyclerViewAdapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;

import static androidx.constraintlayout.motion.widget.MotionScene.TAG;


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


        String fileName = null;
        if (mediaFile.getScheme().equals("file")) {
            fileName = mediaFile.getLastPathSegment();
        } else {
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(mediaFile, new String[]{
                        MediaStore.Images.ImageColumns.DISPLAY_NAME
                }, null, null, null);

                if (cursor != null && cursor.moveToFirst()) {
                    fileName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME));
                    Log.d(TAG, "name is " + fileName);
                }
            } finally {

                if (cursor != null) {
                    cursor.close();
                }
            }
        }


        Glide.with(context)
                .load(mediaFile)
                .into(holder.fileThumbnail);


        holder.filePath.setText(fileName);


        File f = new File(mediaFile.getPath());
        double fileSize = 0.0;
        fileSize = (double) f.length();

        holder.fileSize.setText(String.valueOf(f.length()));


        if (fileSize < 1024) {

            holder.fileSize.setText(String.valueOf(fileSize).concat(" B"));

        } else if (fileSize > 1024 && fileSize < (1024 * 1024)) {

            holder.fileSize.setText(String.valueOf(Math.round((fileSize / 1024 * 100.0)) / 100.0).concat(" KB"));

        } else {

            holder.fileSize.setText(String.valueOf(Math.round((fileSize / (1024 * 1204) * 100.0)) / 100.0).concat(" MB"));

        }


       /* holder.fileMime.setText(context.getString(R.string.mime, mediaFile.getMimeType()));
        holder.fileSize.setText(context.getString(R.string.size, mediaFile.getSize()));
        holder.fileBucketName.setText(context.getString(R.string.bucketname, mediaFile.getBucketName()));
*/

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

        private final TextView filePath, fileSize;
        private ImageView fileThumbnail;




        ViewHolder(View view) {
            super(view);
            fileThumbnail = view.findViewById(R.id.file_thumbnail);

            filePath = view.findViewById(R.id.file_path);
            fileSize = view.findViewById(R.id.file_size);


        }



    }




}
