package simplesns.uiseon.com.simplesns;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import simplesns.uiseon.com.simplesns.manager.AutoLayout;

public class WriteActivity extends Activity {

    private AutoLayout autoLayout = AutoLayout.GetInstance();

    //private EditText etStoryTitle;
    private EditText etSubstance;
    String id = null;
    String check = "0" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        Intent intent =getIntent();
        id = intent.getExtras().getString("id");

        View view = (View) findViewById(android.R.id.content);
        autoLayout.setView(view);
        Button button = (Button) findViewById(R.id.back_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(android.os.Build.VERSION.SDK_INT > 9) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
                sendStory();
                Intent intent_write = new Intent(getApplicationContext(), MainView.class);
                intent_write.putExtra("id",id);
                startActivity(intent_write);
                finish();
            }
        });
        // etStoryTitle = (EditText) findViewById(R.id.storyEditTitle);
        //1 공지사항  0이 기본
        //체크박스
        etSubstance = (EditText) findViewById(R.id.storyEditText);

        Button sendBtn = (Button) findViewById(R.id.send_btn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(android.os.Build.VERSION.SDK_INT > 9) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
                sendStory();
                Intent intent_write = new Intent(getApplicationContext(), MainView.class);
                intent_write.putExtra("id",id);
                startActivity(intent_write);
                finish();

            }
        });

        CheckBox checkbox = (CheckBox) findViewById(R.id.checkbox2);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (buttonView.getId() == R.id.checkbox2) {
                    if (isChecked) {
                        check = "1";
                    } else {
                        check ="0";
                    }
                }
            }
        });

    }
    private void sendStory() {
        //String storyTitle = etStoryTitle.getText().toString();
        String substance = etSubstance.getText().toString();
        String requestURL = "http://27.1.165.192:8082/99JSP_myEMP/userwrite.jsp";
        InputStream is = requestGet(requestURL,id,substance,check);

        if (substance.equals("")) {
            Toast.makeText(this, "내용을 입력하세요", Toast.LENGTH_SHORT).show();
            return;
        }
//        else if (체크박스.equals("")) { 체크박스확인 안씀
//            Toast.makeText(this, "공지사항 여부를 체크해주세요", Toast.LENGTH_SHORT).show();
//            return;
//        }

    }
    public InputStream requestGet(String requestURL, String id,String substance, String check) {

        Log.i("xxx", "requestGet start");
        try {
            if(android.os.Build.VERSION.SDK_INT > 9) {

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                StrictMode.setThreadPolicy(policy);
            }
            //1.
            HttpClient client = new DefaultHttpClient();
            //폼데이터 저장
            List<NameValuePair> dataList = new ArrayList<NameValuePair>();
            dataList.add(new BasicNameValuePair("id", id));
            dataList.add(new BasicNameValuePair("substance", substance));
            dataList.add(new BasicNameValuePair("check", check));
            requestURL = requestURL + "?" + URLEncodedUtils.format(dataList, "UTF-8");
            HttpGet get = new HttpGet(requestURL);

            //2. 요청
            HttpResponse response = client.execute(get);

            //3. 결과 받기
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            return is;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }//end requestGet()
}