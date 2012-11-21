/**
 * This class is generated by jOOQ
 */
package net.user1.union.zz.common.model;

/**
 * This class is generated by jOOQ.
 *
 * A class modelling foreign key relationships between tables of the public schema
 */
@javax.annotation.Generated(value    = {"http://www.jooq.org", "2.0.0"},
                            comments = "This class is generated by jOOQ")
@SuppressWarnings({"unchecked"})
public class Keys extends org.jooq.impl.AbstractKeys {

	// IDENTITY definitions
	public static final org.jooq.Identity<net.user1.union.zz.common.model.tables.records.ZzinvitefriendsRecord, java.lang.String> IDENTITY_zzinvitefriends = createIdentity(net.user1.union.zz.common.model.tables.Zzinvitefriends.ZZINVITEFRIENDS, net.user1.union.zz.common.model.tables.Zzinvitefriends.ZZINVITEFRIENDS.ID);

	// UNIQUE and PRIMARY KEY definitions
	public static final org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.AuthassignmentRecord> authassignment_pkey = createUniqueKey(net.user1.union.zz.common.model.tables.Authassignment.AUTHASSIGNMENT, net.user1.union.zz.common.model.tables.Authassignment.AUTHASSIGNMENT.ITEMNAME, net.user1.union.zz.common.model.tables.Authassignment.AUTHASSIGNMENT.USERID);
	public static final org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.AuthitemRecord> authitem_pkey = createUniqueKey(net.user1.union.zz.common.model.tables.Authitem.AUTHITEM, net.user1.union.zz.common.model.tables.Authitem.AUTHITEM.NAME);
	public static final org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.AuthitemchildRecord> authitemchild_pkey = createUniqueKey(net.user1.union.zz.common.model.tables.Authitemchild.AUTHITEMCHILD, net.user1.union.zz.common.model.tables.Authitemchild.AUTHITEMCHILD.PARENT, net.user1.union.zz.common.model.tables.Authitemchild.AUTHITEMCHILD.CHILD);
	public static final org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.RolesRecord> roles_pkey = createUniqueKey(net.user1.union.zz.common.model.tables.Roles.ROLES, net.user1.union.zz.common.model.tables.Roles.ROLES.ROLE_ID);
	public static final org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzcronRecord> zzcron_primary_key = createUniqueKey(net.user1.union.zz.common.model.tables.Zzcron.ZZCRON, net.user1.union.zz.common.model.tables.Zzcron.ZZCRON.CRON_ID);
	public static final org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzgameRecord> zzgame_pkey = createUniqueKey(net.user1.union.zz.common.model.tables.Zzgame.ZZGAME, net.user1.union.zz.common.model.tables.Zzgame.ZZGAME.GAME_ID);
	public static final org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord> zzgameinst_pkey = createUniqueKey(net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST, net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_ID);
	public static final org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzgameinsttemplRecord> zzgameinsttempl_pkey = createUniqueKey(net.user1.union.zz.common.model.tables.Zzgameinsttempl.ZZGAMEINSTTEMPL, net.user1.union.zz.common.model.tables.Zzgameinsttempl.ZZGAMEINSTTEMPL.GAMEINSTTEMPL_ID);
	public static final org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzgameseatRecord> zzgameseat_pkey = createUniqueKey(net.user1.union.zz.common.model.tables.Zzgameseat.ZZGAMESEAT, net.user1.union.zz.common.model.tables.Zzgameseat.ZZGAMESEAT.GAMESEAT_ID);
	public static final org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzgameusersummaryRecord> zzuser_summary = createUniqueKey(net.user1.union.zz.common.model.tables.Zzgameusersummary.ZZGAMEUSERSUMMARY, net.user1.union.zz.common.model.tables.Zzgameusersummary.ZZGAMEUSERSUMMARY.USER_SUMMARY_ID);
	public static final org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzinvitefriendsRecord> id = createUniqueKey(net.user1.union.zz.common.model.tables.Zzinvitefriends.ZZINVITEFRIENDS, net.user1.union.zz.common.model.tables.Zzinvitefriends.ZZINVITEFRIENDS.ID);
	public static final org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzproductRecord> zzproduct_pkey = createUniqueKey(net.user1.union.zz.common.model.tables.Zzproduct.ZZPRODUCT, net.user1.union.zz.common.model.tables.Zzproduct.ZZPRODUCT.PRODUCT_ID);
	public static final org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzuserRecord> zzuser_pkey = createUniqueKey(net.user1.union.zz.common.model.tables.Zzuser.ZZUSER, net.user1.union.zz.common.model.tables.Zzuser.ZZUSER.USER_ID);
	public static final org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzuserRecord> zzuser_user_email_key = createUniqueKey(net.user1.union.zz.common.model.tables.Zzuser.ZZUSER, net.user1.union.zz.common.model.tables.Zzuser.ZZUSER.USER_EMAIL);
	public static final org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzuserRecord> zzuser_user_fbid_key = createUniqueKey(net.user1.union.zz.common.model.tables.Zzuser.ZZUSER, net.user1.union.zz.common.model.tables.Zzuser.ZZUSER.USER_FBID);
	public static final org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzzlrofigRecord> zzzlrofig_pkey = createUniqueKey(net.user1.union.zz.common.model.tables.Zzzlrofig.ZZZLROFIG, net.user1.union.zz.common.model.tables.Zzzlrofig.ZZZLROFIG.ZLFIG_ID);
	public static final org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzzlrofigcoordRecord> zzzlrofigcoord_pkey = createUniqueKey(net.user1.union.zz.common.model.tables.Zzzlrofigcoord.ZZZLROFIGCOORD, net.user1.union.zz.common.model.tables.Zzzlrofigcoord.ZZZLROFIGCOORD.ZLFIGCOORD_ID);
	public static final org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzzlrofiggroupRecord> zzzlrofiggroup_pkey = createUniqueKey(net.user1.union.zz.common.model.tables.Zzzlrofiggroup.ZZZLROFIGGROUP, net.user1.union.zz.common.model.tables.Zzzlrofiggroup.ZZZLROFIGGROUP.ZLFIGGROUP_ID);
	public static final org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzzlrofiginstRecord> zzzlrofiginst_pkey = createUniqueKey(net.user1.union.zz.common.model.tables.Zzzlrofiginst.ZZZLROFIGINST, net.user1.union.zz.common.model.tables.Zzzlrofiginst.ZZZLROFIGINST.ZLFIGINST_ID);
	public static final org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzzlrofiginstcoordRecord> zzzlrofiginstcoord_pkey = createUniqueKey(net.user1.union.zz.common.model.tables.Zzzlrofiginstcoord.ZZZLROFIGINSTCOORD, net.user1.union.zz.common.model.tables.Zzzlrofiginstcoord.ZZZLROFIGINSTCOORD.ZLFIGINSTCOORD_ID);
	public static final org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzzlrofriendsRecord> zzzlrofriends_pkey = createUniqueKey(net.user1.union.zz.common.model.tables.Zzzlrofriends.ZZZLROFRIENDS, net.user1.union.zz.common.model.tables.Zzzlrofriends.ZZZLROFRIENDS.USER_ID, net.user1.union.zz.common.model.tables.Zzzlrofriends.ZZZLROFRIENDS.FRIEND_ID);
	public static final org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzzlrogamebetRecord> zzzlrogamebet_pkey = createUniqueKey(net.user1.union.zz.common.model.tables.Zzzlrogamebet.ZZZLROGAMEBET, net.user1.union.zz.common.model.tables.Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_ID);
	public static final org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzzlrogameinstfigRecord> zzzlrogameinstfig_pkey = createUniqueKey(net.user1.union.zz.common.model.tables.Zzzlrogameinstfig.ZZZLROGAMEINSTFIG, net.user1.union.zz.common.model.tables.Zzzlrogameinstfig.ZZZLROGAMEINSTFIG.ZLGAMEINSTFIG_ID);
	public static final org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzzlrogameroundRecord> zzzlrogameround_pkey = createUniqueKey(net.user1.union.zz.common.model.tables.Zzzlrogameround.ZZZLROGAMEROUND, net.user1.union.zz.common.model.tables.Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_ID);
	public static final org.jooq.UniqueKey<net.user1.union.zz.common.model.tables.records.ZzzlrogamescoreRecord> zzzlrogamescore_pkey = createUniqueKey(net.user1.union.zz.common.model.tables.Zzzlrogamescore.ZZZLROGAMESCORE, net.user1.union.zz.common.model.tables.Zzzlrogamescore.ZZZLROGAMESCORE.ZLGAMESCORE_ID);

	// FOREIGN KEY definitions
	public static final org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.AuthassignmentRecord, net.user1.union.zz.common.model.tables.records.AuthitemRecord> authassignment_itemname_fkey = createForeignKey(authitem_pkey, net.user1.union.zz.common.model.tables.Authassignment.AUTHASSIGNMENT, net.user1.union.zz.common.model.tables.Authassignment.AUTHASSIGNMENT.ITEMNAME);
	public static final org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.AuthitemchildRecord, net.user1.union.zz.common.model.tables.records.AuthitemRecord> authitemchild_parent_fkey = createForeignKey(authitem_pkey, net.user1.union.zz.common.model.tables.Authitemchild.AUTHITEMCHILD, net.user1.union.zz.common.model.tables.Authitemchild.AUTHITEMCHILD.PARENT);
	public static final org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.AuthitemchildRecord, net.user1.union.zz.common.model.tables.records.AuthitemRecord> authitemchild_child_fkey = createForeignKey(authitem_pkey, net.user1.union.zz.common.model.tables.Authitemchild.AUTHITEMCHILD, net.user1.union.zz.common.model.tables.Authitemchild.AUTHITEMCHILD.CHILD);
	public static final org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, net.user1.union.zz.common.model.tables.records.ZzgameRecord> zzgameinst_gameinst_game_id_fkey = createForeignKey(zzgame_pkey, net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST, net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_GAME_ID);
	public static final org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, net.user1.union.zz.common.model.tables.records.ZzproductRecord> zzgameinst_gameinst_product_id_fkey = createForeignKey(zzproduct_pkey, net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST, net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_PRODUCT_ID);
	public static final org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, net.user1.union.zz.common.model.tables.records.ZzgameinsttemplRecord> zzgameinst_gameinst_gameinsttempl_id_fkey = createForeignKey(zzgameinsttempl_pkey, net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST, net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_GAMEINSTTEMPL_ID);
	public static final org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzgameinstRecord, net.user1.union.zz.common.model.tables.records.ZzuserRecord> zzgame_created_by = createForeignKey(zzuser_pkey, net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST, net.user1.union.zz.common.model.tables.Zzgameinst.ZZGAMEINST.GAMEINST_CREATED_BY);
	public static final org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzgameinsttemplRecord, net.user1.union.zz.common.model.tables.records.ZzgameRecord> zzgameinsttempl_gameinsttempl_game_id_fkey = createForeignKey(zzgame_pkey, net.user1.union.zz.common.model.tables.Zzgameinsttempl.ZZGAMEINSTTEMPL, net.user1.union.zz.common.model.tables.Zzgameinsttempl.ZZGAMEINSTTEMPL.GAMEINSTTEMPL_GAME_ID);
	public static final org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzgameseatRecord, net.user1.union.zz.common.model.tables.records.ZzuserRecord> zzgameseat_gameseat_user_id_fkey = createForeignKey(zzuser_pkey, net.user1.union.zz.common.model.tables.Zzgameseat.ZZGAMESEAT, net.user1.union.zz.common.model.tables.Zzgameseat.ZZGAMESEAT.GAMESEAT_USER_ID);
	public static final org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzgameseatRecord, net.user1.union.zz.common.model.tables.records.ZzgameinstRecord> zzgameseat_gameseat_gameinst_id_fkey = createForeignKey(zzgameinst_pkey, net.user1.union.zz.common.model.tables.Zzgameseat.ZZGAMESEAT, net.user1.union.zz.common.model.tables.Zzgameseat.ZZGAMESEAT.GAMESEAT_GAMEINST_ID);
	public static final org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzgameusersummaryRecord, net.user1.union.zz.common.model.tables.records.ZzgameinsttemplRecord> zzgameinsttempl = createForeignKey(zzgameinsttempl_pkey, net.user1.union.zz.common.model.tables.Zzgameusersummary.ZZGAMEUSERSUMMARY, net.user1.union.zz.common.model.tables.Zzgameusersummary.ZZGAMEUSERSUMMARY.ZZ_GAMEINSTTEMP_ID);
	public static final org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzgameusersummaryRecord, net.user1.union.zz.common.model.tables.records.ZzuserRecord> zzuserfk = createForeignKey(zzuser_pkey, net.user1.union.zz.common.model.tables.Zzgameusersummary.ZZGAMEUSERSUMMARY, net.user1.union.zz.common.model.tables.Zzgameusersummary.ZZGAMEUSERSUMMARY.USER_ID);
	public static final org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzgameusersummaryRecord, net.user1.union.zz.common.model.tables.records.ZzgameinstRecord> last_gameinst_id_fkey = createForeignKey(zzgameinst_pkey, net.user1.union.zz.common.model.tables.Zzgameusersummary.ZZGAMEUSERSUMMARY, net.user1.union.zz.common.model.tables.Zzgameusersummary.ZZGAMEUSERSUMMARY.LAST_GAMEINST_ID);
	public static final org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzuserRecord, net.user1.union.zz.common.model.tables.records.RolesRecord> zzuser_user_role_id_fkey = createForeignKey(roles_pkey, net.user1.union.zz.common.model.tables.Zzuser.ZZUSER, net.user1.union.zz.common.model.tables.Zzuser.ZZUSER.USER_ROLE_ID);
	public static final org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzzlrofigRecord, net.user1.union.zz.common.model.tables.records.ZzzlrofiggroupRecord> zzzlrofig_zlfig_zlfiggroup_id_fkey = createForeignKey(zzzlrofiggroup_pkey, net.user1.union.zz.common.model.tables.Zzzlrofig.ZZZLROFIG, net.user1.union.zz.common.model.tables.Zzzlrofig.ZZZLROFIG.ZLFIG_ZLFIGGROUP_ID);
	public static final org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzzlrofigcoordRecord, net.user1.union.zz.common.model.tables.records.ZzzlrofigRecord> zzzlrofigcoord_zlfigcoord_zlfig_id_fkey = createForeignKey(zzzlrofig_pkey, net.user1.union.zz.common.model.tables.Zzzlrofigcoord.ZZZLROFIGCOORD, net.user1.union.zz.common.model.tables.Zzzlrofigcoord.ZZZLROFIGCOORD.ZLFIGCOORD_ZLFIG_ID);
	public static final org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzzlrofiginstRecord, net.user1.union.zz.common.model.tables.records.ZzzlrofigRecord> zzzlrofiginst_zlfiginst_zlfig_id_fkey = createForeignKey(zzzlrofig_pkey, net.user1.union.zz.common.model.tables.Zzzlrofiginst.ZZZLROFIGINST, net.user1.union.zz.common.model.tables.Zzzlrofiginst.ZZZLROFIGINST.ZLFIGINST_ZLFIG_ID);
	public static final org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzzlrofiginstcoordRecord, net.user1.union.zz.common.model.tables.records.ZzzlrofiginstRecord> zzzlrofiginstcoord_zlfiginstcoord_zlfiginst_id_fkey = createForeignKey(zzzlrofiginst_pkey, net.user1.union.zz.common.model.tables.Zzzlrofiginstcoord.ZZZLROFIGINSTCOORD, net.user1.union.zz.common.model.tables.Zzzlrofiginstcoord.ZZZLROFIGINSTCOORD.ZLFIGINSTCOORD_ZLFIGINST_ID);
	public static final org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzzlrofriendsRecord, net.user1.union.zz.common.model.tables.records.ZzuserRecord> zzzlrofriends_user_id_fkey = createForeignKey(zzuser_pkey, net.user1.union.zz.common.model.tables.Zzzlrofriends.ZZZLROFRIENDS, net.user1.union.zz.common.model.tables.Zzzlrofriends.ZZZLROFRIENDS.USER_ID);
	public static final org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzzlrogamebetRecord, net.user1.union.zz.common.model.tables.records.ZzzlrogameroundRecord> zzzlrogamebet_zlgamebet_zlgameround_id_fkey = createForeignKey(zzzlrogameround_pkey, net.user1.union.zz.common.model.tables.Zzzlrogamebet.ZZZLROGAMEBET, net.user1.union.zz.common.model.tables.Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_ZLGAMEROUND_ID);
	public static final org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzzlrogamebetRecord, net.user1.union.zz.common.model.tables.records.ZzgameseatRecord> zzzlrogamebet_zlgamebet_gameseat_id_fkey = createForeignKey(zzgameseat_pkey, net.user1.union.zz.common.model.tables.Zzzlrogamebet.ZZZLROGAMEBET, net.user1.union.zz.common.model.tables.Zzzlrogamebet.ZZZLROGAMEBET.ZLGAMEBET_GAMESEAT_ID);
	public static final org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzzlrogameinstfigRecord, net.user1.union.zz.common.model.tables.records.ZzzlrofiginstRecord> zzzlrogameinstfig_zlgameinstfig_zlfiginst_id_fkey = createForeignKey(zzzlrofiginst_pkey, net.user1.union.zz.common.model.tables.Zzzlrogameinstfig.ZZZLROGAMEINSTFIG, net.user1.union.zz.common.model.tables.Zzzlrogameinstfig.ZZZLROGAMEINSTFIG.ZLGAMEINSTFIG_ZLFIGINST_ID);
	public static final org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzzlrogameinstfigRecord, net.user1.union.zz.common.model.tables.records.ZzgameinstRecord> zzzlrogameinstfig_zlgameinstfig_gameinst_id_fkey = createForeignKey(zzgameinst_pkey, net.user1.union.zz.common.model.tables.Zzzlrogameinstfig.ZZZLROGAMEINSTFIG, net.user1.union.zz.common.model.tables.Zzzlrogameinstfig.ZZZLROGAMEINSTFIG.ZLGAMEINSTFIG_GAMEINST_ID);
	public static final org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzzlrogameroundRecord, net.user1.union.zz.common.model.tables.records.ZzgameinstRecord> zzzlrogameround_zlgameround_gameinst_id_fkey = createForeignKey(zzgameinst_pkey, net.user1.union.zz.common.model.tables.Zzzlrogameround.ZZZLROGAMEROUND, net.user1.union.zz.common.model.tables.Zzzlrogameround.ZZZLROGAMEROUND.ZLGAMEROUND_GAMEINST_ID);
	public static final org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzzlrogamescoreRecord, net.user1.union.zz.common.model.tables.records.ZzzlrogameroundRecord> zzzlrogamescore_zlgamescore_zlgameround_id_fkey = createForeignKey(zzzlrogameround_pkey, net.user1.union.zz.common.model.tables.Zzzlrogamescore.ZZZLROGAMESCORE, net.user1.union.zz.common.model.tables.Zzzlrogamescore.ZZZLROGAMESCORE.ZLGAMESCORE_ZLGAMEROUND_ID);
	public static final org.jooq.ForeignKey<net.user1.union.zz.common.model.tables.records.ZzzlrogamescoreRecord, net.user1.union.zz.common.model.tables.records.ZzgameseatRecord> zzzlrogamescore_zlgamescore_gameseat_id_fkey = createForeignKey(zzgameseat_pkey, net.user1.union.zz.common.model.tables.Zzzlrogamescore.ZZZLROGAMESCORE, net.user1.union.zz.common.model.tables.Zzzlrogamescore.ZZZLROGAMESCORE.ZLGAMESCORE_GAMESEAT_ID);

	/**
	 * No instances
	 */
	private Keys() {}
}
