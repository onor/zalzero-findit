<?php

/**
 * This is the model class for table "zzgame".
 *
 * The followings are the available columns in table 'zzgame':
 * @property string $game_id
 * @property string $game_name
 * @property string $create_time
 * @property string $update_time
 *
 * The followings are the available model relations:
 * @property Zzgameinst[] $zzgameinsts
 * @property Zzgameinsttempl[] $zzgameinsttempls
 */
class Zzgame extends ZzgameBase
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return Zzgame the static model class
	 */
	public static function model($className=__CLASS__)
	{
		return parent::model($className);
	}

}