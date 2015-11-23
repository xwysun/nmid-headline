package cn.edu.cqupt.nmid.headline.support.repository.headline;

import android.renderscript.ScriptIntrinsicYuvToRGB;

import java.util.ArrayList;

import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.FreshNewList;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.FreshOldList;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.GradeList;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.HeadJson;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.IsLogin;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.MessageGson;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.SendCode;
import retrofit.Call;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;

/**
 * Created by leon on 12/27/14.
 */
public interface HeadlineService {

  String END_POINT = "http://115.29.139.53:8080";
    String END_POINT_TEST="http://121.42.207.100:8080";

  //1	int	学院新闻
  //2	int	科研动态
  //3	int	青春通信
  //4	int	校友新闻
  //required
//  int CATE_SCHOOL = 1;
//  int CATE_TECHNOLOGY = 2;
//  int CATE_TELECOMMUNICATION = 3;
  int CATE_ALUMNUS = 4;

  int STATUS_OK = 200;
  int STATUS_ERR = 500;

  String CATEGORY = "category";
  String TYPE="type";
  String TYPE_JINGWEI="a";
  String TYPE_NEWS="b";

  //关键字	类型	含义	说明
  //自然数	int	设置刷新新闻起始id
  //返回大于该id的新闻，按id升序排列"
  //返回小于该id的新闻，按id逆序排列
  
  
  String ID = "id";

  //若需更新的新闻条数大于请求数，返回最新的limi1t条新闻，若需更新的新闻条数小于请求数，只返回需更新的新闻。
  String LIMIT = "limit";

  @GET("/TongxinHeadline/api/news/fresh") Call<FreshNewList> getFreshFeeds(
      @Query(ID) int id, @Query(LIMIT) int limit,@Query(TYPE) String type);
//
//  @GET("/api/news/freshOldNews") Call<FreshOldList> getOldFeeds(
//          @Query(ID) int id, @Query(LIMIT) int limit,@Query(TYPE) String type);

  @GET("/api/android/newscontent") Call<HeadJson> getNewsContent(@Query(CATEGORY) int category,
      @Query(ID) int id, Callback<Void> callback);

//  @GET("/api/news/fresh") Call<NewsList> upFreshNews(@Query(ID) int id,
//                                                                 @Query(LIMIT) int limit);
//
//  @POST("/api/message/receiveId")
//      Call<MessageNum> IsNewMessage(@Body String classNumber);
@GET("/TongxinHeadline/api/grade/getMsg") Call<GradeList> getGradesList();

   @FormUrlEncoded
   @POST("/TongxinHeadline/api/message/receiveMessage")
   Call<MessageGson> receiveMessage(@Field("offSet") int offSet, @Field("classNumber") String classNumber);

   @FormUrlEncoded
   @POST("/TongxinHeadline/api/message/add")
   Call<SendCode> sendMsg(@Field("account") String account, @Field("passwd") String passwd,
                            @Field("title") String title, @Field("content") String content,
                            @Field("startTime") String starTtime,@Field("endTime") String endTime,
                            @Field("classNumber") String classNumber);

    @FormUrlEncoded
    @POST("/TongxinHeadline/api/user/login")
    Call<IsLogin> CheckAccount(@Field("account") String account, @Field("passwd") String passwd);
    @FormUrlEncoded
    @POST("/TongxinHeadline/api/message/browseMessage")
    Call<MessageGson> receiveTeacherMessage(@Field("offSet") int offSet, @Field("account") String account,@Field("passwd") String passwd);
}
