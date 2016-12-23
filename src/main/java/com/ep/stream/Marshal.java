package com.ep.stream;

public interface Marshal {
	public abstract OctetsStream marshal(OctetsStream os);

	public abstract OctetsStream unmarshal(OctetsStream os)
			throws MarshalException;
}
