# elosztottalkalmazasok

Feladat: Tanárokat és diákokat a nevük és egyedi azonosítójuk jellemez. A tanároknak tantárgyuk,
a diákoknak tanulmányi átlaguk van. 
Az átlag változhat, de a tanított tantárgy nem. Minden ember másolható kell legyen, 
ha név nélkül jönnek létre, akkor „ember-n” nevet kapnak,
 ahol az „n” egy folyamatosan növekvő sorszám.  Tanár vagy diák nem jöhet létre név nélkül.
 Az egy osztályba járók közül adjuk meg a legjobb 3 tanuló nevét, azonosítóját!
 A legjobbakat írjuk ki egy bináris állományba! Ha létezik korábbi kiírás, akkor legyen lehetőség 
a visszaolvasásra is! 


Első build előtt a maven frissítése kell, az alábbi workarounddal:

1. POM file frissítése

alábbi kód beillesztése a properties záró eleme, és a project záróeleme közé:
<pluginRepositories>
   <pluginRepository> 
    <id>maven2</id> 
    <url>https://repo.maven.apache.org/maven2/</url> 
  </pluginRepository> 
</pluginRepositories>

POM kinézete a beillesztés után:

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
<modelVersion>4.0.0</modelVersion>
<groupId>com.mycompany</groupId>
<artifactId>tanardiak</artifactId>
<version>1.0-SNAPSHOT</version>
<packaging>jar</packaging>
<properties>
<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
<maven.compiler.source>1.8</maven.compiler.source>
<maven.compiler.target>1.8</maven.compiler.target>
</properties>
<pluginRepositories>
<pluginRepository>
<id>maven2</id>
<url>https://repo.maven.apache.org/maven2/</url>
</pluginRepository>
</pluginRepositories>
</project>


2. Alábbi Stackowrtflow instrukcók követése, 1. válasz:

https://stackoverflow.com/questions/58411279/java-with-maven-wouldnt-build-cannot-run-program-cmd-malformed-argument-has

First exit netbeans IDE if it's open, then open netbeans configuration file from here: netbeans-Install-Dir/etc/netbeans.conf (Note: for me netbeans-Install-Dir is C:\Program Files\NetBeans 8.2\etc)
Add the below arguments
-J-Djdk.lang.Process.allowAmbiguousCommands=true
to the beginning of the string that you find at this line:
netbeans_default_options="-J-client -J-Xss2m -J-Xms32m ......."

NOTE: Confliguration file csak akkor menthető, ha rendszergazdaként módosítunk rajta



Ez a két lépés megoldja a maven build okozta problémát
