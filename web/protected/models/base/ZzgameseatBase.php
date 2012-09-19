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
 * @property integer $game_rank
 * @property string $zzgameseat_status
 * @property integer $gameseat_score
 * @property string $gameseat_status_update_time
 *
 * The followings are the available model relations:
 * @property Zzzlrogamescore[] $zzzlrogamescores
 * @property Zzzlrogamebet[] $zzzlrogamebets
 * @property Zzgameinst $gameseatGameinst
 * @property Zzuser $gameseatUser
 */
class ZzgameseatBase extends ActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return ZzgameseatBase the static model class
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
		return 'zzgameseat';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('gameseat_id, gameseat_user_id, gameseat_gameinst_id', 'required'),
			array('game_rank, gameseat_score', 'numerical', 'integerOnly'=>true),
			array('gameseat_id, gameseat_user_id, gameseat_gameinst_id', 'length', 'max'=>100),
			array('zzgameseat_status', 'length', 'max'=>150),
			array('gameseat_createdat, create_time, update_time, gameseat_status_update_time', 'safe'),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('gameseat_id, gameseat_user_id, gameseat_gameinst_id, gameseat_createdat, create_time, update_time, game_rank, zzgameseat_status, gameseat_score, gameseat_status_update_time', 'safe', 'on'=>'search'),
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
			'zzzlrogamescores' => array(self::HAS_MANY, 'Zzzlrogamescore', 'zlgamescore_gameseat_id'),
			'zzzlrogamebets' => array(self::HAS_MANY, 'Zzzlrogamebet', 'zlgamebet_gameseat_id'),
			'gameseatGameinst' => array(self::BELONGS_TO, 'Zzgameinst', 'gameseat_gameinst_id'),
			'gameseatUser' => array(self::BELONGS_TO, 'Zzuser', 'gameseat_user_id'),
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'gameseat_id' => 'Gameseat',
			'gameseat_user_id' => 'Gameseat User',
			'gameseat_gameinst_id' => 'Gameseat Gameinst',
			'gameseat_createdat' => 'Gameseat Createdat',
			'create_time' => 'Create Time',
			'update_time' => 'Update Time',
			'game_rank' => 'Game Rank',
			'zzgameseat_status' => 'Zzgameseat Status',
			'gameseat_score' => 'Gameseat Score',
			'gameseat_status_update_time' => 'Gameseat Status Update Time',
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

		$criteria->compare('gameseat_id',$this->gameseat_id,true);
		$criteria->compare('gameseat_user_id',$this->gameseat_user_id,true);
		$criteria->compare('gameseat_gameinst_id',$this->gameseat_gameinst_id,true);
		$criteria->compare('gameseat_createdat',$this->gameseat_createdat,true);
		$criteria->compare('create_time',$this->create_time,true);
		$criteria->compare('update_time',$this->update_time,true);
		$criteria->compare('game_rank',$this->game_rank);
		$criteria->compare('zzgameseat_status',$this->zzgameseat_status,true);
		$criteria->compare('gameseat_score',$this->gameseat_score);
		$criteria->compare('gameseat_status_update_time',$this->gameseat_status_update_time,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}