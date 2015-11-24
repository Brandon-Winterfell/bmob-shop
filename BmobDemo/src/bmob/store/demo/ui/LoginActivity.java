package bmob.store.demo.ui;

import cn.bmob.v3.listener.SaveListener;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import bmob.store.demo.R;
import bmob.store.demo.base.BaseFragmentActivity;
import bmob.store.demo.bean.MyUser;
import bmob.store.demo.utlis.ToastFactory;

public class LoginActivity extends BaseFragmentActivity implements OnClickListener{
	
	Button login_BtnLogin;
	TextView login_tvRegister,login_tvFound;
	EditText login_edUsername,login_edPassword;
	protected int getLayoutViewID() {
		return R.layout.activity_login;
	}

	protected void findViews() {
		login_tvFound = (TextView)findViewById(R.id.login_tvFound);
		login_tvRegister = (TextView)findViewById(R.id.login_tvRegister);
		login_BtnLogin = (Button)findViewById(R.id.login_BtnLogin);
		login_edUsername = (EditText)findViewById(R.id.login_edUsername);
		login_edPassword = (EditText)findViewById(R.id.login_edPassword);
	}
	
	protected void setLinstener() {
		login_BtnLogin.setOnClickListener(this);
		login_tvRegister.setOnClickListener(this);
	}
	
	protected void setupViews() {
		
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_tvRegister:
				register();
			break;
		case R.id.login_BtnLogin:
				login();
			break;
		}
	}
	public void register(){
		startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
	}
	public void login(){
		String getStrUsername = login_edUsername.getText().toString().trim();
		String getStrPassword = login_edPassword.getText().toString().trim();
		if(!getStrUsername.equals("")&&!getStrPassword.equals("")){
			final ProgressDialog pd = ProgressDialog.show(LoginActivity.this, "��ʾ", "���ڵ�½������");
			MyUser myUser = new MyUser();
			myUser.setUsername(getStrUsername);
			myUser.setPassword(getStrPassword);
			myUser.login(LoginActivity.this, new SaveListener() {
				public void onSuccess() {
					pd.dismiss();
					//��ת��������
					startAnimActivity(MainActivity.class);
					finish();
					//��������ʾ��ӭtoast
				}
				public void onFailure(int code, String message) {
					pd.dismiss();
					//�ɸ���code�ж����û������������������ʧ��
					ToastFactory.show(LoginActivity.this, "��¼ʧ�ܣ��˻��������");
				}
			});
		}
	}
}
