package cn.edu.cqupt.nmid.headline.ui.fragment.message;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.logging.LogRecord;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.repository.headline.HeadlineService;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.*;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.Class;
import cn.edu.cqupt.nmid.headline.ui.activity.MessageListActivity;
import cn.edu.cqupt.nmid.headline.utils.thirdparty.RetrofitUtils;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by xwysun on 2015/11/17.
 */
public class UserInfoFragment extends Fragment {

    @InjectView(R.id.studentCheck)
    ImageView studentCheck;
    @InjectView(R.id.student)
    LinearLayout student;
    @InjectView(R.id.spinner_grade)
    Spinner spinnerGrade;
    @InjectView(R.id.gradebackground)
    LinearLayout gradebackground;
    @InjectView(R.id.spinner_class)
    Spinner spinnerClass;
    @InjectView(R.id.classbackground)
    LinearLayout classbackground;
    @InjectView(R.id.studentsetting)
    LinearLayout studentsetting;
    @InjectView(R.id.teacherCheck)
    ImageView teacherCheck;
    @InjectView(R.id.teacher)
    LinearLayout teacher;
    @InjectView(R.id.accoutbackground)
    LinearLayout accoutbackground;
    @InjectView(R.id.passwordbackground)
    LinearLayout passwordbackground;
    @InjectView(R.id.teachersetting)
    LinearLayout teachersetting;

    public  String MODE = "student";
    @InjectView(R.id.accouttext)
    EditText accouttext;
    @InjectView(R.id.passwordtext)
    EditText passwordtext;
    @InjectView(R.id.card_view_grade)
    CardView cardViewGrade;
    @InjectView(R.id.card_view_class)
    CardView cardViewClass;
    @InjectView(R.id.confirm)
    Button confirm;
    private String password;
    private String account;
    private ArrayList<String> gradeList = new ArrayList<String>();
    private ArrayList<Class> classesList=new ArrayList<Class>();
    private Class classes;
    private ArrayList<String> class0List = new ArrayList<String>();
    private ArrayList<String> class1List = new ArrayList<String>();
    private ArrayList<String> class2List = new ArrayList<String>();
    private ArrayList<String> class3List = new ArrayList<String>();
    private String confirmGrade;
    private String confirmClass;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Handler classhandler=new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userinfo, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences=getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        initView();
    }

    public static UserInfoFragment newInstance(Bundle bundle) {
        UserInfoFragment fragment = new UserInfoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * to do initView and SET Listener
     */
    public void initView() {
        getGradeClass();
        changeCardView(MODE);
        ArrayAdapter<String> gradeAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,
                gradeList);
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGrade.setAdapter(gradeAdapter);
        spinnerGrade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                confirmGrade = gradeList.get(position);
                Log.d("gradeNo", confirmGrade);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MODE = "student";
                changeCardView(MODE);
            }
        });
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MODE = "teacher";
                changeCardView(MODE);
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MODE == "student") {
                    if (confirmGrade != null && confirmClass != null) {
                        editor.putString("classNo", confirmClass);
                        Toast.makeText(getActivity(), "设置成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), MessageListActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "请先选择班级", Toast.LENGTH_SHORT).show();
                    }
                } else if (MODE == "teacher") {
                    account=accouttext.getText().toString().trim();
                    password=passwordtext.getText().toString().trim();
                    if (password != null && account != null) {
                        if (checkAccount(account, password)) {
                            editor.putString("account", account);
                            editor.putString("password", password);
                            Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), MessageListActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getActivity(), "账号或密码错误", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "请输入账号密码", Toast.LENGTH_SHORT).show();
                    }
                }
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
                gradeList=(ArrayList<String>)response.body().getGrades();
                classesList=(ArrayList<Class>)response.body().getClasses();
                classes=classesList.get(0);
                class0List=(ArrayList<String>)classes.getGrade0();
                class1List=(ArrayList<String>)classes.getGrade1();
                class2List=(ArrayList<String>)classes.getGrade2();
                class3List=(ArrayList<String>)classes.getGrade3();
                Log.d("class0list0",gradeList.get(0));
            }

            @Override
            public void onFailure(Throwable t) {
               Toast.makeText(getActivity(),"请检查网络连接或稍后重试",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * to check teacher account
     * @param account
     * @param password
     * @return
     */
    public  boolean checkAccount(String account,String password)
    {
        final boolean[] IsChecked = new boolean[1];
        RetrofitUtils.getCachedAdapter(HeadlineService.END_POINT_TEST)
                .create(HeadlineService.class)
                .CheckAccount(account,password).enqueue(new Callback<IsLogin>() {
            @Override
            public void onResponse(Response<IsLogin> response) {
                IsChecked[0] =response.body().getLogin();
            }

            @Override
            public void onFailure(Throwable t) {
                IsChecked[0]=false;
            }
        });
        return IsChecked[0];
    }
    public void changeCardView(String mode)
    {
        if (mode == "student") {
            studentCheck.setBackgroundResource(R.drawable.checkbox_checked);
            teacherCheck.setBackgroundResource(R.drawable.checkbox_uncheck);
            studentsetting.setEnabled(true);
            spinnerGrade.setEnabled(true);
            spinnerClass.setEnabled(true);
            teachersetting.setEnabled(false);
            accouttext.setEnabled(false);
            passwordtext.setEnabled(false);
        } else if (mode=="teacher"){
            studentCheck.setBackgroundResource(R.drawable.checkbox_uncheck);
            teacherCheck.setBackgroundResource(R.drawable.checkbox_checked);
            studentsetting.setEnabled(false);
            spinnerGrade.setEnabled(false);
            spinnerClass.setEnabled(false);
            teachersetting.setEnabled(true);
            accouttext.setEnabled(true);
            passwordtext.setEnabled(true);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
