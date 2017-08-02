package com.speedata.welllid.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

public class FileUtils {

	/**
	 * 将Drawable转化为Bitmap
	 *
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
				.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
				: Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;

	}

	/**
	 * 把文件转成字节数组
	 *
	 * @param filePath
	 * @return
	 */
	public static byte[] File2byte(File file) {
		byte[] buffer = null;
		try {
			if (null == file) {
				return buffer;
			}
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	/**
	 * 把图片压缩到200K
	 *
	 * @param oldpath
	 *            压缩前的图片路径
	 * @param newPath
	 *            压缩后的图片路径
	 * @return
	 */
	/**
	 * 把图片压缩到100K
	 *
	 * @param oldpath
	 *            压缩前的图片路径
	 * @param newPath
	 *            压缩后的图片路径
	 * @return
	 */
	public static File compressFile(String oldpath, String newPath) {
		if (oldpath == null || "".equals(oldpath))
			return null;
		Bitmap compressBitmap = FileUtils.decodeFile(oldpath);
		Bitmap newBitmap = ratingImage(oldpath, compressBitmap);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		newBitmap.compress(CompressFormat.PNG, 100, os);
		byte[] bytes = os.toByteArray();

		File file = null;
		try {
			file = FileUtils.getFileFromBytes(bytes, newPath);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (newBitmap != null) {
				if (!newBitmap.isRecycled()) {
					newBitmap.recycle();
				}
				newBitmap = null;
			}
			if (compressBitmap != null) {
				if (!compressBitmap.isRecycled()) {
					compressBitmap.recycle();
				}
				compressBitmap = null;
			}
		}
		return file;
	}

	private static Bitmap ratingImage(String filePath, Bitmap bitmap) {
		int degree = readPictureDegree(filePath);
		return rotaingImageView(degree, bitmap);
	}

	/**
	 * 旋转图片
	 *
	 * @param angle
	 * @param bitmap
	 * @return Bitmap
	 */
	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		System.out.println("angle2=" + angle);
		// 创建新的图片
		Bitmap resizedBitmap = null;
		try {
			resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
					bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resizedBitmap;
	}

	/**
	 * 读取图片属性：旋转的角度
	 *
	 * @param path
	 *            图片绝对路径
	 * @return degree旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * 把字节数组保存为一个文件
	 *
	 * @param b
	 * @param outputFile
	 * @return
	 */
	public static File getFileFromBytes(byte[] b, String outputFile) {
		File ret = null;
		BufferedOutputStream stream = null;
		try {
			ret = new File(outputFile);
			FileOutputStream fstream = new FileOutputStream(ret);
			stream = new BufferedOutputStream(fstream);
			stream.write(b);
		} catch (Exception e) {
			// log.error("helper:get file from byte process error!");
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					// log.error("helper:get file from byte process error!");
					e.printStackTrace();
				}
			}
		}
		return ret;
	}

	/**
	 * 图片压缩
	 *
	 * @param fPath
	 * @return
	 */
	public static Bitmap decodeFile(String fPath) {
		if ("".equals(fPath) || fPath == null)
			return null;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		opts.inDither = false; // Disable Dithering mode
		opts.inPurgeable = true; // Tell to gc that whether it needs free
		opts.inInputShareable = true; // Which kind of reference will be used to
		BitmapFactory.decodeFile(fPath, opts);
		final int REQUIRED_SIZE = 200;
		int scale = 1;
		if (opts.outHeight > REQUIRED_SIZE || opts.outWidth > REQUIRED_SIZE) {
			final int heightRatio = Math.round((float) opts.outHeight
					/ (float) REQUIRED_SIZE);
			final int widthRatio = Math.round((float) opts.outWidth
					/ (float) REQUIRED_SIZE);
			scale = heightRatio < widthRatio ? heightRatio : widthRatio;//
		}
		Log.i("scale", "scal =" + scale);
		opts.inJustDecodeBounds = false;
		opts.inSampleSize = scale;
		Bitmap bm = null;
		try {
			bm = BitmapFactory.decodeFile(fPath, opts).copy(Config.ARGB_8888,
					false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bm;
	}

	/**
	 * 创建目录
	 *
	 * @param path
	 */
	public static void setMkdir(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
			Log.e("file", "目录不存在  创建目录    ");
		} else {
			Log.e("file", "目录存在");
		}
	}

	/**
	 * 获取目录名称
	 *
	 * @param url
	 * @return FileName
	 */
	public static String getFileName(String url) {
		int lastIndexStart = url.lastIndexOf("/");
		if (lastIndexStart != -1) {
			return url.substring(lastIndexStart + 1, url.length());
		} else {
			return null;
		}
	}

	/**
	 * 删除该目录下的文件
	 *
	 * @param path
	 */
	public static void delFile(String path) {
		if (!TextUtils.isEmpty(path)) {
			File file = new File(path);
			if (file.exists()) {
				file.delete();
			}
		}
	}

	/**
	 * 对bitmap类型的图片进行压缩
	 *
	 * @param bitmap
	 * @param size
	 * @return
	 */
	public static byte[] compressBitmapByQuality(Bitmap bitmap, float size) {
		if (bitmap == null || getBitmapSize(bitmap) <= size) {
			return null;// 如果图片本身的大小已经小于这个大小了，就没必要进行压缩
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 70, baos);// 如果签名是png的话，则不管quality是多少，都不会进行质量的压缩
		int quality = 90;
		while (baos.toByteArray().length / 1024f > size) {
			int num = baos.toByteArray().length;
			if (num > 5120 && quality > 10) {
				quality = quality - 20;// 每次都减少
			} else if (num > 2048 && quality > 10) {
				quality = quality - 10;// 每次都减少
			} else {
				quality = quality - 4;// 每次都减少
			}
			System.out.println("压缩的值为：" + quality);
			if (quality <= 0) {
				break;
			}
			System.out.println("压缩的值为：" + quality);
			baos.reset();// 重置baos即清空baos
			bitmap.compress(CompressFormat.JPEG, quality, baos);
			System.out.println("此时的压缩------质量-----" + baos.toByteArray().length
					/ 1024f);
		}
		return baos.toByteArray();
	}

	/**
	 * 获取bitmap的大小
	 *
	 * @param bitmap
	 * @return
	 */
	@SuppressLint("NewApi")
	public static int getBitmapSize(Bitmap bitmap) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // API 19
			return bitmap.getAllocationByteCount();
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {// API
			// 12
			return bitmap.getByteCount();
		}
		return bitmap.getRowBytes() * bitmap.getHeight(); // earlier version
	}

	public static byte[] decodeBitmap(String path) {

		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;// 设置成了true,不占用内存，只获取bitmap宽高
		BitmapFactory.decodeFile(path, opts);
		opts.inSampleSize = computeSampleSize(opts, -1, 1024 * 800);

		opts.inJustDecodeBounds = false;// 这里一定要将其设置回false，因为之前我们将其设置成了true
		opts.inPurgeable = true;
		opts.inInputShareable = true;
		opts.inDither = false;
		opts.inPurgeable = true;
		opts.inTempStorage = new byte[16 * 1024];
		FileInputStream is = null;
		Bitmap bmp = null;
		InputStream ins = null;
		ByteArrayOutputStream baos = null;
		try {
			is = new FileInputStream(path);
			bmp = BitmapFactory.decodeFileDescriptor(is.getFD(), null, opts);
			double scale = getScaling(opts.outWidth * opts.outHeight,
					1024 * 600);
			Bitmap bmp2 = Bitmap.createScaledBitmap(bmp,
					(int) (opts.outWidth * scale),
					(int) (opts.outHeight * scale), true);
			bmp.recycle();
			baos = new ByteArrayOutputStream();
			bmp2.compress(CompressFormat.JPEG, 100, baos);
			bmp2.recycle();
			return baos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
				if (ins != null)
					ins.close();
				if (baos != null)
					baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.gc();
		}
		return baos.toByteArray();
	}

	private static double getScaling(int src, int des) {
		/**
		 * 目标尺寸÷原尺寸 sqrt开方，得出宽高百分比
		 */
		double scale = Math.sqrt((double) des / (double) src);
		return scale;
	}

	/*
	 * Compute the sample size as a function of minSideLength and
	 * maxNumOfPixels. minSideLength is used to specify that minimal width or
	 * height of a bitmap. maxNumOfPixels is used to specify the maximal size in
	 * pixels that is tolerable in terms of memory usage.
	 *
	 * The function returns a sample size based on the constraints. Both size
	 * and minSideLength can be passed in as IImage.UNCONSTRAINED, which
	 * indicates no care of the corresponding constraint. The functions prefers
	 * returning a sample size that generates a smaller bitmap, unless
	 * minSideLength = IImage.UNCONSTRAINED.
	 *
	 * Also, the function rounds up the sample size to a power of 2 or multiple
	 * of 8 because BitmapFactory only honors sample size this way. For example,
	 * BitmapFactory downsamples an image by 2 even though the request is 3. So
	 * we round up the sample size to avoid OOM.
	 */
	public static int computeSampleSize(BitmapFactory.Options options,
										int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}

	public static int computeInitialSampleSize(BitmapFactory.Options options,
											   int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	/**
	 * 图片按比例大小压缩方法（根据Bitmap图片压缩）
	 *
	 * @param image
	 * @return
	 */
	public static byte[] compressBitmap(Bitmap image, float size) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			image.compress(CompressFormat.JPEG, 40, baos);// 这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		System.out.println("此时的w与h为:" + w + "----" + h);
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 0f;// 这里设置高度为800f
		float ww = 0f;// 这里设置宽度为480f
		if (w > h) {
			ww = 960f;// 这里设置高度为800f
			hh = 720f;// 这里设置宽度为480f
		} else if (w < h) {
			hh = 960f;// 这里设置高度为800f
			ww = 720f;// 这里设置宽度为480f
		} else {
			hh = 960f;// 这里设置高度为800f
			ww = 960f;// 这里设置宽度为480f
		}
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		} else {
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		newOpts.inPreferredConfig = Config.RGB_565;// 降低图片从ARGB888到RGB565
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressBitmapByQuality(bitmap, size);// 压缩好比例大小后再进行质量压缩
	}

	public static Bitmap convertToBitmap(String path, int w, int h) {
		// File file = new File(path);
		// System.out.println(file.exists()
		// + "---------------file.exists()---------");
		Bitmap myBitmap = null;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 设置为ture只获取图片大小
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Config.ARGB_8888;
		// 返回为空
		BitmapFactory.decodeFile(path, opts);
		int width = opts.outWidth;
		int height = opts.outHeight;
		float scaleWidth = 0.f, scaleHeight = 0.f;
		int myWidth = 0, myheight = 0;
		if (width > w || height > h) {
			// 缩放
			scaleWidth = ((float) width) / w;
			scaleHeight = ((float) height) / h;
		}

		if (width > height) { // 4:3 16:9
			myWidth = 960;
			if ((float) width / height > 1.5) {
				myheight = (int) myWidth * 9 / 16;
			} else {
				myheight = (int) myWidth * 3 / 4;
			}
		} else if (width < height) {
			myheight = 960;
			if ((float) width / height > 0.6) { // 3:4
				myWidth = (int) myheight * 3 / 4;
			} else {
				myWidth = (int) myheight * 9 / 16;
			}
		} else {
			myheight = myWidth = 960;
		}
		opts.inJustDecodeBounds = false;
		float scale = Math.max(scaleWidth, scaleHeight);
		opts.inSampleSize = (int) scale;
		WeakReference<Bitmap> weak = new WeakReference<Bitmap>(
				BitmapFactory.decodeFile(path, opts));
		System.out.println(weak + "---------------weak----------------------");
		Bitmap bmp = weak.get();
		if (bmp.getWidth() == myWidth && bmp.getHeight() == myheight) {
			myBitmap = bmp;
		} else {
			myBitmap = Bitmap.createScaledBitmap(weak.get(), myWidth, myheight,
					true);
		}
		// bmp.recycle();
		return myBitmap;
	}

	/**
	 * 将bitmap转化成图片文件
	 */
	public static File saveImg2File(Bitmap b, String name) {

		String path = Environment.getExternalStorageDirectory() + "/"
				+ "touxiang/";
		File dirFile = new File(path);
		File mediaFile = new File(path + File.separator + name + ".jpg");
		if (mediaFile.exists()) {
			mediaFile.delete();
		}
		if (!new File(path).exists()) {
			new File(path).mkdirs();
		}
		try {
			mediaFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(mediaFile);
			b.compress(CompressFormat.PNG, 100, fos);
			fos.flush();
			fos.close();
			b.recycle();
			b = null;
			System.gc();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return mediaFile;
	}
}
