Route Calculator 
Description:
The Route Calculator is an Android application designed to provide users with information about their journey, including details about current stops, distance covered, remaining distance, and the next stop along the route. It offers features such as loading stops, switching between kilometers and miles for distance units, and navigating to the next stop.
Features:
Text Box: Displays information about stops along the journey.
Load Stops: Allows users to load a list of stops with their respective distances.
Next Stop: Allows users to navigate to the next stop in the journey.
Display:Provides information about the current stop, total distance covered, remaining distance, and a progress bar indicating the journey's progress.
Implementation:-
1. MainContent Composable:
Initializes the stops, current stop index, distance unit, total stops, total distance covered, and remaining distance.
Displays the Text Box, Progress Bar, Switch Units button, Next Stop button, and Load Stops button.
2. LoadStopsButton Composable: Displays a button to load stops.
Upon clicking, loads a list of stops with distances and displays them in an AlertDialog.
3. ListTypeTextBox Composable: Displays the type of list (Normal list or Lazy list) based on the number of stops.
4. ProgressBar Composable: This shows a linear progress indicator representing the journey's progress.
5. TextBox Composable: Displays information about the current stop, total distance covered, next stop, and remaining distance.
6. ConvertUnitsButton Composable: Allows users to switch between kilometers and miles for distance units.
7. NextStopButton Composable: Navigate to the next stop in the journey.


Instructions for Use:-
Loading Stops:-Click the Load Stops button to load a list of stops with distances.

Switching Units: Use the Switch Units button to switch between kilometers and miles for distance units.

Navigating to the Next Stop:-Click the Next Stop button to navigate to the next stop in the journey.

Displaying Information: Information about the current stop, total distance covered, remaining distance, and progress bar is displayed on the screen.