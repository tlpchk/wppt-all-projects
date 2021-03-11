#include <Arduino.h>
#include "TimerOne.h"
#include "Wheels.h"
#define INTINPUT0 A0
#define INTINPUT1 A1

#define SET_MOVEMENT(side,f,b) digitalWrite( side[0], f);\
                               digitalWrite( side[1], b)

volatile int cnt0, cnt1;
volatile int cnt;

int beeper = 13;
//int period = 500000;
int speedLeft = 0;
int signLeft = 1;
int speedRight = 0;
int signRight = 1;

Wheels::Wheels() 
{
  Timer1.initialize();
  Timer1.detachInterrupt();
  pinMode(beeper, OUTPUT);
  cnt = 0;
  pinMode(INTINPUT0, INPUT);
  pinMode(INTINPUT1, INPUT);
  PCICR  = 0x02;
  PCMSK1 = 0x03;
 }


ISR(PCINT1_vect){
 cnt++;
}


int old0 = !(PINC & (1 << PC0));
int old1 = !(PINC & (1 << PC1));

void zeroCounters(){
  
  cnt=0;
}

void doBeep() {
  digitalWrite(beeper, digitalRead(beeper) ^ 1);
}

void Wheels::attachRight(int pF, int pB, int pS)
{
    pinMode(pF, OUTPUT);
    pinMode(pB, OUTPUT);
    pinMode(pS, OUTPUT);
    this->pinsRight[0] = pF;
    this->pinsRight[1] = pB;
    this->pinsRight[2] = pS;
}


void Wheels::attachLeft(int pF, int pB, int pS)
{
    pinMode(pF, OUTPUT);
    pinMode(pB, OUTPUT);
    pinMode(pS, OUTPUT);
    this->pinsLeft[0] = pF;
    this->pinsLeft[1] = pB;
    this->pinsLeft[2] = pS;
}

void Wheels::setSpeedRight(uint8_t s)
{
    analogWrite(this->pinsRight[2], s);
    speedRight = s;
}

void Wheels::setSpeedLeft(uint8_t s)
{
    analogWrite(this->pinsLeft[2], s);
    speedLeft = s;
}

void Wheels::setSpeed(uint8_t s)
{
    setSpeedLeft(s);
    setSpeedRight(s);
}

void Wheels::attach(int pRF, int pRB, int pRS, int pLF, int pLB, int pLS)
{
    this->attachRight(pRF, pRB, pRS);
    this->attachLeft(pLF, pLB, pLS);
}

void Wheels::forwardLeft() 
{
    SET_MOVEMENT(pinsLeft, HIGH, LOW);
    signLeft = 1;
}

void Wheels::forwardRight() 
{
    SET_MOVEMENT(pinsRight, HIGH, LOW);
    signRight = 1;
}

void Wheels::backLeft()
{
    SET_MOVEMENT(pinsLeft, LOW, HIGH);
    signLeft = -1;
}

void Wheels::backRight()
{
    SET_MOVEMENT(pinsRight, LOW, HIGH);
    signRight = -1;
}

void Wheels::forward()
{
    Timer1.detachInterrupt();
    this->forwardLeft();
    this->forwardRight();
}

void Wheels::back()
{
    Timer1.detachInterrupt();
    Timer1.attachInterrupt(doBeep, 250000);
    this->backLeft();
    this->backRight();
}

void Wheels::stopLeft()
{
    SET_MOVEMENT(pinsLeft, LOW, LOW);
}

void Wheels::stopRight()
{
    SET_MOVEMENT(pinsRight, LOW, LOW);
}

void Wheels::stop()
{
    Timer1.detachInterrupt();
    this->stopLeft();
    this->stopRight();
}


void Wheels::moveForward(double dist) {
  int cnt0_start = cnt0;
  int cnt1_start = cnt1;
  this->forward();
  while (true) {
    if (cnt0 >= cnt0_start + (dist * 2.0) && cnt1 >= cnt1_start + (dist * 2.0)) {
      this->stop();
      break;
    }
  }
}

void Wheels::moveBackward(double dist) {
  int cnt0_start = cnt0;
  int cnt1_start = cnt1;
  this->back();
  while (true) {
    if (cnt0 >= cnt0_start + (dist * 2.0) && cnt1 >= cnt1_start + (dist * 2.0)) {
      this->stop();
      break;
    }
  }
}

void Wheels::goForward(int cm){
    this->setSpeed(150);
    this->forward();
    while (cnt < 4*cm) delay(50); 
    this->stop();
    zeroCounters();
}

void Wheels::goBack(int cm){
    this->setSpeed(150);
    this->back();
    while (cnt < 4*cm) delay(50);
    this->stop();
    zeroCounters();
}


void Wheels::setBeeper(int pin){
    beeper = pin;
}

void Wheels::turnLeft(double dgrs){
  zeroCounters();
  this->stop();
  this->setSpeed(125);
  this->forwardRight();
  this->backLeft();
  while (cnt < 1.45*dgrs) delay(50);
  this->stop();
  zeroCounters();
}

void Wheels::turnRight(double dgrs){
  zeroCounters();
  this->stop();
  this->setSpeed(125);
  this->forwardLeft();
  this->backRight();
  while (cnt < 1.45*dgrs) delay(25);
  this->stop();
  zeroCounters();
}
