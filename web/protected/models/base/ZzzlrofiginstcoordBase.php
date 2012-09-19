<?php

/**
 * This is the model class for table "zzzlrofiginstcoord".
 *
 * The followings are the available columns in table 'zzzlrofiginstcoord':
 * @property string $zlfiginstcoord_id
 * @property string $zlfiginstcoord_zlfiginst_id
 * @property integer $zlfiginstcoord_posx
 * @property integer $zlfiginstcoord_posy
 * @property string $create_time
 * @property string $update_time
 *
 * The followings are the available model relations:
 * @property ZzzlrofiginstBase $zlfiginstcoordZlfiginst
 */
class ZzzlrofiginstcoordBase extends ActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return ZzzlrofiginstcoordBase the static model class
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
		return 'zzzlrofiginstcoord';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('zlfiginstcoord_id, zlfiginstcoord_zlfiginst_id, zlfiginstcoord_posx, zlfiginstcoord_posy', 'required'),
			array('zlfiginstcoord_posx, zlfiginstcoord_posy', 'numerical', 'integerOnly'=>true),
			array('zlfiginstcoord_id, zlfiginstcoord_zlfiginst_id', 'length', 'max'=>100),
			array('create_time, update_time', 'safe'),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('zlfiginstcoord_id, zlfiginstcoord_zlfiginst_id, zlfiginstcoord_posx, zlfiginstcoord_posy, create_time, update_time', 'safe', 'on'=>'search'),
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
			'zlfiginstcoordZlfiginst' => array(self::BELONGS_TO, 'ZzzlrofiginstBase', 'zlfiginstcoord_zlfiginst_id'),
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'zlfiginstcoord_id' => 'Zlfiginstcoord',
			'zlfiginstcoord_zlfiginst_id' => 'Zlfiginstcoord Zlfiginst',
			'zlfiginstcoord_posx' => 'Zlfiginstcoord Posx',
			'zlfiginstcoord_posy' => 'Zlfiginstcoord Posy',
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

		$criteria->compare('zlfiginstcoord_id',$this->zlfiginstcoord_id,true);
		$criteria->compare('zlfiginstcoord_zlfiginst_id',$this->zlfiginstcoord_zlfiginst_id,true);
		$criteria->compare('zlfiginstcoord_posx',$this->zlfiginstcoord_posx);
		$criteria->compare('zlfiginstcoord_posy',$this->zlfiginstcoord_posy);
		$criteria->compare('create_time',$this->create_time,true);
		$criteria->compare('update_time',$this->update_time,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}