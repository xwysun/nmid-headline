package cn.edu.cqupt.nmid.headline.support.repository.image;

import com.squareup.okhttp.RequestBody;

import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.SendCode;
import cn.edu.cqupt.nmid.headline.support.repository.image.bean.ImageCommnetReslut;
import cn.edu.cqupt.nmid.headline.support.repository.image.bean.ImageLikeResult;
import cn.edu.cqupt.nmid.headline.support.repository.image.bean.ImageStream;
import cn.edu.cqupt.nmid.headline.support.repository.image.bean.UploadResult;
import java.io.File;
import retrofit.Call;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by leon on 2/2/15.
 */
public interface ImageService {

  String IMAGE = "file";
  String NICKNAME = "nickName";
  String DEVICE_INFO = "deviceInfo";
  String AVATAR = "avatar";
  String LIMIT = "limit";

  //upload image file
    //坑出翔的retrofit2.0 Multipart   filename切记一定要按这个格式传
  @Multipart
  @POST("/TongxinHeadline/api/image/upload")
          Call<UploadResult>updateImage(
          @Part("file\"; filename=\"image.jpg") RequestBody photo,
          @Part(NICKNAME) RequestBody description,
          @Part(DEVICE_INFO) RequestBody deviceinfo
          , @Part(AVATAR) RequestBody avatar
  );

    @Multipart
    @POST
          ("/TongxinHeadline/api/image/upload") Observable<UploadResult> getupdateImage(
          @Part(IMAGE) RequestBody photo,
          @Part(NICKNAME) String description,
          @Part(DEVICE_INFO) String deviceinfo
//          , @Part(AVATAR) String avatar
  );


  //get image list
  @GET("/TongxinHeadline/api/image/freshimage") Call<ImageStream> getRefreshImage(@Query("id") int id, @Query("nickName") String nickName,@Query(LIMIT) int limit);

  //get image list
  @GET("/TongxinHeadline/api/image/freshimage") Call<ImageStream> getROldImage(@Query("id") int id,@Query("nickName") String nickName, @Query("limit") int limit);

//  //get image Comment list
//  @GET("/TongxinHeadline/api/android/getimagecomment") void getImageComments(@Query("id") int id,
//      Callback<ImageCommnetReslut> commnetReslutCallback);

  //Like a image
  //http://202.202.43.205:8086/api/android/imagelike
  @GET("/TongxinHeadline/api/image/likeImage") Call<ImageLikeResult> likeImage(@Query("id") int id,@Query("nickName") String nickName, @Query("command") int like);

//
//  @GET("/TongxinHeadline/api/android/imagecomment") void commentImage(@Query("id") int id,
//      @Query("nickname") String namename,@Query("avatar") String avatar, @Query("comment") String comment,@Query("command")int cmd,
//      Callback<ImageLikeResult> resultCallback);
}
