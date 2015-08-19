package com.nextshopper.rest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.nextshopper.common.Util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by siyiliu on 8/16/15.
 */
public class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {

    private String imageUrl;
    private ImageView imageView;
    private boolean toRound;
    private int height;

    public BitmapWorkerTask(ImageView imageView, boolean toRound, int height){
        this.imageView = imageView;
        this.height =(int)(160 * imageView.getContext().getResources().getDisplayMetrics().density);
        this.toRound = toRound;
        if(height!=0)
            this.height = height;
    }


    @Override
    protected Bitmap doInBackground(String... params) {
        imageUrl = params[0];
        Bitmap bitmap= null;
        if(imageView!=null && imageView.getTag().equals(imageUrl)) {
            if (!imageUrl.startsWith("http")) {
                bitmap =downloadBitmap ("http://api.onsalelocal.com/ws/resource/download?path=" + imageUrl);
            }else {
                bitmap = downloadBitmap(imageUrl);
            }
            return bitmap;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        // 根据Tag找到相应的ImageView控件，将下载好的图片显示出来。
        if(imageView!=null && bitmap!=null && imageView.getTag().equals(imageUrl)){
            //bitmap = Util.getRoundedCornerBitmap(gridView.getContext(), bitmap, 12, bitmap.getWidth(), bitmap.getHeight(), false, false, true, true);
            if(toRound)
                bitmap = Util.toRoundCorner(bitmap,height);
            imageView.setImageBitmap(bitmap);
        }
    }
    private Bitmap downloadBitmap(String imageUrl) {
        Bitmap bitmap = null;
        HttpURLConnection con = null;
        InputStream inputStream = null;
        InputStream copyiInputStream1 = null;
        InputStream copyiInputStream2 = null;
        try {
            URL url = new URL(imageUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(5 * 1000);
            con.setReadTimeout(10 * 1000);
            inputStream = con.getInputStream();
            byte[] data = InputStreamTOByte(inputStream);
            try {
                copyiInputStream1 = byteTOInputStream(data);
                copyiInputStream2 = byteTOInputStream(data);
            } catch (Exception e) {
                e.printStackTrace();
            }

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(copyiInputStream1, null, options);
            options.inSampleSize = Util.calculateInSampleSize(options, height, height);
            options.inJustDecodeBounds = false;

            return BitmapFactory.decodeStream(copyiInputStream2, null, options);
            //return BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
                try {
                    if (inputStream != null)
                        inputStream.close();
                    if (copyiInputStream1 != null)
                        copyiInputStream1.close();
                    if (copyiInputStream2 != null)
                        copyiInputStream2.close();
                } catch (Exception e) {
                }
            }
        }
        return bitmap;
    }


    public byte[] InputStreamTOByte(InputStream in) throws IOException {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024 * 16];
        int count = -1;
        while ((count = in.read(data, 0, 1024 * 16)) != -1)
            outStream.write(data, 0, count);

        data = null;
        return outStream.toByteArray();
    }

    public InputStream byteTOInputStream(byte[] in) throws Exception {

        ByteArrayInputStream is = new ByteArrayInputStream(in);
        return is;
    }
}
