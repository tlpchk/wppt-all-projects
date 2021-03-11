#include <Servo.h>

#include "Wheels.h"
Wheels w;

// piny dla sonaru (HC-SR04)
#define TRIG A4
#define ECHO A5

// pin kontroli serwo (musi być PWM)
#define SERVO 3

Servo serwo;
unsigned int dist;
#define oneDegreeTime 6
#define zeroEnergyDist 100

void setup() {
  w.attach(2,4,5,6,7,11);
  w.setSpeed(200);
  w.forward();
  pinMode(TRIG, OUTPUT);    // TRIG startuje sonar
  pinMode(ECHO, INPUT);     // ECHO odbiera powracający impuls

  Serial.begin(9600);

  serwo.attach(SERVO);

/* rozejrzyj się w zakresie od 0 stopni (patrz na jedną burtę)
 *  do 180 stopni (patrz na prawą burtę). Wydrukuj na konsoli
 *  kierunek patrzenia i najbliżej widziany obiekt (pojedynczy pomiar)
 */
  
/* patrz przed siebie */
  

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

  Serial.print("Patrzę w kącie ");
  Serial.print(angle);
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

  Serial.print(": widzę coś w odległości ");
  return distance;
}

void lookAndTellDistance(byte angle) {
  
  unsigned long tot;      // czas powrotu (time-of-travel)
  unsigned int distance;

  Serial.print("Patrzę w kącie ");
  Serial.print(angle);
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

  Serial.print(": widzę coś w odległości ");
  Serial.println(distance);
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
    dist = lookAndReturnDistance();
    doSping(dist - zeroEnergyDist);
    delay(50);
}

void loop() { 
  avoid()
  spring()
}
