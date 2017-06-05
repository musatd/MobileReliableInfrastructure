package infrastructure.reliable.org.reliableinfrastructure.optionFrag;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import infrastructure.reliable.org.reliableinfrastructure.general.Constants;
import infrastructure.reliable.org.reliableinfrastructure.general.Utilities;

public class SaveOptionsThread extends AsyncTask<Void, Void, Boolean> {

    private static final String TAG = "SaveOptionsThread";

    private User user;
    private Activity mActivity;
    private List<Integer> indexesSelectedCities;
    private SharedPreferences.Editor editor;


    public SaveOptionsThread(String token, String phone, List<String> cities, List<Integer> indexesSelectedCities,
                             Activity mActivity, SharedPreferences.Editor editor) {
        user = new User(token, phone, cities);
        this.mActivity = mActivity;
        this.indexesSelectedCities = indexesSelectedCities;
        this.editor = editor;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            RestTemplate restTemplate = Utilities.getRestTemplate();

            Boolean isSaved;
            try {
                isSaved = restTemplate.postForObject(Constants.WEB_SERVICE_CREATE_CLIENT, user, Boolean.class);
            } catch (HttpClientErrorException httpClientErrorException) {
                isSaved = false;
            }

            return isSaved;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {

        if (aBoolean != null && aBoolean.equals(true)) {
            Toast.makeText(mActivity, Constants.SUCCESSFUL_OPTIONS_SAVE, Toast.LENGTH_SHORT).show();
            this.saveUserPreferences();
        } else {
            Toast.makeText(mActivity, Constants.UNSUCCESSFUL_OPTIONS_SAVE, Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUserPreferences() {
        editor.clear();
        editor.putString(Constants.PHONE_NUMBER, user.getPhone());

        StringBuilder selectedCities = new StringBuilder();
        for (Integer index : indexesSelectedCities) {
            selectedCities.append(index);
            selectedCities.append(",");
        }

        String subscribedCities = selectedCities.toString();
        editor.putString(Constants.SUBSCRIBED_CITIES, subscribedCities);
        editor.commit();
    }
}
