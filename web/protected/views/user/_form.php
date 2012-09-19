<div class="form">

<?php $form=$this->beginWidget('CActiveForm', array(
	'id'=>'zzuser-form',
	'enableAjaxValidation'=>false,
)); ?>

	<p class="note">Fields with <span class="required">*</span> are required.</p>

	<?php echo $form->errorSummary($model); ?>

	<div class="row">
		<?php //echo $form->labelEx($model,'user_id'); ?>
		<?php //echo $form->textField($model,'user_id'); ?>
		<?php //echo $form->error($model,'user_id'); ?>
	</div>

	<div class="row">
		<?php echo $form->labelEx($model,'user_name'); ?>
		<?php echo $form->textField($model,'user_name',array('size'=>50,'maxlength'=>50)); ?>
		<?php echo $form->error($model,'user_name'); ?>
	</div>

	<div class="row">
		<?php echo $form->labelEx($model,'user_email'); ?>
		<?php echo $form->textField($model,'user_email',array('size'=>60,'maxlength'=>150)); ?>
		<?php echo $form->error($model,'user_email'); ?>
	</div>

	<div class="row">
		<?php //echo $form->labelEx($model,'user_fbid'); ?>
		<?php //echo $form->textField($model,'user_fbid',array('size'=>60,'maxlength'=>150)); ?>
		<?php //echo $form->error($model,'user_fbid'); ?>
	</div>
        <?php if(Yii::app()->user->isGuest):?> 
        <div class="row">
                <?php echo $form->labelEx($model,'user_password'); ?>
                <?php echo $form->textField($model,'user_password',array('size'=>60,'maxlength'=>150,'value'=>'')); ?>
                <?php echo $form->error($model,'user_password'); ?>
        </div>
        <?php     endif;?>
        
        <div class="row">
		<?php //echo $form->labelEx($model,'user_password_repeat'); ?>
		<?php //echo $form->textField($model,'user_password_repeat',array('size'=>60,'maxlength'=>150)); ?>
		<?php //echo $form->error($model,'user_password_repeat'); ?>
	</div>

	<div class="row">
		<?php echo $form->labelEx($model,'user_handle'); ?>
		<?php echo $form->textField($model,'user_handle',array('size'=>60,'maxlength'=>150)); ?>
		<?php echo $form->error($model,'user_handle'); ?>
	</div>
        <div class="row">
		<?php echo $form->labelEx($model,'invitation_code'); ?>
		<?php echo $form->textField($model,'invitation_code',array('size'=>60,'maxlength'=>150)); ?>
                <a href='#' id='prefinery_apply_link'>Request for Invitation code</a>
                <?php echo $form->error($model,'invitation_code'); ?>
                
	</div>
        <?php 
            if(!Yii::app()->user->isGuest){
                if (Yii::app()->user->role_id == 1) {
        ?>
        <div class="row">
		<?php echo $form->labelEx($model,'user_role_id'); ?>
		<?php echo $form->dropDownList($model,'user_role_id',CHtml::listData(RolesBase::model()->findAll(), 'role_id','role_name')); ?>
		<?php echo $form->error($model,'user_role_id'); ?>
	</div>
        <?php 
                }
            }
        ?>

	<div class="row buttons">
		<?php echo CHtml::submitButton($model->isNewRecord ? 'Create' : 'Save'); ?>
	</div>

<?php $this->endWidget(); ?>

</div><!-- form -->