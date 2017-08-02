package com.speedata.welllid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testpic.Bimp;
import com.example.testpic.FileUtils;
import com.example.testpic.PhotoActivity;
import com.example.testpic.PublishedActivity;
import com.johdan.paint.R;
import com.johdan.paint.TuyaActivity;
import com.speedata.libutils.CommonUtils;
import com.speedata.utils.ProgressDialogUtils;
import com.speedata.welllid.bean.DialogShowMsg;
import com.speedata.welllid.bean.JgDevice;
import com.speedata.welllid.bean.Result;
import com.speedata.welllid.callback.ActiveDevInter;
import com.speedata.welllid.callback.ResultInter;
import com.speedata.welllid.net.Global;
import com.speedata.welllid.net.NetManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.johdan.paint.TuyaActivity.loadFromSdCard;

public class InitDevActivity extends BaseActivity implements ResultInter {
    private static final int TAKE_PHOTO = 1;
    private static final int CROP_PHOTO = 2;
    public static final int TUYA_PHOTO = 3;
    private static final int CAMERA_REQUEST_CODE = 2;
    private File file = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);
    private Uri imageCropUri = Uri.fromFile(file);
    // 拍照存放本地文件名称
    public static final String IMAGE_FILE_NAME = "temp.jpg";
    public static final String IMAGE_DIR = Environment.getExternalStorageDirectory() + "/welllid";

    FloatingActionButton fab;

//    private ImageView imgTest;
//    private LinearLayout layoutPic;

    private GridView noScrollgridview;
    private GridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_dev);
        registerEventBus();
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("初始化井盖");
        setSupportActionBar(toolbar);
        //需在setSupportActionBar后设置icon
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitDevActivity.this.finish();
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                showDialogMsg(new DialogShowMsg("正在上传", Global.REQUEST_PREPARE));

                //上传图片
//                List<String> list = new ArrayList<String>();
//                for (int i = 0; i < Bimp.drr.size(); i++) {
//                    String Str = Bimp.drr.get(i).substring(
//                            Bimp.drr.get(i).lastIndexOf("/") + 1,
//                            Bimp.drr.get(i).lastIndexOf("."));
//                    list.add(FileUtils.SDPATH+Str+".JPEG");
//                }
//                // 高清的压缩图片全部就在  list 路径里面了
//                // 高清的压缩过的 bmp 对象  都在 Bimp.bmp里面
//                // 完成上传服务器后 .........
//                FileUtils.deleteDir();
//                com.speedata.welllid.utils.FileUtils.compressFile(Bimp.drr.get(0),);
                NetManager.commitPic(com.speedata.welllid.utils.FileUtils.decodeFile(Bimp.drr.get(0)), new ResultInter() {
                    @Override
                    public void getResult(Result loginResult) {

                    }
                });


                JgDevice jgDevice = new JgDevice();
                jgDevice.setLatitude(106.298 + "");
                jgDevice.setLongitude(108.786 + "");
                jgDevice.setCode(getCode());
                jgDevice.setDeviceTypeName("污水");
                jgDevice.setAddress("上地六街志远大厦");
//                jgDevice.setCreateTime(SystemClock.currentThreadTimeMillis());
                NetManager.activeDev(jgDevice, InitDevActivity.this);

            }
        });
        Init();
    }


    public void Init() {
        noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(InitDevActivity.this, this);
//        loading();
        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 == Bimp.bmp.size()) {
                    //添加
//                    new PublishedActivity.PopupWindows(InitDevActivity.this, noScrollgridview);
                    Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // 判断存储卡是否可以用，可用进行存储
                    if (hasSdcard()) {
                        intentFromCapture.putExtra("return-data", false);
                        imageUri = Uri.fromFile(file);
                        intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        intentFromCapture.putExtra("noFaceDetection", true);
                    }
                    startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
                } else {
                    Intent intent = new Intent(InitDevActivity.this,
                            PhotoActivity.class);
                    intent.putExtra("ID", arg2);
                    startActivity(intent);
                }
            }
        });
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < Bimp.drr.size(); i++) {
            String Str = Bimp.drr.get(i).substring(
                    Bimp.drr.get(i).lastIndexOf("/") + 1,
                    Bimp.drr.get(i).lastIndexOf("."));
            list.add(FileUtils.SDPATH + Str + ".JPEG");
        }
        // 高清的压缩图片全部就在  list 路径里面了
        // 高清的压缩过的 bmp 对象  都在 Bimp.bmp里面
        // 完成上传服务器后 .........
        FileUtils.deleteDir();
        loading();
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    adapter.notifyDataSetChanged();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public void loading() {
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (Bimp.max == Bimp.drr.size()) {
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                        break;
                    } else {
                        try {
                            String path = Bimp.drr.get(Bimp.max);
                            System.out.println("path=" + path);

                            Bitmap bm = Bimp.revitionImageSize(path);
                            Bimp.bmp.add(bm);
                            String newStr = path.substring(
                                    path.lastIndexOf("/") + 1,
                                    path.lastIndexOf("."));
                            FileUtils.saveBitmap(bm, "" + newStr);
                            Bimp.max += 1;
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    private int resPhotoID;
    Uri imageUri;

    /**
     * 判断sdcard是否存在
     */
    private boolean hasSdcard() {
        // 判断Sdcard是否可用
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }


    /**
     * 处理回转值
     */
    @SuppressWarnings("static-access")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != this.RESULT_CANCELED) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:
                    if (hasSdcard()) {
                        ProgressDialogUtils.showProgressDialog(InitDevActivity.this, "正在生成照片");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Bitmap bitmap = null;
                                try {
                                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                                    saveBitmap(bitmap, IMAGE_FILE_NAME);
                                    //                            intent.putExtra("bitmap", bitmap);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ProgressDialogUtils.dismissProgressDialog();
                                        Intent intent = new Intent(InitDevActivity.this, TuyaActivity.class);
                                        startActivityForResult(intent, TUYA_PHOTO);

//                                        startActivity(intent);
                                    }
                                });

                            }
                        }).start();

                    } else {
                        Toast.makeText(this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                // 涂鸦后加载图片
                case TUYA_PHOTO:
//                    ImageView imageView = new ImageView(this);
//                    imageView.setLayoutParams(new LinearLayout.LayoutParams(150, 150));  //设置图片宽高
//                    imageView.setImageBitmap(loadFromSdCard(IMAGE_DIR + "/" + IMAGE_FILE_NAME)); //图片资源
//                    layoutPic.addView(imageView);
                    if (Bimp.drr.size() < 9) {
                        Bimp.drr.add(IMAGE_DIR + "/" + IMAGE_FILE_NAME);
                    }
                    adapter.notifyDataSetChanged();
                    loading();
                    break;

            }
        }
    }

    /**
     * 保存方法
     */
    public static void saveBitmap(Bitmap bitmap, String picName) {
        System.out.println("saveBitmap start");
        File f = new File(IMAGE_DIR, picName);
        File fpath = new File(IMAGE_DIR);
        if (!fpath.exists()) {
//            f.delete();
            fpath.mkdir();
        }
        if (f.exists())
            f.delete();
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, out);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("saveBitmap end");
    }

    @Override
    public void getResult(Result active) {
        if (active.isSuccess()) {
            Snackbar.make(fab, active.getMessage(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    private void uploadPic() {

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterEventBus();
    }

    private String getCode() {
        Random random = new Random();
        String code;
        code = random.nextInt(100000000) + "";
        return code;
    }
}
