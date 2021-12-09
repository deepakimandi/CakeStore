package com.example.cakestore;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {
    TextView text;
    List<CartItem> cartList;
    int[] productImages = {R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground};
    Button btn_order ;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        btn_order = findViewById(R.id.btn_order);
        final String user_id = getIntent().getExtras().getString("user_id");
        cartList = new ArrayList<>();


        BottomNavigationView bottomNavigationView = findViewById(R.id.nav);
        bottomNavigationView.setSelectedItemId(R.id.cart);

        BottomNavigationItemView itemView = bottomNavigationView.findViewById(R.id.cart);
        View badge = LayoutInflater.from(this).inflate(R.layout.layout_badge, bottomNavigationView, false);
        new Connection8(user_id).execute();
        text = badge.findViewById(R.id.badge_text_view);
        text.setText("0");
        itemView.addView(badge);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case R.id.cart:
                        return true;
                    case R.id.account:
                        Intent i = new Intent(getApplicationContext(), Profile.class);
                        i.putExtra("user_id", user_id);
                        startActivity(i);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home_nav:
                        Intent intent = new Intent(getApplicationContext(), Categories.class);
                        intent.putExtra("user_id", user_id);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });



//        onBackPressed(email);
        new Connection(user_id).execute();
    }

//    public void onBackPressed(String email){
//
//    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Please click the Home button", Toast.LENGTH_SHORT).show();
    }



    class Connection extends AsyncTask<String, String, String> {
        String user_id;
        public Connection(String user_id ) {
            this.user_id = user_id;
        }



        @Override
        protected String doInBackground(String... strings) {
//            String result = "";
            String retCart_url = "http://10.0.2.2/CakeStore/getCart.php";

            try {
                URL url = new URL(retCart_url);
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
                    final JSONArray cartItems = jsonResult.getJSONArray("cartList");
                    int total_price = 0;
                    for (int i = 0; i < cartItems.length(); i++) {
                        JSONObject cartItem = cartItems.getJSONObject(i);
                        int id = cartItem.getInt("id");
                        int p_id = cartItem.getInt("p_id");
                        int qty = cartItem.getInt("qty");
                        int cost = cartItem.getInt("cost");
                        String product_title = cartItem.getString("product_title");
                        total_price = total_price + cost * qty;
                        cartList.add(new CartItem(product_title, productImages[i], qty, cost, id));
                    }
                    RecyclerView myrv = (RecyclerView) findViewById(R.id.rv_cartlist_id);
//                    TextView emptyView = (TextView) findViewById(R.id.empty_view);


//                        myrv.setVisibility(View.VISIBLE);
//                        emptyView.setVisibility(View.GONE);
                        btn_order.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(cartItems.length() == 0){
                                    Toast.makeText(getApplicationContext(), "Your Cart is Empty! Go to Home", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Intent intent = new Intent(getApplicationContext(), Order.class);
                                    intent.putExtra("user_id", user_id);
                                    startActivity(intent);
                                }
                            }
                        });

                        TextView totPrice = (TextView) findViewById(R.id.txt_price);
                        totPrice.setText("Rs." + String.valueOf(total_price) + "/-");

//                        CartAdapter cartAdapter = new CartAdapter(Cart.this, cartList, user_id);
//                        myrv.setLayoutManager(new GridLayoutManager(Cart.this, 1));
//                        myrv.setAdapter(cartAdapter);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(Cart.this);
                    CartAdapter cartAdapter = new CartAdapter(Cart.this, cartList, user_id);
                    myrv.setAdapter(cartAdapter);
                    myrv.setLayoutManager(layoutManager);

                }
                else {
                    Toast.makeText(getApplicationContext(), "Cart is Empty!", Toast.LENGTH_LONG).show();

                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }


    }

    class Connection8 extends AsyncTask<String, String, String>{
        String user_id;

        public Connection8(String user_id) {
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
