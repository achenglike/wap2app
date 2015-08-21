package com.example.like.schemeapp;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class SecondActivity extends ActionBarActivity {

    private boolean fromWap = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("wap")) {
            fromWap = intent.getBooleanExtra("wap", false);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.home) {
            finishActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void closeActivity(View view) {
        finishActivity();
    }

    private void finishActivity() {
        // 获得指向父级activity的intent，NavUtils在support v4 包中
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        // 判断是否需要重建任务栈,有时“NavUtils.shouldUpRecreateTask(this, upIntent)”
        // 判断返回为false，此时需要自己根据情景来判断是否需要重建栈（）
        Toast.makeText(this, "shouldUpRecreateTask:"+NavUtils.shouldUpRecreateTask(this, upIntent), Toast.LENGTH_SHORT).show();
        if (NavUtils.shouldUpRecreateTask(this, upIntent) || fromWap == true) {

            // 这个activity不是这个app任务的一部分, 所以当向上导航时创建
            // 用合成后退栈(synthesized back stack)创建一个新任务。
            TaskStackBuilder.create(this)
                    // 添加这个activity的所有父activity到后退栈中
                    .addNextIntentWithParentStack(upIntent)
                            // 向上导航到最近的一个父activity
                    .startActivities();
        } else {
            // 这个activity是这个app任务的一部分, 所以
            // 向上导航至逻辑父activity.
            NavUtils.navigateUpTo(this, upIntent);
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "onBackPressed", Toast.LENGTH_SHORT).show();
        finishActivity();
        super.onBackPressed();
    }
}
