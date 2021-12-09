package com.example.cakestore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private Context mContext;
    private List<Product> mProductList;
    private String user_id;
    private String text;

    public ProductAdapter(Context mContext, List<Product> mProductList, String user_id) {
        this.mContext = mContext;
        this.mProductList = mProductList;
        this.user_id = user_id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.menu_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
//        TextView tv = (TextView) this.
        holder.product_title.setText(mProductList.get(position).getProduct_title());
        holder.product_image.setImageResource(mProductList.get(position).getProduct_image());
        holder.product_price.setText("Rs." + mProductList.get(position).getProduct_price() + "/-");
        holder.add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext, String.valueOf(user_id), Toast.LENGTH_SHORT).show();
//                new Cart.Connection8(user_id).execute();
//                new Connection(user_id).execute();
//                holder.textOne.setText(text);
                String type = "addCart";
                AddCartBackground addCartBackground = new AddCartBackground(mContext);
                addCartBackground.execute(type, String.valueOf(mProductList.get(position).getProduct_id()), String.valueOf(user_id));

            }
        });

    }
//
//    public static View getLayoutByRes(@LayoutRes int layoutRes, @Nullable ViewGroup viewGroup)
//    {
//        final LayoutInflater factory = getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        return factory.inflate(layoutRes, viewGroup);
//    }


    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView product_title, product_price, textOne;
        ImageView product_image;
        Button add_button;

        CardView cardview_product;
        @SuppressLint("ResourceType")
        public MyViewHolder(View itemView){
            super(itemView);
            product_title = (TextView) itemView.findViewById(R.id.product_title);
            product_image =(ImageView) itemView.findViewById(R.id.product_image);
            add_button = (Button) itemView.findViewById(R.id.add_button);
            product_price = (TextView) itemView.findViewById(R.id.product_price);
            cardview_product = (CardView) itemView.findViewById(R.id.cardview_product);
            textOne = (TextView) itemView.findViewById(R.layout.activity_food_menu);
        }
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
//        protected void onPostExecute(String result){
//            text = result;
//        }
//    }
}
