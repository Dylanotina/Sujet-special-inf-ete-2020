package streaming;

import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.receiver.Receiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DataReceiver extends Receiver<String> {
    private String[] urls;

    private int interator = 0;

    public DataReceiver(String[] receivingURls) {
        super(StorageLevel.MEMORY_AND_DISK_2());
        this.urls = receivingURls;
    }

    public void onStart() {
    new Thread(this::receive).start();
    }

    public void onStop() {

    }

    private void receive(){
        boolean keepOnRunning = true;
        while (keepOnRunning){
            String dataString = this.urls[interator];
            try {
                URL data = new URL(dataString);
                HttpURLConnection dataUrlConnection = (HttpURLConnection) data.openConnection();
                String basicCredentials = "Dylanotina:ae7464181e94cc701a75812939bbf4634e9e9ba3";
                String BasicAuth = "Basic " + Base64.getEncoder().encodeToString(basicCredentials.getBytes(StandardCharsets.UTF_8));
                dataUrlConnection.setRequestProperty("Authorization",BasicAuth);


                InputStream is = dataUrlConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                String line;

                try {
                    while ((line = bufferedReader.readLine()) != null) {
                        String newLine = line.substring(1,line.length()-1);
                        //System.out.println(newLine);
                        store(newLine);

                    }
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    keepOnRunning = false;
                }

                if (interator == 10){
                    interator = 0;
                }else {
                    interator++;
                }

                try {
                    Thread.sleep(1000);
                }catch (InterruptedException err){
                    err.printStackTrace();
                    keepOnRunning = false;
                }


            }catch (MalformedURLException e){
                System.out.println("Erreur sur l'adresse" + e);
                keepOnRunning = false;
            }catch (IOException err){
                System.out.println(err.getMessage());
                keepOnRunning = false;
            }
        }

    }
}
