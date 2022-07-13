package bj.assurance.assurancedeces.recyclerViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import androidx.cardview.widget.CardView;
import bj.assurance.assurancedeces.R;



public class ListExpandViewHolder extends ParentViewHolder {



    private TextView textView;
    private ImageView imageView;

    private CardView cardView;


    public ListExpandViewHolder(View itemView) {
        super(itemView);



        textView = itemView.findViewById(R.id.title_expande);
        imageView = itemView.findViewById(R.id.image_expande);

        cardView = itemView.findViewById(R.id.content_collapse);


    }







    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }


    public CardView getCardView() {
        return cardView;
    }
}
