/**
 * This class is generated by jOOQ
 */
package net.user1.union.zz.common.model.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = {"http://www.jooq.org", "2.0.0"},
                            comments = "This class is generated by jOOQ")
public class ZzgameinstRecord extends org.jooq.impl.UpdatableRecordImpl<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord> {

	private static final long serialVersionUID = 1585550799;

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public void setGameinstId(java.lang.String value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_ID, value);
	}

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public java.lang.String getGameinstId() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_ID);
	}

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public java.util.List<net.user1.union.zz.common.model.tables.records.ZzgameseatRecord> fetchZzgameseatList() {
		return create()
			.selectFrom(net.user1.union.zz.common.model.tables.Zzgameseat.ZZGAMESEAT)
			.where(net.user1.union.zz.common.model.tables.Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID.equal(getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_ID)))
			.fetch();
	}

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public java.util.List<net.user1.union.zz.common.model.tables.records.ZzgameusersummaryRecord> fetchZzgameusersummaryList() {
		return create()
			.selectFrom(net.user1.union.zz.common.model.tables.Zzgameusersummary.ZZGAMEUSERSUMMARY)
			.where(net.user1.union.zz.common.model.tables.Zzgameusersummary.ZZGAMEUSERSUMMARY.LAST_GAMEINST_ID.equal(getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_ID)))
			.fetch();
	}

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public java.util.List<net.user1.union.zz.common.model.tables.records.ZzzlrogameinstfigRecord> fetchZzzlrogameinstfigList() {
		return create()
			.selectFrom(net.user1.union.zz.common.model.tables.Zzzlrogameinstfig.ZZZLROGAMEINSTFIG)
			.where(net.user1.union.zz.common.model.tables.Zzzlrogameinstfig.ZZZLROGAMEINSTFIG.ZLGAMEINSTFIG_GAMEINST_ID.equal(getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_ID)))
			.fetch();
	}

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public java.util.List<net.user1.union.zz.common.model.tables.records.ZzzlrogameroundRecord> fetchZzzlrogameroundList() {
		return create()
			.selectFrom(net.user1.union.zz.common.model.tables.Zzzlrogameround.ZZZLROGAMEROUND)
			.where(net.user1.union.zz.common.model.tables.Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_GAMEINST_ID.equal(getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_ID)))
			.fetch();
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [public.zzgameinst.gameinst_game_id]
	 * REFERENCES zzgame [public.zzgame.game_id]
	 * </pre></code>
	 */
	public void setGameinstGameId(java.lang.String value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_GAME_ID, value);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [public.zzgameinst.gameinst_game_id]
	 * REFERENCES zzgame [public.zzgame.game_id]
	 * </pre></code>
	 */
	public java.lang.String getGameinstGameId() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_GAME_ID);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [public.zzgameinst.gameinst_game_id]
	 * REFERENCES zzgame [public.zzgame.game_id]
	 * </pre></code>
	 */
	public net.user1.union.zz.common.model.tables.records.ZzgameRecord fetchZzgame() {
		return create()
			.selectFrom(net.user1.union.zz.common.model.tables.Zzgame.ZZGAME)
			.where(net.user1.union.zz.common.model.tables.Zzgame.ZZGAME.GAME_ID.equal(getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_GAME_ID)))
			.fetchOne();
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [public.zzgameinst.gameinst_product_id]
	 * REFERENCES zzproduct [public.zzproduct.product_id]
	 * </pre></code>
	 */
	public void setGameinstProductId(java.lang.String value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PRODUCT_ID, value);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [public.zzgameinst.gameinst_product_id]
	 * REFERENCES zzproduct [public.zzproduct.product_id]
	 * </pre></code>
	 */
	public java.lang.String getGameinstProductId() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PRODUCT_ID);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [public.zzgameinst.gameinst_product_id]
	 * REFERENCES zzproduct [public.zzproduct.product_id]
	 * </pre></code>
	 */
	public net.user1.union.zz.common.model.tables.records.ZzproductRecord fetchZzproduct() {
		return create()
			.selectFrom(net.user1.union.zz.common.model.tables.Zzproduct.ZZPRODUCT)
			.where(net.user1.union.zz.common.model.tables.Zzproduct.ZZPRODUCT.PRODUCT_ID.equal(getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PRODUCT_ID)))
			.fetchOne();
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [public.zzgameinst.gameinst_gameinsttempl_id]
	 * REFERENCES zzgameinsttempl [public.zzgameinsttempl.gameinsttempl_id]
	 * </pre></code>
	 */
	public void setGameinstGameinsttemplId(java.lang.String value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_GAMEINSTTEMPL_ID, value);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [public.zzgameinst.gameinst_gameinsttempl_id]
	 * REFERENCES zzgameinsttempl [public.zzgameinsttempl.gameinsttempl_id]
	 * </pre></code>
	 */
	public java.lang.String getGameinstGameinsttemplId() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_GAMEINSTTEMPL_ID);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [public.zzgameinst.gameinst_gameinsttempl_id]
	 * REFERENCES zzgameinsttempl [public.zzgameinsttempl.gameinsttempl_id]
	 * </pre></code>
	 */
	public net.user1.union.zz.common.model.tables.records.ZzgameinsttemplRecord fetchZzgameinsttempl() {
		return create()
			.selectFrom(net.user1.union.zz.common.model.tables.Zzgameinsttempl.ZZGAMEINSTTEMPL)
			.where(net.user1.union.zz.common.model.tables.Zzgameinsttempl.ZZGAMEINSTTEMPL.GAMEINSTTEMPL_ID.equal(getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_GAMEINSTTEMPL_ID)))
			.fetchOne();
	}

	/**
	 * An uncommented item
	 */
	public void setGameinstStarttime(java.sql.Timestamp value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_STARTTIME, value);
	}

	/**
	 * An uncommented item
	 */
	public java.sql.Timestamp getGameinstStarttime() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_STARTTIME);
	}

	/**
	 * An uncommented item
	 */
	public void setGameinstEndtime(java.sql.Timestamp value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_ENDTIME, value);
	}

	/**
	 * An uncommented item
	 */
	public java.sql.Timestamp getGameinstEndtime() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_ENDTIME);
	}

	/**
	 * An uncommented item
	 */
	public void setGameinstEntrytime(java.sql.Timestamp value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_ENTRYTIME, value);
	}

	/**
	 * An uncommented item
	 */
	public java.sql.Timestamp getGameinstEntrytime() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_ENTRYTIME);
	}

	/**
	 * An uncommented item
	 */
	public void setGameinstCapacity(java.lang.Integer value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_CAPACITY, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.Integer getGameinstCapacity() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_CAPACITY);
	}

	/**
	 * An uncommented item
	 */
	public void setGameinstThreshold(java.lang.Integer value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_THRESHOLD, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.Integer getGameinstThreshold() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_THRESHOLD);
	}

	/**
	 * An uncommented item
	 */
	public void setGameinstParamnum1(java.lang.Integer value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMNUM1, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.Integer getGameinstParamnum1() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMNUM1);
	}

	/**
	 * An uncommented item
	 */
	public void setGameinstParamnum2(java.lang.Integer value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMNUM2, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.Integer getGameinstParamnum2() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMNUM2);
	}

	/**
	 * An uncommented item
	 */
	public void setGameinstParamnum3(java.lang.Integer value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMNUM3, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.Integer getGameinstParamnum3() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMNUM3);
	}

	/**
	 * An uncommented item
	 */
	public void setGameinstParamnum4(java.lang.Integer value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMNUM4, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.Integer getGameinstParamnum4() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMNUM4);
	}

	/**
	 * An uncommented item
	 */
	public void setGameinstParamnum5(java.lang.Integer value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMNUM5, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.Integer getGameinstParamnum5() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMNUM5);
	}

	/**
	 * An uncommented item
	 */
	public void setGameinstParamnum6(java.lang.Integer value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMNUM6, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.Integer getGameinstParamnum6() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMNUM6);
	}

	/**
	 * An uncommented item
	 */
	public void setGameinstParamnum7(java.lang.Integer value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMNUM7, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.Integer getGameinstParamnum7() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMNUM7);
	}

	/**
	 * An uncommented item
	 */
	public void setGameinstParamnum8(java.lang.Integer value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMNUM8, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.Integer getGameinstParamnum8() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMNUM8);
	}

	/**
	 * An uncommented item
	 */
	public void setGameinstParamnum9(java.lang.Integer value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMNUM9, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.Integer getGameinstParamnum9() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMNUM9);
	}

	/**
	 * An uncommented item
	 */
	public void setGameinstParamnum10(java.lang.Integer value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMNUM10, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.Integer getGameinstParamnum10() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMNUM10);
	}

	/**
	 * An uncommented item
	 */
	public void setGameinstParamdate1(java.sql.Timestamp value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMDATE1, value);
	}

	/**
	 * An uncommented item
	 */
	public java.sql.Timestamp getGameinstParamdate1() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMDATE1);
	}

	/**
	 * An uncommented item
	 */
	public void setGameinstParamdate2(java.sql.Timestamp value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMDATE2, value);
	}

	/**
	 * An uncommented item
	 */
	public java.sql.Timestamp getGameinstParamdate2() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMDATE2);
	}

	/**
	 * An uncommented item
	 */
	public void setGameinstParamdate3(java.sql.Timestamp value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMDATE3, value);
	}

	/**
	 * An uncommented item
	 */
	public java.sql.Timestamp getGameinstParamdate3() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMDATE3);
	}

	/**
	 * An uncommented item
	 */
	public void setGameinstParamdate4(java.sql.Timestamp value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMDATE4, value);
	}

	/**
	 * An uncommented item
	 */
	public java.sql.Timestamp getGameinstParamdate4() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMDATE4);
	}

	/**
	 * An uncommented item
	 */
	public void setGameinstParamdate5(java.sql.Timestamp value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMDATE5, value);
	}

	/**
	 * An uncommented item
	 */
	public java.sql.Timestamp getGameinstParamdate5() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMDATE5);
	}

	/**
	 * An uncommented item
	 */
	public void setGameinstParamvar1(java.lang.String value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMVAR1, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.String getGameinstParamvar1() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMVAR1);
	}

	/**
	 * An uncommented item
	 */
	public void setGameinstParamvar2(java.lang.String value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMVAR2, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.String getGameinstParamvar2() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMVAR2);
	}

	/**
	 * An uncommented item
	 */
	public void setGameinstParamvar3(java.lang.String value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMVAR3, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.String getGameinstParamvar3() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMVAR3);
	}

	/**
	 * An uncommented item
	 */
	public void setGameinstParamvar4(java.lang.String value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMVAR4, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.String getGameinstParamvar4() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMVAR4);
	}

	/**
	 * An uncommented item
	 */
	public void setGameinstParamvar5(java.lang.String value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMVAR5, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.String getGameinstParamvar5() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PARAMVAR5);
	}

	/**
	 * An uncommented item
	 */
	public void setCreateTime(java.sql.Timestamp value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.CREATE_TIME, value);
	}

	/**
	 * An uncommented item
	 */
	public java.sql.Timestamp getCreateTime() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.CREATE_TIME);
	}

	/**
	 * An uncommented item
	 */
	public void setUpdateTime(java.sql.Timestamp value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.UPDATE_TIME, value);
	}

	/**
	 * An uncommented item
	 */
	public java.sql.Timestamp getUpdateTime() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.UPDATE_TIME);
	}

	/**
	 * An uncommented item
	 */
	public void setZzgameinstStatus(java.lang.String value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.ZZGAMEINST_STATUS, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.String getZzgameinstStatus() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.ZZGAMEINST_STATUS);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [public.zzgameinst.gameinst_created_by]
	 * REFERENCES zzuser [public.zzuser.user_id]
	 * </pre></code>
	 */
	public void setGameinstCreatedBy(java.lang.String value) {
		setValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_CREATED_BY, value);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [public.zzgameinst.gameinst_created_by]
	 * REFERENCES zzuser [public.zzuser.user_id]
	 * </pre></code>
	 */
	public java.lang.String getGameinstCreatedBy() {
		return getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_CREATED_BY);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [public.zzgameinst.gameinst_created_by]
	 * REFERENCES zzuser [public.zzuser.user_id]
	 * </pre></code>
	 */
	public net.user1.union.zz.common.model.tables.records.ZzuserRecord fetchZzuser() {
		return create()
			.selectFrom(net.user1.union.zz.common.model.tables.Zzuser.ZZUSER)
			.where(net.user1.union.zz.common.model.tables.Zzuser.ZZUSER.USER_ID.equal(getValue(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_CREATED_BY)))
			.fetchOne();
	}

	/**
	 * Create a detached ZzgameinstRecord
	 */
	public ZzgameinstRecord() {
		super(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST);
	}
}