<?php
$title = 'Cadastro Exibição - E-Movie';
$home = false;
include_once 'header.php';
?>

<script type="text/javascript">
	$(document).ready(function (){
		// Listagem dos filmes
		$.ajax({
			type: "GET",
			url: "http://localhost:8000/emovie/movies",
			dataType: "json",
			success: function(data) {
				var resultHTML = '';
				$.each(data, function(index, movie) {
					var optionHTML = '';
					optionHTML += '<option value="' + movie['id'] + '">';
					optionHTML += movie['name'];
					optionHTML += '</option>';

					resultHTML += optionHTML;
				});

				$('#movie-list').append(resultHTML);
			},
			error: function(){
				window.location.href = "../erro.php";
			}
		});

		// Listagem das sessões
		$.ajax({
			type: "GET",
			url: "http://localhost:8000/emovie/sessions",
			dataType: "json",
			success: function(data) {
				var resultHTML = '';
				$.each(data, function(index, session) {
                    var sessionDays = daysWeek[parseInt(session['dayWeek'])] + 's';
                    var sessionTime = getFormattedTime(session['hour']);

					var optionHTML = '';
					optionHTML += '<option value="' + session['id'] + '">';
					optionHTML += sessionDays + ' - ' + sessionTime;
					optionHTML += '</option>';

					resultHTML += optionHTML;
				});

				$('#session-list').append(resultHTML);
			},
			error: function(){
				window.location.href = "../erro.php";
			}
		});

		$.ajax({
			type: "GET",
			url: "http://localhost:8000/emovie/rooms",
			dataType: "json",
			success: function(data) {
				var resultHTML = '';
				$.each(data, function(index, room) {
					var optionHTML = '';
					optionHTML += '<option value="' + room['id'] + '">';
					optionHTML += 'Sala ' + room['id'] + ' - ' + room['rows'] + ' fileiras';
					optionHTML += '</option>';

					resultHTML += optionHTML;
				});

				$('#room-list').append(resultHTML);
			},
			error: function(){
				window.location.href = "../erro.php";
			}
		});

	});
</script>

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
						<select id="movie-list" name="id_movie" required>
							<!-- Movie options -->
						</select>
						<br/>

						Horário:<br/>
						<select id="session-list" name="id_session" required>
							<!-- Session options -->
						</select>
						<br/>

						Sala:<br/>
						<select id="room-list" name="id_room" required>
							<!-- Room options -->
						</select>
						<br/>

						Preço:<br/>
						R$ <input type="number" min="0.01" step="0.01" value="0.00" name="price" required>
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
		sendFormData("cadastro", "exhibitions", "cadastrofinalizado.php", "erro.php");
	});
</script>

<?php
include_once 'footer.php';
?>