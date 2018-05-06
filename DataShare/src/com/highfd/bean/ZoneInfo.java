package com.highfd.bean;

public class ZoneInfo implements java.io.Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6698496083102469770L;

	/**
	 * ID
	 */
	private int zoneId;

	/**
	 * 区域编码
	 */
	private String zoneCode;

	/**
	 * 区域名称
	 */
	private String zoneName;

	/**
	 * @return the zoneId
	 */
	public int getZoneId() {
		return zoneId;
	}

	/**
	 * @param zoneId
	 *            the zoneId to set
	 */
	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}

	/**
	 * @return the zoneCode
	 */
	public String getZoneCode() {
		return zoneCode;
	}

	/**
	 * @param zoneCode
	 *            the zoneCode to set
	 */
	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	/**
	 * @return the zoneName
	 */
	public String getZoneName() {
		return zoneName;
	}

	/**
	 * @param zoneName
	 *            the zoneName to set
	 */
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

}
