package com.ep.config;

@SuppressWarnings("serial")
public class ProtocolInfo implements java.io.Serializable {
	private String className;
	private Integer typeId = null;
	private Integer maxSize = null;

	public ProtocolInfo(String className) {
		this.className = className;
	}

	public String getClassName() {
		return className;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((className == null) ? 0 : className.hashCode());
		result = prime * result + ((maxSize == null) ? 0 : maxSize.hashCode());
		result = prime * result + ((typeId == null) ? 0 : typeId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		ProtocolInfo other = (ProtocolInfo) obj;
		if (className == null && other.className != null || className != null
				&& !className.equals(other.className)) {
			return false;
		}
		if (maxSize == null && other.maxSize != null || maxSize != null
				&& !maxSize.equals(other.maxSize)) {
			return false;
		}
		if (typeId == null && other.typeId != null || typeId != null
				&& !typeId.equals(other.typeId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ProtocolInfo [className=" + className + ", typeId=" + typeId
				+ ", maxSize=" + maxSize + "]";
	}
}
