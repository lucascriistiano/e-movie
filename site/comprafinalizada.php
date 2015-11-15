<?php
$title = 'Compra Finalizada - E-Movie';
$home = false;
include_once 'header.php';
?>

<script type="text/javascript">
	$(document).ready(function () {
		var token = getQueryVariable('token');
		$('#token').text(token);
	});
</script>

<!-- MAIN -->
<div id="main">
	<div class="wrapper cf">
		<!-- page content-->
		<div id="page-content" class="cf">
			<!-- entry-content -->
			<div class="entry-content cf">
				<h2 class="heading">Compra Finalizada</h2>

				<p>Sua compra foi finalizada com sucesso!</p>

				<p>Você pode recuperar o seu ingresso na seção "Retirar Ingresso" com o seguinte token:</p>

				<h2 id="token" class="heading"></h2>
			</div>
		</div>
	</div>
</div>
<!-- ENDS MAIN -->

<?php
include_once 'footer.php';
?>