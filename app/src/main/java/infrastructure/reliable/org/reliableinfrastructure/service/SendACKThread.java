package infrastructure.reliable.org.reliableinfrastructure.service;


import android.util.Log;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import infrastructure.reliable.org.reliableinfrastructure.general.Constants;
import infrastructure.reliable.org.reliableinfrastructure.general.Utilities;

public class SendACKThread extends Thread {

    private static final String TAG = "SendACKThread";

    private ACKData ackData;

    public SendACKThread(Long idAlert, String token) {
        ackData = new ACKData(idAlert, token);
    }

    @Override
    public void run() {
        RestTemplate restTemplate = Utilities.getRestTemplate();

        try {
            restTemplate.postForObject(Constants.WEB_SERVICE_SEND_ACK, ackData, Void.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            Log.e(TAG, e.getMessage(), e);
        }

    }
}
