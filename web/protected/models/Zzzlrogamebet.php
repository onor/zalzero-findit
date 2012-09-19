<?php

/**
 * This is the model class for table "zzzlrogamebet".
 *
 * The followings are the available columns in table 'zzzlrogamebet':
 * @property string $zlgamebet_id
 * @property string $zlgamebet_zlgameround_id
 * @property string $zlgamebet_gameseat_id
 * @property string $zlgamebet_bettime
 * @property integer $zlgamebet_betcoord
 * @property string $create_time
 * @property string $update_time
 *
 * The followings are the available model relations:
 * @property Zzzlrogameround $zlgamebetZlgameround
 * @property Zzgameseat $zlgamebetGameseat
 */
class Zzzlrogamebet extends ZzzlrogamebetBase
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return Zzzlrogamebet the static model class
	 */
	public static function model($className=__CLASS__)
	{
		return parent::model($className);
	}
}