package infrastructure.reliable.org.reliableinfrastructure.alertFrag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import infrastructure.reliable.org.reliableinfrastructure.AlertActivity;
import infrastructure.reliable.org.reliableinfrastructure.R;
import infrastructure.reliable.org.reliableinfrastructure.general.Constants;


public class AlertsFrag extends ListFragment {

    public static AlertsFrag newInstance() {
        return new AlertsFrag();
    }

    public AlertsFrag() {}

    private static final String TAG = "AlertsFrag";

    private Button refreshButton;
    private List<String> listValues;
    private List<String> alertMessages;
    private Activity mActivity;
    private ArrayAdapter<String> myAdapter;

    private RefreshButtonClickListener refreshButtonClickListener = new RefreshButtonClickListener();
    private class RefreshButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            GetAlertsThread getAlerts = new GetAlertsThread(myAdapter, listValues, alertMessages, mActivity);
            getAlerts.execute();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.alerts_frag, container, false);

        refreshButton = (Button) rootView.findViewById(R.id.refresh_button);
        refreshButton.setOnClickListener(refreshButtonClickListener);

        listValues = new ArrayList<>();
        alertMessages = new ArrayList<>();

        mActivity = getActivity();
        myAdapter = new ArrayAdapter<> (getActivity(), R.layout.row_layout, R.id.listText, listValues);

        GetAlertsThread getAlerts = new GetAlertsThread(myAdapter, listValues, alertMessages, mActivity);
        getAlerts.execute();

        // assign the list adapter
        setListAdapter(myAdapter);

        return rootView;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Log.i(TAG, "Alert from position " + position + " was accessed");

        Intent intent = new Intent(getActivity(), AlertActivity.class);
        String message = alertMessages.get(position);
        intent.putExtra(Constants.ALERT_MESSAGE, message);
        startActivity(intent);
    }
}
