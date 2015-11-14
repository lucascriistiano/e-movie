<?php
$title = 'Cadastro Exibição - E-Movie';
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
				<h2 class="heading">Nova Exibição</h2>

				<center>
					<form id="cadastro">
						Filme:<br/>
						<select name="id_movie" required>
							<option value="0" selected>Os Vingadores 2</option>
							<option value="1">Crepúsculo</option>
							<option value="2">Ant-Man</option>
							<option value="3">Os Smurfs 2</option>
						</select>
						<br/>

						Horário:<br/>
						<select name="id_session" required>
							<option value="0" selected>Domingos - 14:00</option>
							<option value="1">Quintas - 13:00</option>
							<option value="2">Sábados - 12:00</option>
						</select>
						<br/>

						Sala:<br/>
						<select name="id_room" required>
							<option value="1" selected>Sala 1</option>
							<option value="10">Sala 10</option>
							<option value="15">Sala 15</option>
						</select>
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
		enviarInformacoes("cadastro", "exhibitions", "cadastrofinalizado.php", "../erro.php");
	});
</script>

<?php
include_once 'footer.php';
?>
