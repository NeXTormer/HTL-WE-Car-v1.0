#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>
#include <ESP8266mDNS.h>


const char *ssid = "htl-IoT";
const char *password = "iot..2015";

WiFiUDP userver;
unsigned int UDPPort = 4242;

//Packet Buffer 
const int packetSize = 2;
byte packetBuffer[packetSize]; 

void handleUDPServer();

void setup ( void ) {
	pinMode(16, OUTPUT);
	Serial.begin ( 115200 );
	Serial.println ( "" );

  Serial.println("Connecting to WIFI");  
//wifi
// Wait for connection
  while ( WiFi.status() != WL_CONNECTED ) {
    delay ( 500 );
    Serial.print ( "." );
  }

  Serial.println ( "" );
  Serial.print ( "Connected to " );
  Serial.println ( ssid );
  Serial.print ( "IP address: " );
  Serial.println ( WiFi.localIP() );

  if ( MDNS.begin ( "esp8266" ) ) {
    Serial.println ( "MDNS responder started" );
  }
  


//motor pins

  //motor1  
  pinMode(16, OUTPUT); //pwm
  pinMode(4, OUTPUT);//dir

  digitalWrite(4, HIGH);
  
  
  //motor2  
  pinMode(14, OUTPUT); //pwm
  pinMode(13, OUTPUT);//dir
  
  digitalWrite(13, HIGH);
  
  
  

  analogWriteRange(255);

 
 userver.begin(4242);
 
}

void loop() 
{
     handleUDPServer();
}

void handleUDPServer() 
{
  int cb = userver.parsePacket();
  if (cb) {
    byte data[2];
    userver.read(data, 2);


    Serial.println("New Packet");
    Serial.println(data[0]);
    Serial.println(data[1]);
  

    analogWrite(16, data[0]);
    analogWrite(14, data[1]);

    
    
  }

}

