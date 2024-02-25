# Backlog
1. As a user (of the application) I can see my current location on a map 
2. As a user I can save a short note at my current location
3. As a user I can see notes that I have saved at the location they were saved on the map
4. As a user I can see the location, text, and user-name of notes other users have saved
5. As a user I have the ability to search for a note based on contained text or user-name

# Technology Stack
Android Native Application

## Requirement
1. The Android native application should be written in Kotlin and should target minimum API level 26.
2. You may not use WebView to implement any aspect of the application.
3. Your application should perform well on the latest Google Pixel device.
4. Source must be developed and runnable in Android Studio .

## How to approach the problem
1. Application used by users -> Needs screen & Authentication feature
2. See your current location on the map -> Need a map display screen and feature to get your current location
3. Save a short note at the current location -> Need to display a dialog to add notes and save note feature
4. View your saved notes on the map -> The green markers will show the notes you have saved, the blue marker is your current location
5. View other people's notes on the map -> Yellow markers are other people's notes
6. Search notes by keyword -> Need screen and search feature 
7. Can view own note lists -> Need screen & feature to get entire list of notes according to user's uid 
8. View detailed location of a note -> There should be a note detail display screen on the map

## How much time you spent on which aspects of the application

1. Create structure project & register Google Map API key - (4h)
2. Create Authentication for Application - (4h)
3. Backlog task 1: As a user (of the application) I can see my current location on a map - (3h)
4. Backlog task 2: As a user I can save a short note at my current location - (3h)
5. Backlog task 3: As a user I can see notes that I have saved at the location they were saved on the map - (2h)
6. Backlog task 4: As a user I can see the location, text, and user-name of notes other users have saved - (3h)
7. Backlog task 5: As a user I have the ability to search for a note based on contained text or user-name - (3h)

## Issue
1. AppHelper: Save user status to SharedPreferences with object FirebaseUser

# Note
1. Replace the value of GOOGLE_MAPS_API_KEY in the two files `local.defaults.properties` and `secrets.properties` with the key provided.
```
GOOGLE_MAPS_API_KEY=<PROVIDED_KEY>
```

# APK
1. Link APK here: https://drive.google.com/file/d/1fxLM_JZL_itaylljMuUTA7mPyfoodKTd/view?usp=sharing
