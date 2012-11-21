/**
 * This class is generated by jOOQ
 */
package net.user1.union.zz.common.model.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = {"http://www.jooq.org", "2.0.0"},
                            comments = "This class is generated by jOOQ")
public class Zzzlrogameround extends org.jooq.impl.UpdatableTableImpl<net.user1.union.zz.common.model.tables.records.ZzzlrogameroundRecord> {

	private static final long serialVersionUID = -2079468441;

	/**
	 * The singleton instance of zzzlrogameround
	 */
	public static final net.user1.union.zz.common.model.tables.Zzzlrogameround ZZZLROGAMEROUND = new net.user1.union.zz.common.model.tables.Zzzlrogameround();

	/**
	 * The class holding records for this type
	 */
	private static final java.lang.Class<net.user1.union.zz.common.model.tables.records.ZzzlrogameroundRecord> __RECORD_TYPE = net.user1.union.zz.common.model.tables.records.ZzzlrogameroundRecord.class;

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<net.user1.union.zz.common.model.tables.records.ZzzlrogameroundRecord> getRecordType() {
		return __RECORD_TYPE;
	}

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrogameroundRecord, java.lang.String> ZLGAMEROUND_ID = createField("zlgameround_id", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [public.zzzlrogameround.zlgameround_gameinst_id]
	 * REFERENCES zzgameinst [public.zzgameinst.gameinst_id]
	 * </pre></code>
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrogameroundRecord, java.lang.String> ZLGAMEROUND_GAMEINST_ID = createField("zlgameround_gameinst_id", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrogameroundRecord, java.lang.String> ZLGAMEROUND_ROUNDNAME = createField("zlgameround_roundname", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrogameroundRecord, java.sql.Timestamp> ZLGAMEROUND_TIMESTART = createField("zlgameround_timestart", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrogameroundRecord, java.sql.Timestamp> ZLGAMEROUND_TIMEEND = createField("zlgameround_timeend", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrogameroundRecord, java.sql.Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzzlrogameroundRecord, java.sql.Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * No further instances allowed
	 */
	private Zzzlrogameround() {
		super("zzzlrogameround", net.user1.union.zz.common.model.Public.PUBLIC);
	}

	/**
	 * No further instances allowed
	 */
	private Zzzlrogameround(java.lang.String alias) {
		super(alias, net.user1.union.zz.common.model.Public.PUBLIC, net.user1.union.zz.common.model.tables.Zzzlrogameround.ZZZLROGAMEROUND);
	}

	@Override
	public org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzzlrogameroundRecord> getMainKey() {
		return net.user1.union.zz.common.model.Keys.zzzlrogameround_pkey;
	}

	@Override
	@SuppressWarnings("unchecked")
	public java.util.List<org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzzlrogameroundRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzzlrogameroundRecord>>asList(net.user1.union.zz.common.model.Keys.zzzlrogameround_pkey);
	}

	@Override
	@SuppressWarnings("unchecked")
	public java.util.List<org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzzlrogameroundRecord, ?>> getReferences() {
		return java.util.Arrays.<org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzzlrogameroundRecord, ?>>asList(net.user1.union.zz.common.model.Keys.zzzlrogameround_zlgameround_gameinst_id_fkey);
	}

	@Override
	public net.user1.union.zz.common.model.tables.Zzzlrogameround as(java.lang.String alias) {
		return new net.user1.union.zz.common.model.tables.Zzzlrogameround(alias);
	}
}
