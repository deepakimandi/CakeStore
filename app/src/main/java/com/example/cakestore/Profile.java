package com.example.cakestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
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
import java.util.Iterator;
import java.util.List;

public class Profile extends AppCompatActivity {
    ImageView edit_address_p, edit_phone_p, edit_name_p, edit_email_p;
    TextView name_profile, email_profile, phone_profile, address_profile;
    Button reset_pass;
    private String myText, myText1, myText2, myText3;
    List<OrderItem> orderList = new ArrayList<>();
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final String user_id = getIntent().getExtras().getString("user_id");
//        String email_changed = email;
        name_profile = (TextView) findViewById(R.id.name_profile);
        email_profile = (TextView) findViewById(R.id.email_profile);
        phone_profile = (TextView) findViewById(R.id.phone_profile);
        address_profile = (TextView) findViewById(R.id.address_profile);

        edit_address_p = (ImageView) findViewById(R.id.edit_address_p);
        edit_phone_p = (ImageView) findViewById(R.id.edit_phone_p);
        edit_name_p = (ImageView) findViewById(R.id.edit_name_p);
        edit_email_p = (ImageView) findViewById(R.id.edit_email_p);
        reset_pass = (Button) findViewById(R.id.reset_pass);

        new Connection1(user_id).execute();
        new Connection7(user_id).execute();

        edit_address_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder addressDialog = new AlertDialog.Builder(Profile.this);
                addressDialog.setTitle("Edit Address");
                final EditText address_et = new EditText(Profile.this);
                address_et.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                addressDialog.setView(address_et);
                address_et.setText(address_profile.getText());
                addressDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        myText = address_et.getText().toString().trim();
                        new Connection2(myText, user_id).execute();
                        address_profile.setText(myText);
                    }
                });

                addressDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                addressDialog.show();
            }
        });

        edit_phone_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                openDialog2(email);
                AlertDialog.Builder phoneDialog = new AlertDialog.Builder(Profile.this);
                phoneDialog.setTitle("Edit Phone number");
                final EditText phone_et = new EditText(Profile.this);
                phone_et.setInputType(InputType.TYPE_CLASS_PHONE);
                phoneDialog.setView(phone_et);
                phone_et.setText(phone_profile.getText());
                phoneDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myText = phone_et.getText().toString().trim();
                        if(myText.length() != 10){
                            Toast.makeText(getApplicationContext(), "Mobile number invalid", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            new Connection3(myText, user_id).execute();
                            phone_profile.setText(myText);
                        }
                    }
                });

                phoneDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                phoneDialog.show();
            }
        });

        edit_email_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                openDialog2(email);
                AlertDialog.Builder emailDialog = new AlertDialog.Builder(Profile.this);
                emailDialog.setTitle("Edit Email");
                final EditText email_et = new EditText(Profile.this);
                email_et.setInputType(InputType.TYPE_CLASS_TEXT);
                emailDialog.setView(email_et);
                email_et.setText(email_profile.getText());
                emailDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myText = email_et.getText().toString().trim();
                        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                        if(!myText.matches(emailPattern)){
                                Toast.makeText(getApplicationContext(), "Email invalid", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            new Connection4(myText, user_id).execute();
//                            email_changed = email_profile.getText().toString().trim();
                        }
                    }
                });

                emailDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                emailDialog.show();
            }
        });

        edit_name_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                openDialog2(email);
                AlertDialog.Builder nameDialog = new AlertDialog.Builder(Profile.this);
                nameDialog.setTitle("Edit Full Name");
                final EditText name_et = new EditText(Profile.this);
                name_et.setInputType(InputType.TYPE_CLASS_TEXT);
                nameDialog.setView(name_et);
                name_et.setText(name_profile.getText());
                nameDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myText = name_et.getText().toString().trim();
//                        if(myText.length() != 10){
//                            Toast.makeText(getApplicationContext(), "Invalid Mobile number", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
                            new Connection5(myText, user_id).execute();
                            name_profile.setText(myText);
//                        }
                    }
                });

                nameDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                nameDialog.show();
            }
        });

        reset_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder resetDialogBuilder = new AlertDialog.Builder(Profile.this);
                View mView = getLayoutInflater().inflate(R.layout.password_reset, null);
                final EditText pass = (EditText) mView.findViewById(R.id.pass);
                final EditText new_pass = (EditText) mView.findViewById(R.id.new_pass);
                final EditText c_new_pass = (EditText) mView.findViewById(R.id.c_new_pass);
                Button btn_ok = (Button) mView.findViewById(R.id.btn_ok);
                Button btn_cancel = (Button) mView.findViewById(R.id.btn_cancel);
                resetDialogBuilder.setView(mView);
                final AlertDialog resetDialog = resetDialogBuilder.create();
                resetDialog.setCanceledOnTouchOutside(false);
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        resetDialog.dismiss();
                    }
                });
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myText1 = pass.getText().toString().trim();
                        myText2 = new_pass.getText().toString().trim();
                        myText3 = c_new_pass.getText().toString().trim();
                        if(myText1.isEmpty() || myText2.isEmpty() || myText3.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Fill all the fields", Toast.LENGTH_SHORT).show();
                        }
//                        else if(!myText2.equals(myText3)) {
//                            Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
//                        }
//                        else if(myText1.equals(myText2)){
//                            Toast.makeText(getApplicationContext(), "Password unchanged", Toast.LENGTH_SHORT).show();
//                            resetDialog.dismiss();
//                        }
                        else {
                            new Connection6(user_id, myText1, myText2, myText3, resetDialog).execute();
                        }
                    }
                });
                resetDialog.show();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav);
        bottomNavigationView.setSelectedItemId(R.id.account);

        BottomNavigationItemView itemView = bottomNavigationView.findViewById(R.id.cart);
        View badge = LayoutInflater.from(this).inflate(R.layout.layout_badge, bottomNavigationView, false);
        new Connection8(user_id).execute();
        text = badge.findViewById(R.id.badge_text_view);
        text.setText("0");
        itemView.addView(badge);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home_nav:
                        Intent i = new Intent(getApplicationContext(), Categories.class);
                        i.putExtra("user_id", user_id);
                        startActivity(i);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.cart:
                        Intent intent = new Intent(getApplicationContext(), Cart.class);
                        intent.putExtra("user_id", user_id);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.account:
                        return true;
                }
                return false;
            }
        });



    }

//    public void openDialog2(String email){
//        DialogPhone exampleDialog = new DialogPhone(email);
//        exampleDialog.show(getSupportFragmentManager(), "example dialog");
//    }
//
//    public void openDialog1(String email){
//        DialogAddress exampleDialog = new DialogAddress(email);
//        exampleDialog.show(getSupportFragmentManager(), "example dialog");
//    }
//
//
//    @Override
//    public void applyTextsAddress(String address, String email) {
//        new Connection2(address, email).execute();
//        address_tv.setText(address);
//    }
//
//    @Override
//    public void applyTextsPhone(String phone, String email) {
//        new Connection3(phone, email).execute();
//        phone_tv.setText(phone);
//    }


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

    class Connection7 extends AsyncTask<String, String, String> {
        String user_id;
        public Connection7(String user_id) {
            this.user_id = user_id;
        }

        @Override
        protected String doInBackground(String... strings) {

//            String result = "";
            String cat_url = "http://10.0.2.2/CakeStore/getOrders.php" ;
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
            String[] arrOfStr;
            int total_cost;
            String payment_mode = null;
            try {
//                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                JSONObject orders = (new JSONObject(result)).getJSONObject("orders");
                Iterator<String> time_stamps = orders.keys();
//                String address = data.getString("address");
//                int amount = data.getInt("amount");
                if(orders.length() == 0){
                    Toast.makeText(getApplicationContext(), "No orders yet", Toast.LENGTH_LONG).show();
                }
                else {
                    while (time_stamps.hasNext()) {
                        total_cost = 0;
                        String key = (String) time_stamps.next();
                        JSONArray array = (JSONArray) orders.get(key);
                        List<SubItem> subItemList = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            String str = array.getString(i);
                            arrOfStr = str.split(":", 5);
                            payment_mode = arrOfStr[4];
                            total_cost = total_cost + Integer.parseInt(arrOfStr[3]) * Integer.parseInt(arrOfStr[1]);
                            SubItem subItem = new SubItem(arrOfStr[2], arrOfStr[1], arrOfStr[3]);
                            subItemList.add(subItem);
                        }
                        OrderItem item = new OrderItem(subItemList, key, user_id, String.valueOf(total_cost), payment_mode);
                        orderList.add(item);
                        // System.out.println("Key: " + key);
                        // System.out.println("Value: " + json_array.get(key));
                    }
                    RecyclerView order = findViewById(R.id.orders);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(Profile.this);
                    ItemAdapter itemAdapter = new ItemAdapter(orderList);
                    order.setAdapter(itemAdapter);
                    order.setLayoutManager(layoutManager);
                }

            } catch (Exception e) {
//                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    class Connection1 extends AsyncTask<String, String, String> {
        String user_id;
        public Connection1(String user_id) {
            this.user_id = user_id;
        }

        @Override
        protected String doInBackground(String... strings) {

//            String result = "";
            String cat_url = "http://10.0.2.2/CakeStore/account.php" ;
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
//                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                JSONObject data = (new JSONObject(result)).getJSONObject("data");
                String address = data.getString("address");
//                int amount = data.getInt("amount");
                String phone = data.getString("phone");
                String name = data.getString("name");
                String email = data.getString("email");
                TextView phone_profile = (TextView) findViewById(R.id.phone_profile);
                phone_profile.setText(phone) ;
                TextView name_profile = (TextView) findViewById(R.id.name_profile);
                name_profile.setText(name) ;
                TextView email_profile = (TextView) findViewById(R.id.email_profile);
                email_profile.setText(email) ;
                TextView address_profile = (TextView) findViewById(R.id.address_profile);
                address_profile.setText(address) ;

            } catch (Exception e) {
//                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    class Connection2 extends AsyncTask<String, String, String> {
        String address;
        String user_id;
        public Connection2(String address, String user_id) {
            this.address = address;
            this.user_id = user_id;
        }

        @Override
        protected String doInBackground(String... strings) {

//            String result = "";
            String cat_url = "http://10.0.2.2/CakeStore/editaddress.php" ;
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
                String post_data = URLEncoder.encode("address","UTF-8")+"="+URLEncoder.encode(String.valueOf(address),"UTF-8")+"&"
                        +URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user_id), "UTF-8");
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
//            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
    }

    class Connection3 extends AsyncTask<String, String, String> {
        String user_id;
        String phone;
        public Connection3(String phone, String user_id) {
            this.user_id = user_id;
            this.phone = phone;
        }

        @Override
        protected String doInBackground(String... strings) {

//            String result = "";
            String cat_url = "http://10.0.2.2/CakeStore/editphone.php" ;
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
                String post_data = URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(String.valueOf(phone),"UTF-8")+"&"
                        +URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user_id), "UTF-8");                bufferedWriter.write(post_data);
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

        protected void onPostExecute(String result) {

        }
    }


    class Connection4 extends AsyncTask<String, String, String> {
        String user_id;
        String email_changed;
        public Connection4(String email_changed, String user_id) {
            this.user_id = user_id;
            this.email_changed = email_changed;
        }

        @Override
        protected String doInBackground(String... strings) {

//            String result = "";
            String cat_url = "http://10.0.2.2/CakeStore/editemail.php" ;
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
                String post_data = URLEncoder.encode("email_changed","UTF-8")+"="+URLEncoder.encode(String.valueOf(email_changed),"UTF-8")+"&"
                        +URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user_id), "UTF-8");                bufferedWriter.write(post_data);
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

        protected void onPostExecute(String result) {

            if(result.equals("email unchanged")){
                Toast.makeText(getApplicationContext(), "Email unchanged", Toast.LENGTH_SHORT).show();
            }
            else if(result.equals("email exists")){
                Toast.makeText(getApplicationContext(), "Email exists", Toast.LENGTH_SHORT).show();
            }
            else {
                email_profile.setText(email_changed);
            }
        }
    }


    class Connection5 extends AsyncTask<String, String, String> {
        String user_id;
        String name;
        public Connection5(String name, String user_id) {
            this.user_id = user_id;
            this.name = name;
        }

        @Override
        protected String doInBackground(String... strings) {

//            String result = "";
            String cat_url = "http://10.0.2.2/CakeStore/editname.php" ;
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
                String post_data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(String.valueOf(name),"UTF-8")+"&"
                        +URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user_id), "UTF-8");                bufferedWriter.write(post_data);
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

        protected void onPostExecute(String result) {

        }
    }


    class Connection6 extends AsyncTask<String, String, String> {
        String user_id;
        String myText1, myText2, myText3;
        AlertDialog resetDialog;
        public Connection6(String user_id, String myText1, String myText2, String myText3, AlertDialog resetDialog) {
            this.user_id = user_id;
            this.myText1 = myText1;
            this.myText2 = myText2;
            this.myText3 = myText3;
            this.resetDialog = resetDialog;
        }

        @Override
        protected String doInBackground(String... strings) {

//            String result = "";
            String cat_url = "http://10.0.2.2/CakeStore/resetpassword.php" ;
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
                String post_data = URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(String.valueOf(myText1),"UTF-8")+"&"+
                                   URLEncoder.encode("new_pass","UTF-8")+"="+URLEncoder.encode(String.valueOf(myText2),"UTF-8")+"&"+
                                   URLEncoder.encode("c_new_pass","UTF-8")+"="+URLEncoder.encode(String.valueOf(myText3),"UTF-8")+"&"+
                        URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user_id), "UTF-8");                bufferedWriter.write(post_data);
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

        protected void onPostExecute(String result) {
//            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            if(result.equals("done")) {
                Toast.makeText(getApplicationContext(), "Password successfully reset", Toast.LENGTH_LONG).show();
                resetDialog.dismiss();
            }
            else if(result.equals("wrong"))
                Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_SHORT).show();
            else if(result.equals("pass unch"))
                Toast.makeText(getApplicationContext(), "Password unchanged", Toast.LENGTH_SHORT).show();
            else if(result.equals("no match"))
                Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }



}
