package com.example.mrdreamer.iknow;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.mrdreamer.iknow.AccountManage.Account;
import com.example.mrdreamer.iknow.GetQuestion.GetQuestionPresenter;
import com.example.mrdreamer.iknow.GetQuestion.ShowQuesitonFragment;
import com.example.mrdreamer.iknow.Social.Friends.GetFriends;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , ActivityCompat.OnRequestPermissionsResultCallback{


private  Question question;

    private static final int  PERMISSIONS_REQUEST_READ_PHONE_STATE=1;
    private boolean isLogin=false;
    //private QuestionFragment questionFragment;
    private ShowQuesitonFragment showQuesitonFragment;
    public boolean isLogin(){return isLogin;}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        showQuesitonFragment=new ShowQuesitonFragment();
        new GetQuestionPresenter(showQuesitonFragment);

        Utils.addFragmentToActivity(this,showQuesitonFragment,R.id.question_frame);


      //  int permissionCheck= ContextCompat.checkSelfPermission(IKnowApplication.getAppContext(),
       //         Manifest.permission.READ_PHONE_STATE);
        //ActivityCompat.requestPermissions(this,Utils.Constants.PERMISSIONS,PERMISSIONS_REQUEST_READ_PHONE_STATE);
                //new String[]{Manifest.permission.READ_PHONE_STATE},
                //PERMISSIONS_REQUEST_READ_PHONE_STATE);





    }


    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[],int[] grantResults){
        switch(requestCode){
            case PERMISSIONS_REQUEST_READ_PHONE_STATE:{
                if(grantResults.length>0){}
            }
            break;
            default:break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch(id)
        {
            case R.id.nav_history:{}
            case R.id.nav_math:{}
            case R.id.nav_astronomy:{}

            case R.id.nav_account:{
                Intent intent=new Intent(this,Account.class);
                startActivity(intent);
            }
            break;
            case R.id.nav_friend_list:{
                Intent intent=new Intent(this, GetFriends.class);
                startActivity(intent);
            }
            break;
          //  case R.id.nav_logout:{}
            case R.id.nav_share:{}
            case R.id.nav_send:{}
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void login(){
        Intent login_intent=new Intent(getApplicationContext(),Account.class);
        startActivity(login_intent);
    }


   // public static class QuestionFragment extends Fragment{
   //   private Question question_frag;

   //     public RadioGroup getAnsGroup(){

   //         return (RadioGroup)(getView().findViewById(R.id.group));
   //     }
   //     public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
   //     {
   //         View view=inflater.inflate(R.layout.question,container,false);

   //         return view;
   //     }


   //     private void setQuestion(Question question,View view){

   //           TextView question_text=(TextView)view.findViewById(R.id.question_textview);
   //         question_text.setText(question_frag.getQuestion());
   //          Log.i("log",question_frag.getQuestion());

   //    RadioButton ans_a=(RadioButton)(view.findViewById(R.id.a_radio));
   //         ans_a.setText(question_frag.getAns_a());

   //       RadioButton ans_b=(RadioButton)(view.findViewById(R.id.b_radio));
   //         ans_b.setText(question_frag.getAns_b());

   //     RadioButton ans_c=(RadioButton)(view.findViewById(R.id.c_radio));
   //         ans_c.setText(question_frag.getAns_c());
   //         RadioButton ans_d=(RadioButton)(view.findViewById(R.id.d_radio));


           // ans_d.setText(question_frag.getAns_d());
       // }
   // }
}
