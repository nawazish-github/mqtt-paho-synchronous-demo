
package pubseparatesubseparatedemo;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/*
A simple Subscriber class which would subscribe to a simple messages on a MqTT server (Mosquitto)
on a particular topic.
*/

public class Subscriber implements MqttCallback{
    
    /*
    @param url: the broker url
    @param qos: quality of service for this message transport to mosquitto broker
    @param topic: the topic under which message would be published
    @param clientID: the ID of this publisher (MqTT) client
    */
    
    public Subscriber (String url, String topic, int qos, String clientID){
        
        try {
            MqttClient subscriber = new MqttClient(url, clientID);
            subscriber.setCallback(this);
            subscriber.connect();
            subscriber.subscribe(topic);
            subscriber.disconnect();
        } catch (MqttException ex) {
            Logger.getLogger(Subscriber.class.getName()).log(Level.SEVERE, "subscriber could not be created", ex);
        }
    }

    public static void main(String[] args) {
        
        Subscriber subscriber = new Subscriber ("tcp://localhost:1883", "sensor", 2, "sub");
    }

    @Override
    public void connectionLost(Throwable thrwbl) {
        System.out.println("subscriber lost connection");
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        System.out.println("message arrived to subscriber from mosquitto: "+new String(mm.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        System.out.println("i donot know how a subscriber can \"deliver\" messages!!!");
    }
    
}
