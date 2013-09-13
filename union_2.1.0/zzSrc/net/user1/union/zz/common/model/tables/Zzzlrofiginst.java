/**
 * This class is generated by jOOQ
 */
package net.user1.union.zz.common.model.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = {"http://www.jooq.org", "2.0.0"},
                            comments = "This class is generated by jOOQ")
public class Zzzlrofiginst extends org.jooq.impl.UpdatableTableImpl<net.user1.union.zz.common.model.tables.records.ZzzlrofiginstRecord> {

	private static final long serialVersionUID = 400344134;

	/**
	 * The singleton instance of zzzlrofiginst
	 */
	public static final net.user1.union.zz.common.model.tables.Zzzlrofiginst ZZZLROFIGINST = new net.user1.union.zz.common.model.tables.Zzzlrofiginst();

	/**
	 * The class holding records for this type
	 */
	private static final java.lang.Class<net.user1.union.zz.common.model.tables.records.ZzzlrofiginstRecord> __RECORD_TYPE = net.user1.union.zz.common.model.tables.records.ZzzlrofiginstRecord.class;

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<net.user1.union.zz.common.model.tables.records.ZzzlrofiginstRecord> getRecordType() {
		return __RECORD_TYPE;
	}

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrofiginstRecord, java.lang.String> ZLFIGINST_ID = createField("zlfiginst_id", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [public.zzzlrofiginst.zlfiginst_zlfig_id]
	 * REFERENCES zzzlrofig [public.zzzlrofig.zlfig_id]
	 * </pre></code>
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrofiginstRecord, java.lang.String> ZLFIGINST_ZLFIG_ID = createField("zlfiginst_zlfig_id", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrofiginstRecord, java.lang.Short> ZLFIGINST_VALID = createField("zlfiginst_valid", org.jooq.impl.SQLDataType.SMALLINT, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrofiginstRecord, java.lang.Integer> ZLFIGINST_NOOFCOLUMNS = createField("zlfiginst_noofcolumns", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrofiginstRecord, java.lang.Integer> ZLFIGINST_NOOFROWS = createField("zlfiginst_noofrows", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrofiginstRecord, java.lang.String> ZLFIGINST_ORIENTATION = createField("zlfiginst_orientation", org.jooq.impl.SQLDataType.CHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrofiginstRecord, java.sql.Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrofiginstRecord, java.sql.Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * No further instances allowed
	 */
	private Zzzlrofiginst() {
		super("zzzlrofiginst", net.user1.union.zz.common.model.Public.PUBLIC);
	}

	/**
	 * No further instances allowed
	 */
	private Zzzlrofiginst(java.lang.String alias) {
		super(alias, net.user1.union.zz.common.model.Public.PUBLIC, net.user1.union.zz.common.model.tables.Zzzlrofiginst.ZZZLROFIGINST);
	}

	@Override
	public org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzzlrofiginstRecord> getMainKey() {
		return net.user1.union.zz.common.model.Keys.zzzlrofiginst_pkey;
	}

	@Override
	@SuppressWarnings("unchecked")
	public java.util.List<org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzzlrofiginstRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzzlrofiginstRecord>>asList(net.user1.union.zz.common.model.Keys.zzzlrofiginst_pkey);
	}

	@Override
	@SuppressWarnings("unchecked")
	public java.util.List<org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzzlrofiginstRecord, ?>> getReferences() {
		return java.util.Arrays.<org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzzlrofiginstRecord, ?>>asList(net.user1.union.zz.common.model.Keys.zzzlrofiginst_zlfiginst_zlfig_id_fkey);
	}

	@Override
	public net.user1.union.zz.common.model.tables.Zzzlrofiginst as(java.lang.String alias) {
		return new net.user1.union.zz.common.model.tables.Zzzlrofiginst(alias);
	}
}