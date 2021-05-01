#include <Adafruit_NeoPixel.h>
#define PIN 4
#define NUM_LEDS 12
Adafruit_NeoPixel strip = Adafruit_NeoPixel(NUM_LEDS, PIN, NEO_GRB + NEO_KHZ800);

int trig = 2;
int echo = 3;

boolean b = true;
boolean bb = false;
boolean light = true;
int i = 0;

void setup() {
  strip.setBrightness(30); // 0~255 사이의 값으로 밝기 조절
  strip.begin();
  strip.show();

  Serial.begin(9600);
  Serial1.begin(9600);

  pinMode(trig, OUTPUT);
  pinMode(echo, INPUT);
  pinMode(13, OUTPUT);
  pinMode(12, OUTPUT);
}


void loop() {

  if (light == true) {
          OnLED();
  } else {
    strip.setBrightness(0);
    strip.show();
  }


  if (Serial1.available()) {
    char c = Serial1.read();
    if (c == '1') {
      light = false;
      bb = true;
      delay(3000);
      digitalWrite(13, HIGH);
      delay(2000);
      digitalWrite(13, LOW);
      Serial.println("주차중");

    }
  }

  if (bb == true) {
    digitalWrite(trig, HIGH);
    delay(3);
    digitalWrite(trig, LOW);

    int duration = pulseIn(echo, HIGH);
    int distance = duration / 2 / 29;
    //  Serial.println(distance);

    if (distance > 60) {
      light = true;
      OnLED();
      if (b == true) {
        delay(5000);
        digitalWrite(12, HIGH);
        delay(2000);
        digitalWrite(12, LOW);
        b = false;
        bb = false;
        Serial.println("주차가능");
        //strip.setBrightness(0);
      }
    } else {
      b = true;
    }
  }
}

void OnLED(){
  strip.setBrightness(30);
      i = (i + 1) % 12;

      strip.setPixelColor(i % 12, 0, 0, 0);
      strip.setPixelColor((i + 1) % 12, 0, 1, 1);
      strip.setPixelColor((i + 2) % 12, 0, 4, 2);
      strip.setPixelColor((i + 3) % 12, 0, 16, 8);
      strip.setPixelColor((i + 4) % 12, 0, 30, 15);
      strip.setPixelColor((i + 5) % 12, 0, 60, 30);

      strip.setPixelColor((i + 6) % 12, 0, 0, 0);
      strip.setPixelColor((i + 7) % 12, 1, 1, 0);
      strip.setPixelColor((i + 8) % 12, 4, 2, 0);
      strip.setPixelColor((i + 9) % 12, 16, 8, 0);
      strip.setPixelColor((i + 10) % 12, 30, 15, 0);
      strip.setPixelColor((i + 11) % 12, 60, 30, 0);

      strip.show();
      delay(80);
}
