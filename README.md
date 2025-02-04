# Carts' n Castles Game Michelle and Gordon
This is our SENG201 Project, a take on the classic Tower Defence games!
This readme will provide the information to run the game.

## Description
Carts' n Towers is a strategic game where players must place towers within a map to fill as all the carts to earn the most amount of coins which they can then use to buy more towers and upgrades for the towers!

Look out for random events, random stock levels and fun!

The game is over when 1. the mine cave explodes and you have no more lives 2. you finish all rounds you selected with lives left!

## Prerequisites
To run this project, you will need the following on your local machine:
- JDK 11 or higher (https://www.oracle.com/java/technologies/downloads/#java22)
- Gradle (https://gradle.org/install/)
- IntelliJ DEA (https://www.jetbrains.com/idea/download/)

## To run the Game!
Open the Terminal on your PC and ensure you are in the same directory as the jar file. 
use 'ls' to check the files in your current directory 
use 'cd fileName' to change directory to the fileName (replace fileName with folder name)

Run the jar file by inputting:
'java -jar gho72_myl24_seng_201.jar'

Make sure you are in the same directory as the jar file in your terminal!

## Clone the Project!
1. Open IntelliJ IDEA.
2. Go to `File` > `New` > `Project from Version Control`.
3. In the URL field, enter the following repository URL: https://eng-git.canterbury.ac.nz/seng201-2024/team-99/


## Build the source code!
1. Open the cloned project in IntelliJ IDEA.
2. Go to File > Project Structure.
3. Ensure that the JDK is set to version 11 or higher.
4. Go to Build > Build Project to compile the source code.

## Dependencies

```
dependencies {
    // https://mvnrepository.com/artifact/org.openjfx/javafx-controls
    implementation "org.openjfx:javafx-controls:${javafxVersion}"

    // https://mvnrepository.com/artifact/org.openjfx/javafx-fxml
    implementation "org.openjfx:javafx-fxml:${javafxVersion}"

    // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.10.2'

    // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter
    testImplementation 'org.junit.jupiter:junit-jupiter:5.10.2'

    // https://mvnrepository.com/artifact/org.openjfx/javafx-media
    implementation group: 'org.openjfx', name: 'javafx-media', version:"${javafxVersion}"
}
```


## Authors
Michelle Lee and Gordon Homewood

## Resources
Resource Pack from: pixelfrog-assets.itch.io/tiny-swords

All music sourced from: https://pixabay.com/ 

Cart / Tower Art Sourced from: Ellen Homewood

Coin / Potion / Lives Art from: Michelle Lee

All background images were made using the Resource pack and tiled (https://www.mapeditor.org)




