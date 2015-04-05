# mqtt-paho-synchronous-demo
A simple demonstration of Eclipse PAHO MqTT (Synchronous) library. 

How to install Mosquitto - an MqTT broker on windows.

start
Run win32 installer.
Run OpenSSL and let its dlls be installed at standard windows dll location
download and run pthreads-w32-2-8-0-release application which self extracts. Copy pthreadvc2.dll into the installation folder of mosquitto.
end

Mqtt - Paho programming model
start
create a MqttClient (sync or async)
set a "callback" for the above client.
"connect" client to broker
publish a message to a topic on the broker, preferably using the following API:
  MqttClient.publish(String, byte[] payload, int QoS, boolean retainPolicy);
end
