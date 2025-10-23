# 🎯 DartCounter

**DartCounter** is a JavaFX-based application for keeping track of scores in a darts match.  
It supports legs, sets, checkouts, and both per-dart and per-turn scoring modes.  

Built with **Java 23** and **JavaFX 23.0.2** using Maven.

---

## 📦 What's Included

- `DartCounter-1.0.jar` — Executable JAR file  
- Source files (`.java`, `.fxml`, `.css`)  
- Simple, responsive UI with score tracking and checkout display  

---

## 🧠 Requirements

To run the app, you’ll need:

1. **Java JDK 17 or newer** (Java 23 recommended)  
   👉 [Download JDK](https://www.oracle.com/java/technologies/downloads/)

2. **JavaFX SDK 23.0.2 or newer**  
   👉 [Download JavaFX SDK](https://openjfx.io)

---

## ▶️ How to Run

1. Extract the JavaFX SDK to any folder (for example):  
```
C:\Program Files\JavaFX\javafx-sdk-23.0.2
```

2. Place the `DartCounter-1.0.jar` file anywhere you like (e.g., Desktop or project folder).

3. Open **Command Prompt** (or Terminal) in the same folder as the JAR file.

4. Run this command (update the path to your own JavaFX SDK):

```bash
java --module-path "C:\Program Files\JavaFX\javafx-sdk-23.0.2\lib" --add-modules javafx.controls,javafx.fxml -jar DartCounterApp-1.0.jar
```

- ✅ The DartCounter window should open immediately.

---

## 🧩 Developer Notes
- Built using maven
```
mvn clean package
```
- Output File
```
target/DartCounter-1.0.jar
```
- Entry point:
```
com.example.dartcounter.DartCounterApp
```

- GUI: FXML + CSS

- Features:
    - Legs and sets scoring
    - Per-dart and per-turn modes
    - Checkout suggestions
    - Score averages

---

## 👨‍💻 Author
- Developed by **Gavin Farrelly**
- Stage 3 Computer Science, University College Dublin



