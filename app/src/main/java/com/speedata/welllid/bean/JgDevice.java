package com.speedata.welllid.bean;

import java.sql.Timestamp;



/**
 * JgDevice entity. @author MyEclipse Persistence Tools
 */

public class JgDevice implements java.io.Serializable {

	// Fields

	private Long id;
	private String name;
	private String code;
	private Long deviceTypeId;
	private String model;
	private String sysVersion;
	private String manufacturer;
	private String service;
	private String description;
	private String image;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Integer areaId;
	private Integer companyId;
	private String latitude;
	private String longitude;
	private String address;
	private String rfidCode;
	private Boolean isDel;
	private Short status;
	private String deviceTypeName;

	// Constructors

	/** default constructor */
	public JgDevice() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(Long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSysVersion() {
		return sysVersion;
	}

	public void setSysVersion(String sysVersion) {
		this.sysVersion = sysVersion;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRfidCode() {
		return rfidCode;
	}

	public void setRfidCode(String rfidCode) {
		this.rfidCode = rfidCode;
	}

	public Boolean getDel() {
		return isDel;
	}

	public void setDel(Boolean del) {
		isDel = del;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}

	@Override
	public String toString() {
		return "JgDevice{" +
				"id=" + id +
				", name='" + name + '\'' +
				", code='" + code + '\'' +
				", deviceTypeId=" + deviceTypeId +
				", model='" + model + '\'' +
				", sysVersion='" + sysVersion + '\'' +
				", manufacturer='" + manufacturer + '\'' +
				", service='" + service + '\'' +
				", description='" + description + '\'' +
				", image='" + image + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				", areaId=" + areaId +
				", companyId=" + companyId +
				", latitude='" + latitude + '\'' +
				", longitude='" + longitude + '\'' +
				", address='" + address + '\'' +
				", rfidCode='" + rfidCode + '\'' +
				", isDel=" + isDel +
				", status=" + status +
				", deviceTypeName='" + deviceTypeName + '\'' +
				'}';
	}
}