# Pitch

## Name der App

Richtungsanzeiger

## Kurzbeschreibung der Idee in einem Satz oder als Stichwortliste

Die App zeigt die Richtung zu einem ausgewählten Punkt auf der Welt.

## Motivation hinter der Idee. Weshalb braucht es diese App?

Haben Sie sich auch schon mal an einen Ort gedacht (z.B. Matterhorn, Bundeshaus, ZLI) und sich gefragt, in welcher Richtung dieser Ort liegt? Dabei kann Ihnen diese App helfen.

Ganz einfach können Sie einen Ort auf der Karte auswählen und in einer Liste abspeichern. Wenn Sie diesen Ort dann auf der Liste auswählen, zeigt Ihnen die App einen Kompass und die Richtung zum gewünschten Ort. Wenn Sie sich dann noch mit Ihrem Smartphone hin zur angezeigten Richtung drehen, wird Ihnen durch ein Vibrationssignal die Richtung nochmals verdeutlicht.

## Erwähnung der Rahmenbedingungen

* Sensor auslesen und dessen Daten verarbeiten:
	* Orientationssensor (zur Bestimmung des eigenen Standortes);
	* Magnetfeldsensor (zur Bestimmung der Himmelsrichtungen);
* Aktuator verwenden: Vibrationsaktuator
* Mehr als eine Aktivität oder View verwenden (Liste und Kompass)
* Kommunikation zwischen zwei Aktivitäten oder Views (Kartenapp und Liste)
* Verwenden einer persistenten, lokalen Datenablage (zur Speicherung der Liste)
* Interaktion mit Systemapplikationen (Kartenapp)
