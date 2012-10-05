/**
 * This class is generated by jOOQ
 */
package net.user1.union.zz.common.model.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = {"http://www.jooq.org", "2.0.0"},
                            comments = "This class is generated by jOOQ")
public class Zzgameinst extends org.jooq.impl.UpdatableTableImpl<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord> {

	private static final long serialVersionUID = -1263621133;

	/**
	 * The singleton instance of zzgameinst
	 */
	public static final net.user1.union.zz.common.model.tables.Zzgameinst ZZGAMEINST = new net.user1.union.zz.common.model.tables.Zzgameinst();

	/**
	 * The class holding records for this type
	 */
	private static final java.lang.Class<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord> __RECORD_TYPE = net.user1.union.zz.common.model.tables.records.ZzgameinstRecord.class;

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord> getRecordType() {
		return __RECORD_TYPE;
	}

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.lang.String> GAMEINST_ID = createField("gameinst_id", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [public.zzgameinst.gameinst_game_id]
	 * REFERENCES zzgame [public.zzgame.game_id]
	 * </pre></code>
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.lang.String> GAMEINST_GAME_ID = createField("gameinst_game_id", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [public.zzgameinst.gameinst_product_id]
	 * REFERENCES zzproduct [public.zzproduct.product_id]
	 * </pre></code>
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.lang.String> GAMEINST_PRODUCT_ID = createField("gameinst_product_id", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [public.zzgameinst.gameinst_gameinsttempl_id]
	 * REFERENCES zzgameinsttempl [public.zzgameinsttempl.gameinsttempl_id]
	 * </pre></code>
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.lang.String> GAMEINST_GAMEINSTTEMPL_ID = createField("gameinst_gameinsttempl_id", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.sql.Timestamp> GAMEINST_STARTTIME = createField("gameinst_starttime", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.sql.Timestamp> GAMEINST_ENDTIME = createField("gameinst_endtime", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.sql.Timestamp> GAMEINST_ENTRYTIME = createField("gameinst_entrytime", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.lang.Integer> GAMEINST_CAPACITY = createField("gameinst_capacity", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.lang.Integer> GAMEINST_THRESHOLD = createField("gameinst_threshold", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.lang.Integer> GAMEINST_PARAMNUM1 = createField("gameinst_paramnum1", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.lang.Integer> GAMEINST_PARAMNUM2 = createField("gameinst_paramnum2", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.lang.Integer> GAMEINST_PARAMNUM3 = createField("gameinst_paramnum3", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.lang.Integer> GAMEINST_PARAMNUM4 = createField("gameinst_paramnum4", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.lang.Integer> GAMEINST_PARAMNUM5 = createField("gameinst_paramnum5", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.lang.Integer> GAMEINST_PARAMNUM6 = createField("gameinst_paramnum6", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.lang.Integer> GAMEINST_PARAMNUM7 = createField("gameinst_paramnum7", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.lang.Integer> GAMEINST_PARAMNUM8 = createField("gameinst_paramnum8", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.lang.Integer> GAMEINST_PARAMNUM9 = createField("gameinst_paramnum9", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.lang.Integer> GAMEINST_PARAMNUM10 = createField("gameinst_paramnum10", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.sql.Timestamp> GAMEINST_PARAMDATE1 = createField("gameinst_paramdate1", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.sql.Timestamp> GAMEINST_PARAMDATE2 = createField("gameinst_paramdate2", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.sql.Timestamp> GAMEINST_PARAMDATE3 = createField("gameinst_paramdate3", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.sql.Timestamp> GAMEINST_PARAMDATE4 = createField("gameinst_paramdate4", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.sql.Timestamp> GAMEINST_PARAMDATE5 = createField("gameinst_paramdate5", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.lang.String> GAMEINST_PARAMVAR1 = createField("gameinst_paramvar1", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.lang.String> GAMEINST_PARAMVAR2 = createField("gameinst_paramvar2", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.lang.String> GAMEINST_PARAMVAR3 = createField("gameinst_paramvar3", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.lang.String> GAMEINST_PARAMVAR4 = createField("gameinst_paramvar4", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.lang.String> GAMEINST_PARAMVAR5 = createField("gameinst_paramvar5", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.sql.Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.sql.Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.lang.String> ZZGAMEINST_STATUS = createField("zzgameinst_status", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [public.zzgameinst.gameinst_created_by]
	 * REFERENCES zzuser [public.zzuser.user_id]
	 * </pre></code>
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, java.lang.String> GAMEINST_CREATED_BY = createField("gameinst_created_by", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * No further instances allowed
	 */
	private Zzgameinst() {
		super("zzgameinst", net.user1.union.zz.common.model.Public.PUBLIC);
	}

	/**
	 * No further instances allowed
	 */
	private Zzgameinst(java.lang.String alias) {
		super(alias, net.user1.union.zz.common.model.Public.PUBLIC, net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST);
	}

	@Override
	public org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord> getMainKey() {
		return net.user1.union.zz.common.model.Keys.zzgameinst_pkey;
	}

	@Override
	@SuppressWarnings("unchecked")
	public java.util.List<org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord>>asList(net.user1.union.zz.common.model.Keys.zzgameinst_pkey);
	}

	@Override
	@SuppressWarnings("unchecked")
	public java.util.List<org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, ?>> getReferences() {
		return java.util.Arrays.<org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, ?>>asList(net.user1.union.zz.common.model.Keys.zzgameinst_gameinst_game_id_fkey, net.user1.union.zz.common.model.Keys.zzgameinst_gameinst_product_id_fkey, net.user1.union.zz.common.model.Keys.zzgameinst_gameinst_gameinsttempl_id_fkey, net.user1.union.zz.common.model.Keys.zzgame_created_by);
	}

	@Override
	public net.user1.union.zz.common.model.tables.Zzgameinst as(java.lang.String alias) {
		return new net.user1.union.zz.common.model.tables.Zzgameinst(alias);
	}
}