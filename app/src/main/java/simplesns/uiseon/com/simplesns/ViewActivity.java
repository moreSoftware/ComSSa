package simplesns.uiseon.com.simplesns;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import simplesns.uiseon.com.simplesns.manager.AutoLayout;
import simplesns.uiseon.com.simplesns.manager.CircleTransform;
import simplesns.uiseon.com.simplesns.manager.Communicator;

public class ViewActivity extends Activity {
    private AutoLayout autoLayout = AutoLayout.GetInstance();
    private ArrayList<CommentInfo> commentInfoArrayList = new ArrayList<CommentInfo>();
    private CommentAdapter commentAdapter;
    private int storyId = 0;
    private int userId = 0;
    private EditText commentEditText;
    String id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Intent intent = getIntent();

        id = intent.getExtras().getString("id");

        storyId = intent.getIntExtra("storyId", -1);
        userId = intent.getIntExtra("userId", -1);
        String userName = intent.getStringExtra("userName");
        String userImg = intent.getStringExtra("userImg");
        String storyImg = intent.getStringExtra("storyImg");
        String writeDate = intent.getStringExtra("writeDate");
        String storyText = intent.getStringExtra("storyText");

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
                setComment();
            }
        });

        ListView listView = (ListView) findViewById(R.id.listView);

        LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headerView = vi.inflate(R.layout.list_header_view, null);


        TextView nameTextView = (TextView) headerView.findViewById(R.id.userName);
        nameTextView.setText(userName);

        TextView dateTextView = (TextView) headerView.findViewById(R.id.writeDate);
        dateTextView.setText(writeDate);

        TextView storyTextView = (TextView) headerView.findViewById(R.id.storyText);
        storyTextView.setText(storyText);

        ImageView storyImgView = (ImageView) headerView.findViewById(R.id.storyImg);





        autoLayout.setView(headerView);


        listView.addHeaderView(headerView);

        commentAdapter = new CommentAdapter(this, R.layout.list_item_view, commentInfoArrayList);
        listView.setAdapter(commentAdapter);

        getCommentsList();
    }

    private void getCommentsList() {
        Communicator.getHttp("http://xylophone.uiseon.co.kr/project/sns/sns.php?tr=105&story_id="+storyId, new Handler() {
            public void handleMessage(Message msg) {
                String jsonString = msg.getData().getString("jsonString");
                commentInfoArrayList.clear();

                try {
                    JSONObject dataObject = new JSONObject(jsonString);
                    JSONArray jsonStoryList = dataObject.getJSONArray("comment_list");

                    JSONObject tempObject;
                    for (int i = 0; i < jsonStoryList.length(); i++) {
                        tempObject = jsonStoryList.getJSONObject(i);
                        int story_id = tempObject.getInt("story_id");
                        int user_id = tempObject.getInt("user_id");
                        String userName = tempObject.getString("user_name");
                        String userImg = tempObject.getString("user_img");
                        String comment_text = tempObject.getString("comment_text");
                        String comment_date = tempObject.getString("comment_date");


                        CommentInfo commentInfo = new CommentInfo(story_id, user_id, userName, userImg, comment_text, comment_date);

                        commentInfoArrayList.add(commentInfo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                commentAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setComment() {
        //
        String commentText = commentEditText.getText().toString();
        if (commentText.equals("")) {
            return;
        }

        try {

            Log.d("test","http://xylophone.uiseon.co.kr/project/sns/sns.php?tr=104&story_id=" + storyId + "&user_id=" + userId + "&comment_text=" + URLEncoder.encode(commentText, "UTF-8") );
            Communicator.getHttp("http://xylophone.uiseon.co.kr/project/sns/sns.php?tr=104&story_id=" + storyId + "&user_id=" + userId + "&comment_text=" + URLEncoder.encode(commentText, "UTF-8"), new Handler() {
                public void handleMessage(Message msg) {
                    String jsonString = msg.getData().getString("jsonString");
                    Log.d("test", "jsonString " + jsonString);
                    commentEditText.setText("");
                    getCommentsList();
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private class CommentAdapter extends ArrayAdapter<CommentInfo> {

        private ArrayList<CommentInfo> items;

        public CommentAdapter(Context context, int textViewResourceId, ArrayList<CommentInfo> items) {
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


            CommentInfo commentInfo = items.get(position);

            if (commentInfo != null) {
                ImageView profileImg = (ImageView) v.findViewById(R.id.profile_img);
                if (commentInfo.getUserImg() != null && !commentInfo.getUserImg().equals("")) {
                    try {
                        Picasso.with(ViewActivity.this).load(commentInfo.getUserImg()).transform(new CircleTransform()).into(profileImg);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }

                TextView nameTextView = (TextView) v.findViewById(R.id.userName);
                nameTextView.setText(commentInfo.getUserName());

                TextView dateTextView = (TextView) v.findViewById(R.id.writeDate);
                dateTextView.setText(commentInfo.getCommentDate());

                TextView storyText = (TextView) v.findViewById(R.id.commentText);
                storyText.setText(commentInfo.getCommentText());


            }
            return v;
        }
    }

    class CommentInfo {
        private int commentId;
        private int userId;
        private String userName;
        private String userImg;
        private String commentText;
        private String commentDate;

        CommentInfo(int commentId, int userId, String userName, String userImg, String commentTex, String commentDate) {
            this.commentId = commentId;
            this.userId = userId;
            this.userName = userName;
            this.userImg = userImg;
            this.commentText = commentTex;
            this.commentDate = commentDate;
        }


        public int getCommentId() {
            return commentId;
        }

        public int getUserId() {
            return userId;
        }

        public String getUserName() {
            return userName;
        }

        public String getUserImg() {
            return userImg;
        }

        public String getCommentText() {
            return commentText;
        }

        public String getCommentDate() {
            return commentDate;
        }
    }

}
