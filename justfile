# List available goals
default:
    @just --list

# Build the game
build:
    mvn compile

# Run the game
run:
    mvn exec:java -Dexec.mainClass=fr.braux.alpha.DesktopLauncherKt
