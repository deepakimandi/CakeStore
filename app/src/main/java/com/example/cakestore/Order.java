package com.example.cakestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

public class Order extends AppCompatActivity {
    ImageView edit_address, edit_phone;
    TextView address_tv, phone_tv;
    Button btn_payment;
    private String myText;
    RadioGroup radioGroup;
    RadioButton radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        final String user_id = getIntent().getExtras().getString("user_id");
        address_tv = (TextView) findViewById(R.id.address_tv);
        phone_tv = (TextView) findViewById(R.id.phone_tv);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup) ;
        edit_address = (ImageView) findViewById(R.id.edit_address);
        btn_payment = (Button) findViewById(R.id.btn_payment);
        new Connection1(user_id).execute();
        edit_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder addressDialog = new AlertDialog.Builder(Order.this);
                addressDialog.setTitle("Edit Address");
                final EditText address_et = new EditText(Order.this);
                address_et.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                addressDialog.setView(address_et);
                address_et.setText(address_tv.getText());
                addressDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        myText = address_et.getText().toString().trim();
                        new Connection2(myText, user_id).execute();
                        address_tv.setText(myText);
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
        edit_phone = (ImageView) findViewById(R.id.edit_phone);
        edit_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                openDialog2(email);
                AlertDialog.Builder phoneDialog = new AlertDialog.Builder(Order.this);
                phoneDialog.setTitle("Edit Phone number");
                final EditText phone_et = new EditText(Order.this);
                phone_et.setInputType(InputType.TYPE_CLASS_PHONE);
                phoneDialog.setView(phone_et);
                phone_et.setText(phone_tv.getText());
                phoneDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myText = phone_et.getText().toString().trim();
                        if(myText.length() != 10){
                            Toast.makeText(getApplicationContext(), "Invalid Mobile number", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            new Connection3(myText, user_id).execute();
                            phone_tv.setText(myText);
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

        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
                String payment_mode = radioButton.getText().toString();
                new Connection4(user_id, payment_mode).execute();

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

    class Connection1 extends AsyncTask<String, String, String> {
        String user_id;
        public Connection1(String user_id) {
            this.user_id = user_id;
        }

        @Override
        protected String doInBackground(String... strings) {

//            String result = "";
            String cat_url = "http://10.0.2.2/CakeStore/order.php" ;
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
                int amount = data.getInt("amount");
                String phone = data.getString("phone");

                TextView payable_amt = (TextView) findViewById(R.id.payable_amt);
                payable_amt.setText("Rs." + String.valueOf(amount) + "/-") ;
                TextView address_tv = (TextView) findViewById(R.id.address_tv);
                address_tv.setText(address) ;
                TextView phone_tv = (TextView) findViewById(R.id.phone_tv);
                phone_tv.setText(phone) ;

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
                        +URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user_id), "UTF-8");             bufferedWriter.write(post_data);
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
        String payment_mode;
        public Connection4(String user_id, String payment_mode) {
            this.user_id = user_id;
            this.payment_mode = payment_mode;
        }

        @Override
        protected String doInBackground(String... strings) {

//            String result = "";
            String cat_url = "http://10.0.2.2/CakeStore/insertorder.php" ;
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
                String post_data = URLEncoder.encode("payment_mode","UTF-8")+"="+URLEncoder.encode(String.valueOf(payment_mode),"UTF-8")+"&"
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
            Intent intent = new Intent(getApplicationContext(), Done.class);
            intent.putExtra("user_id", user_id);
            intent.putExtra("time_stamp", result);
            startActivity(intent);
        }
    }


}
