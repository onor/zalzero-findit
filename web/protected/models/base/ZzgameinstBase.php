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
 * @property string $zzgameinst_status
 * @property string $gameinst_created_by
 *
 * The followings are the available model relations:
 * @property Zzgameseat[] $zzgameseats
 * @property Zzgame $gameinstGame
 * @property Zzgameinsttempl $gameinstGameinsttempl
 * @property Zzproduct $gameinstProduct
 * @property Zzuser $gameinstCreatedBy
 * @property Zzzlrogameround[] $zzzlrogamerounds
 * @property Zzzlrogameinstfig[] $zzzlrogameinstfigs
 * @property Zzgameusersummary[] $zzgameusersummaries
 */
class ZzgameinstBase extends ActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return ZzgameinstBase the static model class
	 */
	public static function model($className=__CLASS__)
	{
		return parent::model($className);
	}

	/**
	 * @return string the associated database table name
	 */
	public function tableName()
	{
		return 'zzgameinst';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('gameinst_id, gameinst_game_id, gameinst_product_id, gameinst_gameinsttempl_id', 'required'),
			array('gameinst_capacity, gameinst_threshold, gameinst_paramnum1, gameinst_paramnum2, gameinst_paramnum3, gameinst_paramnum4, gameinst_paramnum5, gameinst_paramnum6, gameinst_paramnum7, gameinst_paramnum8, gameinst_paramnum9, gameinst_paramnum10', 'numerical', 'integerOnly'=>true),
			array('gameinst_id, gameinst_game_id, gameinst_product_id, gameinst_gameinsttempl_id, gameinst_created_by', 'length', 'max'=>100),
			array('gameinst_paramvar1, gameinst_paramvar2, gameinst_paramvar3, gameinst_paramvar4, gameinst_paramvar5, zzgameinst_status', 'length', 'max'=>150),
			array('gameinst_starttime, gameinst_endtime, gameinst_entrytime, gameinst_paramdate1, gameinst_paramdate2, gameinst_paramdate3, gameinst_paramdate4, gameinst_paramdate5, create_time, update_time', 'safe'),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('gameinst_id, gameinst_game_id, gameinst_product_id, gameinst_gameinsttempl_id, gameinst_starttime, gameinst_endtime, gameinst_entrytime, gameinst_capacity, gameinst_threshold, gameinst_paramnum1, gameinst_paramnum2, gameinst_paramnum3, gameinst_paramnum4, gameinst_paramnum5, gameinst_paramnum6, gameinst_paramnum7, gameinst_paramnum8, gameinst_paramnum9, gameinst_paramnum10, gameinst_paramdate1, gameinst_paramdate2, gameinst_paramdate3, gameinst_paramdate4, gameinst_paramdate5, gameinst_paramvar1, gameinst_paramvar2, gameinst_paramvar3, gameinst_paramvar4, gameinst_paramvar5, create_time, update_time, zzgameinst_status, gameinst_created_by', 'safe', 'on'=>'search'),
		);
	}

	/**
	 * @return array relational rules.
	 */
	public function relations()
	{
		// NOTE: you may need to adjust the relation name and the related
		// class name for the relations automatically generated below.
		return array(
			'zzgameseats' => array(self::HAS_MANY, 'Zzgameseat', 'gameseat_gameinst_id'),
			'gameinstGame' => array(self::BELONGS_TO, 'Zzgame', 'gameinst_game_id'),
			'gameinstGameinsttempl' => array(self::BELONGS_TO, 'Zzgameinsttempl', 'gameinst_gameinsttempl_id'),
			'gameinstProduct' => array(self::BELONGS_TO, 'Zzproduct', 'gameinst_product_id'),
			'gameinstCreatedBy' => array(self::BELONGS_TO, 'Zzuser', 'gameinst_created_by'),
			'zzzlrogamerounds' => array(self::HAS_MANY, 'Zzzlrogameround', 'zlgameround_gameinst_id'),
			'zzzlrogameinstfigs' => array(self::HAS_MANY, 'Zzzlrogameinstfig', 'zlgameinstfig_gameinst_id'),
			'zzgameusersummaries' => array(self::HAS_MANY, 'Zzgameusersummary', 'last_gameinst_id'),
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'gameinst_id' => 'Gameinst',
			'gameinst_game_id' => 'Gameinst Game',
			'gameinst_product_id' => 'Gameinst Product',
			'gameinst_gameinsttempl_id' => 'Gameinst Gameinsttempl',
			'gameinst_starttime' => 'Gameinst Starttime',
			'gameinst_endtime' => 'Gameinst Endtime',
			'gameinst_entrytime' => 'Gameinst Entrytime',
			'gameinst_capacity' => 'Gameinst Capacity',
			'gameinst_threshold' => 'Gameinst Threshold',
			'gameinst_paramnum1' => 'Gameinst Paramnum1',
			'gameinst_paramnum2' => 'Gameinst Paramnum2',
			'gameinst_paramnum3' => 'Gameinst Paramnum3',
			'gameinst_paramnum4' => 'Gameinst Paramnum4',
			'gameinst_paramnum5' => 'Gameinst Paramnum5',
			'gameinst_paramnum6' => 'Gameinst Paramnum6',
			'gameinst_paramnum7' => 'Gameinst Paramnum7',
			'gameinst_paramnum8' => 'Gameinst Paramnum8',
			'gameinst_paramnum9' => 'Gameinst Paramnum9',
			'gameinst_paramnum10' => 'Gameinst Paramnum10',
			'gameinst_paramdate1' => 'Gameinst Paramdate1',
			'gameinst_paramdate2' => 'Gameinst Paramdate2',
			'gameinst_paramdate3' => 'Gameinst Paramdate3',
			'gameinst_paramdate4' => 'Gameinst Paramdate4',
			'gameinst_paramdate5' => 'Gameinst Paramdate5',
			'gameinst_paramvar1' => 'Gameinst Paramvar1',
			'gameinst_paramvar2' => 'Gameinst Paramvar2',
			'gameinst_paramvar3' => 'Gameinst Paramvar3',
			'gameinst_paramvar4' => 'Gameinst Paramvar4',
			'gameinst_paramvar5' => 'Gameinst Paramvar5',
			'create_time' => 'Create Time',
			'update_time' => 'Update Time',
			'zzgameinst_status' => 'Zzgameinst Status',
			'gameinst_created_by' => 'Gameinst Created By',
		);
	}

	/**
	 * Retrieves a list of models based on the current search/filter conditions.
	 * @return CActiveDataProvider the data provider that can return the models based on the search/filter conditions.
	 */
	public function search()
	{
		// Warning: Please modify the following code to remove attributes that
		// should not be searched.

		$criteria=new CDbCriteria;

		$criteria->compare('gameinst_id',$this->gameinst_id,true);
		$criteria->compare('gameinst_game_id',$this->gameinst_game_id,true);
		$criteria->compare('gameinst_product_id',$this->gameinst_product_id,true);
		$criteria->compare('gameinst_gameinsttempl_id',$this->gameinst_gameinsttempl_id,true);
		$criteria->compare('gameinst_starttime',$this->gameinst_starttime,true);
		$criteria->compare('gameinst_endtime',$this->gameinst_endtime,true);
		$criteria->compare('gameinst_entrytime',$this->gameinst_entrytime,true);
		$criteria->compare('gameinst_capacity',$this->gameinst_capacity);
		$criteria->compare('gameinst_threshold',$this->gameinst_threshold);
		$criteria->compare('gameinst_paramnum1',$this->gameinst_paramnum1);
		$criteria->compare('gameinst_paramnum2',$this->gameinst_paramnum2);
		$criteria->compare('gameinst_paramnum3',$this->gameinst_paramnum3);
		$criteria->compare('gameinst_paramnum4',$this->gameinst_paramnum4);
		$criteria->compare('gameinst_paramnum5',$this->gameinst_paramnum5);
		$criteria->compare('gameinst_paramnum6',$this->gameinst_paramnum6);
		$criteria->compare('gameinst_paramnum7',$this->gameinst_paramnum7);
		$criteria->compare('gameinst_paramnum8',$this->gameinst_paramnum8);
		$criteria->compare('gameinst_paramnum9',$this->gameinst_paramnum9);
		$criteria->compare('gameinst_paramnum10',$this->gameinst_paramnum10);
		$criteria->compare('gameinst_paramdate1',$this->gameinst_paramdate1,true);
		$criteria->compare('gameinst_paramdate2',$this->gameinst_paramdate2,true);
		$criteria->compare('gameinst_paramdate3',$this->gameinst_paramdate3,true);
		$criteria->compare('gameinst_paramdate4',$this->gameinst_paramdate4,true);
		$criteria->compare('gameinst_paramdate5',$this->gameinst_paramdate5,true);
		$criteria->compare('gameinst_paramvar1',$this->gameinst_paramvar1,true);
		$criteria->compare('gameinst_paramvar2',$this->gameinst_paramvar2,true);
		$criteria->compare('gameinst_paramvar3',$this->gameinst_paramvar3,true);
		$criteria->compare('gameinst_paramvar4',$this->gameinst_paramvar4,true);
		$criteria->compare('gameinst_paramvar5',$this->gameinst_paramvar5,true);
		$criteria->compare('create_time',$this->create_time,true);
		$criteria->compare('update_time',$this->update_time,true);
		$criteria->compare('zzgameinst_status',$this->zzgameinst_status,true);
		$criteria->compare('gameinst_created_by',$this->gameinst_created_by,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}