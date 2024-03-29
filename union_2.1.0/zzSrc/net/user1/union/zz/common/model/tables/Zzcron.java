/**
 * This class is generated by jOOQ
 */
package net.user1.union.zz.common.model.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = {"http://www.jooq.org", "2.0.0"},
                            comments = "This class is generated by jOOQ")
public class Zzcron extends org.jooq.impl.UpdatableTableImpl<net.user1.union.zz.common.model.tables.records.ZzcronRecord> {

	private static final long serialVersionUID = -248036489;

	/**
	 * The singleton instance of zzcron
	 */
	public static final net.user1.union.zz.common.model.tables.Zzcron ZZCRON = new net.user1.union.zz.common.model.tables.Zzcron();

	/**
	 * The class holding records for this type
	 */
	private static final java.lang.Class<net.user1.union.zz.common.model.tables.records.ZzcronRecord> __RECORD_TYPE = net.user1.union.zz.common.model.tables.records.ZzcronRecord.class;

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<net.user1.union.zz.common.model.tables.records.ZzcronRecord> getRecordType() {
		return __RECORD_TYPE;
	}

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzcronRecord, java.lang.Integer> CRON_ID = createField("cron_id", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzcronRecord, java.sql.Timestamp> CRON_RUNTIME = createField("cron_runtime", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzcronRecord, java.sql.Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * No further instances allowed
	 */
	private Zzcron() {
		super("zzcron", net.user1.union.zz.common.model.Public.PUBLIC);
	}

	/**
	 * No further instances allowed
	 */
	private Zzcron(java.lang.String alias) {
		super(alias, net.user1.union.zz.common.model.Public.PUBLIC, net.user1.union.zz.common.model.tables.Zzcron.ZZCRON);
	}

	@Override
	public org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzcronRecord> getMainKey() {
		return net.user1.union.zz.common.model.Keys.zzcron_primary_key;
	}

	@Override
	@SuppressWarnings("unchecked")
	public java.util.List<org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzcronRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzcronRecord>>asList(net.user1.union.zz.common.model.Keys.zzcron_primary_key);
	}

	@Override
	public net.user1.union.zz.common.model.tables.Zzcron as(java.lang.String alias) {
		return new net.user1.union.zz.common.model.tables.Zzcron(alias);
	}
}
