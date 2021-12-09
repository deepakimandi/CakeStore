package com.example.cakestore;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.HttpClientStack;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.http.HttpResponse;
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

public class FoodMenu extends AppCompatActivity {

    TextView heading_cat, textOne;
    ImageView iv_person, bgapp, clover;
    LinearLayout textsplash, texthome;
    GridLayout categories;
    Animation frombottom;
    String[] product_names = {"One", "Two", "Three", "four"};
    int[] product_images = {R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground};
    List<Product> productList;
    CardView productCard;
    String u_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu);
        productList = new ArrayList<>();
        final String user_id = getIntent().getExtras().getString("user_id");
        u_id = user_id;
//        final String email = getIntent().getExtras().getString("email");
        final String cat_name = getIntent().getExtras().getString("cat_name");
        heading_cat = (TextView) findViewById(R.id.heading_cat) ;
//        textOne = (TextView) findViewById(R.id.textOne) ;
        heading_cat.setText(cat_name);
//        iv_person = findViewById(R.id.iv_person);
//        final String cat_name = getIntent().getExtras().getString("Value");
////        iv_person.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String type = "icon";
//                ProfileBackground profileBackground = new ProfileBackground(FoodMenu.this);
//                profileBackground.execute(type, cat_name);
////                Toast.makeText(getApplicationContext(), email, Toast.LENGTH_SHORT).show();
//            }
//        });
//        for(int i = 0; i < cat_images.length; i++) {
//            catList.add(new Category(cat_names[i], cat_images[i]));
//        }

        new Connection(user_id).execute();


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

//        BottomNavigationView bottomNavigationView = findViewById(R.id.nav);
//        bottomNavigationView.setSelectedItemId(R.id.home);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()){
//                    case R.id.home:
//                        Intent intent1 = new Intent(getApplicationContext(), Categories.class);
//                        intent1.putExtra("user_id", user_id);
//                        startActivity(intent1);
//                        overridePendingTransition(0, 0);
//                        return true;
//                    case R.id.cart:
//                        Intent intent2 = new Intent(getApplicationContext(), Cart.class);
//                        intent2.putExtra("user_id", user_id);
//                        startActivity(intent2);
//                        overridePendingTransition(0, 0);
//                        return true;
//                    case R.id.account:
//                        Intent intent3 = new Intent(getApplicationContext(), Profile.class);
//                        intent3.putExtra("user_id", user_id);
//                        startActivity(intent3);
//                        overridePendingTransition(0, 0);
//                        return true;
//                }
//                return false;
//            }
//        });
        FloatingActionButton fab = findViewById(R.id.fab);
//        new Connection1(user_id).execute();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), Cart.class);
                intent2.putExtra("user_id", user_id);
                startActivity(intent2);
//                overridePendingTransition(0, 0);
            }
        });
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), Categories.class);
        intent.putExtra("user_id", u_id);
        startActivity(intent);
    }

    class Connection extends AsyncTask<String, String, String>{
        String user_id;

        public Connection(String user_id) {
            this.user_id = user_id;
        }

        @Override
        protected String doInBackground(String... strings) {
            String menu_url = "http://10.0.2.2/CakeStore/menu.php";
            final int cat_id = getIntent().getIntExtra("cat_id", 0);
            try {

                URL url = new URL(menu_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("cat_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(cat_id), "UTF-8");
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
            } catch (MalformedURLException e) {
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
                if(success == 1) {
                    JSONArray menu = jsonResult.getJSONArray("menu");
                    for (int i = 0; i < menu.length(); i++) {
                        JSONObject item = menu.getJSONObject(i);
                        int product_id = item.getInt("product_id");
                        int product_cat = item.getInt("product_cat");
//                        int product_image = item.getInt("product_image");
                        int product_price = item.getInt("product_price");
                        String product_title = item.getString("product_title");
                        String product_desc = item.getString("product_desc");
                        productList.add(new Product(product_title, product_desc, product_id, product_cat, product_images[i], product_price));
                    }
                    RecyclerView myrv = (RecyclerView) findViewById(R.id.rv_menu_id);
                    ProductAdapter productAdapter = new ProductAdapter(FoodMenu.this, productList, user_id);
//                    myrv.setLayoutManager(new GridLayoutManager(FoodMenu.this, 1));
                    myrv.setLayoutManager(new LinearLayoutManager(FoodMenu.this, LinearLayoutManager.VERTICAL, false));
                    myrv.setAdapter(productAdapter);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Retrieve failed!", Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }
    }

//    class Connection1 extends AsyncTask<String, String, String>{
//        String user_id;
//        public Connection1(String user_id) {
//            this.user_id = user_id;
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//
////            String result = "";
//            String cat_url = "http://10.0.2.2/CakeStore/cartnumber.php" ;
//            try {
////                HttpClient client = new DefaultHttpClient();
////                HttpGet request = new HttpGet();
////                request.setURI(new URI(cat_url));
////                HttpResponse response = client.execute(request);
////                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "iso-8859-1"));
////                StringBuffer stringBuffer = new StringBuffer("");
////                String line = "";
////                while ((line = reader.readLine()) != null){
////                    stringBuffer.append(line);
////
////                }
////                reader.close();
////                result = stringBuffer.toString();
//                URL url = new URL(cat_url);
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.setDoOutput(true);
//                httpURLConnection.setDoInput(true);
//                OutputStream outputStream = httpURLConnection.getOutputStream();
//                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                String post_data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user_id), "UTF-8");
//                bufferedWriter.write(post_data);
//                bufferedWriter.flush();
//                bufferedWriter.close();
//                outputStream.close();
//                InputStream inputStream = httpURLConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
//                String result = "";
//                String line = "";
//                while ((line = bufferedReader.readLine()) != null) {
//                    result += line;
//                }
//                bufferedReader.close();
//                inputStream.close();
//                httpURLConnection.disconnect();
//                return result;
//            }
////            catch (Exception e) {
////                return new String("There exception: " + e.getMessage());
////            }
//            catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result){
//            textOne.setText(result);
//        }
//    }
}
