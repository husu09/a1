package com.su.config.co;
import com.su.config.obj.Item;
public class XunBaoCostCo {
	/**id*/
	private int id;
	/**花费*/
	private Item cost;
	/**奖励个数*/
	private int rewardCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Item getCost() {
		return cost;
	}

	public void setCost(Item cost) {
		this.cost = cost;
	}

	public int getRewardCount() {
		return rewardCount;
	}

	public void setRewardCount(int rewardCount) {
		this.rewardCount = rewardCount;
	}
}