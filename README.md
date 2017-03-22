# DPM-Project
Repository for ECSE 211: Design Principles and Methods.

The goal of the project is as follows:

Construct an autonomous robot to play a one-on-one game that is a cross between soccer
and basketball. The robot must be able to play either forward or defense and be capable
of navigating the field without hitting obstacles. Instructions are received via WiFi prior
to the game.

The requirements of the project are subject to change at the professors' whim.

## Contributing
Make sure to follow these guidelines when collaborating:
* Don't create spurious releases. All releases must be approved by the whole team before being made.
* Try to keep each feature you develop on a different branch and merge into master with a pull request when you're done.
* Always branch off the latest release.
* Make sure the commit messages are concise and informative.
* Try to use bug IDs and commit IDs where you can to be more specific.
* If you have several spurious commits(e.g.: correcting spelling mistakes) then squash before you commit.

## Pull Requests
When you begin to work on a feature, create a new branch for that feature. Once you're done working on it, issue a pull request
to merge with master. The pull request allows us to have a notification to test your module/feature.

Before any pull request can be approved it must be tested. This means that we have to prepare a test requirements and outcome document.
The test requirements will be written by the programmer of that feature and will include
things like the expected operating input ranges and the expected outputs. A tester will then use this document to
test the feature/module. Only after the test outcome report has been published with an approval stamp, can the pull request be approved.

## Feature and Module Development
A feature/module can be any component of code such as an interface, a function, a class or a modification to an algorithm. The requirements
are that the feature be self contained and easy to merge into the codebase.

## Changing interfaces
Anyone can implement a class that follows an interface. This allows us to rapidly develop implementation of our algorithms of choice.
However, changing the interface would cause problems in the existing codebase and require changes to every class that implements that interface. Hence, changing an interface is team wide decision.

## Fixing Bugs
If you see an issue in the code you should issue a bug in GitHub. Remember to be detailed about what you write
and make sure to write down the steps to reproduce the bug in the description. However, if the bug is a logical or syntactical error
then there is no need to do this.

If you wish to fix a bug please do so on a new branch. Create a new branch with the bug's ID and once you have fixed the issue please merge
it back into master. Even if the fix would take only one commit this will allow us finer control over the changes to the code.

## Current Modules/Classes
* `EntryPoint` - Serves as the starting point of the program.
* `GlobalDefinitions` - Contains `static final` variables that will be accessible anywhere in the program. Constants like wheel radius, wheel base, light sensor offset, etc. Additionally static resources such as motors and sensor will also be initialized and stored here.
* `Navigation` - Provides high level way to move the robot around the field. It will provide coordinate and heading based movement. Will be used by the `Pilot` class.
* `Odometer` - Provides internal tracking of the robot's position and heading using tachometers.
* `OdomterCorrection` - Complements the `Odometer` module by correcting it's position and heading using information from the light sensors.
* `WifiConnection` - Listens on WiFi to receive instructions for the competition. Stores the data in `GlobalDefinitions`.
* `BallLauncer` - Uses an unregulated motor to launch a ball. The launch structure is to be decided.
* `USLocalizer` - Orients the robot to zero initial heading according to it's position in a corner.
* `LightLocalizer` - Calculates the robot's actual position using the black gridlines on the floor. Not present in latest version.
* `Pathfinder` - Converts a target position into a series of step for the robot to follow. This simplifies odometry correction math.
* `Pilot` - Follows the instructions given by the pathfinder.
