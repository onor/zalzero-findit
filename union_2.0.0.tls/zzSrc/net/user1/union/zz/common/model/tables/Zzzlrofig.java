/**
 * This class is generated by jOOQ
 */
package net.user1.union.zz.common.model.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = {"http://www.jooq.org", "2.0.0"},
                            comments = "This class is generated by jOOQ")
public class Zzzlrofig extends org.jooq.impl.UpdatableTableImpl<net.user1.union.zz.common.model.tables.records.ZzzlrofigRecord> {

	private static final long serialVersionUID = -280157184;

	/**
	 * The singleton instance of zzzlrofig
	 */
	public static final net.user1.union.zz.common.model.tables.Zzzlrofig ZZZLROFIG = new net.user1.union.zz.common.model.tables.Zzzlrofig();

	/**
	 * The class holding records for this type
	 */
	private static final java.lang.Class<net.user1.union.zz.common.model.tables.records.ZzzlrofigRecord> __RECORD_TYPE = net.user1.union.zz.common.model.tables.records.ZzzlrofigRecord.class;

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<net.user1.union.zz.common.model.tables.records.ZzzlrofigRecord> getRecordType() {
		return __RECORD_TYPE;
	}

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrofigRecord, java.lang.String> ZLFIG_ID = createField("zlfig_id", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrofigRecord, java.lang.String> ZLFIG_NAME = createField("zlfig_name", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrofigRecord, java.lang.Short> ZLFIG_REQUIRED = createField("zlfig_required", org.jooq.impl.SQLDataType.SMALLINT, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrofigRecord, java.lang.String> ZLFIG_REMARK = createField("zlfig_remark", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrofigRecord, java.lang.Integer> ZLFIG_POINTS = createField("zlfig_points", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [public.zzzlrofig.zlfig_zlfiggroup_id]
	 * REFERENCES zzzlrofiggroup [public.zzzlrofiggroup.zlfiggroup_id]
	 * </pre></code>
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrofigRecord, java.lang.String> ZLFIG_ZLFIGGROUP_ID = createField("zlfig_zlfiggroup_id", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrofigRecord, java.sql.Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrofigRecord, java.sql.Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrofigRecord, java.lang.Integer> ZLFIG_TYPE = createField("zlfig_type", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * No further instances allowed
	 */
	private Zzzlrofig() {
		super("zzzlrofig", net.user1.union.zz.common.model.Public.PUBLIC);
	}

	/**
	 * No further instances allowed
	 */
	private Zzzlrofig(java.lang.String alias) {
		super(alias, net.user1.union.zz.common.model.Public.PUBLIC, net.user1.union.zz.common.model.tables.Zzzlrofig.ZZZLROFIG);
	}

	@Override
	public org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzzlrofigRecord> getMainKey() {
		return net.user1.union.zz.common.model.Keys.zzzlrofig_pkey;
	}

	@Override
	@SuppressWarnings("unchecked")
	public java.util.List<org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzzlrofigRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzzlrofigRecord>>asList(net.user1.union.zz.common.model.Keys.zzzlrofig_pkey);
	}

	@Override
	@SuppressWarnings("unchecked")
	public java.util.List<org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzzlrofigRecord, ?>> getReferences() {
		return java.util.Arrays.<org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzzlrofigRecord, ?>>asList(net.user1.union.zz.common.model.Keys.zzzlrofig_zlfig_zlfiggroup_id_fkey);
	}

	@Override
	public net.user1.union.zz.common.model.tables.Zzzlrofig as(java.lang.String alias) {
		return new net.user1.union.zz.common.model.tables.Zzzlrofig(alias);
	}
}