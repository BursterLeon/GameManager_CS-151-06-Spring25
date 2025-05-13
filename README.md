# CS151-06-Spring25 Game Suite

## Overview
A JavaFX gaming suite featuring Blackjack and Snake with user management and persistent data storage. Designed for CS 151 Object-Oriented Design course, this project demonstrates:

Key Components:
1. **Game Manager**: User authentication & high score tracking
2. **Blackjack**: Full casino-style implementation with save states
3. **Snake**: Classic arcade game with dynamic difficulty

## How to run the app:

https://openjfx.io/openjfx-docs/#introduction

#### 1. Create a JavaFX project: 
Download the appropriate JavaFX SDK for your operating system and unzip it to a desired location, for instance /Users/your-user/Downloads/javafx-sdk-24

#### 2. 
Open the repository in an IDE of your choice

#### 3. Set JDK 24: 
Go to File -> Project Structure -> Project, and set the project SDK to 24. You can also set the language level to 11 or greater.

#### 4. Create a library: 
Go to File -> Project Structure -> Libraries and add the JavaFX 24 SDK as a library to the project. Point to the lib folder of the JavaFX SDK.

#### 5. Add VM options: 
click on Run -> Edit Configurations... and add these VM options: 
Mac/Linux:
—module-path /path/to/javafx-sdk-24.0.1/lib --add-modules javafx.controls,javafx.fxml
Windows:
--module-path "\path\to\javafx-sdk-24.0.1\lib" --add-modules javafx.controls,javafx.fxml


## Usage
### Snake Game Features
#### Controls:
- Arrow keys for direction
- ESC to pause/resume
- ENTER\ESC to restart after game over

##### Scoring:
- +1 per food collected
- Speed increases with score

## Contributions
### Team Roles
#### Member	Ownership Areas

- Leon Burster - Game Manager & Auth System (Task 1 - 4):
Login/SignUp, GameWindow, Data management (files: high_scores.txt, user_accounts.txt), unitTest -> userTest, Password hashing

- [Name 2] - Blackjack Core Logic & GUI Design & Integration

- Sokuntheary Em	- Snake Game Implementation & GUI Design & Integration
