# Rules for Code
Always create unit tests for any changes
Always use the latest java 21 compatible java language features
Always ensure code is clean and follows the coding standards
Always run the complete build to verify all changes
Always handle exceptions gracefully and log errors appropriately
Keep the documentation up to date with how to use the app and the features in the app along with tech stack

# Context
This project represents a robot that will play a game of robot wars by calling API's. 
In order to play the game a battle needs to either be created and a battle id received or a battle id can be provided from someone else creating the battle
Once the battle has been started either by this app or by another robot (there must be at least two robots in the game)
* then the robot can execute apis to 
  * move around the arena,
  * use radar to scan the surroundings
  * fire a lazer to attempt to hit other robots
* The robot should also keep it's state upto date with the state of the robot on the server
  * That is, if it's crashed, or been hit, or been destroyed
  * What it's current hit points are.
The swagger for the API documenation is avaiable at https://api.rwars.steven-webber.com/swagger-ui/
