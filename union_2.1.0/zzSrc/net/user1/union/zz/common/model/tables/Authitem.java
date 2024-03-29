/**
 * This class is generated by jOOQ
 */
package net.user1.union.zz.common.model.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = {"http://www.jooq.org", "2.0.0"},
                            comments = "This class is generated by jOOQ")
public class Authitem extends org.jooq.impl.UpdatableTableImpl<net.user1.union.zz.common.model.tables.records.AuthitemRecord> {

	private static final long serialVersionUID = -682041988;

	/**
	 * The singleton instance of authitem
	 */
	public static final net.user1.union.zz.common.model.tables.Authitem AUTHITEM = new net.user1.union.zz.common.model.tables.Authitem();

	/**
	 * The class holding records for this type
	 */
	private static final java.lang.Class<net.user1.union.zz.common.model.tables.records.AuthitemRecord> __RECORD_TYPE = net.user1.union.zz.common.model.tables.records.AuthitemRecord.class;

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<net.user1.union.zz.common.model.tables.records.AuthitemRecord> getRecordType() {
		return __RECORD_TYPE;
	}

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.AuthitemRecord, java.lang.String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.AuthitemRecord, java.lang.Integer> TYPE = createField("type", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.AuthitemRecord, java.lang.String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.CLOB, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.AuthitemRecord, java.lang.String> BIZRULE = createField("bizrule", org.jooq.impl.SQLDataType.CLOB, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.AuthitemRecord, java.lang.String> DATA = createField("data", org.jooq.impl.SQLDataType.CLOB, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.AuthitemRecord, java.sql.Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.AuthitemRecord, java.sql.Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * No further instances allowed
	 */
	private Authitem() {
		super("authitem", net.user1.union.zz.common.model.Public.PUBLIC);
	}

	/**
	 * No further instances allowed
	 */
	private Authitem(java.lang.String alias) {
		super(alias, net.user1.union.zz.common.model.Public.PUBLIC, net.user1.union.zz.common.model.tables.Authitem.AUTHITEM);
	}

	@Override
	public org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.AuthitemRecord> getMainKey() {
		return net.user1.union.zz.common.model.Keys.authitem_pkey;
	}

	@Override
	@SuppressWarnings("unchecked")
	public java.util.List<org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.AuthitemRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.AuthitemRecord>>asList(net.user1.union.zz.common.model.Keys.authitem_pkey);
	}

	@Override
	public net.user1.union.zz.common.model.tables.Authitem as(java.lang.String alias) {
		return new net.user1.union.zz.common.model.tables.Authitem(alias);
	}
}
