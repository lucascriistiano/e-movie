<?php
$title = 'Detalhes Filme - E-Movie';
$home = false;
include_once 'header.php';
?>

<script type="text/javascript">
	// Listagem das sessões
	var daysWeek = ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado'];

	var movieId = getQueryVariable("id");
	if(movieId != '') {
		// Get movie info
		$.ajax({
			type: "GET",
			url: "http://localhost:8000/emovie/movies/" + movieId,
			dataType: "json",
			success: function(data) {
				console.log(data);

				if(data != null) {
					var movie = data;
					var resultHTML = '';
					resultHTML += '<div><img src="img/movies/' + movie['image'] + '.jpg" title="" alt="alt" /></div></br>';
					resultHTML += '<h2 class="heading">' + movie['name'] + '</h2>';
					resultHTML += '<p>' + movie['advertisement'] + '</p>';

					resultHTML += '<p>Sinopse:</br>' + movie['synopsis'] + '</p>';

					var startExhibition = getFormattedDate(movie['startExhibition']);
					var endExhibition = getFormattedDate(movie['endExhibition']);
					resultHTML += '<p>Período de exibição: ' + startExhibition + ' a ' + endExhibition + '</p>';

					$('#movie-info').append(resultHTML);
				} else {
					console.log('invalid "id" parameter');
					window.location.href = "index.php";
				}
			},
			error: function(){
				window.location.href = "erro.php";
			}
		});

		// Get registered movie exhibitions
		$.ajax({
			type: "GET",
			url: "http://localhost:8000/emovie/exhibitions",
			data: 'id_movie=' + movieId,
			dataType: "json",
			success: function(data) {
				console.log(data);

				var exhibitions = data
				if(exhibitions.length > 0) {
					var resultHTML = '';
					resultHTML += '<form id="select-exhibition">';
					resultHTML += 'Próximas exibições:</br>';
					resultHTML += '<select id="exhibition" name="exhibition">';

					$.each(data, function(index, exhibition) {
						console.log(exhibition);

						var session = exhibition['session'];
                        var sessionDay = daysWeek[parseInt(session['dayWeek'])];
                        var sessionTime = getFormattedTime(session['hour']);

						var room = exhibition['room'];
						var price = parseFloat(exhibition['price']).toFixed(2);

						var strExhibition = sessionDay + ' - ' + sessionTime + ' - Sala ' + room['id'] + ' - R$ ' + price;
						resultHTML += '<option value="' + exhibition['id'] + '">' + strExhibition + '</option>';
					});

					resultHTML += '</select>';
					resultHTML += '</br>'
					resultHTML += '</br>'

					resultHTML += '<input id="select-exhibition-button" class="link-button blue" type="submit" value="Comprar">';
					resultHTML += '</form>';

					$('#movie-info').append(resultHTML);

					// Add listener to button click to redirect to chair selection
					$("#select-exhibition").submit(function(evento) {
						event.preventDefault(); //prevenir o form de fazer submit
						var selectedExhibitionId = $("#exhibition option:selected").val();
						window.location.href = "selecionarpoltrona.php?id_exhibition=" + selectedExhibitionId;
					});

				} else {
					$('#movie-info').append('<p>Não existe nenhuma exibição cadastrada para esse filme.</p>');
				}
			},
			error: function(){
				$('#movie-info').append('<p>Não foi possível listar as exibições para esse filme.</p>');
			}
		});
	} else {
		console.log('Empty "id" parameter');
		window.location.href = "index.php";
	}
</script>

<!-- MAIN -->
<div id="main">
	<div class="wrapper cf">
		<!-- page content-->
		<div id="page-content" class="cf">
			<!-- entry-content -->
			<div id="movie-info" class="entry-content cf">
				<!-- Found movie info -->
			</div>
		</div>
	</div>
</div>
<!-- ENDS MAIN -->

<?php
include_once 'footer.php';
?>