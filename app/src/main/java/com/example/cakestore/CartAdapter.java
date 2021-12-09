package com.example.cakestore;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private Context mContext;
    private List<CartItem> mCartList;
    private String user_id;
//    private String email;

    public CartAdapter(Context mContext, List<CartItem> mCartList, String user_id) {
        this.mContext = mContext;
        this.mCartList = mCartList;
        this.user_id = user_id;
//        this.email = email;
    }

    @NonNull
    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view;
//        LayoutInflater mInflater = LayoutInflater.from(mContext);
//        view = mInflater.inflate(R.layout.cart_item, parent, false);
//        return new MyViewHolder(view);
//    }
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item, viewGroup, false);
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
//        final TextView textView = (TextView) new Cart().findViewById(R.id.txt_price);
        holder.item_title.setText(mCartList.get(position).getProduct_title());
        holder.singleitem_cost.setText(String.valueOf(mCartList.get(position).getCost()));
        holder.totalitem_cost.setText(String.valueOf(mCartList.get(position).getCost() * mCartList.get(position).getQty()));
        holder.qty.setText(String.valueOf(mCartList.get(position).getQty()));

        holder.item_image.setImageResource(mCartList.get(position).getProduct_image());
        holder.dec_qty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCartList.get(position).getQty() == 1){
                    Toast.makeText(mContext, "Can't reduce Quantity below 1", Toast.LENGTH_SHORT).show();
                }
                else{
                    mCartList.get(position).setQty(mCartList.get(position).getQty() - 1);
                    holder.qty.setText(String.valueOf(mCartList.get(position).getQty()));
                    holder.totalitem_cost.setText(String.valueOf(mCartList.get(position).getCost() * mCartList.get(position).getQty()));
                    String type = "dec";
                    CartBackground cartBackground = new CartBackground(mContext);
                    cartBackground.execute(type, String.valueOf(mCartList.get(position).getId()), String.valueOf(mCartList.get(position).getQty()));
                }
//                int total = 0;
//                for(int i = 0; i < mCartList.size(); i++){
////                   total = total + mCartList.get(i).getQty() * mCartList.get(i).getCost();
//                }

//                textView.setText("Rs." + String.valueOf(total) + "/-");
            }
        });

        holder.inc_qty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCartList.get(position).setQty(mCartList.get(position).getQty() + 1);
                holder.qty.setText(String.valueOf(mCartList.get(position).getQty()));
                holder.totalitem_cost.setText(String.valueOf(mCartList.get(position).getCost() * mCartList.get(position).getQty()));
                String type = "inc";
                CartBackground cartBackground = new CartBackground(mContext);
                cartBackground.execute(type, String.valueOf(mCartList.get(position).getId()), String.valueOf(mCartList.get(position).getQty()));
//                int total = 0;
//                for(int i = 0; i < mCartList.size(); i++){
////                    total = total + mCartList.get(i).getQty() * mCartList.get(i).getCost();
//                }

//                textView.setText("Rs." + String.valueOf(total) + "/-");
            }
        });

        holder.delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                new Connection(user_id).execute();
                String type = "del";
                CartBackground cartBackground = new CartBackground(mContext);
                cartBackground.execute(type, String.valueOf(mCartList.get(position).getId()));

            }
        });

//        int total_price = 0;
//        for(int i = 0; i < mCartList.size(); i++) {
//            total_price = total_price + (mCartList.get(i).getQty() * mCartList.get(i).getCost());
//        }
//        holder.total_price.setText("Rs." + String.valueOf(total_price) + "/-");
    }

    @Override
    public int getItemCount() {
        return mCartList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView item_title,total_price;
        private TextView singleitem_cost;
        private TextView totalitem_cost;
        private TextView qty;
        private ImageView item_image;
        private ImageView dec_qty;
        private ImageView inc_qty;
        private ImageView delete_item;
//        CardView cardView;
        MyViewHolder(View itemView){
            super(itemView);
            item_title = (TextView) itemView.findViewById(R.id.item_title);
            singleitem_cost = (TextView) itemView.findViewById(R.id.singleitem_cost);
            totalitem_cost = (TextView) itemView.findViewById(R.id.totalitem_cost);
            qty = (TextView) itemView.findViewById(R.id.qty);

            item_image =(ImageView) itemView.findViewById(R.id.item_image);
            dec_qty =(ImageView) itemView.findViewById(R.id.dec_qty);
            inc_qty =(ImageView) itemView.findViewById(R.id.inc_qty);
            delete_item = (ImageView) itemView.findViewById(R.id.delete_item);

        }
//        total_price = (TextView) findViewById(R.id.txt_price);
    }

//    class Connection extends AsyncTask<String, String, String> {
//        String user_id;
//        public Connection(String user_id) {
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
////        protected void onPostExecute(String result){
////            try {
////                JSONObject jsonResult = new JSONObject(result);
////                int amount = jsonResult.getInt("amount");
////                String address = jsonResult.getString("address");
////                String phone = jsonResult.getString("phone");
////
////                TextView payable_amt = (TextView) findViewById(R.id.payable_amt);
////                payable_amt.setText("Rs." + String.valueOf(amount) + "/-") ;
////                TextView address_tv = (TextView) findViewById(R.id.address_tv);
////                address_tv.setText(address) ;
////                TextView phone_tv = (TextView) findViewById(R.id.phone_tv);
////                phone_tv.setText(phone) ;
////
////            } catch (JSONException e) {
//////                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
////            }
//
//        protected void onPostExecute(String result){
//
//        }
//    }


}
