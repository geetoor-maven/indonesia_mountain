package com.shegi.idpendaki;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * created by shegi-developer on 31/10/20
 */
public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String NAME = "NAME";
    public static final String EMAIL = "EMAIL";
    public static final String TOTALGN = "TOTALGUNUNG";

    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void CreateSession(String name, String email ,String totalgunung){
        editor.putBoolean(LOGIN,true);
        editor.putString(NAME,name);
        editor.putString(EMAIL,email);
        editor.putString(TOTALGN,totalgunung);
        editor.apply();
    }

    public boolean isLogin(){
        return sharedPreferences.getBoolean(LOGIN,false);
    }

    public  void CheckLogin(){
        if (!this.isLogin()){
            Intent intent = new Intent(context,Loginactivity.class);
            context.startActivity(intent);
            ((HomeActivity)context).finish();
        }
    }

    public HashMap<String,String> getUserDetail(){
        HashMap<String,String> user = new HashMap<>();
        user.put(NAME,sharedPreferences.getString(NAME,null));
        user.put(EMAIL,sharedPreferences.getString(EMAIL,null));
        user.put(TOTALGN,sharedPreferences.getString(TOTALGN,null));
        return user;
    }

    public void Logout(){
        editor.clear();
        editor.commit();
        Intent intent = new Intent(context,Loginactivity.class);
        context.startActivity(intent);
        ((ProfileActivity)context).finish();
    }
}
