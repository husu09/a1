package com.su.config.co;
import com.su.config.obj.Item;
public class ContestRewardCo {
	private int id;
	/**比赛id*/
	private int constId;
	/**名次*/
	private int ranking;
	/**奖励*/
	private Item reward;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getConstId() {
		return constId;
	}

	public void setConstId(int constId) {
		this.constId = constId;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public Item getReward() {
		return reward;
	}

	public void setReward(Item reward) {
		this.reward = reward;
	}
}