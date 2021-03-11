#include "Wheels.h"
#include <SPI.h>
#include <SD.h>

void executeCommand(char command,double parametr);

Wheels w;
File dataFile;
 
void setup() {
  
  w.attach(5,4,6,9,8,10);
  w.cnt0 = 0;
  w.cnt1 = 1;
  Serial.begin(9600);

  
  //4 mean the CS pin
  if (!SD.begin(4)) {
    Serial.println("initialization failed!");
  }

  // Open for writing
  dataFile = SD.open("data.txt",FILE_READ);

  w.setSpeed(250);

  if (dataFile) {
    // Read from the file until there's nothing else in it:
    while (dataFile.available()) {
      String line = dataFile.readStringUntil('\n');
      char command = line[0];
      double parametr = (line.substring(2).toDouble());
      Serial.println(parametr);
      executeCommand(command,parametr);
      delay(500);
    }
    dataFile.close();
  } else {
    Serial.println("Error opening file");
  }

}



void executeCommand(char command, double parametr){
  double mult = 6.0;
  switch (command){
    case 'F' : 
      w.goForward(parametr/mult);
      break;
    case 'L' : 
      w.turnLeft(parametr);
      break;
    case 'R' : 
      w.turnRight(parametr);
      break;
    default:
      Serial.println("Error parsing command.");
  }
}

void loop() {
  
}

void doBeepIn() {
  digitalWrite(13, digitalRead(13) ^ 1);
}
