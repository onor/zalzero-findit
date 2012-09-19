<?php

/**
 * This is the model class for table "zzgameseat".
 *
 * The followings are the available columns in table 'zzgameseat':
 * @property string $gameseat_id
 * @property string $gameseat_user_id
 * @property string $gameseat_gameinst_id
 * @property string $gameseat_createdat
 * @property string $create_time
 * @property string $update_time
 *
 * The followings are the available model relations:
 * @property Zzuser $gameseatUser
 * @property Zzgameinst $gameseatGameinst
 * @property Zzzlrogamescore[] $zzzlrogamescores
 * @property Zzzlrogamebet[] $zzzlrogamebets
 */
class Zzgameseat extends ZzgameseatBase
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return Zzgameseat the static model class
	 */
	public static function model($className=__CLASS__)
	{
		return parent::model($className);
	}

}