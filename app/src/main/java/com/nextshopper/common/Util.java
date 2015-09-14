package com.nextshopper.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.provider.MediaStore;

import com.nextshopper.activity.R;
import com.nextshopper.rest.beans.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import retrofit.client.Header;
import retrofit.client.Response;

/**
 * Created by siyiliu on 7/16/15.
 */
public class Util {
    public static Bitmap getRoundedCornerBitmap(Context context, Bitmap input, int pixels , int w , int h , boolean squareTL, boolean squareTR, boolean squareBL, boolean squareBR  ) {

        Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, w, h);
        final RectF rectF = new RectF(rect);

        //make sure that our rounded corner is scaled appropriately
        final float roundPx = pixels*densityMultiplier;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);


        //draw rectangles over the corners we want to be square
        if (squareTL ){
            canvas.drawRect(0, 0, w/2, h/2, paint);
        }
        if (squareTR ){
            canvas.drawRect(w/2, 0, w, h/2, paint);
        }
        if (squareBL ){
            canvas.drawRect(0, h/2, w/2, h, paint);
        }
        if (squareBR ){
            canvas.drawRect(w/2, h/2, w, h, paint);
        }

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(input, 0,0, paint);

        return output;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float)height / (float)reqHeight);
            final int widthRatio = Math.round((float)width / (float)reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static void saveUserData(Context ctx, User user) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences(Constant.USER, ctx.MODE_PRIVATE).edit();
        editor.putString(Constant.USER_ID, user.id);
        editor.putString(Constant.FIRST_NAME, user.info.firstName);
        editor.putString(Constant.LAST_NAME, user.info.lastName);
       // editor.putString(Constant.EMAIL, user.);
       // editor.putString(Constant.PASSWORD, user.info.pwd);
        editor.putString(Constant.GENDER, user.info.gender.toString());

        editor.commit();
    }
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static Bitmap getThumbnail(Context ctx, Uri uri, int THUMBNAIL_SIZE) throws FileNotFoundException, IOException {
        InputStream input = ctx.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int inSampleSize = Util.calculateInSampleSize(onlyBoundsOptions, THUMBNAIL_SIZE, THUMBNAIL_SIZE);
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = inSampleSize;
        input = ctx.getContentResolver().openInputStream(uri);
        Bitmap bitmap =  BitmapFactory.decodeStream(input, null,o2);
        input.close();
        return bitmap;
    }

    public static void share(Context ctx, String text, Bitmap bitmap){
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("image/*");
        String title = ctx.getResources().getString(R.string.share);
        Intent chooser = Intent.createChooser(sendIntent, title);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, Constant.NEXT_SHOPPER_SHARE);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        String url= MediaStore.Images.Media.insertImage(ctx.getContentResolver(), bitmap, "title", "nextshopper");
        sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(url));
        if (sendIntent.resolveActivity(ctx.getPackageManager()) != null) {
            ctx.startActivity(chooser);
        }
    }

    public static String getCookieString(Response response) {
        for (Header header : response.getHeaders()) {
            if (null != header.getName() && header.getName().equals("Set-Cookie")) {
                return header.getValue();
            }
        }
        return null;
    }

    public static void alertBox(Context ctx, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(msg).setTitle(R.string.dialog_title);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
