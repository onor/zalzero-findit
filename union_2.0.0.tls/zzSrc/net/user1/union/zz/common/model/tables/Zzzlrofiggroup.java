/**
 * This class is generated by jOOQ
 */
package net.user1.union.zz.common.model.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = {"http://www.jooq.org", "2.0.0"},
                            comments = "This class is generated by jOOQ")
public class Zzzlrofiggroup extends org.jooq.impl.UpdatableTableImpl<net.user1.union.zz.common.model.tables.records.ZzzlrofiggroupRecord> {

	private static final long serialVersionUID = -494424363;

	/**
	 * The singleton instance of zzzlrofiggroup
	 */
	public static final net.user1.union.zz.common.model.tables.Zzzlrofiggroup ZZZLROFIGGROUP = new net.user1.union.zz.common.model.tables.Zzzlrofiggroup();

	/**
	 * The class holding records for this type
	 */
	private static final java.lang.Class<net.user1.union.zz.common.model.tables.records.ZzzlrofiggroupRecord> __RECORD_TYPE = net.user1.union.zz.common.model.tables.records.ZzzlrofiggroupRecord.class;

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<net.user1.union.zz.common.model.tables.records.ZzzlrofiggroupRecord> getRecordType() {
		return __RECORD_TYPE;
	}

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrofiggroupRecord, java.lang.String> ZLFIGGROUP_ID = createField("zlfiggroup_id", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrofiggroupRecord, java.lang.String> ZLFIGGROUP_NAME = createField("zlfiggroup_name", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrofiggroupRecord, java.lang.Integer> ZLFIGGROUP_NOOFFIGREQUIRED = createField("zlfiggroup_nooffigrequired", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrofiggroupRecord, java.sql.Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrofiggroupRecord, java.sql.Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * No further instances allowed
	 */
	private Zzzlrofiggroup() {
		super("zzzlrofiggroup", net.user1.union.zz.common.model.Public.PUBLIC);
	}

	/**
	 * No further instances allowed
	 */
	private Zzzlrofiggroup(java.lang.String alias) {
		super(alias, net.user1.union.zz.common.model.Public.PUBLIC, net.user1.union.zz.common.model.tables.Zzzlrofiggroup.ZZZLROFIGGROUP);
	}

	@Override
	public org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzzlrofiggroupRecord> getMainKey() {
		return net.user1.union.zz.common.model.Keys.zzzlrofiggroup_pkey;
	}

	@Override
	@SuppressWarnings("unchecked")
	public java.util.List<org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzzlrofiggroupRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzzlrofiggroupRecord>>asList(net.user1.union.zz.common.model.Keys.zzzlrofiggroup_pkey);
	}

	@Override
	public net.user1.union.zz.common.model.tables.Zzzlrofiggroup as(java.lang.String alias) {
		return new net.user1.union.zz.common.model.tables.Zzzlrofiggroup(alias);
	}
}