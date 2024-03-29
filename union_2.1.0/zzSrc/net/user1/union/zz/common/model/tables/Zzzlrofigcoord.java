/**
 * This class is generated by jOOQ
 */
package net.user1.union.zz.common.model.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = {"http://www.jooq.org", "2.0.0"},
                            comments = "This class is generated by jOOQ")
public class Zzzlrofigcoord extends org.jooq.impl.UpdatableTableImpl<net.user1.union.zz.common.model.tables.records.ZzzlrofigcoordRecord> {

	private static final long serialVersionUID = 124322644;

	/**
	 * The singleton instance of zzzlrofigcoord
	 */
	public static final net.user1.union.zz.common.model.tables.Zzzlrofigcoord ZZZLROFIGCOORD = new net.user1.union.zz.common.model.tables.Zzzlrofigcoord();

	/**
	 * The class holding records for this type
	 */
	private static final java.lang.Class<net.user1.union.zz.common.model.tables.records.ZzzlrofigcoordRecord> __RECORD_TYPE = net.user1.union.zz.common.model.tables.records.ZzzlrofigcoordRecord.class;

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<net.user1.union.zz.common.model.tables.records.ZzzlrofigcoordRecord> getRecordType() {
		return __RECORD_TYPE;
	}

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrofigcoordRecord, java.lang.String> ZLFIGCOORD_ID = createField("zlfigcoord_id", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [public.zzzlrofigcoord.zlfigcoord_zlfig_id]
	 * REFERENCES zzzlrofig [public.zzzlrofig.zlfig_id]
	 * </pre></code>
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrofigcoordRecord, java.lang.String> ZLFIGCOORD_ZLFIG_ID = createField("zlfigcoord_zlfig_id", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrofigcoordRecord, java.lang.Integer> ZLFIGCOORD_POSX = createField("zlfigcoord_posx", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrofigcoordRecord, java.lang.Integer> ZLFIGCOORD_POSY = createField("zlfigcoord_posy", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrofigcoordRecord, java.sql.Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrofigcoordRecord, java.sql.Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * No further instances allowed
	 */
	private Zzzlrofigcoord() {
		super("zzzlrofigcoord", net.user1.union.zz.common.model.Public.PUBLIC);
	}

	/**
	 * No further instances allowed
	 */
	private Zzzlrofigcoord(java.lang.String alias) {
		super(alias, net.user1.union.zz.common.model.Public.PUBLIC, net.user1.union.zz.common.model.tables.Zzzlrofigcoord.ZZZLROFIGCOORD);
	}

	@Override
	public org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzzlrofigcoordRecord> getMainKey() {
		return net.user1.union.zz.common.model.Keys.zzzlrofigcoord_pkey;
	}

	@Override
	@SuppressWarnings("unchecked")
	public java.util.List<org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzzlrofigcoordRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzzlrofigcoordRecord>>asList(net.user1.union.zz.common.model.Keys.zzzlrofigcoord_pkey);
	}

	@Override
	@SuppressWarnings("unchecked")
	public java.util.List<org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzzlrofigcoordRecord, ?>> getReferences() {
		return java.util.Arrays.<org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzzlrofigcoordRecord, ?>>asList(net.user1.union.zz.common.model.Keys.zzzlrofigcoord_zlfigcoord_zlfig_id_fkey);
	}

	@Override
	public net.user1.union.zz.common.model.tables.Zzzlrofigcoord as(java.lang.String alias) {
		return new net.user1.union.zz.common.model.tables.Zzzlrofigcoord(alias);
	}
}
