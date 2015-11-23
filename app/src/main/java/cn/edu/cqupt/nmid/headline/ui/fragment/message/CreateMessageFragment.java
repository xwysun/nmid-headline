package cn.edu.cqupt.nmid.headline.ui.fragment.message;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.repository.headline.HeadlineService;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.Class;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.GradeList;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.SendCode;
import cn.edu.cqupt.nmid.headline.utils.slidedatetimepicker.SlideDateTimeListener;
import cn.edu.cqupt.nmid.headline.utils.slidedatetimepicker.SlideDateTimePicker;
import cn.edu.cqupt.nmid.headline.utils.thirdparty.RetrofitUtils;
import retrofit.Callback;
import retrofit.Response;
import rx.internal.util.SubscriptionIndexedRingBuffer;

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
    @InjectView(R.id.confirmButton)
    Button confirmButton;
    @InjectView(R.id.editTitle)
    EditText editTitle;
    @InjectView(R.id.editContent)
    EditText editContent;
    @InjectView(R.id.classNo)
    Button classNo;
    @InjectView(R.id.grade)
    Spinner grade;


    private String title;
    private String content;
    private ArrayList<String> classList = new ArrayList<String>();
    private String gradeChoose=null;
    private List<String> classChoose = new ArrayList<String>();
    private ArrayList<String> chooseClasses;
    private boolean[] flags = {true, false, true, false};
    private ArrayList<String> gradeList = new ArrayList<String>();
    private ArrayList<Class> classesList = new ArrayList<Class>();
    private ArrayList<String> class0List = new ArrayList<String>();
    private ArrayList<String> class1List = new ArrayList<String>();
    private ArrayList<String> class2List = new ArrayList<String>();
    private ArrayList<String> class3List = new ArrayList<String>();
    private ArrayList<ArrayList<String>> classLists = new ArrayList<ArrayList<String>>();

    private String startTimeString = null;
    private String endTimeString = null;
    private String account;
    private String password;
    private Dialog mDialog;
    TimeZone tz = TimeZone.getDefault();
    Calendar cal = new GregorianCalendar(tz, Locale.CHINA);

    private Handler classhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setGradeClass();
        }
    };
    private SlideDateTimeListener startlistener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date) {
            cal.setTimeInMillis(date.getTime());
            startTimeString = cal.get(Calendar.YEAR) + "年" + (cal.get(Calendar.MONTH) + 1) + "月" +
                    cal.get(Calendar.DAY_OF_MONTH) + "日" + cal.get(Calendar.HOUR_OF_DAY)
                    + ":  " + cal.get(Calendar.MINUTE);
            startTime.setText(startTimeString);
        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel() {
            Toast.makeText(getActivity(),
                    "Canceled", Toast.LENGTH_SHORT).show();
        }
    };
    private SlideDateTimeListener endlistener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date) {
            cal.setTimeInMillis(date.getTime());
            endTimeString = cal.get(Calendar.YEAR) + "年" + (cal.get(Calendar.MONTH) + 1) + "月" +
                    cal.get(Calendar.DAY_OF_MONTH) + "日" + cal.get(Calendar.HOUR_OF_DAY)
                    + ":  " + cal.get(Calendar.MINUTE);
            endTime.setText(endTimeString);
        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel() {
            Toast.makeText(getActivity(),
                    "Canceled", Toast.LENGTH_SHORT).show();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_creatmsg, container, false);
        ButterKnife.inject(this, view);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        getGradeClass();
        password=sharedPreferences.getString("password","NONE");
        account=sharedPreferences.getString("account","NONE");
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
    public void initDate(Date date){
        cal.setTimeInMillis(date.getTime());
        startTimeString = cal.get(Calendar.YEAR) + "年" + (cal.get(Calendar.MONTH) + 1) + "月" +
                cal.get(Calendar.DAY_OF_MONTH) + "日" + cal.get(Calendar.HOUR_OF_DAY)
                + ":  " + cal.get(Calendar.MINUTE);
        endTimeString=startTimeString;
        startTime.setText(startTimeString);
        endTime.setText(endTimeString);
    }

    public void initView() {
        Date date =new Date();
        initDate(date);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = editTitle.getText().toString().trim();
                content = editContent.getText().toString().trim();

                if (title.equals(null)) {
                    Toast.makeText(getActivity(), "请输入标题", Toast.LENGTH_SHORT).show();
                } else if (content.equals(null)) {
                    Toast.makeText(getActivity(), "请输入内容", Toast.LENGTH_SHORT).show();
                } else if (gradeChoose==null) {
                    Toast.makeText(getActivity(), "请选择年级", Toast.LENGTH_SHORT).show();
                } else if (classChoose.isEmpty()) {
                    Toast.makeText(getActivity(), "请选择班级", Toast.LENGTH_SHORT).show();
                } else {
                    Gson gson = new Gson();
                    RetrofitUtils.getCachedAdapter(HeadlineService.END_POINT_TEST)
                            .create(HeadlineService.class)
                            .sendMsg(account, password, title, content, startTimeString, endTimeString,
                                   gson.toJson(classChoose) )
                            .enqueue(new Callback<SendCode>() {
                                @Override
                                public void onResponse(Response<SendCode> response) {
                                    if (response.body().getCode() == HeadlineService.STATUS_ERR) {
                                        Toast.makeText(getActivity(), "发送失败", Toast.LENGTH_SHORT).show();
                                    } else if (response.body().getCode() == HeadlineService.STATUS_OK) {
                                        Toast.makeText(getActivity(), "发送成功", Toast.LENGTH_SHORT).show();
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

    /**
     * to get grade and class LIST
     * use Retrofit 2.0
     */
    public void getGradeClass() {
        RetrofitUtils.getCachedAdapter(HeadlineService.END_POINT_TEST)
                .create(HeadlineService.class)
                .getGradesList().enqueue(new Callback<GradeList>() {
            @Override
            public void onResponse(Response<GradeList> response) {
                //怪得不行的JSON解析
                gradeList = (ArrayList<String>) response.body().getGrades();
                classesList = (ArrayList<Class>) response.body().getClasses();
                class0List = (ArrayList<String>) classesList.get(0).getGrade0();
                class1List = (ArrayList<String>) classesList.get(1).getGrade1();
                class2List = (ArrayList<String>) classesList.get(2).getGrade2();
                class3List = (ArrayList<String>) classesList.get(3).getGrade3();
                Log.d("class1list", "" + class0List.toString());
                classLists.add(class0List);
                Log.d("class1list", "" + class1List.toString());
                classLists.add(class1List);
                Log.d("class1list", "" + class2List.toString());
                classLists.add(class2List);
                Log.d("class1list", "" + class3List.toString());
                classLists.add(class3List);
                Log.d("class0list0", gradeList.get(0));
                classhandler.sendEmptyMessage(0);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getActivity(), "请检查网络连接或稍后重试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setGradeClass() {

        ArrayAdapter<String> gradeAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,
                gradeList);
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        grade.setAdapter(gradeAdapter);
        grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gradeChoose = gradeList.get(position);
                chooseClasses = classLists.get(position);
                mDialog=setDialog();
                classNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.show();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public Dialog setDialog()
    {
        AlertDialog.Builder builder =new AlertDialog.Builder(getContext());
        String[] classes=new String[chooseClasses.size()];
        boolean[] isCheck=new boolean[chooseClasses.size()];
        for (int i=0;i<chooseClasses.size();i++)
        {
            classes[i]=chooseClasses.get(i);
        }
        builder.setTitle("请选择班级")
                .setMultiChoiceItems(classes, isCheck, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        isCheck[which] = isChecked;
                    }
                });
        builder.setPositiveButton(" 确 定 ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                for (int i=0;i<chooseClasses.size();i++)
                {
                    if (isCheck[i])
                    {
                        classChoose.add(chooseClasses.get(i));
                    }

                }
                classNo.setText("共"+classChoose.size()+"个班");
            }
        });
        Dialog dialog=builder.create();
        return dialog;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
