<?php
$title = 'Cadastro Sala - E-Movie';
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
				<h2 class="heading">Nova Sala</h2>

				<center>
					<form id="cadastro">
						Número de Fileiras:
						<input type="number" name="rows" value="1" min="1" max="26">
						<br/>
						<br/>
						<input type="hidden" name="operation" value="create" required>
						<input class="link-button blue" type="submit" value="Cadastrar">
					</form>
				</center>

			</div>
		</div>
	</div>
</div>
<!-- ENDS MAIN -->

<script type="text/javascript">
	$("#cadastro").submit(function(evento) {
		sendFormData("cadastro", "rooms", "cadastrofinalizado.php", "erro.php");
	});
</script>

<?php
include_once 'footer.php';
?>
