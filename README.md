# FeedForward Restaurant

MiniApp Restaurant is an Android application that allows restaurants to donate surplus food to non-profit organizations.
This application helps manage and distribute leftover food to minimize waste and aid those in need.

## Features
- User Authentication (Sign In / Sign Up)
- Add, Edit, and Delete Donation Items
- View and Manage Donations
- Google Places Autocomplete for Address Input
- Supports English and Hebrew languages


## Requirements
- Android Studio - Installation guide:
 ```sh
https://developer.android.com/codelabs/basic-android-kotlin-compose-install-android-studio#0
```

- Android SDK
- A physical or virtual Android device (emulator)
- Google API Key for Places

## Installation

### Step 1: Clone the Repository
Clone the repository to your local machine using the following command:
```sh
git clone https://github.com/TzachiPinhas/Miniapp_Restaurant
```

### Step 2: Open in Android Studio
1. Open Android Studio.
2. Click on File > Open.
3. Navigate to the cloned repository and click OK.

### Step 3: Sync Project with Gradle Files
Once the project is opened in Android Studio, click on File > Sync Project with Gradle Files.

## API Key Setup

### Step 1: Obtain a Google API Key

1. Go to the Google Cloud Console.
2. Create a new project or select an existing project.
3. Navigate to APIs & Services > Credentials.
4. Click on Create credentials and select API key.
5. Enable the Places API for your project.

 ### Step 2: Add the API Key to the Project
1. Open local.properties file in the root of your project.
2. Add the following line, replacing YOUR_API_KEY with your actual API key:
 ```sh
MAPS_API_KEY=YOUR_API_KEY
```

### Step 3: Configure the Server Connection
To use the application and connect to the server, access the **'RetrofitClient'** class and change **'BASE_URL'** to the desired value.
The app is currently configured to run using an emulator.

## Application Flow

1. **Registration/Login**: Restaurants begin by registering for an account using an email.
2. **Home Page**: Upon successful login, the home page displays upcoming donations. Restaurants can navigate through different sections via the menu.
3. **Managing Donations**: In the donations section, restaurants can view, reschedule, or cancel their future and past donations.
4. **Order Management**: Restaurants can manage their orders, track the status of donations, and update details if necessary.
5. **Reviews and Ratings**: Restaurants can leave reviews and rate the service they received, which can be viewed by other users.

## Technology Stack
The Restaurant Donation App is built with a range of modern technologies ensuring robust and scalable performance. Here's a breakdown of the main components:

### Frontend
- **Android Studio**: Used for the entire application development, providing a powerful environment for building Android apps.
- **XML**: Utilized for designing the layout of the application.
- **MaterialDateTimePicker by wdullaer**: An external library used to provide a user-friendly date and time picker. This enhances the UI/UX for scheduling donations.

### Backend
- **Custom Backend Server**: Serves as the backend to store all application data including user profiles, donation details, and reviews in real time.
- **Retrofit**: Used for managing API calls to the backend server.


## Screenshots

### Sign in
<img src="https://github.com/TzachiPinhas/Miniapp_Restaurant/assets/141555220/396f1470-2b14-477c-a11d-560490318538" alt="התחברות" width="350" height="700">

### Sign up
<img src="https://github.com/TzachiPinhas/Miniapp_Restaurant/assets/141555220/0f57e920-0b53-4a75-8056-d38aaa305e3b" alt="הרשמהה" width="350" height="700">

### Home Page
<img src="https://github.com/user-attachments/assets/0a41413c-7598-4585-89c1-b4ce87859f50" alt="home" width="350" height="700">

### Add Donation
<img src="https://github.com/TzachiPinhas/Miniapp_Restaurant/assets/141555220/87408ddd-25c8-48eb-a357-ec3cf8e9cd76" alt="הוספת מזון" width="350" height="700">

### Order management
<img src="https://github.com/TzachiPinhas/Miniapp_Restaurant/assets/141555220/8e4d4249-4535-4ff3-ad0d-4843fb905751" alt="כל ההזמנות" width="350" height="700">

### Reviews and Ratings
<img src="https://github.com/TzachiPinhas/Miniapp_Restaurant/assets/141555220/d1f977e7-44f1-492d-b0ac-7d04b297b566" alt="ביקורת" width="350" height="700">




## Contributing
Contributions are welcome! Please fork this repository and submit a pull request for any enhancements or bug fixes.




