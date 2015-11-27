package bmob.store.demo.base;

import bmob.store.demo.bean.MyUser;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

public abstract class BaseFragmentActivity extends FragmentActivity{
	
	public FragmentManager mfragmentManager;
	public FragmentTransaction mfragmentTransaction;
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(getLayoutViewID());
		findViews();
		setupViews();
		setLinstener();
	}
	public void back(View view){
		finish();
	}
	/**
	 * �������ݵ�activity��ת
	 * @param cla ��Ҫ��ת����
	 */
	public void startAnimActivity(Class<?> cla) {
		this.startActivity(new Intent(this, cla));
	}
	/**
	 * �����ݵ�activity��ת
	 * @param intent ����bundle��extra��intent
	 */
	public void startAnimActivity(Intent intent) {
		this.startActivity(intent);
	}
	protected abstract int getLayoutViewID();
	protected abstract void findViews();
	protected abstract void setupViews();
	protected abstract void setLinstener();
}
