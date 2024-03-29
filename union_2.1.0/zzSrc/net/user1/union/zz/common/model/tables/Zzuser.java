/**
 * This class is generated by jOOQ
 */
package net.user1.union.zz.common.model.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = {"http://www.jooq.org", "2.0.0"},
                            comments = "This class is generated by jOOQ")
public class Zzuser extends org.jooq.impl.UpdatableTableImpl<net.user1.union.zz.common.model.tables.records.ZzuserRecord> {

	private static final long serialVersionUID = 998974914;

	/**
	 * The singleton instance of zzuser
	 */
	public static final net.user1.union.zz.common.model.tables.Zzuser ZZUSER = new net.user1.union.zz.common.model.tables.Zzuser();

	/**
	 * The class holding records for this type
	 */
	private static final java.lang.Class<net.user1.union.zz.common.model.tables.records.ZzuserRecord> __RECORD_TYPE = net.user1.union.zz.common.model.tables.records.ZzuserRecord.class;

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<net.user1.union.zz.common.model.tables.records.ZzuserRecord> getRecordType() {
		return __RECORD_TYPE;
	}

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzuserRecord, java.lang.String> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzuserRecord, java.lang.String> USER_NAME = createField("user_name", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzuserRecord, java.lang.String> USER_EMAIL = createField("user_email", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzuserRecord, java.lang.String> USER_FBID = createField("user_fbid", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzuserRecord, java.lang.String> USER_PASSWORD = createField("user_password", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzuserRecord, java.lang.String> USER_HANDLE = createField("user_handle", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzuserRecord, java.sql.Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzuserRecord, java.sql.Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [public.zzuser.user_role_id]
	 * REFERENCES roles [public.roles.role_id]
	 * </pre></code>
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzuserRecord, java.lang.String> USER_ROLE_ID = createField("user_role_id", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzuserRecord, java.lang.String> USER_FNAME = createField("user_fname", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzuserRecord, java.lang.String> USER_LNAME = createField("user_lname", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<net.user1.union.zz.common.model.tables.records.ZzuserRecord, java.lang.String> ZZUSER_STATUS = createField("zzuser_status", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * No further instances allowed
	 */
	private Zzuser() {
		super("zzuser", net.user1.union.zz.common.model.Public.PUBLIC);
	}

	/**
	 * No further instances allowed
	 */
	private Zzuser(java.lang.String alias) {
		super(alias, net.user1.union.zz.common.model.Public.PUBLIC, net.user1.union.zz.common.model.tables.Zzuser.ZZUSER);
	}

	@Override
	public org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzuserRecord> getMainKey() {
		return net.user1.union.zz.common.model.Keys.zzuser_pkey;
	}

	@Override
	@SuppressWarnings("unchecked")
	public java.util.List<org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzuserRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzuserRecord>>asList(net.user1.union.zz.common.model.Keys.zzuser_pkey, net.user1.union.zz.common.model.Keys.zzuser_user_email_key, net.user1.union.zz.common.model.Keys.zzuser_user_fbid_key);
	}

	@Override
	@SuppressWarnings("unchecked")
	public java.util.List<org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzuserRecord, ?>> getReferences() {
		return java.util.Arrays.<org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzuserRecord, ?>>asList(net.user1.union.zz.common.model.Keys.zzuser_user_role_id_fkey);
	}

	@Override
	public net.user1.union.zz.common.model.tables.Zzuser as(java.lang.String alias) {
		return new net.user1.union.zz.common.model.tables.Zzuser(alias);
	}
}
