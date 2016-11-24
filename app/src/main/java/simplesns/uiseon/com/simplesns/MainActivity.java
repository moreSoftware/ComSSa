package simplesns.uiseon.com.simplesns;

/**
 * Created by user on 2016-11-19.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    EditText et_id;
    EditText et_pw;
    TextView tv_msg;

    private static String id = "";
    private static String pw = "";
    private static String msgString = null;
    private static boolean isConnected = false;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        et_id = (EditText) findViewById(R.id.edit_id);
        et_pw = (EditText) findViewById(R.id.edit_pw);



        Button btn_join = (Button) findViewById(R.id.btn_join);
        btn_join.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });

        final Button btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String url = "http://27.1.165.192:8082/99JSP_myEMP/userlogin2.jsp";
                id = et_id.getText().toString();
                pw = et_pw.getText().toString();


                if ((id.length() == 0) || (pw.length() == 0)) {
                    Toast.makeText(MainActivity.this, "다시입력하세요", Toast.LENGTH_LONG).show();
                    Intent intent_retry = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent_retry);
                    finish();
                } else {
                    new Handler().post(new Runnable() {

                        public void run() {
                            String url = "http://27.1.165.192:8082/99JSP_myEMP/userlogin2.jsp";
//                                                              String url = "http://125.179.204.2:8080/AndroidServer/login.jsp?id=mygirl2&pwd=1111";


                            new MainActivity().connect(url);
                            //이게 중요함
                            while (true) {
                                if (isConnected) {

                                    Log.v("xxx", msgString);
                                    if (msgString.equals(id + "님 로그인되었습니다. 환영합니다.")) {
                                        Toast.makeText(MainActivity.this, msgString, Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), MainView.class);
                                        intent.putExtra("id", id);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(MainActivity.this, "다시입력하세요", Toast.LENGTH_LONG).show();

                                    }
                                    break;
                                }
                            }
                        }
                    });
                }
            }
        });


    }




    private void setText(String s) {
        tv_msg.setText(s);
    }

    private void connect(String url){
        new WebConnection().execute(url);
    }
    private class WebConnection extends AsyncTask<String, Void, HttpResponse> {

        @Override
        protected HttpResponse doInBackground(String... urls) {
            HttpClient client = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("id", id));
            list.add(new BasicNameValuePair("passwd", pw));

            Log.i("id", id);
            Log.i("passwd", pw);

            String url = urls[0] + "?" + URLEncodedUtils.format(list, "UTF-8");
//                              String url = "http://125.179.204.2:8080/AndroidServer/login.jsp?id=mygirl2&pwd=1111";

            HttpGet httpGet = new HttpGet(url);

            HttpResponse response = null;
            String s ="";
            StringBuffer sb = null;
            try {
                response = client.execute(httpGet);
                HttpEntity entity = response.getEntity();
                InputStream is;
                BufferedReader br = null;
                if(entity != null) {
                    is = entity.getContent();
                    br =  new BufferedReader(new InputStreamReader(is));
                    s = "";
                    sb = new StringBuffer();
                }
                while((s = br.readLine()) != null) {
                    sb.append(s);
                }
                Log.i("sb : ", sb.toString());
                msgString = sb.toString().trim().toString();
                Log.i("msgString : ", msgString);
                isConnected = true;
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }
    }


}