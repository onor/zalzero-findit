<?php
class Zzcron extends ZzcronBase
{
        /**
         * Returns the static model of the specified AR class.
         * @param string $className active record class name.
         * @return Zzgame the static model class
         */
        public static function model($className=__CLASS__)
        {
                return parent::model($className);
        }

}
