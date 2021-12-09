package com.example.cakestore;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class Done extends AppCompatActivity {
    TextView payment;
    Button back_home, cancel_order;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);
        payment = (TextView) findViewById(R.id.payment);
        back_home = (Button) findViewById(R.id.back_home);
        cancel_order = (Button) findViewById(R.id.cancel_order);
        final String user_id = getIntent().getExtras().getString("user_id");
        final String time_stamp = getIntent().getExtras().getString("time_stamp");
        new Connection(user_id, time_stamp).execute();
        back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Categories.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });

        cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder cancelAlertBuilder = new AlertDialog.Builder(Done.this);
                cancelAlertBuilder.setTitle("Confirm Cancel..!");
                cancelAlertBuilder.setIcon(R.drawable.ic_cancel_black_24dp);
                cancelAlertBuilder.setMessage("Do you really want to CANCEL the Order");
                cancelAlertBuilder.setCancelable(false);
                cancelAlertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new Conection1(user_id, time_stamp).execute();
                        Intent intent = new Intent(getApplicationContext(), Cancel.class);
                        intent.putExtra("user_id", user_id);
                        startActivity(intent);
                    }
                });
                cancelAlertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"Order not cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog cancelAlert = cancelAlertBuilder.create();
                cancelAlert.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Please press Home button to go back", Toast.LENGTH_LONG).show();
    }

    class Connection extends AsyncTask<String, String, String> {
        String user_id;
        String time_stamp;

        public Connection(String user_id, String time_stamp) {
            this.time_stamp = time_stamp;
            this.user_id = user_id;
        }

        @Override
        protected String doInBackground(String... strings) {

//            String result = "";
            String cat_url = "http://10.0.2.2/CakeStore/done.php" ;
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
                String post_data = URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(String.valueOf(user_id),"UTF-8")+"&"
                        +URLEncoder.encode("time_stamp", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(time_stamp), "UTF-8");
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
//        protected void onPostExecute(String result){
//            try {
//                JSONObject jsonResult = new JSONObject(result);
//                int amount = jsonResult.getInt("amount");
//                String address = jsonResult.getString("address");
//                String phone = jsonResult.getString("phone");
//
//                TextView payable_amt = (TextView) findViewById(R.id.payable_amt);
//                payable_amt.setText("Rs." + String.valueOf(amount) + "/-") ;
//                TextView address_tv = (TextView) findViewById(R.id.address_tv);
//                address_tv.setText(address) ;
//                TextView phone_tv = (TextView) findViewById(R.id.phone_tv);
//                phone_tv.setText(phone) ;
//
//            } catch (JSONException e) {
////                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//            }

        protected void onPostExecute(String result){
            try {
                JSONObject jsonResult = new JSONObject(result);
                int success = jsonResult.getInt("success");
//                int user_id = jsonResult.getInt("user_id");
                if(success == 1) {
                    JSONArray orderItems = jsonResult.getJSONArray("orderList");
                    int total_price = 0;
                    String payment_mode = null;
                    for (int i = 0; i < orderItems.length(); i++) {
                        JSONObject orderItem = orderItems.getJSONObject(i);
//                        int id = orderItem.getInt("id");
//                        int p_id = orderItem.getInt("p_id");
                        int qty = orderItem.getInt("qty");
                        int cost = orderItem.getInt("cost");
                        payment_mode = orderItem.getString("payment_mode");
                        total_price = total_price + cost * qty;
//                        cartList.add(new CartItem(product_title, productImages[i], qty, cost, id));
                    }
//                    TextView totPrice = (TextView) findViewById(R.id.txt_price);
//                    TextView payment = (TextView) findViewById(R.id.payment);
                    if(payment_mode.equals("Cash on Delivery")) {
                        payment.setText("Payment of Rs." + String.valueOf(total_price) + " will be done" + " by\n" + String.valueOf(payment_mode));
                    }
                    else
                    {
                        payment.setText("Payment of Rs." + String.valueOf(total_price) + " done" + " by\n" + String.valueOf(payment_mode));
                    }
//                    RecyclerView myrv = (RecyclerView) findViewById(R.id.rv_cartlist_id);
////                    CartAdapter cartAdapter = new CartAdapter(Cart.this, cartList, user_id);
////                    myrv.setLayoutManager(new GridLayoutManager(Cart.this, 1));
////                    myrv.setAdapter(cartAdapter);
                }
                else {
//                    Toast.makeText(getApplicationContext(), "Cart is Empty!", Toast.LENGTH_LONG).show();

                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    class Conection1 extends AsyncTask<String, String, String> {
        String user_id;
        String time_stamp;

        public Conection1(String user_id, String time_stamp) {
            this.time_stamp = time_stamp;
            this.user_id = user_id;
        }

        @Override
        protected String doInBackground(String... strings) {

//            String result = "";
            String cat_url = "http://10.0.2.2/CakeStore/cancel.php" ;
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
                String post_data = URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(String.valueOf(user_id),"UTF-8")+"&"
                        +URLEncoder.encode("time_stamp", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(time_stamp), "UTF-8");
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
//        protected void onPostExecute(String result){
//            try {
//                JSONObject jsonResult = new JSONObject(result);
//                int amount = jsonResult.getInt("amount");
//                String address = jsonResult.getString("address");
//                String phone = jsonResult.getString("phone");
//
//                TextView payable_amt = (TextView) findViewById(R.id.payable_amt);
//                payable_amt.setText("Rs." + String.valueOf(amount) + "/-") ;
//                TextView address_tv = (TextView) findViewById(R.id.address_tv);
//                address_tv.setText(address) ;
//                TextView phone_tv = (TextView) findViewById(R.id.phone_tv);
//                phone_tv.setText(phone) ;
//
//            } catch (JSONException e) {
////                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//            }

        protected void onPostExecute(String result){

        }
    }
}
