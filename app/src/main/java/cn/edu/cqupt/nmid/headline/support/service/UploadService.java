package cn.edu.cqupt.nmid.headline.support.service;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import cn.edu.cqupt.nmid.headline.support.GlobalContext;
import cn.edu.cqupt.nmid.headline.support.repository.headline.HeadlineService;
import cn.edu.cqupt.nmid.headline.support.repository.image.ImageService;
import cn.edu.cqupt.nmid.headline.support.repository.image.bean.UploadResult;
import cn.edu.cqupt.nmid.headline.utils.ImageUtils;
import cn.edu.cqupt.nmid.headline.utils.LogUtils;
import cn.edu.cqupt.nmid.headline.utils.thirdparty.RetrofitUtils;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qzone.QZone;
import java.io.File;
import java.io.IOException;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UploadService extends Service {

  public static final String TAG = LogUtils.makeLogTag(UploadService.class);
  public static final String Key = "urikey";

  private Uri mImageUri;
  private Uri lowImage;

  public UploadService() {
  }

  @Override public IBinder onBind(Intent intent) {
    // TODO: Return the communication channel to the service.
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override public void onCreate() {
    super.onCreate();
    GlobalContext.getBus().register(this);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    GlobalContext.getBus().unregister(this);
    Log.d(TAG, "onDestroy");
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {

    mImageUri = intent.getParcelableExtra(Key);
    lowImage=resizeImg(mImageUri);
    //if (mImageUri == null && !ShareSDK.getPlatform(QZone.NAME).isValid()) {
    //  onDestroy();
    //  return -1;
    //}
    //
    //String nickname = ShareSDK.getPlatform(QZone.NAME).getDb().getUserName();
    //String avatar = ShareSDK.getPlatform(QZone.NAME).getDb().getUserIcon();

    //try {
    //  ImageUtils.createImageThumbnail(getApplicationContext(), mImageUri.getPath(),
    //      mImageUri.getPath(), 800, 100);
    //} catch (IOException e) {
    //  Toast.makeText(getApplicationContext(), "上传失败！", Toast.LENGTH_SHORT).show();
    //  return super.onStartCommand(intent, flags, startId);
    //}

    String nickname = ShareSDK.getPlatform(QZone.NAME).getDb().getUserName();
    String avatar = ShareSDK.getPlatform(QZone.NAME).getDb().getUserIcon();
//    Observable.just(mImageUri)
//        .filter(uri -> uri != null)
//        .map(this::resizeImg)
//        .flatMap(uri1 -> resultObservable(uri1, nickname, avatar))
//        .subscribeOn(Schedulers.newThread())
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribe(next -> showSuccessToast(), err -> showBadToast(), this::showSuccessToast);
    RetrofitUtils.getCachedAdapter(HeadlineService.END_POINT)
            .create(ImageService.class)
            .updateImage(RequestBody.create(MediaType.parse("image/*"), new File(lowImage.getPath()))
                    , RequestBody.create(MediaType.parse("text/plain"), nickname),
                    RequestBody.create(MediaType.parse("text/plain"), Build.MODEL),
                    RequestBody.create(MediaType.parse("text/plain"),avatar))
                            .enqueue(new Callback<UploadResult>() {

                              @Override
                              public void onResponse(Response<UploadResult> response, Retrofit retrofit) {
                                if (response != null && response.body().getCode() == 200) {
                                  showSuccessToast();
                                } else {
                                  showBadToast();
                                }
                              }

                              @Override
                              public void onFailure(Throwable t) {
                                Log.d("TAG", t.toString());
                                showBadToast();
                              }
                            });
    return super.onStartCommand(intent, flags, startId);
  }

  void showBadToast() {
    Log.e(TAG, "upload failed!");
    Toast.makeText(getApplicationContext(), "上传失败！", Toast.LENGTH_SHORT).show();
    this.stopSelf();
  }

  void showSuccessToast() {
    Log.d(TAG, "upload successfully!");
    Toast.makeText(getApplicationContext(), "上传成功", Toast.LENGTH_SHORT).show();
    this.stopSelf();
  }

  Uri resizeImg(Uri uri) {
    try {
      ImageUtils.createImageThumbnail(getApplicationContext(), mImageUri.getPath(),
          mImageUri.getPath(), 800, 100);
      return uri;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  Observable<UploadResult> resultObservable(Uri uri, String nickname, String avatar) {
    Log.d(TAG,uri.toString()+"nickname"+nickname+"avater"+avatar);
    return RetrofitUtils.getCachedAdapter(HeadlineService.END_POINT)
        .create(ImageService.class)
        .getupdateImage(RequestBody.create(MediaType.parse("image/png"),new File(uri.getPath())), nickname, Build.MODEL);
  }
}
