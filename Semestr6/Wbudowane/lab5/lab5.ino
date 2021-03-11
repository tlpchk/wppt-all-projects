#include <Servo.h>

#include "Wheels.h"
Wheels w;

// piny dla sonaru (HC-SR04)
#define TRIG A4
#define ECHO A5

// pin kontroli serwo (musi być PWM)
#define SERVO 9

Servo serwo;
unsigned int dist;
int speed;
#define oneDegreeTime 6
#define zeroEnergyDist 20
#define maxSpeed 250
#define minSpeed 70

void setup() {
  w.attach(3,2,6,5,4,11);
  w.setSpeed(200);
  w.forward();
  pinMode(TRIG, OUTPUT);    // TRIG startuje sonar
  pinMode(ECHO, INPUT);     // ECHO odbiera powracający impuls

  Serial.begin(9600);

  serwo.attach(SERVO);
}


void turnOnLeft(int angle){ 
  w.stop();
  w.forwardRight();
  w.backLeft();
  delay(angle*oneDegreeTime);
  w.forward();
}

void turnOnRight(int angle){
  w.stop();
  
  w.forwardLeft();
  w.backRight();
  delay(angle*oneDegreeTime);
  w.forward();
}

unsigned int lookAndReturnDistance(byte angle) {
  
  unsigned long tot;      // czas powrotu (time-of-travel)
  unsigned int distance;

  //Serial.print("Patrzę w kącie ");
  //Serial.print(angle);
  serwo.write(angle);
  
/* uruchamia sonar (puls 10 ms na `TRIGGER')
 * oczekuje na powrotny sygnał i aktualizuje
 */
  digitalWrite(TRIG, HIGH);
  delay(10);
  digitalWrite(TRIG, LOW);
  tot = pulseIn(ECHO, HIGH);

/* prędkość dźwięku = 340m/s => 1 cm w 29 mikrosekund
 * droga tam i z powrotem, zatem:
 */
  distance = tot/58;

  //Serial.print(": widzę coś w odległości ");
  return distance;
}


int findOptimalAngle(){
  int result = 0;
  int optimalDist = 0;
  int tempDist;
  for(byte angle = 0; angle < 180; angle+= 20) {
    tempDist = lookAndReturnDistance(angle);
    if(tempDist < optimalDist){
      optimalDist = tempDist;
      result = angle;
    }
  }
  return result;
}

void avoid(){
  int optimalAngle;
  for(byte angle = 45; angle < 135; angle+= 20) {
      dist = lookAndReturnDistance(angle);
      if(dist < 30){
        w.stop();
        optimalAngle = findOptimalAngle();
        if(optimalAngle < 90){
          turnOnLeft(90 - optimalAngle);
        }else{
          turnOnRight(optimalAngle - 90);
        }
      }
      else{
        w.forward();
      }
      delay(50);
  }
}


void spring(){
    dist = lookAndReturnDistance(80);
    
    speed = (dist - zeroEnergyDist) * 5;
    Serial.println(speed);
    if(speed >= minSpeed){
      speed = speed < maxSpeed ? speed : maxSpeed;
      w.forward();
    }else if(-(speed*4) >= minSpeed){
      w.back();
      speed = -(speed*4);
      speed = speed < maxSpeed ? speed : maxSpeed;
    }else{
      w.stop();
    }

    w.setSpeed(speed);

    Serial.println(speed);
    
    delay(50);
}

void loop() { 
  //avoid();
  spring();
 
}
