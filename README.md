# I Love India

A simple Java 8 Swing desktop application that displays the Indian tricolor, Ashoka Chakra, and a patriotic message.

## Requirements

- JDK 8 or newer
- Maven 3.6 or newer

## Run

```text
mvn clean compile exec:java
```

To compile without Maven:

```text
javac -d out src\main\java\com\india\love\ILoveIndiaApp.java
java -cp out com.india.love.ILoveIndiaApp
```

On a computer with a graphical desktop, a window opens and **Celebrate India** rotates through the messages. In a headless terminal such as Killercoda, the command prints the application text instead.