<?php

/**
 * This is the model class for table "zzzlrofiginst".
 *
 * The followings are the available columns in table 'zzzlrofiginst':
 * @property string $zlfiginst_id
 * @property string $zlfiginst_zlfig_id
 * @property integer $zlfiginst_valid
 * @property integer $zlfiginst_noofcolumns
 * @property integer $zlfiginst_noofrows
 * @property string $zlfiginst_orientation
 * @property string $create_time
 * @property string $update_time
 *
 * The followings are the available model relations:
 * @property ZzzlrofigBase $zlfiginstZlfig
 * @property ZzzlrofiginstcoordBase[] $zzzlrofiginstcoords
 * @property ZzzlrogameinstfigBase[] $zzzlrogameinstfigs
 */
class ZzzlrofiginstBase extends ActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return ZzzlrofiginstBase the static model class
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
		return 'zzzlrofiginst';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('zlfiginst_id, zlfiginst_zlfig_id', 'required'),
			array('zlfiginst_valid, zlfiginst_noofcolumns, zlfiginst_noofrows', 'numerical', 'integerOnly'=>true),
			array('zlfiginst_id, zlfiginst_zlfig_id', 'length', 'max'=>100),
			array('zlfiginst_orientation', 'length', 'max'=>1),
			array('create_time, update_time', 'safe'),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('zlfiginst_id, zlfiginst_zlfig_id, zlfiginst_valid, zlfiginst_noofcolumns, zlfiginst_noofrows, zlfiginst_orientation, create_time, update_time', 'safe', 'on'=>'search'),
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
			'zlfiginstZlfig' => array(self::BELONGS_TO, 'ZzzlrofigBase', 'zlfiginst_zlfig_id'),
			'zzzlrofiginstcoords' => array(self::HAS_MANY, 'ZzzlrofiginstcoordBase', 'zlfiginstcoord_zlfiginst_id'),
			'zzzlrogameinstfigs' => array(self::HAS_MANY, 'ZzzlrogameinstfigBase', 'zlgameinstfig_zlfiginst_id'),
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'zlfiginst_id' => 'Zlfiginst',
			'zlfiginst_zlfig_id' => 'Zlfiginst Zlfig',
			'zlfiginst_valid' => 'Zlfiginst Valid',
			'zlfiginst_noofcolumns' => 'Zlfiginst Noofcolumns',
			'zlfiginst_noofrows' => 'Zlfiginst Noofrows',
			'zlfiginst_orientation' => 'Zlfiginst Orientation',
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

		$criteria->compare('zlfiginst_id',$this->zlfiginst_id,true);
		$criteria->compare('zlfiginst_zlfig_id',$this->zlfiginst_zlfig_id,true);
		$criteria->compare('zlfiginst_valid',$this->zlfiginst_valid);
		$criteria->compare('zlfiginst_noofcolumns',$this->zlfiginst_noofcolumns);
		$criteria->compare('zlfiginst_noofrows',$this->zlfiginst_noofrows);
		$criteria->compare('zlfiginst_orientation',$this->zlfiginst_orientation,true);
		$criteria->compare('create_time',$this->create_time,true);
		$criteria->compare('update_time',$this->update_time,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}