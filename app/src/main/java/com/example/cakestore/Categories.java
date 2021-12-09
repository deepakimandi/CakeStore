package com.example.cakestore;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.HttpClientStack;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Categories extends AppCompatActivity {

    ImageView iv_person, bgapp, clover;
    LinearLayout textsplash, texthome;
    GridLayout categories;
    Animation frombottom;
    String[] cat_names = {"One", "Two", "Three", "four"};
    int[] cat_images = {R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground};
    List<Category> catList;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        catList = new ArrayList<>();
        final String user_id = getIntent().getExtras().getString("user_id");
        iv_person = findViewById(R.id.iv_person);

        iv_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = "icon";
                ProfileBackground profileBackground = new ProfileBackground(Categories.this);
                profileBackground.execute(type, user_id);
//                Toast.makeText(getApplicationContext(), email, Toast.LENGTH_SHORT).show();
            }
        });
////        for(int i = 0; i < cat_images.length; i++) {
////            catList.add(new Category(cat_names[i], cat_images[i]));
////        }
//        Toast.makeText(getApplicationContext(), "ssssssssssssssssssss", Toast.LENGTH_SHORT).show();
//        new Connection(user_id).execute();
//        String type = "cats";
//        CatBackground catBackground = new CatBackground(this);
//        catBackground.execute(type, email);

//        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);


//        bgapp = (ImageView) findViewById(R.id.bgapp);
//        clover = (ImageView) findViewById(R.id.clover);
//        textsplash = (LinearLayout) findViewById(R.id.textsplash);
//        texthome = (LinearLayout) findViewById(R.id.texthome);
//        categories = (GridLayout) findViewById(R.id.categories);

//        bgapp.animate().translationY(-2200).setDuration(800).setStartDelay(300);
//        clover.animate().alpha(0).setDuration(800).setStartDelay(600);
//        textsplash.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(300);

//        texthome.startAnimation(frombottom);
//        categories.startAnimation(frombottom);


//        myrv.setOnItemClickListener(new RecyclerView().OnItemClickListener() {
//            @Override
//            public void onItemClick(RecyclerView<?> adapterView, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(), "You Clicked " + cat_names[+position], Toast.LENGTH_SHORT).show();
//            }
//        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav);
        bottomNavigationView.setSelectedItemId(R.id.home_nav);

//        removeBadge(bottomNavigationView, R.id.cart);
        BottomNavigationItemView itemView = bottomNavigationView.findViewById(R.id.cart);
        View badge = LayoutInflater.from(this).inflate(R.layout.layout_badge, bottomNavigationView, false);
        new Connection1(user_id).execute();
        text = badge.findViewById(R.id.badge_text_view);
        text.setText("0");
        itemView.addView(badge);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home_nav:
                        return true;
                    case R.id.cart:
                        Intent intent = new Intent(getApplicationContext(), Cart.class);
                        intent.putExtra("user_id", user_id);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.account:
                        Intent i = new Intent(getApplicationContext(), Profile.class);
                        i.putExtra("user_id", user_id);
                        startActivity(i);
                        overridePendingTransition(0, 0);
                }
                return false;
            }
        });

        new Connection(user_id).execute();
    }
    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
    }

    class Connection extends AsyncTask<String, String, String>{
        String user_id;
        public Connection(String user_id) {
            this.user_id = user_id;
        }

        @Override
        protected String doInBackground(String... strings) {

//            String result = "";
            String cat_url = "http://10.0.2.2/CakeStore/categories.php" ;
            try {
//                HttpClient client = new DefaultHttpClient();
//                HttpGet request = new HttpGet();
//                request.setURI(new URI(cat_url));
//                HttpResponse response = client.execute(request);
//                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "iso-8859-1"));
//                StringBuffer stringBuffer = new StringBuffer("");
//                String line = "";
//                while ((line = reader.readLine()) != null){
//                    stringBuffer.append(line);
//
//                }
//                reader.close();
//                result = stringBuffer.toString();
                URL url = new URL(cat_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user_id), "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            }
//            catch (Exception e) {
//                return new String("There exception: " + e.getMessage());
//            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            try {
                JSONObject jsonResult = new JSONObject(result);
                int success = jsonResult.getInt("success");
//                int user_id = jsonResult.getInt("user_id");
                if(success == 1) {
                    JSONArray categories = jsonResult.getJSONArray("categories");
                    for (int i = 0; i < categories.length(); i++) {
                        JSONObject category = categories.getJSONObject(i);
                        int cat_id = category.getInt("cat_id");
                        String cat_name = category.getString("cat_name");
                        catList.add(new Category(cat_name, cat_images[i], cat_id));
                    }
                    RecyclerView myrv = (RecyclerView) findViewById(R.id.rv_id);
                    CatAdapter catAdapter = new CatAdapter(Categories.this, catList, user_id);
                    myrv.setLayoutManager(new GridLayoutManager(Categories.this, 2));
                    myrv.setAdapter(catAdapter);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Access failed!", Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    class Connection1 extends AsyncTask<String, String, String>{
        String user_id;
        public Connection1(String user_id) {
            this.user_id = user_id;
        }

        @Override
        protected String doInBackground(String... strings) {

//            String result = "";
            String cat_url = "http://10.0.2.2/CakeStore/cartnumber.php" ;
            try {
//                HttpClient client = new DefaultHttpClient();
//                HttpGet request = new HttpGet();
//                request.setURI(new URI(cat_url));
//                HttpResponse response = client.execute(request);
//                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "iso-8859-1"));
//                StringBuffer stringBuffer = new StringBuffer("");
//                String line = "";
//                while ((line = reader.readLine()) != null){
//                    stringBuffer.append(line);
//
//                }
//                reader.close();
//                result = stringBuffer.toString();
                URL url = new URL(cat_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user_id), "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            }
//            catch (Exception e) {
//                return new String("There exception: " + e.getMessage());
//            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            text.setText(result);
        }
    }
}
