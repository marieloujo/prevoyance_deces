package bj.assurance.prevoyancedeces.fragment.client;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.gjiazhe.wavesidebar.WaveSideBar;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.activity.Main2Activity;
import bj.assurance.prevoyancedeces.activity.MarchandMainActivity;

public class Marchands extends Fragment {

    private ExpandingList mExpandingList;

    public Marchands() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_marchands, container, false);

        // MarchandMainActivity.getFloatingActionButton().setVisibility(View.VISIBLE);

        // init(view);

        mExpandingList = view.findViewById(R.id.expanding_list_main);
        createItems();

        return view;
    }

    public void init(View view) {
        Main2Activity.getTextTitle().setVisibility(View.VISIBLE);
        Main2Activity.getBackTitle().setVisibility(View.INVISIBLE);

        Main2Activity.getBackTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });

        LinearLayout linearLayout = view.findViewById(R.id.item_marchand);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main, new DetailMarchand()).commit();
            }
        });
    }

    private void createItems() {
        addItem("John", new String[]{"House", "Boat", "Candy", "Collection", "Sport", "Ball", "Head"});
        addItem("Mary", new String[]{"Dog", "Horse", "Boat"});
        addItem("Ana", new String[]{"Cat"});
        addItem("Peter", new String[]{"Parrot", "Elephant", "Coffee"});
        addItem("Joseph", new String[]{});
        addItem("Paul", new String[]{"Golf", "Football"});
        addItem("Larry", new String[]{"Ferrari", "Mazda", "Honda", "Toyota", "Fiat"});
        addItem("Moe", new String[]{"Beans", "Rice", "Meat"});
        addItem("Bart", new String[]{"Hamburger", "Ice cream", "Candy"});
    }

    private void addItem(String title, String[] subItems) {
        //Let's create an item with R.layout.expanding_layout
        final ExpandingItem item = mExpandingList.createNewItem(R.layout.expanding_layout);

        //If item creation is successful, let's configure it
        if (item != null) {
            item.setIndicatorColorRes(R.color.gery_inactive);
            item.setIndicatorIconRes(R.drawable.ic_location_black);
            //It is possible to get any view inside the inflated layout. Let's set the text in the item

            // ((TextView) item.findViewById(R.id.title)).setText(title);

            //We can create items in batch.
            item.createSubItems(subItems.length);
            for (int i = 0; i < item.getSubItemsCount(); i++) {
                //Let's get the created sub item by its index
                final View view = item.getSubItemView(i);

                //Let's set some values in
                configureSubItem(item, view, subItems[i]);
            }

            /*item.findViewById(R.id.).setOnClickListener(new View.OnClickListener() {<
                @Override
                public void onClick(View v) {
                    showInsertDialog(new OnItemCreated() {
                        @Override
                        public void itemCreated(String title) {
                            View newSubItem = item.createSubItem();
                            configureSubItem(item, newSubItem, title);
                        }
                    });
                }
            });

            item.findViewById(R.id.remove_item).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mExpandingList.removeItem(item);
                }
            });*/
        }
    }

    private void configureSubItem(final ExpandingItem item, final View view, String subTitle) {
        //((TextView) view.findViewById(R.id.sub_title)).setText(subTitle);
        /*view.findViewById(R.id.remove_sub_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.removeSubItem(view);
            }
        });*/
    }

    /*private void showInsertDialog(final OnItemCreated positive) {
        final EditText text = new EditText(getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(text);
        builder.setTitle(R.string.enter_title);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                positive.itemCreated(text.getText().toString());
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.show();
    }*/

    interface OnItemCreated {
        void itemCreated(String title);
    }

}
