/**
 * This class is generated by jOOQ
 */
package net.user1.union.zz.common.model.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = {"http://www.jooq.org", "2.0.0"},
                            comments = "This class is generated by jOOQ")
public class Zzgameusersummary extends org.jooq.impl.UpdatableTableImpl<net.user1.union.zz.common.model.tables.records.ZzgameusersummaryRecord> {

	private static final long serialVersionUID = 598252810;

	/**
	 * The singleton instance of zzgameusersummary
	 */
	public static final net.user1.union.zz.common.model.tables.Zzgameusersummary ZZGAMEUSERSUMMARY = new net.user1.union.zz.common.model.tables.Zzgameusersummary();

	/**
	 * The class holding records for this type
	 */
	private static final java.lang.Class<net.user1.union.zz.common.model.tables.records.ZzgameusersummaryRecord> __RECORD_TYPE = net.user1.union.zz.common.model.tables.records.ZzgameusersummaryRecord.class;

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<net.user1.union.zz.common.model.tables.records.ZzgameusersummaryRecord> getRecordType() {
		return __RECORD_TYPE;
	}

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameusersummaryRecord, java.lang.String> USER_SUMMARY_ID = createField("user_summary_id", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [public.zzgameusersummary.zz_gameinsttemp_id]
	 * REFERENCES zzgameinsttempl [public.zzgameinsttempl.gameinsttempl_id]
	 * </pre></code>
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameusersummaryRecord, java.lang.String> ZZ_GAMEINSTTEMP_ID = createField("zz_gameinsttemp_id", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [public.zzgameusersummary.user_id]
	 * REFERENCES zzuser [public.zzuser.user_id]
	 * </pre></code>
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameusersummaryRecord, java.lang.String> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameusersummaryRecord, java.lang.Integer> TOTAL_GAMES_PLAYED = createField("total_games_played", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameusersummaryRecord, java.lang.Integer> TOTAL_GAME_WON = createField("total_game_won", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameusersummaryRecord, java.lang.Integer> USER_LEVEL = createField("user_level", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameusersummaryRecord, java.sql.Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameusersummaryRecord, java.sql.Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [public.zzgameusersummary.last_gameinst_id]
	 * REFERENCES zzgameinst [public.zzgameinst.gameinst_id]
	 * </pre></code>
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameusersummaryRecord, java.lang.String> LAST_GAMEINST_ID = createField("last_gameinst_id", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameusersummaryRecord, java.sql.Timestamp> LAST_GAMEINST_UPDATE_TIME = createField("last_gameinst_update_time", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzgameusersummaryRecord, java.lang.String> CURRENT_GAMEINST_ID = createField("current_gameinst_id", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * No further instances allowed
	 */
	private Zzgameusersummary() {
		super("zzgameusersummary", net.user1.union.zz.common.model.Public.PUBLIC);
	}

	/**
	 * No further instances allowed
	 */
	private Zzgameusersummary(java.lang.String alias) {
		super(alias, net.user1.union.zz.common.model.Public.PUBLIC, net.user1.union.zz.common.model.tables.Zzgameusersummary.ZZGAMEUSERSUMMARY);
	}

	@Override
	public org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzgameusersummaryRecord> getMainKey() {
		return net.user1.union.zz.common.model.Keys.zzuser_summary;
	}

	@Override
	@SuppressWarnings("unchecked")
	public java.util.List<org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzgameusersummaryRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzgameusersummaryRecord>>asList(net.user1.union.zz.common.model.Keys.zzuser_summary);
	}

	@Override
	@SuppressWarnings("unchecked")
	public java.util.List<org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzgameusersummaryRecord, ?>> getReferences() {
		return java.util.Arrays.<org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzgameusersummaryRecord, ?>>asList(net.user1.union.zz.common.model.Keys.zzgameinsttempl, net.user1.union.zz.common.model.Keys.zzuserfk, net.user1.union.zz.common.model.Keys.last_gameinst_id_fkey);
	}

	@Override
	public net.user1.union.zz.common.model.tables.Zzgameusersummary as(java.lang.String alias) {
		return new net.user1.union.zz.common.model.tables.Zzgameusersummary(alias);
	}
}
