package com.cpssd.organizr.activity;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.cpssd.organizr.pojos.Events.Calendars;
import com.cpssd.organizr.pojos.Events.Event;
import com.cpssd.organizr.pojos.Events.Lecture;
import com.cpssd.organizr.pojos.Events.Lectures;
import com.cpssd.organizr.pojos.Finance.BalanceUpdate;
import com.cpssd.organizr.pojos.Finance.Transaction;
import com.cpssd.organizr.pojos.Maps.NearbyStop;
import com.cpssd.organizr.pojos.Offers.Offer;
import com.cpssd.organizr.pojos.Offers.SpecialOffers;
import com.cpssd.organizr.pojos.Recipes.Recipe;
import com.cpssd.organizr.pojos.Recipes.RecipeDetail;
import com.cpssd.organizr.pojos.Recipes.Recipes;
import com.cpssd.organizr.pojos.Settings.Settings;
import com.cpssd.organizr.pojos.Settings.Societies;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Created by Niall on 11/11/2016.
 * Handles all the network activity.
 */


public class NetworkController extends Application {

    private static final String TAG = NetworkController.class.getSimpleName();

    Context applicationContext = LoginActivity.getContextOfApplication();
    private SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
    SharedPreferences.Editor editor = sharedPreferences.edit();

	// The main helper method of the network controller class. This simply takes in a URl and gets the JSON response from it,
	// which is used in other functions that parse the json response and turn them into POJO's.
    public String getJson(String myUrl) {
        try {
            URL url = new URL(myUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                Log.d(TAG, stringBuilder.toString());
                return stringBuilder.toString();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
                return null;
            }
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }






    String postSocietyLoginDetails(String college, String email, String password) {
        URL url;
        StringBuilder sb = new StringBuilder();
        sb.append("");
        try {
            url = new URL("https://tpot.space/soc/auth");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.addRequestProperty("email", "dcusubaquaclub@gmail.com");
            conn.addRequestProperty("college", "DCU");
            conn.addRequestProperty("password", "A8w6KkWmUL");
            conn.connect();
            if(conn.getHeaderField("email")!=null){
                Log.d(TAG, conn.getHeaderField("email"));
            }
            int STATUS = conn.getResponseCode();
            Log.e(TAG, Integer.toString(STATUS));
            String line;
            BufferedReader reader;
            if (STATUS == 200 || STATUS == 201) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            while ((line = reader.readLine()) != null) {
                sb.append(line).append("");
            }

            Log.e(TAG, "response: " + sb.toString());

            return sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
        return "none";
    }


	
	// This is the method that creates a new event and sends it to the server, 
	// to be sent to Google, to be added to the persons main colendar.
	// Takes in an Event object and the user's sub.
    void postEvent(Event event, String sub) {
        URL url;
        StringBuilder sb = new StringBuilder();
        sb.append("");
        try {
            url = new URL("https://tpot.space/tasks/insert");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            JSONObject resObject = new JSONObject();
            resObject.put("userId", sub);
            resObject.put("calendarId", "primary");
            resObject.put("summary", event.getSummary());
            resObject.put("location", "Dublin/Ireland");
            resObject.put("description", event.getDescription());
            resObject.put("timeMin", event.getStart().getDateTime());
            resObject.put("timeMax", event.getEnd().getDateTime());
            OutputStream os = conn.getOutputStream();
            Log.d(TAG, resObject.toString());
            os.write(resObject.toString().getBytes("UTF-8"));
            conn.connect();

            int STATUS = conn.getResponseCode();
            Log.e(TAG, Integer.toString(STATUS));
            String line;
            BufferedReader reader;
            if (STATUS == 200 || STATUS == 201) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            while ((line = reader.readLine()) != null) {
                sb.append(line).append("");
            }

            Log.e(TAG, "response: " + sb.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void postCoursePrefs(String college, String course, int year, String userId) {
        URL url;
        StringBuilder sb = new StringBuilder();
        sb.append("");
        try {
            url = new URL("https://tpot.space/settings/course");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            JSONObject resObject = new JSONObject();
            resObject.put("college", college);
            resObject.put("course", course);
            resObject.put("year", year);
            resObject.put("userId", userId);
            OutputStream os = conn.getOutputStream();
            Log.d(TAG, resObject.toString());
            os.write(resObject.toString().getBytes("UTF-8"));
            conn.connect();
            int STATUS = conn.getResponseCode();
            Log.e(TAG, Integer.toString(STATUS));
            String line;
            BufferedReader reader;
            if (STATUS == 200 || STATUS == 201) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            while ((line = reader.readLine()) != null) {
                sb.append(line).append("");
            }

            Log.e(TAG, "response: " + sb.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
	
	
	// This is the method that assigns a chosen recipe to a chosen day.
	// Takes in a chosen recipe object, the user's sub and the day that it should be assigned to, in lowercase.
    public void postDay(Recipe recipe, String sub, String day){
        URL url;
        StringBuilder sb = new StringBuilder();
        sb.append("");
        String id = String.valueOf(recipe.getId());
        String title = String.valueOf(recipe.getTitle());
        String readyInMinutes = String.valueOf(recipe.getReadyInMinutes());
        String image = recipe.getImage();
        try {
            url = new URL("https://tpot.space/recipes/insert");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            JSONObject entireObject = new JSONObject();
            JSONObject breakfast = new JSONObject();
            JSONObject meal = new JSONObject();
            breakfast.put("id", id);
            breakfast.put("title", title);
            breakfast.put("readyInMinutes", readyInMinutes);
            breakfast.put("image", image);
            meal.put("breakfast", breakfast);
            entireObject.put("userId", sub);
            entireObject.put("day", day);
            entireObject.put("meal", meal);
            OutputStream os = conn.getOutputStream();
            os.write(entireObject.toString().getBytes("UTF-8"));
            conn.connect();

            int STATUS = conn.getResponseCode();
            Log.e(TAG, Integer.toString(STATUS));
            String line;
            BufferedReader reader;
            if (STATUS == 200 || STATUS == 201) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            while ((line = reader.readLine()) != null) {
                sb.append(line).append("");
            }

            Log.e(TAG, "response: " + sb.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
	
	// This is the method used in user login that sends the auth tokens generated to the Google server to get the refresh tokens.
	// This is also the original function that gets the users sub and stores it in sharedprefs.

    String sendToken(String idToken, String aud, String authCode) throws UnsupportedEncodingException {

        URL url;
        StringBuilder sb = new StringBuilder();
        sb.append("");
        try {
            url = new URL("https://tpot.space/auth");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("idToken", idToken));
            params.add(new BasicNameValuePair("aud", aud));
            params.add(new BasicNameValuePair("authCode", authCode));
            Log.d(TAG, idToken+aud+authCode);
            StringBuilder result = new StringBuilder();
            boolean first = true;
            for (NameValuePair pair : params) {
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
            }

            writer.write(String.valueOf(result));
            writer.flush();
            writer.close();
            os.close();
            conn.connect();

            int STATUS = conn.getResponseCode();
            Log.d(TAG, Integer.toString(STATUS));
            String line;
            BufferedReader reader;
            if (STATUS == 200 || STATUS == 201) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));

            }

            while ((line = reader.readLine()) != null) {
                sb.append(line).append("");
            }
            String token = sb.toString();
			Log.d(TAG, token);
            try {
                JSONObject response = new JSONObject(token);
                Log.d(TAG, response.toString());
                // String sub = response.getString("userId");
                String sub = "111589791051790050333";
                editor.putString("sub", "111589791051790050333");
                editor.apply();
                Log.d(TAG, "THIS IS THE SUB"+sub);
                return sub;
            } catch (JSONException e) {

                Log.e(TAG, e.toString());

            }

            Log.d(TAG, token);
            return token;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
        return null;

    }
	
	// This function gets search results based on a query that the user gives.
	// Uses the getJson function to do the majotity of the work and then returns the parsed data.

    public ArrayList<Recipe> getRecipes(String query, String SUB) {
        String API_URL = "https://tpot.space/recipes/search/"+SUB+'/';
        String jsonResponse = getJson(API_URL+query);
        if(jsonResponse!=null) {
            Log.d(TAG + "GetRecipes", jsonResponse);
        }
        Gson gson = new Gson();
        Recipes recipesData = gson.fromJson(jsonResponse, Recipes.class);
        try {
            return recipesData.getRecipes();
        }
        catch (NullPointerException e){
            return new ArrayList<>();
        }


    }


    public Settings getSettings(String userId){
        String API_URL = "https://tpot.space/settings/"+userId;
        Log.d(TAG, API_URL);
        String jsonResponse = getJson(API_URL);
        if(jsonResponse!=null) {
            Log.d(TAG + "GetSettings", jsonResponse);
        }
        Gson gson = new Gson();
        Settings settings = gson.fromJson(jsonResponse, Settings.class);
        try {
            return settings;
        }
        catch (NullPointerException e){
            return new Settings();
        }
    }

    List<String> getSocs(String SUB){
        String API_URL = "https://tpot.space/soc/"+SUB;
        Log.d(TAG, API_URL);
        String jsonResponse = getJson(API_URL);
        if(jsonResponse!=null) {
            Log.d(TAG + "GetSocs", jsonResponse);
        }
        Gson gson = new Gson();
        Societies societies = gson.fromJson(jsonResponse, Societies.class);
        List<String> socs = societies.getSocieties();
        return socs;

    }



    public String createSocietyEvent(String summary, String description, String start, String end, String location){
        return "Hhhh";
    }

    public ArrayList<Offer> getOffers(String shop){
        String API_URL = "https://tpot.space/specials";
        String jsonResponse = getJson(API_URL);
        if (Objects.equals(jsonResponse, "{}") || jsonResponse == null){
            return new ArrayList<Offer>();
        }
        Gson gson = new Gson();
        SpecialOffers specialOffers = gson.fromJson(jsonResponse, SpecialOffers.class);
        ArrayList<Offer> offerArrayList = new ArrayList<>();
        if(Objects.equals(shop, "tesco")){
            List<Offer> offers = specialOffers.getTesco();
            for(Offer offer : offers){
                offerArrayList.add(offer);
            }
        }
        else{
            List<Offer> offers = specialOffers.getSupervalu();
            for(Offer offer : offers){
                offerArrayList.add(offer);
            }
        }
        return offerArrayList;
    }

	//Given a recipe id, returns a much more detailed jsonResponse with all the details of the recipe chosen.

    public RecipeDetail getRecipeDetail(String id){
        String API_URL = "https://tpot.space/recipes/info/";
        String jsonResponse = getJson(API_URL+id);
        if(jsonResponse != null){
            Log.d(TAG+"RecipeDetail", jsonResponse);
        }
        Gson gson = new Gson();
        return gson.fromJson(jsonResponse, RecipeDetail.class);
    }
	
	// Given a day, returns all the recipes assigned to that day.

    public ArrayList<Recipe> getDay(String sub, String day) throws JSONException {
        String API_URL = "https://tpot.space/recipes/getDay/";

        String jsonResponse = getJson(API_URL+sub+"/"+day);
        Log.d("getDay@Network", API_URL+sub+"/"+day);
        Gson gson = new Gson();
        JSONArray dayRecipes;
        try {
            dayRecipes = new JSONArray(jsonResponse);
        }
        catch(NullPointerException e){
            Log.d(TAG, e.getMessage());
            return new ArrayList<>();
        }
        JSONObject today = dayRecipes.getJSONObject(0);
        JSONObject breakfast = today.getJSONObject("breakfast");
        breakfast.put("imageUrls", new JSONArray());
        Log.d(TAG, String.valueOf(breakfast));
        Recipe breakfastRecipe = gson.fromJson(String.valueOf(breakfast), Recipe.class);
        ArrayList<Recipe> recipes = new ArrayList<>();
        recipes.add(breakfastRecipe);
        return recipes;

    }
	
	// This is the main function that gets the events from the google calendars. uses the getJson method
	// and then uses GSON to parse the data into POJO's, and returns an arrayList of events.

    public ArrayList<Event> getCalendarEvents(String API_URL){
        String jsonResponse = getJson(API_URL);
        ArrayList<Event> events = new ArrayList<>();
        try {
            Gson gson = new Gson();
            Calendars calendarData = gson.fromJson(jsonResponse, Calendars.class);
            List<Event> calendarDataEventData = calendarData.getEventData();

            for (Event event : calendarDataEventData){
                    events.add(event);
            }
            return events;
        } catch (Throwable t) {
            String TAG = ".Event";
            Log.e(TAG, "Could not parse malformed JSON:"+jsonResponse);
        }
        return events;
    }

    public ArrayList<Lecture> getLectureData(String API_URL){
        String jsonResponse = getJson(API_URL);
        ArrayList<Lecture> lectures = new ArrayList<>();
        try {
            Gson gson = new Gson();
            Lectures calendarData = gson.fromJson(jsonResponse, Lectures.class);
            List<Lecture> lectureList = calendarData.getLectures();

            for (Lecture lecture : lectureList){
                lectures.add(lecture);
            }
            return lectures;
        } catch (Throwable t) {
            String TAG = ".Event/CollegeData";
            Log.e(TAG, "Could not parse malformed JSON:"+jsonResponse);
        }
        return lectures;

    }

    public ArrayList<Transaction> getDayTransactions(String URL, String date, String sub) throws NullPointerException, JSONException {
        String url = URL+(date.replace("-", ""))+"/"+sub;

        Log.d(TAG, url);
        Gson gson = new Gson();
        String jsonResponse = getJson(url);
        JSONObject resObject;
        JSONArray todaysTrans;
        ArrayList<Transaction> transactionArray = new ArrayList<>();
        if(jsonResponse!="[]") {
            try {
                resObject = new JSONObject(jsonResponse);
                todaysTrans = (JSONArray) resObject.get(date);
            } catch (NullPointerException e) {
                Log.d(TAG, "No transactions found for" + date);
                return transactionArray;
            }


        }

        else {
            return transactionArray;
        }

        for(int i = 0; i < todaysTrans.length(); i++){
            JSONObject object = todaysTrans.getJSONObject(i);
            Transaction transaction = gson.fromJson(String.valueOf(object), Transaction.class);
            transactionArray.add(transaction);
        }
        return transactionArray;
    }

    public void callGetWeek(String sub, String weekStart){
        String API_URL = "https://tpot.space/finance/week/"+weekStart+"/"+sub;
        Log.d(TAG, API_URL);
        String jsonResponse = getJson(API_URL);
        Log.d(TAG, jsonResponse);

    }

    public void addSocEvent(){
        URL url;
        try{
            url = new URL("https://tpot.space/soc/insert");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.addRequestProperty("email", "dcusubaquaclub@gmail.com");
            connection.addRequestProperty("college", "DCU");
            connection.addRequestProperty("password", "A8w6KkWmUL");
            connection.setUseCaches(true);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            JSONObject addSocObject = new JSONObject();
            JSONObject detailsObj = new JSONObject();
            addSocObject.put("name", "Sub Aqua");
            detailsObj.put("summary", "test");
            detailsObj.put("description", "TEST");
            detailsObj.put("location", "HG21");
            detailsObj.put("start", "12:00");
            detailsObj.put("end", "13:00");
            detailsObj.put("date", "20170421");
            addSocObject.put("event", detailsObj);


            Log.d(TAG, addSocObject.toString());
            OutputStream os = connection.getOutputStream();
            os.write(addSocObject.toString().getBytes("UTF-8"));
            connection.connect();

            int STATUS = connection.getResponseCode();
            Log.d(TAG, Integer.toString(STATUS));
            String line;


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void UpdateBalance(BalanceUpdate balanceUpdate){
        URL url;
        try{
            url = new URL("https://tpot.space/finance/insert");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setUseCaches(true);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            JSONObject BalanceUpdateObj = new JSONObject();
            JSONObject InputObj = new JSONObject();
            JSONObject DetailsObj = new JSONObject();
            DetailsObj.put("amount", balanceUpdate.getInput().getDetails().getAmount());
            DetailsObj.put("summary", balanceUpdate.getInput().getDetails().getSummary());
            InputObj.put("weekStart", balanceUpdate.getInput().getWeekStart());
            InputObj.put("date", balanceUpdate.getInput().getDate());
            InputObj.put("amount", balanceUpdate.getInput().getAmount());
            InputObj.put("details", DetailsObj);
            BalanceUpdateObj.put("userId", balanceUpdate.getUserId());
            BalanceUpdateObj.put("input", InputObj);
            Log.d(TAG, BalanceUpdateObj.toString());
            OutputStream os = connection.getOutputStream();
            os.write(BalanceUpdateObj.toString().getBytes("UTF-8"));
            connection.connect();

            int STATUS = connection.getResponseCode();
            Log.d(TAG, Integer.toString(STATUS));
            String line;


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<NearbyStop> getNearbyStops(String latitude, String longditude) throws JSONException {
        String API_URL = "https://tpot.space/transport/"+latitude+"/"+longditude;
        Gson gson = new Gson();
        String jsonResponse = getJson(API_URL);
        ArrayList<NearbyStop> nearbyStopArrayList = new ArrayList<>();
        JSONArray stopArray;
        try {
            stopArray = new JSONArray(jsonResponse);
        }
        catch (NullPointerException e){
            return new ArrayList<>();
        }
        for(int i = 0; i < stopArray.length();i++){
            JSONObject stop = stopArray.getJSONObject(i);
            NearbyStop nearbyStop = gson.fromJson(stop.toString(), NearbyStop.class);
            nearbyStopArrayList.add(nearbyStop);
        }
        Log.d(TAG, String.valueOf(nearbyStopArrayList.get(0)));
        return nearbyStopArrayList;
    }




}




