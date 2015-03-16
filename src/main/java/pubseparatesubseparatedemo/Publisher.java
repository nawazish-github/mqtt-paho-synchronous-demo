
package pubseparatesubseparatedemo;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/*
A Publisher class which would publish simple messages to a MqTT server (Mosquitto)
on a particular topic.
*/
public class Publisher implements MqttCallback {

    /*
    @param url: the broker url
    @param clientID: the ID of this publisher (MqTT) client
    @param qos: quality of service for this message transport to mosquitto broker
    @param msg: the message that the publisher wants to publish to mosquitto broker
    @param topic: the topic under which message would be published
    */
    
    public Publisher (String url, String clientID, int qos, String msg, String topic){
    
        
        try {
            MqttClient client = new MqttClient(url,clientID);
            client.setCallback(this); //it logically makes sense that we set the call back MqttClient before connecting to broker
            client.connect();
            client.publish(topic, msg.getBytes(), 2, true); // true here is defining the retain policy of broker.
                                                            // true implies that mosquuitto would retain the last 
                                                            //successfully published message from this publisher ID.
            System.out.println("message published. lets rest for some time");
            Thread.sleep(2000); //not necessary
            //since msg is published to mosquitto by this time, we may disconnect publisher client
            client.disconnect();
        } catch (MqttException ex) {
            Logger.getLogger(Publisher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Publisher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
       Publisher publisher = new Publisher("tcp://localhost:1883", "pub", 2, "RPi Sensor", "sensor");
       
    }

    @Override
    public void connectionLost(Throwable thrwbl) {
        //called when connection to client-broker connection is lost!
        System.out.println("publisher lost connection");
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        //called whenever broker hands over message on a particular topic to the subscriber.
        //since this client is a publisher, and not a subscriber, this method would never be called.
        System.out.println("how come publisher received msg: "+new String(mm.getPayload()));
        
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        //message delivery to broker completed.
        try {
            imdt.waitForCompletion(); // a simple API to block-on until message is published to broker 
                                      // and depending on QoS setting broker would respond back.
            System.out.println("delivery to mosquitto complete: ");
        } catch (MqttException ex) {
            Logger.getLogger(Publisher.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
