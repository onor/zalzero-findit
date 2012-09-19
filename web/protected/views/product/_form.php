<div class="form">

<?php $form=$this->beginWidget('CActiveForm', array(
	'id'=>'zzproduct-form',
	'enableAjaxValidation'=>false,
)); ?>

	<p class="note">Fields with <span class="required">*</span> are required.</p>

	<?php echo $form->errorSummary($model); ?>


	<div class="row">
		<?php echo $form->labelEx($model,'product_sku'); ?>
		<?php echo $form->textField($model,'product_sku',array('size'=>60,'maxlength'=>150)); ?>
		<?php echo $form->error($model,'product_sku'); ?>
	</div>

	<div class="row">
		<?php echo $form->labelEx($model,'product_name'); ?>
		<?php echo $form->textField($model,'product_name',array('size'=>60,'maxlength'=>150)); ?>
		<?php echo $form->error($model,'product_name'); ?>
	</div>

	<div class="row">
		<?php echo $form->labelEx($model,'product_brand'); ?>
		<?php echo $form->textField($model,'product_brand',array('size'=>60,'maxlength'=>150)); ?>
		<?php echo $form->error($model,'product_brand'); ?>
	</div>

	<div class="row">
		<?php echo $form->labelEx($model,'product_shortdesc'); ?>
		<?php echo $form->textField($model,'product_shortdesc',array('size'=>60,'maxlength'=>200)); ?>
		<?php echo $form->error($model,'product_shortdesc'); ?>
	</div>

	<div class="row">
		<?php echo $form->labelEx($model,'product_desc'); ?>
		<?php echo $form->textField($model,'product_desc',array('size'=>60,'maxlength'=>500)); ?>
		<?php echo $form->error($model,'product_desc'); ?>
	</div>

	<div class="row">
		<?php echo $form->labelEx($model,'product_price'); ?>
		<?php echo $form->textField($model,'product_price',array('size'=>19,'maxlength'=>19)); ?>
		<?php echo $form->error($model,'product_price'); ?>
	</div>

	<div class="row buttons">
		<?php echo CHtml::submitButton($model->isNewRecord ? 'Create' : 'Save'); ?>
	</div>

<?php $this->endWidget(); ?>

</div><!-- form -->