package bmob.store.demo.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import bmob.store.demo.R;
import bmob.store.demo.base.BaseFragmentActivity;
import bmob.store.demo.bean.Order;
import bmob.store.demo.utlis.ToastFactory;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.SaveListener;

import com.bmob.pay.tool.BmobPay;
import com.bmob.pay.tool.PayListener;

public class PayActivity extends BaseFragmentActivity implements OnClickListener{
	
	TextView price;
	Button zfb,weix,pay;
	Float get_price;
	public int STATE =-1;//0 :zfb 1:weixin
	protected int getLayoutViewID() {
		return R.layout.activity_pay;
	}

	protected void findViews() {
		get_price = getIntent().getFloatExtra("price",0.5f);
		price = (TextView)findViewById(R.id.pay_Allmoney);
		zfb = (Button)findViewById(R.id.pay_zhifubaoBtn);
		weix = (Button)findViewById(R.id.pay_weixinBtn);
		pay = (Button)findViewById(R.id.pay_okBtn);
	}
	
	protected void setupViews() {
		price.setText("�ܼƣ�"+get_price+"Ԫ");
	}
	
	protected void setLinstener() {
		zfb.setOnClickListener(this);
		weix.setOnClickListener(this);
		pay.setOnClickListener(this);
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pay_weixinBtn:
			weix.setBackgroundResource(R.drawable.check_selected_green);
			zfb.setBackgroundResource(R.drawable.check_unselected);
			STATE = 1;
			break;
		case R.id.pay_zhifubaoBtn:
			zfb.setBackgroundResource(R.drawable.check_selected_green);
			weix.setBackgroundResource(R.drawable.check_unselected);
			STATE = 0;
			break;
		case R.id.pay_okBtn:
			myHandler.sendEmptyMessage(STATE);
			break;
		}
	}
	Handler myHandler = new Handler(){
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				//zfb
				payZFB();
				break;
			case 1:
				//weixin
				payWEIXIN();
				break;
			}
		}
	};
	public void payZFB(){
		//��һ�������Ǽ۸�Ϊdouble����
		//�ڶ��������Ƕ�������
		//�����������ǻص��ӿ�
		new BmobPay(this).pay(get_price, "���Ե��ţ�123456", new PayListener() {
			public void unknow() {
				
			}
			public void succeed() {
				//��������
				createOrder();
			}
			
			public void orderId(String s) {
				//����id������֧����������
			}
			
			public void fail(int code, String message) {
				ToastFactory.show(PayActivity.this, "֧��ʧ�ܣ�");
				finish();
			}
		});
	}
	public void createOrder(){
		ToastFactory.showProgressDialog(this,"���ڴ�������...");
		Order order = new Order();
		order.setOrderNumber("123456");
		order.setOrderPrice(get_price);
		order.setOrderInfo("������Ʒ");
		order.save(this,new SaveListener() {
			public void onSuccess() {
				ToastFactory.dismissProgressDialog();
//				ToastFactory.show(PayActivity.this, "֧���ɹ���");
				ToastFactory.showNotNotification(PayActivity.this, "", "��ϲ���µ��ɹ�������bmob store");
				//���͸��û�
				BmobPushManager bpm =new BmobPushManager(PayActivity.this);
				BmobQuery<BmobInstallation> query = BmobInstallation.getQuery();
				query.addWhereEqualTo("pushId","bmobshop");
				bpm.setQuery(query);
			   try {
				bpm.pushMessage(new JSONObject().put("bmobpushText","�����µ���~~ "));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				finish();
			}
			public void onFailure(int arg0, String arg1) {
				ToastFactory.dismissProgressDialog();
			}
		});
	}
	public void payWEIXIN(){
		
	}
}
