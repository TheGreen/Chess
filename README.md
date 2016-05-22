# Chess
Chess implementation for Computer Science Class, final year.

Files in ```edu.gymneureut``` are files for this project only,
Files in ```de.janchristiangruenhage``` are probably used again later.

## How the game runs:
The class Game calls the Players,
for them to provide the turn that they want to execute.
The Player then generates a list of possible turns by calling each figure
for generating a list of possible turns. In the end the Player passes
the generated list to one of the controllers. This controller then chooses
the turn to execute.