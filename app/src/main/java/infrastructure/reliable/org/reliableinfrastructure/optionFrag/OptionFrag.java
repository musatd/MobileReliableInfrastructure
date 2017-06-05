package infrastructure.reliable.org.reliableinfrastructure.optionFrag;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import infrastructure.reliable.org.reliableinfrastructure.R;
import infrastructure.reliable.org.reliableinfrastructure.general.Constants;


public class OptionFrag extends Fragment {

    public static OptionFrag newInstance() {
        return new OptionFrag();
    }

    public OptionFrag() {}

    private static final String TAG = "OptionFrag";

    private EditText phoneNumber;
    private Button saveButton;

    private List<String> cities = null;
    private Activity mActivity;
    private ListView mListView;
    private SharedPreferences.Editor editor;

    private SaveButtonClickListener saveButtonClickListener = new SaveButtonClickListener();
    private class SaveButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String phone = phoneNumber.getText().toString();
            String token = FirebaseInstanceId.getInstance().getToken();
            List<String> subscribedCities = new ArrayList<>();
            List<Integer> indexesSelectedCities = new ArrayList<>();

            SparseBooleanArray checked = mListView.getCheckedItemPositions();
            int size = cities.size();

            for (int i = 0; i < size; i++) {
                if (checked.get(i)) {

                    if (i != 0) {
                        subscribedCities.add(cities.get(i));
                    }

                    indexesSelectedCities.add(i);
                }
            }

            Log.i(TAG, Constants.STARTING_SAVEOPTIONSTHREAD);

            SaveOptionsThread saveOptionsThread = new SaveOptionsThread(token, phone, subscribedCities,
                                                                                    indexesSelectedCities, mActivity, editor);
            saveOptionsThread.execute();
        }
    }

    private AdapterOnClickListener adapterOnClickListener = new AdapterOnClickListener();
    private class AdapterOnClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (i == Constants.ENTIRE_COUNTRY) {
                boolean checked = mListView.isItemChecked(i);
                int size = cities.size();

                if (checked) {
                    for (int j = 1; j < size; j++) {
                        mListView.setItemChecked(j, true);
                    }
                } else {
                    for (int j = 1; j < size; j++) {
                        mListView.setItemChecked(j, false);
                    }
                }
            } else if (!mListView.isItemChecked(i) && mListView.isItemChecked(Constants.ENTIRE_COUNTRY)) {
                mListView.setItemChecked(Constants.ENTIRE_COUNTRY, false);
            }

            Log.i(TAG, "Option " + i + " was clicked");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.options_frag, container, false);

        mActivity = getActivity();
        mListView = (ListView) rootView.findViewById(R.id.listViewCities);
        phoneNumber = (EditText) rootView.findViewById(R.id.phoneEditText);
        saveButton = (Button) rootView.findViewById(R.id.save_button);
        saveButton.setOnClickListener(saveButtonClickListener);

        SharedPreferences preferences = this.getActivity().getSharedPreferences(Constants.USER_PREFERENCES, Context.MODE_PRIVATE);
        editor = preferences.edit();

        // Initializing a new list
        cities = Arrays.asList("Toata tara", "Alba", "Arad", "Arges", "Bacau", "Bihor",
                                "Bistrita-Nasaud", "Botosani", "Brasov", "Braila", "Bucuresti",
                                "Buzau", "Caras-Severin", "Calarasi", "Cluj", "Constanta",
                                "Covasna", "Dambovita", "Dolj", "Galati", "Giurgiu", "Gorj",
                                "Harghita", "Hunedoara", "Ialomita", "Iasi", "Ilfov", "Maramures",
                                "Mehedinti", "Mures", "Neamt", "Olt", "Prahova", "Satu Mare",
                                "Salaj", "Sibiu", "Suceava", "Teleorman", "Timis", "Tulcea",
                                "Vaslui", "Valcea", "Vrancea");

        // Initialize a new ArrayAdapter
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(
                mActivity,
                android.R.layout.select_dialog_multichoice,
                cities
        );

        // Set the adapter for ListView
        mListView.setAdapter(adapter);
        // Set an item click listener for the ListView
        mListView.setOnItemClickListener(adapterOnClickListener);
        setSavedPreferences(preferences);

        return rootView;
    }


    private void setSavedPreferences(SharedPreferences preferences) {
        String userPhoneNumber = preferences.getString(Constants.PHONE_NUMBER, Constants.DEFAULT_STRING);
        phoneNumber.setText(userPhoneNumber);

        String selectedCities = preferences.getString(Constants.SUBSCRIBED_CITIES, Constants.DEFAULT_STRING);

        if (selectedCities.equals(Constants.DEFAULT_STRING)) {
            return;
        }

        String subscribedCities[] = selectedCities.split(Constants.COMMA);
        for (String city : subscribedCities) {
            Integer index = Integer.parseInt(city);
            mListView.setItemChecked(index, true);
        }
    }
}