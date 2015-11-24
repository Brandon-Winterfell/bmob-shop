package bmob.store.demo.ui;

import java.io.File;
import java.util.Date;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import bmob.store.demo.R;
import bmob.store.demo.base.BaseFragmentActivity;
import bmob.store.demo.bean.MyUser;
import bmob.store.demo.bean.Shop;
import bmob.store.demo.config.MyConfig;
import bmob.store.demo.utlis.ImageViewUtil;
import bmob.store.demo.utlis.ToastFactory;
import bmob.store.demo.view.CircleImageView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class RealeseShopActivity extends BaseFragmentActivity implements OnClickListener{
	CircleImageView shop_avator;
	Button realeseOK;
	protected int getLayoutViewID() {
		return R.layout.realese_shop_activity;
	}
	protected void findViews() {
		shop_avator = (CircleImageView)findViewById(R.id.realese_addavatorBtn);
		realeseOK = (Button)findViewById(R.id.release_ok);
		
	}
	
	protected void setupViews() {
		
	}
	
	protected void setLinstener() {
		shop_avator.setOnClickListener(this);
		realeseOK.setOnClickListener(this);
	}
	
	public void go_changeFromAlbum()
	{
		Intent intent = new Intent(Intent.ACTION_PICK, null);
    	intent.setDataAndType(
			MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		startActivityForResult(intent, MyConfig.REQUESTCODE_TAKE_ALBUM);
	}
	
	public String getSDPath(){
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState()
		.equals(android.os.Environment.MEDIA_MOUNTED); //�ж�sd���Ƿ����
		if (sdCardExist)
		{
			sdDir = Environment.getExternalStorageDirectory();//��ȡ��Ŀ¼
		}
		return sdDir.toString();
	}
	//����·��
    private String filePath = "";
    //��Ƭ�洢��Ŀ¼
    private String avator_cacheDir = getSDPath()+MyConfig.AVATOR_CACHE_DIR;
    
    private String camera_Name = "";
    /**
     * ��ת���������
     * @param dirpath ��Ҫһ��sd��Ŀ¼�������Ƭ
     */
    public void go_CameraActivity()
    {
    	Log.i("sd dir",avator_cacheDir);
		File avator_cache_file = new File(avator_cacheDir);
		if (!avator_cache_file.exists()) {
			avator_cache_file.mkdirs();
		}
		camera_Name = String.valueOf((new Date()).getTime());
		File avator2File = new File(avator_cache_file, camera_Name+".png");//Ŀ¼������
		// ԭͼ
	    filePath = avator2File.getAbsolutePath();// ��ȡ��Ƭ�ı���·��
		Uri imageUri = Uri.fromFile(avator2File);
		Intent intentFromCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intentFromCamera.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
		intentFromCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//�����˼�Ǵ��ڽ��������Ƭ��Uri�浽ָ��λ��
		//�������������Ļ�,�ü��޷���ø���Ƭ
		startActivityForResult(intentFromCamera, MyConfig.REQUESTCODE_TAKE_CAMERA);
    }
    /**
     * �ص�
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
	    	if(resultCode == RESULT_OK){
	    	switch (requestCode) {
			case MyConfig.REQUESTCODE_TAKE_CAMERA:
				Log.i("filepath----------",filePath);
				go_crop_pic(Uri.fromFile(new File(filePath)));
				break;
			case MyConfig.REQUESTCODE_TAKE_ALBUM:
				if(data == null)
					return;
				go_crop_pic(data.getData());
				break;
			case MyConfig.REQUESTCODE_TAKE_CROP:
				if(data !=null)
					cropBack(data);
				break;
			default:
				break;
			}
	    }
    }
    /**
     * ����ü������Ƭ
     * @param data
     */
    Bitmap bb = null;
    public void cropBack(Intent data){
    	bb = data.getExtras().getParcelable("data");//��ȡ�ü����Bitmap����
		//����ƬĿ¼����������С��bitmap,����ǲü����bitmap
    	//ѹ��50%,����JPGģʽѹ��
    	//�ɱ�����9~15kb֮��(4M~5M)
		ImageViewUtil.saveBitmap(avator_cacheDir,camera_Name+".png", bb, 50, true);
		//����,�ϴ�,����,over
		shop_avator.setImageBitmap(bb);
    }
    /**
     * ����,����File�ϴ�
     */
    public void createShop(){
    	final BmobFile bf = new BmobFile(new File(avator_cacheDir+camera_Name+".png"));
    	final ProgressDialog pd = ProgressDialog.show(this, "","���ڴ���....");
    	bf.upload(this,new UploadFileListener() {
			public void onSuccess() {
				//����shop��ͼ�����Ϣ��save
				Shop s = new Shop();
				final MyUser u = BmobUser.getCurrentUser(RealeseShopActivity.this,MyUser.class);
				Log.i("id",u.getObjectId());
				s.setMyuser(u);
				s.setAdress("������");
				s.setShopAvator(bf);
				s.setShopName("Bmob store");
				s.setShopType("С��");
				s.save(RealeseShopActivity.this, new SaveListener() {
					public void onSuccess() {
						//�����ɹ�
						//���bmobobject�����а�����objectid���Ե�ֵ,��ô���µ�ʱ��Ҫ����
						u.setIs_openShop(true);
						u.update(RealeseShopActivity.this,new UpdateListener() {
							public void onSuccess() {
								//���³ɹ�
								pd.dismiss();
								ToastFactory.show(RealeseShopActivity.this, "�����ɹ�!");
								finish();
							}
							public void onFailure(int arg0, String arg1) {
								pd.dismiss();
								ToastFactory.show(RealeseShopActivity.this, "����ʧ��!");
							}
						});
					}
					public void onFailure(int arg0, String arg1) {
						pd.dismiss();
					}
				});
			}
			public void onFailure(int arg0, String arg1) {
				//���ѿ���ʧ��
				pd.dismiss();
			}
		});
    }
   /**
    * �ü�
    */
	public void go_crop_pic(Uri fileUri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
         intent.setDataAndType(fileUri, "image/*");  
		intent.putExtra("aspectX", 4);//�����
		intent.putExtra("aspectY", 4);
		intent.putExtra("outputX", 50);
		intent.putExtra("outputY", 50);
		intent.putExtra("crop", "true");
		intent.putExtra("scale", true);
//		 intent.putExtra("noFaceDetection", true);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("return-data", true);
		startActivityForResult(intent, MyConfig.REQUESTCODE_TAKE_CROP);
	}
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.realese_addavatorBtn:
			//��ʾdialog�������պ��������ģʽ
			new AlertDialog.Builder(RealeseShopActivity.this)
			.setItems(new String[]{"���","���"},new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					if(which == 0){
						//��ת���������
						go_CameraActivity();
					}else{
						//��ת��������
						go_changeFromAlbum();
					}
				}
			}).show();
			break;
		case R.id.release_ok:
			//����Ӧ���ж�EditText�Ƿ�Ϊ���Լ��ж����֤���Ƿ�ϸ�,����ʱ���ϵ��ֱ��д����
			if(bb != null)
				createShop();
			else 
				ToastFactory.show(RealeseShopActivity.this, "��û��С��ͼ��!");
			break;
		}
	}
}
