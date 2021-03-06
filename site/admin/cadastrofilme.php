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
				<h2 class="heading">Novo Filme</h2>

				<center>
					<form id="cadastro">
						Nome do Filme:<br/>
						<input type="text" name="name" required>
						<br/>
						Anúncio do Filme:<br/>
						<input type="text" name="advertisement" required>
						<br/>
						Sinopse:<br/>
						<textarea rows="4" cols="50" name="synopsis" maxlength="10000"></textarea>
						<br/>
						Início da Exibição:<br/>
						<input type="date" name="start_exhibition" required>
						<br/>
						Fim da Exibição:<br/>
						<input type="date" name="end_exhibition" required>
						<br/>
						Prefixo da Imagem:<br/>
						<input type="text" name="image" required>
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
		sendFormData("cadastro", "movies", "cadastrofinalizado.php", "erro.php");
	});
</script>

<?php
include_once 'footer.php';
?>
