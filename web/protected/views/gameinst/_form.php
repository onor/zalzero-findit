<div class="form">

<?php $form=$this->beginWidget('CActiveForm', array(
	'id'=>'zzgameinst-form',
	'enableAjaxValidation'=>false,
)); ?>

	<p class="note">Fields with <span class="required">*</span> are required.</p>

	<?php echo $form->errorSummary($model); ?>

        <div class="row">
            <?php
                echo $form->labelEx($model,'gameinst_gameinsttempl_id');
                echo $form->dropDownList($model,'gameinst_gameinsttempl_id',  CHtml::listData(ZzGameInstTempl::model()->findAll(), 'gameinsttempl_id','gameinsttempl_game_desc'));
                echo $form->error($model,'gameinst_gameinsttempl_id');
            ?>
        </div>
	<div class="row">
		<?php echo $form->labelEx($model,'gameinst_game_id'); ?>
                <?php echo $form->dropDownList($model,'gameinst_game_id',  CHtml::listData(Zzgame::model()->findAll(), 'game_id','game_name')); ?>
		<?php echo $form->error($model,'gameinst_game_id'); ?>
	</div>

	<div class="row">
		<?php echo $form->labelEx($model,'gameinst_product_id'); ?>
		<?php echo $form->dropDownList($model,'gameinst_product_id',  CHtml::listData(Zzproduct::model()->findAll(), 'product_id', 'product_name')); ?>
		<?php echo $form->error($model,'gameinst_product_id'); ?>
	</div>

	<div class="row">
		<?php echo $form->labelEx($model,'gameinst_starttime'); ?>
                <?php Yii::import('application.extensions.CJuiDateTimePicker.CJuiDateTimePicker');
                    $this->widget('CJuiDateTimePicker',array(
                        'model'=>$model, //Model object
                        'attribute'=>'gameinst_starttime', //attribute name
                        'mode'=>'datetime', //use "time","date" or "datetime" (default)
                        'options'=>array("dateFormat"=>'yy-mm-dd'), // jquery plugin options
                        'language' => ''
                    ));
                ?>
		<?php //echo $form->textField($model,'gameinst_starttime'); ?>
		<?php echo $form->error($model,'gameinst_starttime'); ?>
	</div>

	<div class="row">
		<?php echo $form->labelEx($model,'gameinst_endtime'); ?>
                <?php Yii::import('application.extensions.CJuiDateTimePicker.CJuiDateTimePicker');
                    $this->widget('CJuiDateTimePicker',array(
                        'model'=>$model, //Model object
                        'attribute'=>'gameinst_endtime', //attribute name
                        'mode'=>'datetime', //use "time","date" or "datetime" (default)
                        'options'=>array("dateFormat"=>'yy-mm-dd'), // jquery plugin options
                        'language' => ''
                    ));
                ?>
		<?php //echo $form->textField($model,'gameinst_endtime'); ?>
		<?php echo $form->error($model,'gameinst_endtime'); ?>
	</div>

	<div class="row">
		<?php echo $form->labelEx($model,'gameinst_entrytime'); ?>
                <?php Yii::import('application.extensions.CJuiDateTimePicker.CJuiDateTimePicker');
                    $this->widget('CJuiDateTimePicker',array(
                        'model'=>$model, //Model object
                        'attribute'=>'gameinst_entrytime', //attribute name
                        'mode'=>'datetime', //use "time","date" or "datetime" (default)
                        'options'=>array("dateFormat"=>'yy-mm-dd'), // jquery plugin options
                        'language' => ''
                    ));
                ?>
		<?php //echo $form->textField($model,'gameinst_entrytime'); ?>
		<?php echo $form->error($model,'gameinst_entrytime'); ?>
	</div>

	<div class="row">
		<?php echo $form->labelEx($model,'gameinst_capacity'); ?>
		<?php echo $form->textField($model,'gameinst_capacity'); ?>
		<?php echo $form->error($model,'gameinst_capacity'); ?>
	</div>

	<div class="row">
		<?php echo $form->labelEx($model,'gameinst_threshold'); ?>
		<?php echo $form->textField($model,'gameinst_threshold'); ?>
		<?php echo $form->error($model,'gameinst_threshold'); ?>
	</div>

	
	<div class="row buttons">
		<?php echo CHtml::submitButton($model->isNewRecord ? 'Create' : 'Save'); ?>
	</div>

<?php $this->endWidget(); ?>

</div><!-- form -->