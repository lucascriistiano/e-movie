<?php
$title = 'Cadastro Horário - E-Movie';
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
				<h2 class="heading">Novo Horário</h2>

				<center>
					<form id="cadastro">
						Dia da semana:<br/>
						<select name="day_week">
							<option value="0" selected>Domingo</option>
							<option value="1">Segunda</option>
							<option value="2">Terça</option>
							<option value="3">Quarta</option>
							<option value="4">Quinta</option>
							<option value="5">Sexta</option>
							<option value="6">Sábado</option>
						</select>
						<br/>
						Hora:<br/>
						<input type="time" name="hour" min="12:00" max="23:59">
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
		sendFormData("cadastro", "sessions", "cadastrofinalizado.php", "erro.php");
	});
</script>

<?php
include_once 'footer.php';
?>
