package com.su.common.po;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.su.common.data.Cache;

@Cache(startId = 10000000000L)
@Entity
public class Mail implements Serializable {
	
	@Id
	private long id;
	/**
	 * 玩家id
	 * */
	private long playerId;
	/**
	 * 邮件id
	 * */
	private int mailId;
	/**
	 * 发送人
	 * */
	private String sendName;
	/**
	 * 发送时间
	 * */
	private long sendTime;
	/**
	 * 邮件奖励
	 * */
	private String rewardData;
	/**
	 * 参数
	 * */
	private String paramData;
	/**
	 * 是否领取附件
	 * */
	private int isReceive;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}
	public int getMailId() {
		return mailId;
	}
	public void setMailId(int mailId) {
		this.mailId = mailId;
	}
	public long getSendTime() {
		return sendTime;
	}
	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}
	public String getSendName() {
		return sendName;
	}
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	public int getIsReceive() {
		return isReceive;
	}
	public void setIsReceive(int isReceive) {
		this.isReceive = isReceive;
	}
	public String getRewardData() {
		return rewardData;
	}
	public void setRewardData(String rewardData) {
		this.rewardData = rewardData;
	}
	public String getParamData() {
		return paramData;
	}
	public void setParamData(String paramData) {
		this.paramData = paramData;
	}
}
