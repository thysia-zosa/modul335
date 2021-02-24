# Modul 335

## Tag 1

### Typen von mobilen Applikationen

* Vor-Nachteile, Limitierungen, Wirtschaftlichkeit
* Tools
* Beispiel
* Native App, CrossPlatform, HybridPlatform, ProgressiveWebApplication, SinglePageApplication

#### Native App

* Vorteile: Nahe am Gerät, kann alle Funktionen nutzen, Performanz, direkter Hardwarezugriff, viel Potenzial (neueste Features schnell verfügbar);
* Nachteile: Eigene Codebases für verschiedene Plattformen
* Limitierungen: 
* Wirtschaftlichkeit: schwierig
* Tools: xCode, AndroidStudio, 
* Beispiel: die meisten (alle?) mitgelieferten / hauseigenen Programme (besonders Apple-Geräte)

#### Cross Platform App

Abstraktionsschicht zwischen Plattform und App

* Vorteile: Eine Codebase für mehrere Plattformen
* Nachteile: Einschränkungen bei den Funktionen
* Limitierungen:
* Wirtschaftlichkeit:
* Tools:
* Beispiel:

#### Hybrid Platform App

kombiniert native mit Web mittels Cordova, Capacitor; Browserfenster mit Zusatz

* Vorteile: Webbasis
* Nachteile: Zugriff auf Hardware nur über JS-API;
* Limitierungen: Weder Fisch noch Vogel
* Wirtschaftlichkeit:
* Tools:
* Beispiel:

#### Progressive Web Application

SinglePage++, grundsätzlich im Browser, spezieller Modus
Beim Aufruf wird die App im Cache gespeichert ("installiert"); beim Aufruf wird (sofern internet == true) auf neuere Versionen geprüft;
z.B. mit WorkBox (zur Programmierung von ServiceWorkern);

* Vorteile: multiPlatform, individuelles UI, offline nutzbar, NO APP_STORE!
* Nachteile: limitierter Hardwarezugriff
* Limitierungen:
* Wirtschaftlichkeit:
* Tools:
* Beispiel:

#### Single Page Application

Das Gerüst wird geschickt, alles andere per JSON; im Browser;

* Vorteile:
* Nachteile:
* Limitierungen:
* Wirtschaftlichkeit:
* Tools:
* Beispiel:

