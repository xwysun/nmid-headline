package cn.edu.cqupt.nmid.headline.ui.fragment.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.repository.headline.HeadlineService;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.MessageGson;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.SendCode;
import cn.edu.cqupt.nmid.headline.utils.slidedatetimepicker.SlideDateTimeListener;
import cn.edu.cqupt.nmid.headline.utils.slidedatetimepicker.SlideDateTimePicker;
import cn.edu.cqupt.nmid.headline.utils.thirdparty.RetrofitUtils;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by xwysun on 2015/11/14.
 */
public class CreateMessageFragment extends Fragment {


    @InjectView(R.id.scrollView)
    ScrollView scrollView;
    @InjectView(R.id.startImage)
    ImageView startImage;
    @InjectView(R.id.startTime)
    TextView startTime;
    @InjectView(R.id.start)
    RelativeLayout start;
    @InjectView(R.id.endImage)
    ImageView endImage;
    @InjectView(R.id.endTime)
    TextView endTime;
    @InjectView(R.id.end)
    RelativeLayout end;
    @InjectView(R.id.classNo)
    Spinner classNo;
    @InjectView(R.id.grade)
    Spinner grade;
    @InjectView(R.id.confirmButton)
    Button confirmButton;
    @InjectView(R.id.editTitle)
    EditText editTitle;
    @InjectView(R.id.editContent)
    EditText editContent;

    private String title;
    private String content;
    private ArrayList<String> gradeList = new ArrayList<String>();
    private ArrayList<String> classList = new ArrayList<String>();
    private ArrayList<String> gradeChoose = new ArrayList<String>();
    private ArrayList<String> classChoose = new ArrayList<String>();
    private String startTimeString=null;
    private String endTimeString=null;
    private String account;
    private String password;
    TimeZone tz = TimeZone.getDefault();
    Calendar cal = new GregorianCalendar(tz, Locale.CHINA);
    private SlideDateTimeListener startlistener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date)
        {
            cal.setTimeInMillis(date.getTime());
            startTimeString=cal.get(Calendar.YEAR)+"年"+(cal.get(Calendar.MONTH)+1)+"月"+
                    cal.get(Calendar.DAY_OF_MONTH)+"日"+cal.get(Calendar.HOUR_OF_DAY)
                    +":  "+cal.get(Calendar.MINUTE);
            startTime.setText(startTimeString);
        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel()
        {
            Toast.makeText(getActivity(),
                    "Canceled", Toast.LENGTH_SHORT).show();
        }
    };
    private SlideDateTimeListener endlistener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date)
        {
            cal.setTimeInMillis(date.getTime());
            endTimeString=cal.get(Calendar.YEAR)+"年"+(cal.get(Calendar.MONTH)+1)+"月"+
                    cal.get(Calendar.DAY_OF_MONTH)+"日"+cal.get(Calendar.HOUR_OF_DAY)
                    +":  "+cal.get(Calendar.MINUTE);
            endTime.setText(endTimeString);
        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel()
        {
            Toast.makeText(getActivity(),
                    "Canceled", Toast.LENGTH_SHORT).show();
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_creatmsg, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    public static CreateMessageFragment newInstance(Bundle bundle) {
        CreateMessageFragment fragment = new CreateMessageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * to do initview and addlistener
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    public void initView() {
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = editTitle.getText().toString().trim();
                content = editContent.getText().toString().trim();

                if(title.equals(null)){Toast.makeText(getActivity(),"请输入标题",Toast.LENGTH_SHORT).show();}
                else if (content.equals(null)){Toast.makeText(getActivity(),"请输入内容",Toast.LENGTH_SHORT).show();}
                else if(gradeChoose.isEmpty()){Toast.makeText(getActivity(),"请选择年级",Toast.LENGTH_SHORT).show();}
                else if (classChoose.isEmpty()){Toast.makeText(getActivity(),"请选择班级",Toast.LENGTH_SHORT).show();}
                else
                {
                    RetrofitUtils.getCachedAdapter(HeadlineService.END_POINT_TEST)
                            .create(HeadlineService.class)
                            .sendMsg(account,password,title,content,startTimeString,endTimeString,classChoose)
                            .enqueue(new Callback<SendCode>() {
                                @Override
                                public void onResponse(Response<SendCode> response) {
                                    if (response.body().getCode() == HeadlineService.STATUS_ERR){
                                        Toast.makeText(getActivity(),"发送失败",Toast.LENGTH_SHORT).show();
                                    }else if (response.body().getCode()==HeadlineService.STATUS_OK)
                                    {
                                        Toast.makeText(getActivity(),"发送成功",Toast.LENGTH_SHORT).show();
                                        getActivity().finish();
                                    }
                                }

                                @Override
                                public void onFailure(Throwable t) {

                                }
                            });
                }


            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SlideDateTimePicker.Builder(getFragmentManager())
                        .setListener(startlistener)
                        .setInitialDate(new Date())
                                //.setMinDate(minDate)
                                //.setMaxDate(maxDate)
                                .setIs24HourTime(true)
//                                .setTheme(SlideDateTimePicker.HOLO_DARK)
                                //.setIndicatorColor(Color.parseColor("#990000"))
                        .build()
                        .show();
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SlideDateTimePicker.Builder(getFragmentManager())
                        .setListener(endlistener)
                        .setInitialDate(new Date())
                                //.setMinDate(minDate)
                                //.setMaxDate(maxDate)
                                .setIs24HourTime(true)
                                //.setTheme(SlideDateTimePicker.HOLO_DARK)
                                //.setIndicatorColor(Color.parseColor("#990000"))
                        .build()
                        .show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
