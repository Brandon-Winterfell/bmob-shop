package bmob.store.demo.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class MyUser extends BmobUser implements Serializable{
	
	private Integer age;//����
	private Boolean sex;//�Ա�
	private BmobFile avator;//���ڴ��ͷ���ļ�
	private Boolean is_openShop;//�Ƿ���С��
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Boolean getSex() {
		return sex;
	}
	public void setSex(Boolean sex) {
		this.sex = sex;
	}
	public BmobFile getAvator() {
		return avator;
	}
	public void setAvator(BmobFile avator) {
		this.avator = avator;
	}
	public Boolean getIs_openShop() {
		return is_openShop;
	}
	public void setIs_openShop(Boolean is_openShop) {
		this.is_openShop = is_openShop;
	}
}
