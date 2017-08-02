package com.speedata.welllid.bean;

import java.sql.Timestamp;

/**
 * JgTrouble entity. @author MyEclipse Persistence Tools
 */

public class JgTrouble implements java.io.Serializable {

	// Fields

	private Long id;
	private String troubleCode;
	private Long type;
	private Integer areaId;
	private Integer companyId;
	private String address;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Short isRepair;
	private Timestamp repairTime;
	private String description;
	private String longitude;
	private String latitude;
	private Long deviceId;
	private String deviceCode;
    private String troubleTypeName;
    private String companyName;
	// Constructors

	/** default constructor */
	public JgTrouble() {
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTroubleCode() {
		return this.troubleCode;
	}

	public void setTroubleCode(String troubleCode) {
		this.troubleCode = troubleCode;
	}

	public Long getType() {
		return this.type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Integer getAreaId() {
		return this.areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Short getIsRepair() {
		return this.isRepair;
	}

	public void setIsRepair(Short isRepair) {
		this.isRepair = isRepair;
	}

	public Timestamp getRepairTime() {
		return this.repairTime;
	}

	public void setRepairTime(Timestamp repairTime) {
		this.repairTime = repairTime;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Long getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public String getTroubleTypeName() {
		return troubleTypeName;
	}
	public void setTroubleTypeName(String troubleTypeName) {
		this.troubleTypeName = troubleTypeName;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	

}