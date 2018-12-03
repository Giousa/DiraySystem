package com.zmm.diary.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/7/7
 * Time:下午10:59
 */

public class PictureCompressUtil {


    /**
     * 对图片进行重新采样
     * @param context
     * @param uri 图片的Uri地址
     * @param imageView
     * @return
     */
    public static Bitmap compressBitmap(Context context, Uri uri, ImageView imageView) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        decodeBitmap(context, uri, options);
        options = new BitmapFactory.Options();
        options.inSampleSize = calculatInSampleSize(options, imageView);
        Bitmap bitmap = null;

        try {
            bitmap = decodeBitmap(context, uri, options);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }


    /**
     * 将Uri转换成Bitmap
     * @param context
     * @param uri
     * @param options
     * @return
     */
    public static Bitmap decodeBitmap(Context context, Uri uri, BitmapFactory.Options options) {
        Bitmap bitmap = null;

        if (uri != null) {
            ContentResolver cr = context.getContentResolver();
            InputStream inputStream = null;
            try {
                /**
                 * 将图片的Uri地址转换成一个输入流
                 */
                inputStream = cr.openInputStream(uri);

                /**
                 * 将输入流转换成Bitmap
                 */
                bitmap = BitmapFactory.decodeStream(inputStream, null, options);

                assert inputStream != null;
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }


    /**
     * 计算位图的采样比例大小
     * @param options
     * @param imageView 控件(根据控件的大小进行压缩)
     * @return
     */
    private static int calculatInSampleSize(BitmapFactory.Options options, ImageView imageView) {
        //获取位图的原宽高
        final int w = options.outWidth;
        final int h = options.outHeight;

        if (imageView!=null){
            //获取控件的宽高
            final int reqWidth = imageView.getWidth();
            final int reqHeight = imageView.getHeight();

            //默认为一(就是不压缩)
            int inSampleSize = 1;
            //如果原图的宽高比需要的图片宽高大
            if (w > reqWidth || h > reqHeight) {
                if (w > h) {
                    inSampleSize = Math.round((float) h / (float) reqHeight);
                } else {
                    inSampleSize = Math.round((float) w / (float) reqWidth);
                }
            }

            System.out.println("压缩比为:" + inSampleSize);

            return inSampleSize;

        }else {
            return 1;
        }
    }


    //----------------------
    /**
     * @param path
     * @return
     * @throws IOException
     * 压缩图片
     */
    public static Bitmap revitionImageSize(String path) throws IOException {
        //根据文件路径,创建一个字节缓冲输入流
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //根据流返回一个位图也就是bitmap，当options.inJustDecodeBounds = true的时候不需要完全解码，
        // 它仅仅会把它的宽，高取回来给你，这样就不会占用太多的内存，也就不会那么频繁的发生OOM了
        BitmapFactory.decodeStream(in, null, options);
        //关闭流
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            // options.outWidth >> i 。右移运算符，num >> 1,相当于num除以2
            if ((options.outWidth >> i <= 1000) && (options.outHeight >> i <= 1000)) {
                //得到一个输入流
                in = new BufferedInputStream(new FileInputStream(new File(path)));
                //为了解决图片解码时候出现SanpleSize错误，设置恰当的inSampleSize可以使BitmapFactory分配更少的空间以消除该错误
                //你将 inSampleSize 赋值为2,那就是每隔2行采1行,每隔2列采一列,那你解析出的图片就是原图大小的1/4.
                // Math.pow(2.0D, i)次方运算，2的i次方是多少
                options.inSampleSize = (int) Math.pow(2.0D, i);
                // 这里之前设置为了true，所以要改为false，否则就创建不出图片
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }

    /**
     * @param bitmap
     * 保存图片到SD卡的方法
     */
    public static String saveBitmapFile(Bitmap bitmap, String name){
        //Environment.getExternalStorageDirectory() 获取Android外部存储的空间，当有外部SD卡就在外部SD卡上建立。
        //没有外部SD卡就在内部SD卡的非data/data/目录建立目录。（data/data/目录才是真正的内存目录。）
        //IMAGE_NAME文件的名字，随便起。比如（xxx.jpg）

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/tmsystem");

        if(!file.exists()){
            file.mkdirs();
        }

        File tempFile = new File(Environment.getExternalStorageDirectory(), name );

        try {
            //创建一个输出流，将数据写入到创建的文件对象中。
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempFile));
            ////30 是压缩率，表示压缩70%; 如果不压缩是100，
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
           /* 为什么要调用flush()方法？当FileOutputStream作为BufferedOutputStream构造函数的参数传入，然后对BufferedOutputStream进行写入操作，才能利用缓冲及flush()。
            查看BufferedOutputStream的源代码，发现所谓的buffer其实就是一个byte[]。
            BufferedOutputStream的每一次write其实是将内容写入byte[]，当buffer容量到达上限时，会触发真正的磁盘写入。
            而另一种触发磁盘写入的办法就是调用flush()了。*/
            bos.flush();
            //关闭流对象
            bos.close();

            return name;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void deleteFile(String filePath){
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

}
