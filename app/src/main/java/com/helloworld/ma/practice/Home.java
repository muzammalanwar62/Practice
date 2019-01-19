package com.helloworld.ma.practice;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<catogery> catogeries;
    RecyclerView catogery_list;
    ImageView navimage;
    TextView navname,navemail;
    SharedPreferences sharedPreferences;
    IntentResult result;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(Home.this);
         catogeries=new ArrayList<>();
        /* catogery a=new catogery("Drinks","https://malibubeautyjm.files.wordpress.com/2012/03/soaps_250x250.jpg");
         catogery b=new catogery("Soaps","https://malibubeautyjm.files.wordpress.com/2012/03/soaps_250x250.jpg");
         catogeries.add(a);
        catogeries.add(b);*/

        catogery_adapter adapter= new catogery_adapter(catogeries,Home.this);
        catogery_list=findViewById(R.id.catogery_list);
        catogery_list.setLayoutManager(new LinearLayoutManager(this));
        new Async_get_categories(catogery_list,Home.this).execute();
        catogery_list.setAdapter(adapter);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View v=LayoutInflater.from(Home.this).inflate(R.layout.nav_header_home,null);
        navigationView.addHeaderView(v);
        navname=v.findViewById(R.id.name_nav);
        navemail=v.findViewById(R.id.email_nav);
        navimage=v.findViewById(R.id.imageView);
        Log.e("userinfo",sharedPreferences.getString("userinfo",""));
        List<UsersDB> user_info=new Gson().fromJson(sharedPreferences.getString("userinfo",""),new TypeToken< List<UsersDB>>(){}.getType());
        if(user_info!=null) {
            navname.setText(user_info.get(0).name);
            navemail.setText(user_info.get(0).email);
            if(user_info.get(0).user_role.equals("retailer")){
             navigationView.inflateMenu(R.menu.menu_retailer);
            }else if(user_info.get(0).user_role.equals("Admin")){
                navigationView.inflateMenu(R.menu.activity_home_drawer);
            }
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
        getMenuInflater().inflate(R.menu.home, menu);
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

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.add_product){
            Intent intent=new Intent(Home.this,Add_product.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.signout_drw){
            sharedPreferences.edit().remove("userinfo").apply();
            Intent intent=new Intent(Home.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        else  if(item.getItemId()==R.id.add_categories){
            Intent intent=new Intent(Home.this,add_category_page.class);
            startActivity(intent);
        }
        else  if(item.getItemId()==R.id.order_list){
            Intent intent=new Intent(Home.this,items_list_page.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.barcode_scaner){
            i=0;
            new IntentIntegrator(Home.this).setCaptureActivity(ScannerActivity.class).initiateScan();

        }else if(item.getItemId()==R.id.billing){
            i=1;
            new IntentIntegrator(Home.this).setCaptureActivity(ScannerActivity.class).initiateScan();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //We will get scan results here
         result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        //check for null
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Scan Cancelled", Toast.LENGTH_LONG).show();
            } else {
                //show dialogue with result
                //showResultDialogue();
                if(i==0) {
                    Intent intent = new Intent(Home.this, Add_product.class);
                    intent.putExtra("barcode", result.getContents());
                    startActivity(intent);
                }else if(i==1){
                    new Async_get_product_by_id(Home.this).execute(result.getContents());
                   /* Intent intent = new Intent(Home.this, items_list_page.class);
                    intent.putExtra("barcode", result.getContents());
                    startActivity(intent);*/
                }
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
   /* public void showResultDialogue() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Scan Result")
                .setMessage("Scanned result is " + result)
                .setPositiveButton("Add Product", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                       // ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        //ClipData clip = ClipData.newPlainText("Scan Result", result);
                        //clipboard.setPrimaryClip(clip);
                       // Toast.makeText(Home.this, "Result copied to clipboard", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.dismiss();
                    }
                })
                .show();

    }*/
}
