<?php
$title = 'Cadastro Filme - E-Movie';
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
				<h2 class="heading">Cadastrar Filme</h2>

				<center>
					<form id="cadastro">
						Nome do Filme:<br/>
						<input type="text" name="name" required>
						<br/>
						Sinopse:<br/>
						<textarea rows="4" cols="50" name="synopsis" maxlength="255"></textarea>
						<br/>
						Início da Exibição:<br/>
						<input type="date" name="start_exhibition" required>
						<br/>
						Fim da Exibição:<br/>
						<input type="date" name="end_exhibition" required>
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
		enviarInformacoes("cadastro", "movies", "cadastrofinalizado.php", "../erro.php");
	});
</script>

<?php
include_once 'footer.php';
?>
