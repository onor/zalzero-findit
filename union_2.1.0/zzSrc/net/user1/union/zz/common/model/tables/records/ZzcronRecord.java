/**
 * This class is generated by jOOQ
 */
package net.user1.union.zz.common.model.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = {"http://www.jooq.org", "2.0.0"},
                            comments = "This class is generated by jOOQ")
public class ZzcronRecord extends org.jooq.impl.UpdatableRecordImpl<net.user1.union.zz.common.model.tables.records.ZzcronRecord> {

	private static final long serialVersionUID = 1482730508;

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public void setCronId(java.lang.Integer value) {
		setValue(net.user1.union.zz.common.model.tables.Zzcron.ZZCRON.CRON_ID, value);
	}

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public java.lang.Integer getCronId() {
		return getValue(net.user1.union.zz.common.model.tables.Zzcron.ZZCRON.CRON_ID);
	}

	/**
	 * An uncommented item
	 */
	public void setCronRuntime(java.sql.Timestamp value) {
		setValue(net.user1.union.zz.common.model.tables.Zzcron.ZZCRON.CRON_RUNTIME, value);
	}

	/**
	 * An uncommented item
	 */
	public java.sql.Timestamp getCronRuntime() {
		return getValue(net.user1.union.zz.common.model.tables.Zzcron.ZZCRON.CRON_RUNTIME);
	}

	/**
	 * An uncommented item
	 */
	public void setUpdateTime(java.sql.Timestamp value) {
		setValue(net.user1.union.zz.common.model.tables.Zzcron.ZZCRON.UPDATE_TIME, value);
	}

	/**
	 * An uncommented item
	 */
	public java.sql.Timestamp getUpdateTime() {
		return getValue(net.user1.union.zz.common.model.tables.Zzcron.ZZCRON.UPDATE_TIME);
	}

	/**
	 * Create a detached ZzcronRecord
	 */
	public ZzcronRecord() {
		super(net.user1.union.zz.common.model.tables.Zzcron.ZZCRON);
	}
}
