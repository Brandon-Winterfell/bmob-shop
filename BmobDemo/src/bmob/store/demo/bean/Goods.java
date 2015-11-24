package bmob.store.demo.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Goods extends BmobObject implements Serializable{
	
	
	private Float goods_price;//�۸�
	private Integer Stock;//���
	private String info;//����
	private String goods_name;//����
	private String goods_type;//��Ʒ����
	private BmobFile goods_avator;//ͷ��
	public Shop goods_shop;//��������
	
	public Float getGoods_price() {
		return goods_price;
	}
	public void setGoods_price(Float goods_price) {
		this.goods_price = goods_price;
	}
	public Integer getStock() {
		return Stock;
	}
	public void setStock(Integer stock) {
		Stock = stock;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getGoods_type() {
		return goods_type;
	}
	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}
	public BmobFile getGoods_avator() {
		return goods_avator;
	}
	public void setGoods_avator(BmobFile goods_avator) {
		this.goods_avator = goods_avator;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public Shop getGoods_shop() {
		return goods_shop;
	}
	public void setGoods_shop(Shop goods_shop) {
		this.goods_shop = goods_shop;
	}
}
