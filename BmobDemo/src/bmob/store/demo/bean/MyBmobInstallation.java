package bmob.store.demo.bean;

import android.content.Context;
import cn.bmob.v3.BmobInstallation;

public class MyBmobInstallation extends BmobInstallation{
	
	private String pushId;
	//�������͸�ָ���û���Ψһ��ʶ����ֵ������obejctId,�������û���
	
	public MyBmobInstallation(Context context) {
		super(context);
	}

	public String getPushId() {
		return pushId;
	}

	public void setPushId(String pushId) {
		this.pushId = pushId;
	}
}
