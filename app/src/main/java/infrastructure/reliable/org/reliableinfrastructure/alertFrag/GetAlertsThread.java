package infrastructure.reliable.org.reliableinfrastructure.alertFrag;


import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

import infrastructure.reliable.org.reliableinfrastructure.general.Constants;
import infrastructure.reliable.org.reliableinfrastructure.general.Utilities;

public class GetAlertsThread extends AsyncTask<Void, Void, List<String>> {

    private static final String TAG = "GetAlertsThread";

    private List<String> listValues;
    private List<String> alertMessages;
    private ArrayAdapter<String> myAdapter;
    private Activity mActivity;

    public GetAlertsThread (ArrayAdapter<String> myAdapter, List<String> listValues,
                                                            List<String> alertMessages, Activity mActivity) {
        this.myAdapter = myAdapter;
        this.listValues = listValues;
        this.alertMessages = alertMessages;
        this.mActivity = mActivity;
    }

    @Override
    protected List<String> doInBackground(Void... params) {
        RestTemplate restTemplate = Utilities.getRestTemplate();

        String token = FirebaseInstanceId.getInstance().getToken();

        List<String> results;
        try {
            results = (List<String>) restTemplate.postForObject(Constants.WEB_SERVICE_GET_ALERTS, token, List.class);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            results = new ArrayList<>();
        }

        Log.i(TAG, results.toString());

        return results;
    }

    @Override
    protected void onPostExecute(List<String> strings) {
        super.onPostExecute(strings);

        listValues.clear();
        alertMessages.clear();
        int size = strings.size();
        for (int i = 0; i < size; i++) {
            if (strings.get(i).length() > Constants.NUMBER_OF_CHARACTERS_TO_BE_SHOWN) {
                listValues.add(strings.get(i).substring(0, Constants.NUMBER_OF_CHARACTERS_TO_BE_SHOWN) + Constants.DOTS);
            } else {
                listValues.add(strings.get(i));
            }
            alertMessages.add(strings.get(i));
        }

        myAdapter.notifyDataSetChanged();
        Toast.makeText(mActivity, Constants.UPDATE_ALERTS_MESSAGE, Toast.LENGTH_SHORT).show();
    }
}
