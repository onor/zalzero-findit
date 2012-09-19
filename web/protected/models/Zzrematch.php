<?php

/**
 * This is the model class for table "zzrematch".
 *
 * The followings are the available columns in table 'zzrematch':
 * @property string $rematch_id
 * @property string $gameinst_id
 * @property string $game_seat_id
 * @property string $rematch_status
 * @property string $new_game_seat_id
 *
 * The followings are the available model relations:
 * @property Zzgameseat $gameSeat
 * @property Zzgameinst $gameinst
 */
class Zzrematch extends CActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return Zzrematch the static model class
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
		return 'zzrematch';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('rematch_id', 'required'),
			array('rematch_id, gameinst_id, game_seat_id, new_game_seat_id', 'length', 'max'=>100),
			array('rematch_status', 'length', 'max'=>20),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('rematch_id, gameinst_id, game_seat_id, rematch_status, new_game_seat_id', 'safe', 'on'=>'search'),
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
			'gameSeat' => array(self::BELONGS_TO, 'Zzgameseat', 'game_seat_id'),
			'gameinst' => array(self::BELONGS_TO, 'Zzgameinst', 'gameinst_id'),
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'rematch_id' => 'Rematch',
			'gameinst_id' => 'Gameinst',
			'game_seat_id' => 'Game Seat',
			'rematch_status' => 'Rematch Status',
			'new_game_seat_id' => 'New Game Seat',
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

		$criteria->compare('rematch_id',$this->rematch_id,true);
		$criteria->compare('gameinst_id',$this->gameinst_id,true);
		$criteria->compare('game_seat_id',$this->game_seat_id,true);
		$criteria->compare('rematch_status',$this->rematch_status,true);
		$criteria->compare('new_game_seat_id',$this->new_game_seat_id,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}