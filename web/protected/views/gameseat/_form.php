<div class="form">

<?php $form=$this->beginWidget('CActiveForm', array(
	'id'=>'zzgameseat-form',
	'enableAjaxValidation'=>false,
)); ?>

	<p class="note">Fields with <span class="required">*</span> are required.</p>

	<?php echo $form->errorSummary($model); ?>

	<div class="row">
		<?php echo $form->labelEx($model,'gameseat_user_id'); ?>
                <?php echo $form->dropDownList($model,'gameseat_user_id',  CHtml::listData(Zzuser::model()->findAll(), 'user_id','user_email'));?>
		<?php echo $form->error($model,'gameseat_user_id'); ?>
	</div>

	<div class="row">
		<?php echo $form->labelEx($model,'gameseat_gameinst_id'); ?>
		<?php echo $form->dropDownList($model,'gameseat_gameinst_id',  CHtml::listData(Zzgameinst::model()->findAll(), 'gameinst_id','gameinst_id'));?>
		<?php echo $form->error($model,'gameseat_gameinst_id'); ?>
	</div>

	

	<div class="row buttons">
		<?php echo CHtml::submitButton($model->isNewRecord ? 'Create' : 'Save'); ?>
	</div>

<?php $this->endWidget(); ?>

</div><!-- form -->