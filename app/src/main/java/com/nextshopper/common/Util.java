package com.nextshopper.common;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.nextshopper.activity.MainActivity;
import com.nextshopper.activity.R;
import com.nextshopper.activity.RegistrationIntentService;
import com.nextshopper.rest.ApiService;
import com.nextshopper.rest.beans.GenericResponse;
import com.nextshopper.rest.beans.PushNotificationToken;
import com.nextshopper.rest.beans.ShippingInfo;
import com.nextshopper.rest.beans.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by siyiliu on 7/16/15.
 */
public class Util {
    public static Bitmap getRoundedCornerBitmap(Context context, Bitmap input, int pixels, int w, int h, boolean squareTL, boolean squareTR, boolean squareBL, boolean squareBR) {

        Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, w, h);
        final RectF rectF = new RectF(rect);

        //make sure that our rounded corner is scaled appropriately
        final float roundPx = pixels * densityMultiplier;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);


        //draw rectangles over the corners we want to be square
        if (squareTL) {
            canvas.drawRect(0, 0, w / 2, h / 2, paint);
        }
        if (squareTR) {
            canvas.drawRect(w / 2, 0, w, h / 2, paint);
        }
        if (squareBL) {
            canvas.drawRect(0, h / 2, w / 2, h, paint);
        }
        if (squareBR) {
            canvas.drawRect(w / 2, h / 2, w, h, paint);
        }

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(input, 0, 0, paint);

        return output;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static void saveUserData(Context ctx, User user) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences(Constant.USER, ctx.MODE_PRIVATE).edit();
        editor.putString(Constant.USER_ID, user.id);
        editor.putString(Constant.FIRST_NAME, user.info.firstName);
        editor.putString(Constant.LAST_NAME, user.info.lastName);
        editor.putString(Constant.IMG_PATH, user.info.imgPath);
        // editor.putString(Constant.EMAIL, user.);
        // editor.putString(Constant.PASSWORD, user.info.pwd);
        if(user.info.gender!=null) {
            editor.putString(Constant.GENDER, user.info.gender.toString());
        }
        if (user.shippingInfo != null && user.shippingInfo.size() > 0) {
            saveShipping(editor, ctx, user.shippingInfo.get(0));
        }

        editor.commit();
    }


    public static void saveCookie(Context ctx, String cookie) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences(Constant.USER, ctx.MODE_PRIVATE).edit();
        editor.putString(Constant.COOKIE, cookie);
        editor.commit();
    }

    public static void saveShippingInfo(Context ctx, ShippingInfo shippingInfo){
        SharedPreferences.Editor editor = ctx.getSharedPreferences(Constant.USER, ctx.MODE_PRIVATE).edit();
        saveShipping(editor, ctx, shippingInfo);
        editor.commit();
    }

    public static void saveGCMToken(Context ctx, String token){
        SharedPreferences.Editor editor = ctx.getSharedPreferences(Constant.USER, ctx.MODE_PRIVATE).edit();
        editor.putString(Constant.TOKEN,token);
        editor.commit();
    }


    public static void saveShipping(SharedPreferences.Editor editor, Context ctx, ShippingInfo shippingInfo) {
        editor.putString(Constant.SHIPPING_FIRST_NAME, shippingInfo.firstName);
        editor.putString(Constant.SHIPPING_LAST_NAME, shippingInfo.lastName);
        editor.putString(Constant.PHONE, shippingInfo.phoneNumber);
        editor.putString(Constant.ADDRESS, shippingInfo.address);
        editor.putString(Constant.COUNTRY, shippingInfo.country);
        editor.putString(Constant.STATE, shippingInfo.state);
        editor.putString(Constant.CITY, shippingInfo.city);
        editor.putString(Constant.ZIP, shippingInfo.zipcode);
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
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, o2);
        input.close();
        return bitmap;
    }

    public static void share(Context ctx, String text, Bitmap bitmap) {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("image/*");
        String title = ctx.getResources().getString(R.string.share);
        Intent chooser = Intent.createChooser(sendIntent, title);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, Constant.NEXT_SHOPPER_SHARE);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        String url = MediaStore.Images.Media.insertImage(ctx.getContentResolver(), bitmap, "title", "nextshopper");
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

    public static void alertBox(Context ctx, RetrofitError error) {
        String msg = new String(((TypedByteArray) error.getResponse().getBody()).getBytes());
        String[] msgs  =msg.split(":");
        msg = msgs[msgs.length-1].substring(0, msgs[msgs.length-1].length()-1);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(msg).setTitle(R.string.dialog_title);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void alertBox(Context ctx, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(msg).setTitle(R.string.dialog_title);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void loginBox(final Context ctx) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(R.string.warning_message).setTitle(R.string.dialog_warining);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(ctx, MainActivity.class);
                ctx.startActivity(intent);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static List<String> countries = new ArrayList<String>() {{
        add("China");
        add("United Kingdom (Great Britain)");
        add("United States");
        add("United States Virgin Islands");
        add("Venezuela");
        //add("Vietnam");
    }};

    public static String[][] states = {{"Sichuan", "Beijing", "Shanghai", "Guangdong", "Hunan"}, {"city1"}, {"CA", "MA", "FL", "AL", "AK", "AR", "CO", "CT"}, {"t1", "t2", "t3"}, {"s1", "s2", "s3"},};

    public static ProgressDialog getProgressDialog(Context ctx){
        return ProgressDialog.show(ctx, "", "Loading...");
    }

    public static ProgressBar getProgressBar(Context ctx){
        ProgressBar progressBar = (ProgressBar)LayoutInflater.from(ctx).inflate(R.layout.progressbar, null);
        progressBar.setVisibility(View.VISIBLE);
        return progressBar;
    }

    public static void sendRegistrationToServer(Context ctx){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(Constant.USER, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(Constant.TOKEN,"");
        if(!token.isEmpty())
            sendRegistrationToServer(token);
        else {
            Intent intent = new Intent(ctx, RegistrationIntentService.class);
            ctx.startService(intent);
        }
    }


    public static void sendRegistrationToServer(String token) {
        PushNotificationToken pushNotificationToken = new PushNotificationToken();
        pushNotificationToken.token = token;
        ApiService.getService().PushNotificationAPI_RegisterGoogleDeviceToken(pushNotificationToken, new Callback<GenericResponse>() {
            @Override
            public void success(GenericResponse genericResponse, Response response) {
                Log.d(Constant.NEXTSHOPPER, "token sent to server");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(Constant.NEXTSHOPPER, "error when token sent to server"+new String(((TypedByteArray) error.getResponse().getBody()).getBytes()));
            }
        });

    }

}


