<?php
	$title = 'Recuperar Ticket - E-Movie';
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
					<h2 class="heading">Recuperar Ticket</h2>

					<center>
						<form id="cadastro">
								Ticket:<br/>
								<input type="text" name="token">
							<br/>
							<br/>
							<input type="hidden" name="operation" value="other" required>
							<input class="link-button blue" type="submit" value="Cadastrar">
						</form>
					</center>

          <div id="comprovante">
            <br/>
            <br/>
            <center>
              <h1>Seu Ticket</h1>
              <br/>
              <h2 id="ticket">afionsafoiasn</h2>
              <br/>
              <h3 id="email">exemplo@email.com</h3>
              <br/>
              <h4 id="filme">Vingadores</h4>
              <br/>
              <h4 id="horario">20/10/2015 10:35</h4>
              <br/>
              <h4 id="sala">1</h4>
            </center>
          </div>

				</div>
			</div>
		</div>
	</div>
	<!-- ENDS MAIN -->

	<script type="text/javascript">
		$("#cadastro").submit(function(evento) {
			enviarInformacoes("cadastro", "sessions", "cadastrofinalizado.php", "../erro.php");
		});
	</script>

	<?php
		include_once 'footer.php';
	?>
