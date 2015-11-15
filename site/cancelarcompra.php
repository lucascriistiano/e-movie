<?php
$title = 'Cancelar Compra - E-Movie';
$home = false;
include_once 'header.php';
?>

<!-- MAIN -->
<div id="main">
	<div class="wrapper cf">
		<!-- page content-->
		<div id="page-content" class="cf">
			<!-- entry-content -->
			<div class="entry-content cf">
				<h2 class="heading">Cancelar Compra</h2>
				<form id="cadastro">
					<center>
						Token:<br/>
						<input type="text" name="token" size="25">
						<br/>
						Email:<br/>
						<input type="email" name="email" size="25">
						<br/>
						Senha:<br/>
						<input type="password" name="password" size="25">
						<br/>
						<br/>
						<input type="hidden" name="operation" value="delete" required>
						<input class="link-button blue" type="submit" value="Cancelar">
					</center>
				</form>

			</div>
		</div>
	</div>
</div>
<!-- ENDS MAIN -->

<script type="text/javascript">
	$("#cadastro").submit(function(evento) {
		sendFormData("cadastro", "tickets", "cadastrofinalizado.php", "erro.php");
	});
</script>

<?php
include_once 'footer.php';
?>
