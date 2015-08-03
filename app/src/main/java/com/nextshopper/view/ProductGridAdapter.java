package com.nextshopper.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextshopper.activity.ProductDetailsActivity;
import com.nextshopper.activity.R;
import com.nextshopper.common.RoundImageUtil;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.NextShopperService;
import com.nextshopper.rest.beans.Product;
import com.nextshopper.rest.beans.ProductList;
import com.nextshopper.rest.beans.SearchableProduct;
import com.nextshopper.rest.beans.SearchableProductList;
import com.nextshopper.rest.beans.TrendProductList;

import java.net.HttpURLConnection;
import java.net.URL;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by Zhang_Kevin on 7/4/15.
 */
public class ProductGridAdapter extends BaseAdapter implements AbsListView.OnScrollListener{
    private Context ctx;
    private GridView gridView;
    private SearchableProductList searchableProductList = new SearchableProductList();
    private LruCache<String, Bitmap> memoryCache;
    private int start = 0;
    private int numOfItem = 20;
    private int mFirstVisibleItem;
    private int mVisibleItemCount;
    private TrendingFragment trendingFragment;

    public ProductGridAdapter(Context ctx, GridView gridView, TrendingFragment trendingFragment) {
        this.ctx = ctx;
        this.gridView = gridView;
        this.trendingFragment = trendingFragment;
        int maxMemory =(int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory/24;
        memoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected  int sizeOf(String key, Bitmap bitmap){
                 return bitmap.getByteCount();
            }
        };

    }

    @Override
    public int getCount() {
        if(searchableProductList == null)
            return 0;
        return searchableProductList.items.size();
    }

    @Override
    public Object getItem(int position) {
        return searchableProductList.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchableProduct sp = searchableProductList.items.get(position);
        if(convertView == null) {
            convertView= LayoutInflater.from(ctx).inflate(R.layout.list_item, parent, false);
        }

        TextView textViewDisPrice = (TextView) convertView.findViewById(R.id.item_disount_price);
        textViewDisPrice.setText("$"+Math.round(sp.price*100)/100.0);
        TextView textViewPrice =(TextView) convertView.findViewById(R.id.item_ori_price);
        textViewPrice.setText("$"+Math.round(sp.listPrice*100)/100.0);
        TextView textViewName = (TextView) convertView.findViewById(R.id.item_name);
        textViewName.setText(sp.name);
        ImageView imageView = (ImageView)convertView.findViewById(R.id.item_image);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, ProductDetailsActivity.class);
                intent.putExtra("productId", (String)v.getTag());
                ctx.startActivity(intent);
            }
        };
        imageView.setOnClickListener(listener);
        imageView.setTag(sp.imgUrl.get(0));
        setImageView(imageView, sp.imgUrl.get(0));
        //new DownloadImageTask(imageView).execute(sp.imgUrl.get(0));
        return convertView;
    }

    void setImageView(ImageView imageView, String url) {
        Bitmap bitmap = getBitmapFromMemoryCache(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            loadBitmaps(url);

        }
    }

    void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }


    class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {

        private String imageUrl;

        @Override
        protected Bitmap doInBackground(String... params) {
            imageUrl = params[0];
            if(!imageUrl.startsWith("http"))
                imageUrl = "http://api.onsalelocal.com/ws/resource/download?path=" + imageUrl;
            // 在后台开始下载图片
            Bitmap bitmap = downloadBitmap(imageUrl);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            // 根据Tag找到相应的ImageView控件，将下载好的图片显示出来。
            ImageView imageView = (ImageView) gridView.findViewWithTag(imageUrl);
            if (imageView != null && bitmap != null) {
                bitmap = RoundImageUtil.getRoundedCornerBitmap(ctx, bitmap, 10, imageView.getWidth(), imageView.getHeight(), false, false, true, true);
                imageView.setImageBitmap(bitmap);
                addBitmapToMemoryCache(imageUrl, bitmap);
            }
        }

        /**
         * 建立HTTP请求，并获取Bitmap对象。
         *
         * @param imageUrl
         *            图片的URL地址
         * @return 解析后的Bitmap对象
         */
        private Bitmap downloadBitmap(String imageUrl) {
            Bitmap bitmap = null;
            HttpURLConnection con = null;
            try {
                URL url = new URL(imageUrl);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(10 * 1000);
                bitmap = BitmapFactory.decodeStream(con.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (con != null) {
                    con.disconnect();
                }
            }
            return bitmap;
        }

    }


    public void setSearchableProductList(SearchableProductList searchableProductList) {
        if(searchableProductList.items==null) return;
        this.searchableProductList.items.addAll(searchableProductList.items);
        notifyDataSetChanged();
    }

    public Bitmap getBitmapFromMemoryCache(String key) {
        return memoryCache.get(key);
    }

    private void loadBitmaps(String imageUrl) {
        try {
                Bitmap bitmap = getBitmapFromMemoryCache(imageUrl);
                if (bitmap == null) {
                    BitmapWorkerTask task = new BitmapWorkerTask();
                    task.execute(imageUrl);
                } else {
                    ImageView imageView = (ImageView)gridView.findViewWithTag(imageUrl);
                    if (imageView != null && bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    }
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mFirstVisibleItem = firstVisibleItem;
        mVisibleItemCount = visibleItemCount;
        int lastVisibleItem = firstVisibleItem + visibleItemCount;
        if ( lastVisibleItem == this.getCount()) {
            NextShopperService service = ApiService.getService();
            if(trendingFragment.getArguments().getString("Tab").equals("Trending")) {
                service.ProductAPI_PopularProducts(null, start, numOfItem, new Callback<TrendProductList>() {
                    @Override
                    public void success(TrendProductList trendProductList, Response response) {
                        setSearchableProductList(trendProductList);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("NextShopper", error.getMessage() + ": " + new String(((TypedByteArray) error.getResponse().getBody()).getBytes()), error);

                    }
                });
            } else if(trendingFragment.getArguments().getString("Tab").equals("Newest")){
                service.ProductAPI_NewProducts(start, numOfItem, new Callback<SearchableProductList>() {
                    @Override
                    public void success(SearchableProductList searchableProductList, Response response) {
                        setSearchableProductList(searchableProductList);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.i("HomeActivity", error.getMessage());
                    }
                });
            } else if(trendingFragment.getArguments().getString("Tab").equals("Just For You")){
                service.ProductAPI_RecommendationsForUser(start, numOfItem, null, null, new Callback<SearchableProductList>() {
                    @Override
                    public void success(SearchableProductList searchableProductList, Response response) {
                        setSearchableProductList(searchableProductList);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("HomeActivity", error.getMessage());
                    }
                });

            }else if(trendingFragment.getArguments().get("Tab").equals("")){
                service.ProductAPI_Search(trendingFragment.getArguments().getString("Keywords"), trendingFragment.getArguments().getString("Cat"), null,null, null, 0, numOfItem,null,new Callback<ProductList>(){
                    @Override
                    public void success(ProductList productList, Response response) {
                        searchableProductList= convert(productList);
                        setSearchableProductList(searchableProductList);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("NextShopper", error.getMessage() + ": " + new String(((TypedByteArray) error.getResponse().getBody()).getBytes()), error);

                    }
                });
            }
            start = start + numOfItem;
        }
    }
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    SearchableProductList convert(ProductList productList){
        SearchableProductList spList = new SearchableProductList();
        for(Product p: productList.items){
            SearchableProduct sp = new SearchableProduct();
            sp.imgUrl = p.imgs;
            sp.name = p.name;
            sp.price = p.salePrice;
            sp.listPrice=p.salePrice;
            spList.items.add(sp);
        }
       return spList;
    }
}
