package nova.croprotector;

import android.app.Fragment;
import android.content.Context;


import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.ColorDrawable;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.AudioAttributes;
import android.media.Image;
import android.media.ImageReader;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Range;
import android.util.Size;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Shoot_fragment extends Fragment {
    private static final String TAG = "Shoot_fragment";
    private static final int SETIMAGE = 1;
    private static final int MOVE_FOCK = 2;

    TextureView mTextureView;
    ImageView mThumbnail;
    Button mButton;
    Handler mHandler;
    Handler mUIHandler;
    ImageReader mImageReader;
    CaptureRequest.Builder mPreViewBuilder;
    CameraCaptureSession mCameraSession;
    CameraCharacteristics mCameraCharacteristics;
    Ringtone ringtone;

    ByteBuffer mBuffer=null;


    //网络通信数据传输相关
    private Gson gson=new Gson();
    private CommonResponse<DiseaseKind> res=new CommonResponse<DiseaseKind>();
    private static final MediaType JSON=MediaType.parse("application/json;charset=utf-8");
    private DiseaseInfo diseaseinfo1=new DiseaseInfo();

    //用户信息缓存文件存储
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    //弹窗控件
    private TextView classify_result_text;
    private com.wang.avi.AVLoadingIndicatorView loadingview;
    private TextView classify_no_result;
    private TextView classify_result_title;

    //更新View状态
    private static final int UPDATE_VIEW=1;
    private Handler handler;

    //相机会话的监听器，通过他得到mCameraSession对象，这个对象可以用来发送预览和拍照请求
    private CameraCaptureSession.StateCallback mSessionStateCallBack = new CameraCaptureSession
            .StateCallback() {
        @Override
        public void onConfigured(CameraCaptureSession cameraCaptureSession) {
            try {
                mCameraSession = cameraCaptureSession;
                cameraCaptureSession.setRepeatingRequest(mPreViewBuilder.build(), null, mHandler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {

        }
    };
    private Surface surface;
    //打开相机时候的监听器，通过他可以得到相机实例，这个实例可以创建请求建造者
    private CameraDevice.StateCallback cameraOpenCallBack = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice cameraDevice) {
            Log.d(TAG, "相机已经打开");
            try {
                mPreViewBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                SurfaceTexture texture = mTextureView.getSurfaceTexture();
                texture.setDefaultBufferSize(mPreViewSize.getWidth(), mPreViewSize.getHeight());
                surface = new Surface(texture);
                mPreViewBuilder.addTarget(surface);
                cameraDevice.createCaptureSession(Arrays.asList(surface, mImageReader.getSurface
                        ()), mSessionStateCallBack, mHandler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onDisconnected(CameraDevice cameraDevice) {
            Log.d(TAG, "相机连接断开");
        }

        @Override
        public void onError(CameraDevice cameraDevice, int i) {
            Log.d(TAG, "相机打开失败");
        }
    };

    //本次拍照的图片数据,可以通过文件流把他输出成JPEG格式的图片文件
    private ImageReader.OnImageAvailableListener onImageAvailableListener = new ImageReader
            .OnImageAvailableListener() {
        @Override
        public void onImageAvailable(ImageReader imageReader) {
            Image reader=imageReader.acquireNextImage();
            ByteBuffer Buffer = reader.getPlanes()[0].getBuffer();
            byte[] buff = new byte[Buffer.remaining()];
            Buffer.get(buff);
            BitmapFactory.Options ontain = new BitmapFactory.Options();
            ontain.inSampleSize = 50;
            Bitmap bm = BitmapFactory.decodeByteArray(buff, 0, buff.length, ontain);

            mHandler.post(new ImageSaver(bm,buff));

            final View view = LayoutInflater.from(getActivity()).inflate(R.layout.classify_result, null);
            classify_result_title=(TextView)view.findViewById(R.id.classify_result_title);
            //classify_result_text=(TextView)view.findViewById(R.id.classify_result_text);
            //classify_no_result=(TextView)view.findViewById(R.id.classify_no_result);
            loadingview=(com.wang.avi.AVLoadingIndicatorView)view.findViewById(R.id.avi);

            //弹出识别结果窗口
            final PopupWindow resultWindow=resultWindow(view);
            Log.d(TAG, "窗口已弹出");

            //网络传输接收数据，并存入缓存文件
            String base64Str=PictureClass.BitmapToString(bm);
            diseaseinfo1.setPicture(base64Str);

            //获取当前时间，转成String类型
            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date curDate=new Date(System.currentTimeMillis());
            String sinfoTime=formatter.format(curDate);
            diseaseinfo1.setInfoTime(sinfoTime);

            //设置diseaseNo，diseaseKind，longitude,latitude，因为此时还未检测，所以全部是默认值
            diseaseinfo1.setDiseaseNo("未检测");
            diseaseinfo1.setDiseaseKind(new DiseaseKind());
            diseaseinfo1.setLongitude(-1.0);
            diseaseinfo1.setLatitude(-1.0);

            //从用户信息的缓存文件中取出phonenumber
            sp=getActivity().getSharedPreferences("userdata",Context.MODE_PRIVATE);
            String phonenumber=sp.getString("phonenumber","检测不到登录信息");
            diseaseinfo1.setPhonenumber(phonenumber);
            //设置infoNo
            String infoNo=phonenumber+sinfoTime;
            diseaseinfo1.setInfoNo(infoNo);

            //数据获取完毕，开始网络通信部分
            String jsonStr=gson.toJson(diseaseinfo1);
            RequestBody requestBody=RequestBody.create(JSON,jsonStr);
            HttpUtil.sendHttpRequest("http://172.20.10.14:8080/Croprotector/TestPictureServlet",requestBody,new okhttp3.Callback(){
                @Override
                public void onResponse(Call call, Response response) throws IOException{
                    //setSimulateClick(button, 160, 100);
                    String responseData = response.body().string();
                    res=GsonToBean.fromJsonObject(responseData,DiseaseKind.class);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final View view = LayoutInflater.from(getActivity()).inflate(R.layout.classify_result_1, null);
                            if(res.code==0){

                                //接收返回的data
                                DiseaseKind diseaseKind=res.data;
                                diseaseinfo1.setDiseaseKind(diseaseKind);
                                diseaseinfo1.setDiseaseNo(diseaseKind.getDiseaseNo());


                                //将记录存储进缓存文件
                                String jsonStr1=gson.toJson(diseaseinfo1);
                                sp=getActivity().getSharedPreferences("infodata",Context.MODE_PRIVATE);
                                editor=sp.edit();
                                editor.putString(diseaseinfo1.getInfoNo(),jsonStr1);
                                editor.putBoolean("isEmpty",false);                     //false证明文件不为空
                                editor.commit();
                                Log.d(TAG, "缓存文件已存储");

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Message message=new Message();
                                        message.what=UPDATE_VIEW;
                                        handler.sendMessage(message);
                                    }
                                }).start();

                                //弹出识别结果窗口
                                //classify_result_text.setText("检测结果为："+diseaseKind.getDiseaseName());
                                //classify_result_title.setVisibility(View.VISIBLE);
                                //classify_result_text.setVisibility(View.VISIBLE);
                                //classify_no_result.setVisibility(View.INVISIBLE);
                                //loadingview.setVisibility(View.INVISIBLE);
                                resultWindow_1(view,diseaseKind.getDiseaseName());
                                Log.d(TAG, "窗口已弹出");

                            }
                            else{
                                //classify_result_title.setVisibility(View.VISIBLE);
                                //classify_result_text.setVisibility(View.INVISIBLE);
                                //classify_no_result.setVisibility(View.VISIBLE);
                                //loadingview.setVisibility(View.INVISIBLE);
                                //弹出识别结果窗口
                                resultWindow_1(view,"健康");
                                Log.d(TAG, "窗口已弹出");
                            }
                        }
                    });
                }

                @Override
                public void onFailure(Call call,IOException e){
                    //异常处理
                }
            });

            handler=new Handler(){
                public void handleMessage(Message msg){
                    if(msg.what==UPDATE_VIEW){
                        //更新View
                    }
                }
            };
        }
    };

    private Size mPreViewSize;
    private Rect maxZoomrect;
    private int maxRealRadio;
    //预览图显示控件的监听器，可以监听这个surface的状态
    private TextureView.SurfaceTextureListener mSurfacetextlistener = new TextureView
            .SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
            HandlerThread thread = new HandlerThread("Ceamera3");
            thread.start();
            mHandler = new Handler(thread.getLooper());
            CameraManager manager = (CameraManager) getActivity().getSystemService(Context
                    .CAMERA_SERVICE);
            String cameraid = CameraCharacteristics.LENS_FACING_FRONT + "";
            try {
                mCameraCharacteristics = manager.getCameraCharacteristics(cameraid);

                //画面传感器的面积，单位是像素。
                maxZoomrect = mCameraCharacteristics.get(CameraCharacteristics
                        .SENSOR_INFO_ACTIVE_ARRAY_SIZE);
                //最大的数字缩放
                maxRealRadio = mCameraCharacteristics.get(CameraCharacteristics
                        .SCALER_AVAILABLE_MAX_DIGITAL_ZOOM).intValue();
                picRect = new Rect(maxZoomrect);

                StreamConfigurationMap map = mCameraCharacteristics.get(CameraCharacteristics
                        .SCALER_STREAM_CONFIGURATION_MAP);
                Size largest = Collections.max(Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)
                ), new CompareSizeByArea());
                mPreViewSize = map.getOutputSizes(SurfaceTexture.class)[0];
                mImageReader = ImageReader.newInstance(largest.getWidth(), largest.getHeight(),
                        ImageFormat.JPEG, 5);
                mImageReader.setOnImageAvailableListener(onImageAvailableListener, mHandler);
                manager.openCamera(cameraid, cameraOpenCallBack, mHandler);
                //设置点击拍照的监听
                mButton.setOnTouchListener(onTouchListener);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            } catch(SecurityException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

        }
    };

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //应该是长按触发连拍
                    try {
                        mCameraSession.setRepeatingRequest(initDngBuilder().build(), null, mHandler);
                    } catch (CameraAccessException e) {
                        Toast.makeText(getActivity(), "请求相机权限被拒绝", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    try {
                        mCameraSession.setRepeatingRequest(initDngBuilder().build(), null, mHandler);
                        updateCameraPreviewSession();
                    } catch (CameraAccessException e) {
                        Toast.makeText(getActivity(), "请求相机权限被拒绝", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            return true;
        }
    };

    private void updateCameraPreviewSession() throws CameraAccessException {
        mPreViewBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_CANCEL);
        mPreViewBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
        mCameraSession.setRepeatingRequest(mPreViewBuilder.build(), null, mHandler);
    }

    /**
     * 设置连拍的参数
     *
     * @return
     */
    private CaptureRequest.Builder initDngBuilder() {
        CaptureRequest.Builder captureBuilder = null;
        try {
            captureBuilder = mCameraSession.getDevice().createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);

            captureBuilder.addTarget(mImageReader.getSurface());
            captureBuilder.addTarget(surface);
            // Required for RAW capture
            captureBuilder.set(CaptureRequest.STATISTICS_LENS_SHADING_MAP_MODE, CaptureRequest.STATISTICS_LENS_SHADING_MAP_MODE_ON);
            captureBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_OFF);
            captureBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            captureBuilder.set(CaptureRequest.SENSOR_EXPOSURE_TIME, (long) ((214735991 - 13231) / 2));
            captureBuilder.set(CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION, 0);
            captureBuilder.set(CaptureRequest.SENSOR_SENSITIVITY, (10000 - 100) / 2);//设置 ISO，感光度
            captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, 90);
            //设置每秒30帧
            CameraManager cameraManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
            String cameraid = CameraCharacteristics.LENS_FACING_FRONT + "";
            CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraid);
            Range<Integer> fps[] = cameraCharacteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
            captureBuilder.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, fps[fps.length - 1]);
        } catch (CameraAccessException e) {
            Toast.makeText(getActivity(), "请求相机权限被拒绝", Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            Toast.makeText(getActivity(), "打开相机失败", Toast.LENGTH_SHORT).show();
        }
        return captureBuilder;
    }

    private View.OnClickListener picOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                shootSound();
                Log.d(TAG, "正在拍照");
                CaptureRequest.Builder builder = mCameraSession.getDevice().createCaptureRequest
                        (CameraDevice.TEMPLATE_STILL_CAPTURE);
                builder.addTarget(mImageReader.getSurface());
                builder.set(CaptureRequest.SCALER_CROP_REGION, picRect);
                builder.set(CaptureRequest.CONTROL_AF_MODE,
                        CaptureRequest.CONTROL_AF_MODE_EDOF);
//                builder.set(CaptureRequest.CONTROL_AF_TRIGGER,
//                        CameraMetadata.CONTROL_AF_TRIGGER_START);
                builder.set(CaptureRequest.JPEG_ORIENTATION, 90);
                mCameraSession.capture(builder.build(), null, mHandler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    };
    private View.OnTouchListener textTureOntuchListener = new View.OnTouchListener() {
        //时时当前的zoom
        public double zoom;
        // 0<缩放比<mCameraCharacteristics.get(CameraCharacteristics
        // .SCALER_AVAILABLE_MAX_DIGITAL_ZOOM).intValue();
        //上次缩放前的zoom
        public double lastzoom;
        //两个手刚一起碰到手机屏幕的距离
        public double length;
        int count;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    count = 1;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (count >= 2) {
                        float x1 = event.getX(0);
                        float y1 = event.getY(0);
                        float x2 = event.getX(1);
                        float y2 = event.getY(1);
                        float x = x1 - x2;
                        float y = y1 - y2;
                        Double lengthRec = Math.sqrt(x * x + y * y) - length;
                        Double viewLength = Math.sqrt(v.getWidth() * v.getWidth() + v.getHeight()
                                * v.getHeight());
                        zoom = ((lengthRec / viewLength) * maxRealRadio) + lastzoom;
                        picRect.top = (int) (maxZoomrect.top / (zoom));
                        picRect.left = (int) (maxZoomrect.left / (zoom));
                        picRect.right = (int) (maxZoomrect.right / (zoom));
                        picRect.bottom = (int) (maxZoomrect.bottom / (zoom));
                        Message.obtain(mUIHandler, MOVE_FOCK).sendToTarget();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    count = 0;
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    count++;
                    if (count == 2) {
                        float x1 = event.getX(0);
                        float y1 = event.getY(0);
                        float x2 = event.getX(1);
                        float y2 = event.getY(1);
                        float x = x1 - x2;
                        float y = y1 - y2;
                        length = Math.sqrt(x * x + y * y);
                    }
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    count--;
                    if (count < 2)
                        lastzoom = zoom;
                    break;
            }
            return true;
        }
    };
    //相机缩放相关
    private Rect picRect;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shoot, null);
        findview(v);
        mUIHandler = new Handler(new InnerCallBack());
        //初始化拍照的声音
        ringtone = RingtoneManager.getRingtone(getActivity(), Uri.parse
                ("file:///system/media/audio/ui/camera_click.ogg"));
        AudioAttributes.Builder attr = new AudioAttributes.Builder();
        attr.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION);
        ringtone.setAudioAttributes(attr.build());
        //初始化相机布局
        mTextureView.setSurfaceTextureListener(mSurfacetextlistener);
        mTextureView.setOnTouchListener(textTureOntuchListener);
        return v;
    }
	
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
    }
	
	

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mCameraSession != null) {
            mCameraSession.getDevice().close();
            mCameraSession.close();
        }
    }

    private void findview(View v) {
        mTextureView = (TextureView) v.findViewById(R.id.tv_textview);
        mButton = (Button) v.findViewById(R.id.btn_takepic);
        mThumbnail = (ImageView) v.findViewById(R.id.iv_Thumbnail);
        mThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "别戳了，那个页面还没写", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 播放系统的拍照的声音
     */
    public void shootSound() {
        ringtone.stop();
        ringtone.play();
    }

    private class ImageSaver implements Runnable {
        Bitmap bm;
        byte[] buff;

        public ImageSaver(Bitmap bm,byte[] buff) {
            this.bm = bm;
            this.buff=buff;
        }

        @Override
        public void run() {
            Log.d(TAG, "正在保存图片");
            File dir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsoluteFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, System.currentTimeMillis() + ".jpg");
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(file);
                Message.obtain(mUIHandler, SETIMAGE, bm).sendToTarget();
                outputStream.write(buff);
                Log.d(TAG, "保存图片完成");
                for(int i=0;i<=100;i++){

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                /*if (reader != null) {
                    reader.close();
                }*/
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private class InnerCallBack implements Handler.Callback {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case SETIMAGE:
                    Bitmap bm = (Bitmap) message.obj;
                    mThumbnail.setImageBitmap(bm);
                    break;
                case MOVE_FOCK:
                    mPreViewBuilder.set(CaptureRequest.SCALER_CROP_REGION, picRect);
                    try {
                        mCameraSession.setRepeatingRequest(mPreViewBuilder.build(), null,
                                mHandler);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            return false;
        }
    }


    //识别后弹出结果窗口
    private PopupWindow resultWindow(View view){
        //View view = LayoutInflater.from(getActivity()).inflate(R.layout.classify_result, null);
        //1.构造一个PopupWindow，参数依次是加载的View，宽高
        final PopupWindow popWindow = new PopupWindow(view,
                400, ViewGroup.LayoutParams.WRAP_CONTENT);
        //final PopupWindow popWindow = new PopupWindow(this.getActivity());
        //popWindow.setAnimationStyle(R.anim.anim_pop);  //设置加载动画

        //这些为了点击非PopupWindow区域，PopupWindow会消失的，如果没有下面的
        //代码的话，你会发现，当你把PopupWindow显示出来了，无论你按多少次后退键
        //PopupWindow并不会关闭，而且退不出程序，加上下述代码可以解决这个问题
        popWindow.setOutsideTouchable(true);
        popWindow.setTouchable(true);
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popWindow.setBackgroundDrawable(new ColorDrawable(0x11111111));    //要为popWindow设置一个背景才有效
        //设置popupWindow显示的位置
        popWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        return popWindow;
    }

    private PopupWindow resultWindow_1(View view,String text){
        //View view = LayoutInflater.from(getActivity()).inflate(R.layout.classify_result, null);
        //1.构造一个PopupWindow，参数依次是加载的View，宽高
        final PopupWindow popWindow = new PopupWindow(view,
                400, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView result=(TextView)view.findViewById(R.id.classify_result_text);
        result.setText(text);
        //final PopupWindow popWindow = new PopupWindow(this.getActivity());
        //popWindow.setAnimationStyle(R.anim.anim_pop);  //设置加载动画

        //这些为了点击非PopupWindow区域，PopupWindow会消失的，如果没有下面的
        //代码的话，你会发现，当你把PopupWindow显示出来了，无论你按多少次后退键
        //PopupWindow并不会关闭，而且退不出程序，加上下述代码可以解决这个问题
        popWindow.setOutsideTouchable(true);
        popWindow.setTouchable(true);
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popWindow.setBackgroundDrawable(new ColorDrawable(0x11111111));    //要为popWindow设置一个背景才有效
        //设置popupWindow显示的位置
        popWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        return popWindow;
    }

    private void setSimulateClick(View view, float x, float y) {
        long downTime = SystemClock.uptimeMillis();
        final MotionEvent downEvent = MotionEvent.obtain(downTime, downTime,
                MotionEvent.ACTION_DOWN, x, y, 0);
        downTime += 1000;
        final MotionEvent upEvent = MotionEvent.obtain(downTime, downTime,
                MotionEvent.ACTION_UP, x, y, 0);
        view.onTouchEvent(downEvent);
        view.onTouchEvent(upEvent);
        downEvent.recycle();
        upEvent.recycle();
    }
}
