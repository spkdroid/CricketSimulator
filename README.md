# Cricket Simulator Game

A console-based cricket simulator game written in Kotlin.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Game Rules](#game-rules)
- [Customization](#customization)
- [Contributing](#contributing)
- [License](#license)

## Introduction

This project is a simple cricket simulator game implemented in Kotlin. It allows users to experience a basic cricket match simulation in the console.

## Features

- Simulates a cricket match with two innings.
- Tracks scores and wickets for each team.
- Basic player attributes such as batting skill and bowling accuracy.
- Randomized outcomes for ball deliveries.
- Supports customization for the number of overs per inning and players per team.

## Prerequisites

- Kotlin installed on your machine.

## Getting Started

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/cricket-simulator.git
   ```

2. Navigate to the project directory:

   ```bash
   cd cricket-simulator
   ```

3. Run the game:

   ```bash
   kotlin -cp . MainKt
   ```

   This will start the cricket simulator.

## Game Rules

- The game simulates a cricket match with two innings.
- Players score runs based on randomized outcomes for ball deliveries.
- Players may get out based on their batting skill.
- The team with the highest total score wins the match.

## Customization

You can customize the game by adjusting the following parameters:

- Number of overs per inning: Modify the `oversPerInning` parameter in the `CricketGame` class.
- Number of players per team: Modify the `playersPerTeam` parameter in the `CricketGame` class.
- Player attributes: Adjust batting skill, bowling accuracy, and player types in the `Player` class.

## Contributing

Contributions are welcome! If you have ideas for improvements or find bugs, feel free to open an issue or submit a pull request.

## Authors

* **Ramkumar Velmurugan** - <a href="http://www.spkdroid.com/CV/">Portfolio</a>

### License
    Copyright 2023 Ramkumar Velmurugan

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.