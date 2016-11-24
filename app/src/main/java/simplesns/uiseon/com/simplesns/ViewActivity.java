package simplesns.uiseon.com.simplesns;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import simplesns.uiseon.com.simplesns.manager.AutoLayout;
import simplesns.uiseon.com.simplesns.manager.CommentDTO;

public class ViewActivity extends Activity {
    private AutoLayout autoLayout = AutoLayout.GetInstance();
    private ArrayList<CommentDTO> commentInfoArrayList = new ArrayList<CommentDTO>();
    private CommentAdapter commentAdapter;

    private EditText commentEditText;
    String id = null;
    String write_user_id = null;
    int board_number;
    String name ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Intent intent = getIntent();

        id = intent.getStringExtra("id");
        write_user_id = intent.getStringExtra("write_user_id");
        board_number = intent.getExtras().getInt("board_number");
        String notice_check = intent.getStringExtra("notice_check");
        String storyText = intent.getStringExtra("storyText");
        name = intent.getStringExtra("name");
        String date = intent.getStringExtra("writeDate");


        View view = (View) findViewById(android.R.id.content);
        autoLayout.setView(view);

        commentEditText = (EditText) findViewById(R.id.commentEditText);

        Button button = (Button) findViewById(R.id.back_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(android.os.Build.VERSION.SDK_INT > 9) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
                Intent intent_main = new Intent(getApplicationContext(), MainView.class);
                intent_main.putExtra("id",id);
                startActivity(intent_main);
                finish();
            }
        });


        Button sendBtn = (Button) findViewById(R.id.send_btn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(android.os.Build.VERSION.SDK_INT > 9) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
                sendComment();
                commentEditText.setText("");
                requestGet(board_number);
//                Intent intent_comment = new Intent(getApplicationContext(), ViewActivity.class);
//                intent_comment.putExtra("id",id);
//                startActivity(intent_write);
//                finish();

                //내용 입력하고 전송하기

            }
        });

        ListView listView = (ListView) findViewById(R.id.listView);

        LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headerView = vi.inflate(R.layout.list_header_view, null);








        TextView board_numberTextView = (TextView) headerView.findViewById(R.id.board_numberText);
        board_numberTextView.setText(String.valueOf(board_number));

        TextView nameTextView = (TextView) headerView.findViewById(R.id.name);
        nameTextView.setText(name);
        TextView substanceTextView = (TextView) headerView.findViewById(R.id.substanceText);
        substanceTextView.setText(storyText);

        TextView write_user_idText = (TextView) headerView.findViewById(R.id.write_user_idText);
        write_user_idText.setText(write_user_id);

        TextView notice_checkText = (TextView) headerView.findViewById(R.id.notice_check);
        if(notice_check.equals("0"))
            notice_checkText.setText(" ");


        TextView datetimeText = (TextView) headerView.findViewById(R.id.datetimeText);
        datetimeText.setText("date");




        autoLayout.setView(headerView);


        listView.addHeaderView(headerView);

        commentAdapter = new CommentAdapter(this, R.layout.list_item_view, commentInfoArrayList);
        listView.setAdapter(commentAdapter);

        requestGet(board_number);
    }
    public void requestGet(int board_number) {
        String requestURL = "http://27.1.165.192:8082/99JSP_myEMP/usercommentselect.jsp"+"?board_number="+board_number;
        Log.i("xxx", "requestGet start");
        try {
            //1.
            HttpClient client = new DefaultHttpClient();

            HttpGet get = new HttpGet(requestURL);
            //2. 요청
            HttpResponse response = client.execute(get);
            //3. 결과 받기
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            getXML(is);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }//end requestGet()
    public void getXML(InputStream is) {

        Log.i("xxx", "getXML start!");
        try {
            commentInfoArrayList.clear();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(is, "UTF-8");
            int eventType = parser.getEventType();
            CommentDTO dto = null;
            while( eventType != XmlPullParser.END_DOCUMENT) {
                switch(eventType) {
                    case XmlPullParser.START_TAG:
                        String startTag = parser.getName();
                        Log.i("xxxx", startTag);
                        if(startTag.equals("record")){ dto = new CommentDTO(); }
                        if(dto !=null ) {
                            if(startTag.equals("comment_number")){ dto.setComment_number(Integer.parseInt(parser.nextText()));}
                            if(startTag.equals("board_number")){ dto.setBoard_number(Integer.parseInt(parser.nextText())); }
                            if(startTag.equals("substance")){ dto.setSubstance(parser.nextText()); }
                            if(startTag.equals("write_user_id")){ dto.setWrite_user_id(parser.nextText()); }
                            if(startTag.equals("name")){ dto.setName(parser.nextText()); }
                            if(startTag.equals("date")){ dto.setDatetime(parser.nextText()); }
                        } else { Log.i("xxx", "dto = null"); }
                        break;
                    case XmlPullParser.END_TAG:
                        String endTag = parser.getName();
                        if(endTag.equals("record")){
                            commentInfoArrayList.add(dto);
                        }
                }//end switch
                eventType = parser.next();
            }//end while
            for( CommentDTO xx : commentInfoArrayList){
                Log.i("xxx",xx.getBoard_number()+" "+xx.getSubstance()+" "+xx.getWrite_user_id()+ " "+ xx.getDatetime()+" "+xx.getName()+" ");
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException ie) {
            // TODO Auto-generated catch block
            ie.printStackTrace();
        }

        commentAdapter.notifyDataSetChanged();
    }
    public void sendComment(){
        String commentText = commentEditText.getText().toString();
        String requestURL = "http://27.1.165.192:8082/99JSP_myEMP/usercommentinsertdo2.jsp";
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
            dataList.add(new BasicNameValuePair("board_number", String.valueOf(board_number)));
            dataList.add(new BasicNameValuePair("substance", commentText));
            dataList.add(new BasicNameValuePair("write_user_id", write_user_id));
            dataList.add(new BasicNameValuePair("name",name));

            requestURL = requestURL + "?" + URLEncodedUtils.format(dataList, "UTF-8");
            HttpGet get = new HttpGet(requestURL);

            //2. 요청
            HttpResponse response = client.execute(get);

            //3. 결과 받기
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();


        } catch (Exception e) {
            e.printStackTrace();

        }

        if (commentText.equals("")) {
            Toast.makeText(this, "내용을 입력하세요", Toast.LENGTH_SHORT).show();
            return;
        }

    }
    //수정완료
    private class CommentAdapter extends ArrayAdapter<CommentDTO> {

        private ArrayList<CommentDTO> items;

        public CommentAdapter(Context context, int textViewResourceId, ArrayList<CommentDTO> items) {
            super(context, textViewResourceId, items);
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.list_item_view, null);
                autoLayout.setView(v);
            }


            CommentDTO commentInfo = items.get(position);

            if (commentInfo != null) {
                //댓글 다는 id
                TextView idTextView = (TextView) v.findViewById(R.id.commentId);
                idTextView.setText(id);
                //댓글 다는 이름
                TextView nameTextView = (TextView) v.findViewById(R.id.commentName);
                nameTextView.setText(commentInfo.getName());
                //댓글 다는 날짜
                TextView dateTextView = (TextView) v.findViewById(R.id.commentDate);
                dateTextView.setText(commentInfo.getDatetime());
                //댓글 내용
                TextView storyText = (TextView) v.findViewById(R.id.commentText);
                storyText.setText(commentInfo.getSubstance());

            }
            return v;
        }
    }



}
