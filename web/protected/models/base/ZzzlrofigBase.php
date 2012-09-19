<?php

/**
 * This is the model class for table "zzzlrofig".
 *
 * The followings are the available columns in table 'zzzlrofig':
 * @property string $zlfig_id
 * @property string $zlfig_name
 * @property integer $zlfig_required
 * @property string $zlfig_remark
 * @property integer $zlfig_points
 * @property string $zlfig_zlfiggroup_id
 * @property string $create_time
 * @property string $update_time
 *
 * The followings are the available model relations:
 * @property ZzzlrofiggroupBase $zlfigZlfiggroup
 * @property ZzzlrofiginstBase[] $zzzlrofiginsts
 * @property ZzzlrofigcoordBase[] $zzzlrofigcoords
 */
class ZzzlrofigBase extends ActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return ZzzlrofigBase the static model class
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
		return 'zzzlrofig';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('zlfig_id, zlfig_zlfiggroup_id', 'required'),
			array('zlfig_required, zlfig_points', 'numerical', 'integerOnly'=>true),
			array('zlfig_id, zlfig_zlfiggroup_id', 'length', 'max'=>100),
			array('zlfig_name, zlfig_remark', 'length', 'max'=>150),
			array('create_time, update_time', 'safe'),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('zlfig_id, zlfig_name, zlfig_required, zlfig_remark, zlfig_points, zlfig_zlfiggroup_id, create_time, update_time', 'safe', 'on'=>'search'),
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
			'zlfigZlfiggroup' => array(self::BELONGS_TO, 'ZzzlrofiggroupBase', 'zlfig_zlfiggroup_id'),
			'zzzlrofiginsts' => array(self::HAS_MANY, 'ZzzlrofiginstBase', 'zlfiginst_zlfig_id'),
			'zzzlrofigcoords' => array(self::HAS_MANY, 'ZzzlrofigcoordBase', 'zlfigcoord_zlfig_id'),
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'zlfig_id' => 'Zlfig',
			'zlfig_name' => 'Zlfig Name',
			'zlfig_required' => 'Zlfig Required',
			'zlfig_remark' => 'Zlfig Remark',
			'zlfig_points' => 'Zlfig Points',
			'zlfig_zlfiggroup_id' => 'Zlfig Zlfiggroup',
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

		$criteria->compare('zlfig_id',$this->zlfig_id,true);
		$criteria->compare('zlfig_name',$this->zlfig_name,true);
		$criteria->compare('zlfig_required',$this->zlfig_required);
		$criteria->compare('zlfig_remark',$this->zlfig_remark,true);
		$criteria->compare('zlfig_points',$this->zlfig_points);
		$criteria->compare('zlfig_zlfiggroup_id',$this->zlfig_zlfiggroup_id,true);
		$criteria->compare('create_time',$this->create_time,true);
		$criteria->compare('update_time',$this->update_time,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}