<?php

/**
 * This is the model class for table "zzgameinst".
 *
 * The followings are the available columns in table 'zzgameinst':
 * @property string $gameinst_id
 * @property string $gameinst_game_id
 * @property string $gameinst_product_id
 * @property string $gameinst_gameinsttempl_id
 * @property string $gameinst_starttime
 * @property string $gameinst_endtime
 * @property string $gameinst_entrytime
 * @property integer $gameinst_capacity
 * @property integer $gameinst_threshold
 * @property integer $gameinst_paramnum1
 * @property integer $gameinst_paramnum2
 * @property integer $gameinst_paramnum3
 * @property integer $gameinst_paramnum4
 * @property integer $gameinst_paramnum5
 * @property integer $gameinst_paramnum6
 * @property integer $gameinst_paramnum7
 * @property integer $gameinst_paramnum8
 * @property integer $gameinst_paramnum9
 * @property integer $gameinst_paramnum10
 * @property string $gameinst_paramdate1
 * @property string $gameinst_paramdate2
 * @property string $gameinst_paramdate3
 * @property string $gameinst_paramdate4
 * @property string $gameinst_paramdate5
 * @property string $gameinst_paramvar1
 * @property string $gameinst_paramvar2
 * @property string $gameinst_paramvar3
 * @property string $gameinst_paramvar4
 * @property string $gameinst_paramvar5
 * @property string $create_time
 * @property string $update_time
 *
 * The followings are the available model relations:
 * @property Zzgameseat[] $zzgameseats
 * @property Zzgame $gameinstGame
 * @property Zzproduct $gameinstProduct
 * @property Zzgameinsttempl $gameinstGameinsttempl
 * @property Zzzlrogameinstfig[] $zzzlrogameinstfigs
 * @property Zzzlrogameround[] $zzzlrogamerounds
 */
class Zzgameinst extends ZzgameinstBase
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return Zzgameinst the static model class
	 */
	public static function model($className=__CLASS__)
	{
		return parent::model($className);
	}

}