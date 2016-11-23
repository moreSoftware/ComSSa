package simplesns.uiseon.com.simplesns;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class JoinActivity extends Activity {

    Button btnDone ,btnCancel;
    EditText etUserNum,etType,etID,etPassword,etPasswordConfirm;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        etUserNum = (EditText)findViewById(R.id. etUserNum);
        etType = (EditText)findViewById(R.id.etType);
        etID = (EditText)findViewById(R.id.etID);
        etPassword= (EditText)findViewById(R.id.etPassword);
        etPasswordConfirm= (EditText)findViewById(R.id.etPasswordConfirm);


        btnDone= (Button) findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String userNumber =  etUserNum.getText().toString();
                String type =   etType.getText().toString();
                String id =  etID.getText().toString();
                String passwd  = etPassword.getText().toString();
                String passwdConfirm = etPasswordConfirm.getText().toString();
                String requestURL = "http://27.1.165.192:8082/99JSP_myEMP/userinsertdo2.jsp";
                InputStream is= requestGet(requestURL,userNumber,type,id,passwd,passwdConfirm);


                if(android.os.Build.VERSION.SDK_INT > 9) {

                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                        StrictMode.setThreadPolicy(policy);

                 }
    finish();
}
});//end onClick()
        btnCancel= (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });//end onClick()

    }

    public InputStream requestGet(String requestURL, String userNumber, String type, String id, String passwd, String passwdConfirm) {

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
            dataList.add(new BasicNameValuePair("userNumber", userNumber));
            dataList.add(new BasicNameValuePair("type", type));
            dataList.add(new BasicNameValuePair("id", id));
            dataList.add(new BasicNameValuePair("passwd", passwd));
            dataList.add(new BasicNameValuePair("passwdConfirm", passwdConfirm));
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
