<?php

/**
 * This is the model class for table "zzzlrogameinstfig".
 *
 * The followings are the available columns in table 'zzzlrogameinstfig':
 * @property string $zlgameinstfig_id
 * @property string $zlgameinstfig_zlfiginst_id
 * @property string $zlgameinstfig_gameinst_id
 * @property integer $zlgameinstfig_posleft
 * @property integer $zlgameinstfig_postop
 * @property string $create_time
 * @property string $update_time
 *
 * The followings are the available model relations:
 * @property ZzzlrofiginstBase $zlgameinstfigZlfiginst
 * @property ZzgameinstBase $zlgameinstfigGameinst
 */
class ZzzlrogameinstfigBase extends ActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return ZzzlrogameinstfigBase the static model class
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
		return 'zzzlrogameinstfig';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('zlgameinstfig_id, zlgameinstfig_zlfiginst_id, zlgameinstfig_gameinst_id, zlgameinstfig_posleft, zlgameinstfig_postop', 'required'),
			array('zlgameinstfig_posleft, zlgameinstfig_postop', 'numerical', 'integerOnly'=>true),
			array('zlgameinstfig_id, zlgameinstfig_zlfiginst_id, zlgameinstfig_gameinst_id', 'length', 'max'=>100),
			array('create_time, update_time', 'safe'),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('zlgameinstfig_id, zlgameinstfig_zlfiginst_id, zlgameinstfig_gameinst_id, zlgameinstfig_posleft, zlgameinstfig_postop, create_time, update_time', 'safe', 'on'=>'search'),
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
			'zlgameinstfigZlfiginst' => array(self::BELONGS_TO, 'ZzzlrofiginstBase', 'zlgameinstfig_zlfiginst_id'),
			'zlgameinstfigGameinst' => array(self::BELONGS_TO, 'ZzgameinstBase', 'zlgameinstfig_gameinst_id'),
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'zlgameinstfig_id' => 'Zlgameinstfig',
			'zlgameinstfig_zlfiginst_id' => 'Zlgameinstfig Zlfiginst',
			'zlgameinstfig_gameinst_id' => 'Zlgameinstfig Gameinst',
			'zlgameinstfig_posleft' => 'Zlgameinstfig Posleft',
			'zlgameinstfig_postop' => 'Zlgameinstfig Postop',
			'create_time' => 'Create Time',
			'update_time' => 'Update Time',
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

		$criteria->compare('zlgameinstfig_id',$this->zlgameinstfig_id,true);
		$criteria->compare('zlgameinstfig_zlfiginst_id',$this->zlgameinstfig_zlfiginst_id,true);
		$criteria->compare('zlgameinstfig_gameinst_id',$this->zlgameinstfig_gameinst_id,true);
		$criteria->compare('zlgameinstfig_posleft',$this->zlgameinstfig_posleft);
		$criteria->compare('zlgameinstfig_postop',$this->zlgameinstfig_postop);
		$criteria->compare('create_time',$this->create_time,true);
		$criteria->compare('update_time',$this->update_time,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}