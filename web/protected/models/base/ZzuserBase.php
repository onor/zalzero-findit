<?php

/**
 * This is the model class for table "zzuser".
 *
 * The followings are the available columns in table 'zzuser':
 * @property string $user_id
 * @property string $user_name
 * @property string $user_email
 * @property string $user_fbid
 * @property string $user_password
 * @property string $user_handle
 * @property string $create_time
 * @property string $update_time
 * @property string $user_role_id
 * @property string $user_fname
 * @property string $user_lname
 * @property string $zzuser_status
 *
 * The followings are the available model relations:
 * @property Zzgameusersummary[] $zzgameusersummaries
 * @property Zzzlrofriends[] $zzzlrofriends
 * @property Zzgameseat[] $zzgameseats
 * @property Roles $userRole
 */
class ZzuserBase extends ActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return ZzuserBase the static model class
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
		return 'zzuser';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('user_id, user_name, user_email, user_password, user_handle, user_role_id', 'required'),
			array('user_id, user_role_id', 'length', 'max'=>100),
			array('user_name, user_fname, user_lname', 'length', 'max'=>50),
			array('user_email, user_fbid, user_password, user_handle, zzuser_status', 'length', 'max'=>150),
			array('create_time, update_time', 'safe'),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('user_id, user_name, user_email, user_fbid, user_password, user_handle, create_time, update_time, user_role_id, user_fname, user_lname, zzuser_status', 'safe', 'on'=>'search'),
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
			'zzgameusersummaries' => array(self::HAS_MANY, 'Zzgameusersummary', 'user_id'),
			'zzzlrofriends' => array(self::HAS_MANY, 'Zzzlrofriends', 'user_id'),
			'zzgameseats' => array(self::HAS_MANY, 'Zzgameseat', 'gameseat_user_id'),
			'userRole' => array(self::BELONGS_TO, 'RolesBase', 'user_role_id'),
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'user_id' => 'User',
			'user_name' => 'User Name',
			'user_email' => 'User Email',
			'user_fbid' => 'User Fbid',
			'user_password' => 'User Password',
			'user_handle' => 'User Handle',
			'create_time' => 'Create Time',
			'update_time' => 'Update Time',
			'user_role_id' => 'User Role',
			'user_fname' => 'User Fname',
			'user_lname' => 'User Lname',
			'zzuser_status' => 'Zzuser Status',
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

		$criteria->compare('user_id',$this->user_id,true);
		$criteria->compare('user_name',$this->user_name,true);
		$criteria->compare('user_email',$this->user_email,true);
		$criteria->compare('user_fbid',$this->user_fbid,true);
		$criteria->compare('user_password',$this->user_password,true);
		$criteria->compare('user_handle',$this->user_handle,true);
		$criteria->compare('create_time',$this->create_time,true);
		$criteria->compare('update_time',$this->update_time,true);
		$criteria->compare('user_role_id',$this->user_role_id,true);
		$criteria->compare('user_fname',$this->user_fname,true);
		$criteria->compare('user_lname',$this->user_lname,true);
		$criteria->compare('zzuser_status',$this->zzuser_status,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}
