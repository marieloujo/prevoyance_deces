package bj.assurance.prevoyancedeces.fragment.marchand;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.fragment.app.FragmentManager;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.activity.MarchandMainActivity;


public class ListeClients extends Fragment {

    ExpandingList mExpandingList;
    private FloatingActionButton floatingActionButton;

    public ListeClients() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_liste_clients, container, false);

        init(view);
        createItems();
        floatingButtonaddClient();

        return view;
    }

    public void init(View view) {
        mExpandingList = view.findViewById(R.id.expanding_list_main);
        floatingActionButton = view.findViewById(R.id.floatingAdd);
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
        final ExpandingItem item = mExpandingList.createNewItem(R.layout.expanding_layout_client);

        //If item creation is successful, let's configure it
        if (item != null) {
            item.setIndicatorColorRes(R.color.gery_inactive);
            item.setIndicatorIconRes(R.drawable.ic_location_black);
            //It is possible to get any view inside the inflated layout. Let's set the text in the item

            // ((TextView) item.findViewById(R.id.title)).setText(title);

            //We can create items in batch.
            item.createSubItems(subItems.length);
        }
    }

    private void floatingButtonaddClient() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new AddClientStepOne(), "Ajouter un client");
            }
        });
    }

    private void replaceFragment(Fragment fragment, String string) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main_marchand, fragment).commit();

        MarchandMainActivity.getTitleFrame().setText(string);
    }
}
