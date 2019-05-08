package com.su.common.po;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.su.common.data.Cache;

@Cache(startId = 10000000000L)
@Entity
public class PlayerDetail implements Serializable {
	
	@Id
	private long id;
	/**
	 * 背包
	 */
	private String bagData;
	/**
	 * 角色
	 * */
	private String roleData;
	/**
	 * 寻宝
	 * */
	private String xunBaoData;
	/**
	 * 拍卖
	 * */
	private String acuItemData;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getBagData() {
		return bagData;
	}
	public void setBagData(String bagData) {
		this.bagData = bagData;
	}
	public String getRoleData() {
		return roleData;
	}
	public void setRoleData(String roleData) {
		this.roleData = roleData;
	}
	public String getXunBaoData() {
		return xunBaoData;
	}
	public void setXunBaoData(String xunBaoData) {
		this.xunBaoData = xunBaoData;
	}
	public String getAcuItemData() {
		return acuItemData;
	}
	public void setAcuItemData(String acuItemData) {
		this.acuItemData = acuItemData;
	}
	
}
