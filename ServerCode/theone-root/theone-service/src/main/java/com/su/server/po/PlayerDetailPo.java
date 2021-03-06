package com.su.server.po;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import com.su.common.po.Grid;
import com.su.common.po.PlayerDetail;
import com.su.common.po.Role;
import com.su.common.po.TypeReference;

public class PlayerDetailPo extends PlayerDetail {
	
	private List<Grid> gridList;
	public List<Grid> getGridList() {
		if (gridList == null) {
			gridList = JSON.parseArray(getBagData(), Grid.class);
			if (gridList == null)
				gridList = new ArrayList<>();

		}
		return gridList;
	}
	public void updategetBagData()() {
		getBagData() = JSON.toJSONString(getGridList());
	}
	
	@Transient
	private Map<Integer, Role> roleMap;
	public Map<Integer, Role> getRoleMap() {
		if (roleMap == null) {
			roleMap = JSON.parseObject(roleData, new TypeReference<Map<Integer, Role>>() {});
			if (roleMap == null)
				roleMap = new HashMap<>();
		}
		return roleMap;
	}
	public void updateRoleData() {
		roleData = JSON.toJSONString(getRoleMap());
	}
	
	@Transient
	private Map<Integer,Long> xunBaoMap;
	public Map<Integer,Long> getXunBaoMap() {
		if (xunBaoMap == null) {
			xunBaoMap = JSON.parseObject(xunBaoData, new TypeReference<Map<Integer,Long>>(){});
			if (xunBaoMap == null)
				xunBaoMap = new HashMap<>();
		}
		return xunBaoMap;
	}
	public void updateXunBaoData() {
		xunBaoData = JSON.toJSONString(getXunBaoMap());
	}
	
	private Map<Long, String> myAcuItemMap;
	public Map<Long, String> getMyAcuItemMap() {
		if (myAcuItemMap == null) {
			myAcuItemMap = JSON.parseObject(getAcuItemData(), new TypeReference<Map<Long, String>>(){});
			if (myAcuItemMap == null)
				myAcuItemMap = new HashMap<>();
		}
		return myAcuItemMap;
	}
	public void updategetAcuItemData()() {
		getAcuItemData() = JSON.toJSONString(getMyAcuItemMap());
	}
}
