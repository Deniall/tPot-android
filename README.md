
# Documentation of tPot.io Android App Code

## Accessing the source code
1. Download and install Android Studio for free.
2. On startup, choose "Import from VCS" and choose GitHub. Point it at this repository.
3. Android Studio will take about 20 minutes to build everything and generate all the stuff needed to load the project correctly. It will ask you to install certain packages and dependencies, and may take quite a bit of disk space.
4. Once everything is loaded, open the folder 'src'. This contains all the apps code.

## Understanding the source code
 1. Once in 'src', go to 'main'. All of the Java code is in **/java/**, all of the layout XML files are in **/res/**.
 
 ### /java/
 In /java/, there are 5 directories; /activity/, /adapters/, /fragment/, /other/, and /pojos/.
 
 ### **/activity/** contains all the Java classes that make up the seperate activities in the app.
 
 **MainActivity** is the meat of the app. It controls the NavBar and loads the different fragments into the view.
 
 **LoginActivity** is the first initial activity. It authenticates the user via Google, and lets a society log in.
 
 **MyIntro** is the class for the first-launch introduction screen that takes a users college details and cuisine preferences.
 
 **NetworkController** controls all of the network calls and data parsing, which must be handled in an ASyncTask.
 
  All other activities are simply just setting up the layout XML files for various screens that needed their own activity and handling any   interactions with the UI elements on those screens. Any network activity can be found in methods extending ASyncTask. Any layout setting   and view-based tasks are done in either onCreate lifecycle methods or onCreateView lifecycle methods (which usually are only found in     Fragments).
 
 ### **/adapters/** contains all the adapter Java classes that handle processing data into RecyclerViews or ListViews.
 
 Their task is to take in a Dataset and provide methods for taking UI elements that make up a RecyclerView or ListView and processing various elements of the datasets objects into these UI elements in an asyncronous fashion. Everywhere you see a reyclerview in the app (which is about 3 times on every screen) there is an adapter to handle the elements in that.
 
 ### **/fragment/** contains all the fragment Java classes; fragments are like Dynamic activities that simply swap Views.
 
 All of the fragments do a lot of work. In their onCreate methods, variables are initialized; in the onCreateView methods, all of the UI elements are initialized and given corresponding data or adapters based on the response of network calls fired in ASyncTasks. Every network call is seperate to free up the UI thread and it provides a nice smooth experience. Think of Activities as a window and Fragments as a pane of that window; these fragments are a pain of the MainActivity window.
 
 ### **/other/** contains any misc. Java classes that provide handy methods or don't belong in another catagory.
 
 Contained in here is a class for rescaling profile photos, a couple of RecyclerView onSwipeListener classes, and the privacy policy/ about us activities.
 
 ### **/pojos/** contains all the *P*lain *O*'l *J*ava *O*bjects that Gson uses to parse Json from API calls into nice objects.
 
 These are all basic objects; some class variables, getters and setters, and a couple of constructors here and there. These are used when NetworkController makes an API call and gets a jsonResponse, we use the Gson method fromJson to parse it into a Java object which makes everybodies lives much easier.
 
  ### /res/
 In /res/, there are only 6 folders of interest (others just contain versions of the normal files that are rescaled for other resolutions). These are /color/, /drawable/, /layout/, /menu/, /values/ and /xml/.
 
 #### /color/
 
 Contains the XML defining which colors the app should use for different themes.
 
 #### /drawable/
 
 Contains all the icons and image files that are displayed in the app such as menu item indicators.
 
 ### **/layout/**
 
 This is the main folder in /res/. It contains all the XML layout files for every activity, fragment, popup dialog, recyclerview item and everything else in the app. It defines what UI elements go where and creates placeholder values for the elements for the Activity to programatically replace and modify.
 
 #### /menu/
 
 Contains all the layout XML files for any menu's you see in the app, such as the NavBar menu and assigning a recipe to a day.
 
 ### /values/
 
 Contains any strings, arrays, dimensions, color codes or styles that the app may want to use. The app can access these resources by using getResources, eg. getApplication().getResources().getDrawable(R.id.string.user_name).
 
 #### /xml/
 
 Contains the XML for the preferences/settings activity (since android requires them to be designed differently as they are styled via the SharedPreferences at the time of the activity loading).
 
 
 
 ## Other files
 
 ### AndroidManifest.xml 
 
 Tells the compiler what activities are included in the app, what theme to apply to them, what label they have, and other filters.
 
 ### build.gradle
 
 Keeps a list of all the packages and dependencies needed for the app to compile.
 
 
 
 
  
 
 
 


