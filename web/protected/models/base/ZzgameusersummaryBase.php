<?php

/**
 * This is the model class for table "zzgameusersummary".
 *
 * The followings are the available columns in table 'zzgameusersummary':
 * @property string $user_summary_id
 * @property string $zz_gameinsttemp_id
 * @property string $user_id
 * @property integer $total_games_played
 * @property integer $total_game_won
 * @property integer $user_level
 * @property string $create_time
 * @property string $update_time
 * @property string $last_gameinst_id
 * @property string $last_gameinst_update_time
 *
 * The followings are the available model relations:
 * @property Zzgameinsttempl $zzGameinsttemp
 * @property Zzuser $user
 * @property Zzgameinst $lastGameinst
 */
class ZzgameusersummaryBase extends ActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return ZzgameusersummaryBase the static model class
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
		return 'zzgameusersummary';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('user_summary_id, zz_gameinsttemp_id, user_id, user_level', 'required'),
			array('total_games_played, total_game_won, user_level', 'numerical', 'integerOnly'=>true),
			array('user_summary_id, zz_gameinsttemp_id, user_id, last_gameinst_id', 'length', 'max'=>100),
			array('create_time, update_time, last_gameinst_update_time', 'safe'),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('user_summary_id, zz_gameinsttemp_id, user_id, total_games_played, total_game_won, user_level, create_time, update_time, last_gameinst_id, last_gameinst_update_time', 'safe', 'on'=>'search'),
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
			'zzGameinsttemp' => array(self::BELONGS_TO, 'Zzgameinsttempl', 'zz_gameinsttemp_id'),
			'user' => array(self::BELONGS_TO, 'Zzuser', 'user_id'),
			'lastGameinst' => array(self::BELONGS_TO, 'Zzgameinst', 'last_gameinst_id'),
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'user_summary_id' => 'User Summary',
			'zz_gameinsttemp_id' => 'Zz Gameinsttemp',
			'user_id' => 'User',
			'total_games_played' => 'Total Games Played',
			'total_game_won' => 'Total Game Won',
			'user_level' => 'User Level',
			'create_time' => 'Create Time',
			'update_time' => 'Update Time',
			'last_gameinst_id' => 'Last Gameinst',
			'last_gameinst_update_time' => 'Last Gameinst Update Time',
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

		$criteria->compare('user_summary_id',$this->user_summary_id,true);
		$criteria->compare('zz_gameinsttemp_id',$this->zz_gameinsttemp_id,true);
		$criteria->compare('user_id',$this->user_id,true);
		$criteria->compare('total_games_played',$this->total_games_played);
		$criteria->compare('total_game_won',$this->total_game_won);
		$criteria->compare('user_level',$this->user_level);
		$criteria->compare('create_time',$this->create_time,true);
		$criteria->compare('update_time',$this->update_time,true);
		$criteria->compare('last_gameinst_id',$this->last_gameinst_id,true);
		$criteria->compare('last_gameinst_update_time',$this->last_gameinst_update_time,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}