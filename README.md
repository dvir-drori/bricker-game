# Bricker

A brick-breaker game in Java built on the **DanoGameLab** game engine. HUJI OOP coursework (Exercise 2).

## Features

- Configurable brick grid (rows × columns) via CLI args
- 3 lives with synced numeric + graphical heart displays
- Color-coded life counter (green / yellow / red)
- **Pucks** — additional balls that spawn from special bricks via the Strategy pattern
- Win/lose detection with replay prompt
- Random initial ball direction; paddle controlled with ← →

## Project structure

```
src/
└── bricker/
    ├── LivesCounter.java
    ├── main/
    │   └── BrickerGameManager.java
    ├── gameobjects/
    │   ├── Ball.java
    │   ├── Brick.java
    │   ├── Paddle.java
    │   ├── Puck.java
    │   ├── Heart.java
    │   ├── GraphicLifeCounter.java
    │   └── NumericLifeCounter.java
    └── brick_strategies/
        ├── CollisionStrategy.java
        ├── BasicCollisionStrategy.java
        └── PuckCollisionStrategy.java
assets/                 (course-provided sprites and sounds)
```

## Requirements

- **Java 11+**
- **DanoGameLab.jar** (course-licensed, not committed)
- Asset files in `assets/`:
  - `ball.png`, `paddle.png`, `brick.png`, `heart.png`
  - `DARK_BG2_small.jpeg`, `blop.wav`
  - power-up assets: `botBad.png`, `botGood.png`, `buffNarrow.png`, `buffWiden.png`, `Bubble5_4.wav`

## Running

### IntelliJ IDEA
1. Open this folder as an IntelliJ project
2. Add `DanoGameLab.jar` under **File → Project Structure → Libraries**
3. Run `BrickerGameManager.main()`

### Command line
```bash
javac -cp "DanoGameLab.jar" -d out src/bricker/**/*.java src/bricker/*.java

# default 8×7 grid
java -cp "out;DanoGameLab.jar" bricker.main.BrickerGameManager

# custom: 10 columns × 5 rows
java -cp "out;DanoGameLab.jar" bricker.main.BrickerGameManager 10 5
```

## Controls

| Key | Action |
|---|---|
| ← | Move paddle left |
| → | Move paddle right |

## Design

- **Strategy pattern** for brick collisions — `CollisionStrategy` interface lets bricks behave differently on hit (basic removal, spawn pucks, etc.)
- **Shared `LivesCounter`** — both numeric and graphical life displays observe the same counter
- **Layered rendering** — background, default, static-objects, and UI layers managed independently

## Author

**Dvir Drori** — Computer Science & Computational Biology, Hebrew University of Jerusalem
