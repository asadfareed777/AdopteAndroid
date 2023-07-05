package com.didaagency.adopteunelivraison.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;


public class ImageUtils {
    private static ImageUtils instance = null;
    private static final String[] CS_IMAGE_OPTIONS = new String[]{"Take Picture", "Gallery"};
    public static final int TAKE_IMAGE_CAMERA = 1;
    public static final int UPLOAD_IMAGE_GALLERY = 2;
    @Inject
    Context context;

    private ImageUtils () {
    }

    public static ImageUtils getInstance() {
        if (instance == null) {
            instance = new ImageUtils();
        }
        return instance;
    }

    public void showDialogPicture(byte[] bsImage, String title) {
        try {
            showDialogPicture(getImage(bsImage), title);
        } catch (Exception e) {
            Toast.makeText(context, "Unable to Load Image", Toast.LENGTH_LONG).show();
        }
    }

    public void showDialogPicture(byte[] bsImage, String title,Activity mActivity) {
        try {
            showDialogPicture(getImage(bsImage), title,mActivity);
        } catch (Exception e) {
            Toast.makeText(mActivity, "Unable to Load Image", Toast.LENGTH_LONG).show();
        }
    }

    public void showDialogPicture(Bitmap image, String title,Activity mActivity) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            ImageView imageView = new ImageView(mActivity);
            if (title != null && title.length() > 0) {
                builder.setTitle(title);
            }
            builder.setView(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageBitmap(image);
            AlertDialog alertDialog = builder.create();
            alertDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            alertDialog.show();
        } catch (Exception e) {
            Toast.makeText(mActivity, "Error displaying image", Toast.LENGTH_LONG).show();
        }
    }

    public void showDialogPicture(Bitmap image, String title) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            ImageView imageView = new ImageView(context);
            if (title != null && title.length() > 0) {
                builder.setTitle(title);
            }
            builder.setView(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageBitmap(image);
            AlertDialog alertDialog = builder.create();
            alertDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            alertDialog.show();
        } catch (Exception e) {
            Toast.makeText(context, "Error displaying image", Toast.LENGTH_LONG).show();
        }
    }

    public byte[] setImageOnActivityResult(int requestCode, int resultCode, Intent data, ImageView imageView, String pictureLocation) throws Exception {
        byte[] bsPicture = null;
        if (resultCode == Activity.RESULT_OK && data != null) {
            Uri uriSelectedImage;
            String picturePath;
            Bitmap bitmapPictureTaken;
            Bitmap bitmapResized;
            BitmapFactory.Options bitmapFactoryOptions;
            switch (requestCode) {
                case UPLOAD_IMAGE_GALLERY:
                    uriSelectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = context.getContentResolver().query(uriSelectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    bitmapFactoryOptions = new BitmapFactory.Options();
                    bitmapFactoryOptions.inSampleSize = 2;
                    bitmapPictureTaken = BitmapFactory.decodeFile(picturePath, bitmapFactoryOptions);
                    bitmapResized = Bitmap.createScaledBitmap(bitmapPictureTaken, bitmapPictureTaken.getWidth() * 2, bitmapPictureTaken.getHeight() * 2, false);
                    bsPicture = getBytes(bitmapResized, picturePath, pictureLocation);
                    break;
                case TAKE_IMAGE_CAMERA:
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    bitmapResized = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * 2, bitmap.getHeight() * 2, true);
                    File photo = null;
                    FileOutputStream stream;
                    String fileName = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                    fileName = fileName.replaceAll(" ", "");
                    fileName = fileName.replaceAll(":", "");
                    fileName = fileName.replaceAll(",", "");
                    fileName = fileName + ".jpg";
                    photo = new File(android.os.Environment.getExternalStorageDirectory(), fileName);
                    stream = new FileOutputStream(photo);
                    bitmapResized.compress(CompressFormat.JPEG, 100, stream);
                    uriSelectedImage = Uri.fromFile(photo);
                    picturePath = uriSelectedImage.getPath();
                    bitmapPictureTaken = (Bitmap) data.getExtras().get("data");
                    bitmapFactoryOptions = new BitmapFactory.Options();
                    bitmapFactoryOptions.inSampleSize = 2;
                    bsPicture = getBytes(bitmapResized, picturePath, pictureLocation);
                    break;
            }
            if (bsPicture != null) {
                bitmapPictureTaken = getImage(bsPicture);
                imageView.setImageBitmap(bitmapPictureTaken);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
        }
        return bsPicture;
    }


    public byte[] setImageOnActivityResult(int requestCode, int resultCode, Intent data, ImageView imageView, String pictureLocation,Activity mActivity) throws Exception {
        byte[] bsPicture = null;
        if (resultCode == Activity.RESULT_OK && data != null) {
            Uri uriSelectedImage;
            String picturePath;
            Bitmap bitmapPictureTaken;
            Bitmap bitmapResized;
            BitmapFactory.Options bitmapFactoryOptions;
            switch (requestCode) {
                case UPLOAD_IMAGE_GALLERY:
                    uriSelectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = mActivity.getContentResolver().query(uriSelectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    bitmapFactoryOptions = new BitmapFactory.Options();
                    bitmapFactoryOptions.inSampleSize = 2;
                    bitmapPictureTaken = BitmapFactory.decodeFile(picturePath, bitmapFactoryOptions);
                    bitmapResized = Bitmap.createScaledBitmap(bitmapPictureTaken, bitmapPictureTaken.getWidth() * 2, bitmapPictureTaken.getHeight() * 2, false);
                    bsPicture = getBytes(bitmapResized, picturePath, pictureLocation);
                    break;
                case TAKE_IMAGE_CAMERA:
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    bitmapResized = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * 2, bitmap.getHeight() * 2, true);
                    File photo = null;
                    FileOutputStream stream;
                    String fileName = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                    fileName = fileName.replaceAll(" ", "");
                    fileName = fileName.replaceAll(":", "");
                    fileName = fileName.replaceAll(",", "");
                    fileName = fileName + ".jpg";
                    String mPath;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
                        mPath = mActivity.getExternalFilesDir(Environment.DIRECTORY_DCIM) + "/saved_images";
                    } else {
                        mPath= Environment.getExternalStorageDirectory().toString() + "/saved_images";
                    }
                    File myDir  = new File(/*android.os.Environment.getExternalStorageDirectory()*/mPath);
                    myDir.mkdirs();

                    photo = new File (myDir, fileName);
                    if (photo.exists ()) photo.delete ();
                    photo.createNewFile();
                    //photo = new File(/*android.os.Environment.getExternalStorageDirectory()*/mPath, fileName);
                    stream = new FileOutputStream(photo);
                    bitmapResized.compress(CompressFormat.JPEG, 100, stream);
                    uriSelectedImage = Uri.fromFile(photo);
                    picturePath = uriSelectedImage.getPath();
                    bitmapPictureTaken = (Bitmap) data.getExtras().get("data");
                    bitmapFactoryOptions = new BitmapFactory.Options();
                    bitmapFactoryOptions.inSampleSize = 2;
                    bsPicture = getBytes(bitmapResized, picturePath, pictureLocation);
                    break;
            }
            if (bsPicture != null) {
                bitmapPictureTaken = getImage(bsPicture);
                imageView.setImageBitmap(bitmapPictureTaken);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
        }
        return bsPicture;
    }

    public byte[] getBytes(Bitmap resizedBitmap, String picturePath, String pictureLocation) {
        File file = new File(picturePath);
        Log.v("bitmap res", resizedBitmap.getWidth() + " " + resizedBitmap.getHeight());

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy  HH:mm");
        String dateTime = sdf.format(file.lastModified());
        Canvas cs = new Canvas(resizedBitmap);
        Paint tPaint = new Paint();
        if (resizedBitmap.getWidth() > resizedBitmap.getHeight()) {
            tPaint.setTextSize(27);
        } else {
            tPaint.setTextSize(20);
        }
        tPaint.setColor(Color.RED);
        tPaint.setStyle(Style.FILL);
        cs.drawText(dateTime, 10, resizedBitmap.getHeight() - 60, tPaint);
        if (pictureLocation != null && !pictureLocation.equals(""))
            cs.drawText("Location: " + pictureLocation, 10, resizedBitmap.getHeight() - 30, tPaint);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        resizedBitmap.compress(CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    public Bitmap getImage(byte[] image) throws Exception {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}