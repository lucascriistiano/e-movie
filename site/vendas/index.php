<?php
$title = 'Central de Vendas - E-Movie';
$home = false;
include_once 'header.php';
?>

<script>
	function setOccupied(chairCode) {
		$("#" + chairCode).attr("src", occupiedImg);
	}

	function setFree(chairCode) {
		$("#" + chairCode).attr("src", freeImg);
	}

	function setCurrent(chairCode) {
		$("#" + chairCode).attr("src", selectedImg);
	}

	var imgFolder = "../img/chair/";
	var freeImg = imgFolder + "free.png";
	var occupiedImg = imgFolder + "occupied.png";
	var selectedImg = imgFolder + "selected.png";
	var selectedChair = '';

	var movies, exhibitions, chairs, user, roomRows;
	var movieId, exhibitionId, userId;
	var refreshTableIntervalId;

	function updateState() {
		console.log('Atualizando');
		// Get chair state from server
		$.ajax({
			type: "GET",
			url: "http://localhost:8000/emovie/chairs",
			data: 'id_exhibition=' + exhibitionId,
			dataType: "json",
			success: function(data) {
				chairs = data;
				updateView();
			},
			error: function(){
				window.location.href = "index.php?id_user=" + userId + "&error=true";
			}
		});
	}

	function updateView() {
		// Updates table exhibition
		for (var chairCode in chairs) {
			if(chairs[chairCode] == 0) {
				setFree(chairCode);
			} else {
				setOccupied(chairCode);
			}
		}

		// Check if the chair was taken
		if(chairs[selectedChair] == 1) {
			releaseCurrentChair();
			alert('A poltrona que você havia selecionado foi tomada');
		} else {
			// Maintains the image on chair selected by user
			setCurrent(selectedChair);
		}
	}

	function releaseCurrentChair() {
		if(chairs[selectedChair] == 0) {
			$("#" + selectedChair).attr("src", freeImg);
		} else {
			$("#" + selectedChair).attr("src", occupiedImg);
		}

		selectedChair = '';
		$('#selected-chair').text(selectedChair);
	}

	function selectChair(chairCode) {
		if(chairs[chairCode] == 0) {
			if(selectedChair != '') {
				releaseCurrentChair();
			}

			setCurrent(chairCode);
			selectedChair = chairCode;
		} else {
			alert("Poltrona já está ocupada");
		}

		$('#selected-chair').text(selectedChair);
	}

	function loadExhibitionRoomRows() {
		// Get number of rows of room from server
		$.ajax({
			type: "GET",
			url: "http://localhost:8000/emovie/exhibitions/" + exhibitionId,
			dataType: "json",
			async: false,
			success: function(data) {
				var exhibition = data;
				var room = exhibition['room']
				roomRows = parseInt(room['rows'])
			},
			error: function(){
				window.location.href = "index.php?id_user=" + userId + "&error=true";
			}
		});
	}

	function createTable() {
		loadExhibitionRoomRows();

		var rowCodes = [ 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
				 		 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' ];
		var rowChairs = 10;

		var tableContent = '';
		//Gera linhas com cadeiras
		for (var row = 0; row < roomRows; row++){
			var rowCode = rowCodes[row];
			tableContent += '<tr>';
			tableContent += '<td class="chair-number">' + rowCode +'</td>';
			for (var j = 1; j <= rowChairs; j++) {
				var chairNumber = ("0" + j).slice(-2);
				var chairCode = rowCode + chairNumber;
				tableContent += '<td><a href="#" target="_self">' + '<img id="' + chairCode + '" class="chair-img" src="" onclick="selectChair(\'' + chairCode + '\')"/>' +'</a></td>';
			}
			tableContent += '</tr>';
		}

		// Generates last row
		tableContent += '<tr>';
		tableContent += '<td></td>';
		for (var chairNumber = 1; chairNumber <= rowChairs; chairNumber++){
			tableContent += '<td class="chair-number">' + (chairNumber) + '</td>';
		}
		tableContent += '<td></td>';
		tableContent += '</tr>';

		var tableChairs = $('#table-chairs');
		tableChairs.empty();
		tableChairs.append(tableContent);
	}

	function loadMovieList() {
		// Get movie list from server
		$.ajax({
			type: "GET",
			url: "http://localhost:8000/emovie/movies",
			dataType: "json",
			success: function(data) {
				console.log(data);
				movies = data;

				var movieListSelect = $('#movie-list');
				movieListSelect.empty();

				movieListSelect.append('<option disabled selected>Selecione o filme</option>');
				$.each(movies, function(index, movie) {
					var movieOption = '<option value="' + movie['id'] + '">' + movie['name'] + '</option>';
					movieListSelect.append(movieOption);
				});
			},
			error: function(){
				window.location.href = "index.php?id_user=" + userId + "&error=true";
			}
		});
	}

	function loadExhibitionList() {
		// Get registered movie exhibitions
		$.ajax({
			type: "GET",
			url: "http://localhost:8000/emovie/exhibitions",
			data: 'id_movie=' + movieId,
			dataType: "json",
			success: function(data) {
				console.log(data);
				exhibitions = data;

				var exhibitionListSelect = $('#exhibition-list');
				exhibitionListSelect.empty();

				exhibitionListSelect.append('<option disabled selected>Selecione a exibição</option>');
				$.each(exhibitions, function(index, exhibition) {
					var session = exhibition['session'];
                    var sessionDay = daysWeek[parseInt(session['dayWeek'])];
                    var sessionTime = getFormattedTime(session['hour']);

					var room = exhibition['room'];
					var price = parseFloat(exhibition['price']).toFixed(2);

					var strExhibition = sessionDay + ' - ' + sessionTime + ' - Sala ' + room['id'] + ' - R$ ' + price;
					var exhibitionOption = '<option value="' + exhibition['id'] + '">' + strExhibition + '</option>';
					exhibitionListSelect.append(exhibitionOption);
				});
			},
			error: function(){
				window.location.href = "index.php?id_user=" + userId + "&error=true";
			}
		});
	}

	function checkValidId () {
		if(userId == '') {
			alert("É necessário que um id de usuário válido, responsável pela venda, seja fornecido");
			window.location.href = "/index.php";
		} else {
			// Check if id is invalid
			$.ajax({
				type: "GET",
				url: "http://localhost:8000/emovie/users/" + userId,
				dataType: "json",
				success: function(data) {
					user = data;
					if(user == null) {
						alert("O id não corresponde a nenhum usuario cadastrado");
						window.location.href = "/index.php";
					}
					else {
						if(user["type"] != "VENDAS") {
							alert("O usuário referente ao id passado não tem permissão para realizar vendas");
							window.location.href = "/index.php";
						}
					}
				},
				error: function(){
					window.location.href = "/index.php";
				}
			});
		}
	}

	$(document).ready(function () {
		userId = getQueryVariable('id_user');
		checkValidId();	// redirects user to index page if thre isn't a valid id_user on param

		//If it passed, user id is valid
		$('#id_user').val(userId);

		var error = getQueryVariable('error');
		if(error == 'true') {
			$('#error').append('<h3>Ocorreu um erro durante o processamento dos dados. Por favor, repita a operação.</h3></br></br>');
		} else {
			$('#error').empty();
		}

		// exhibitionId = getQueryVariable('id_exhibition');
		loadMovieList();
	});
</script>

<!-- MAIN -->
<div id="main">
	<div class="wrapper cf">
		<!-- page content-->
		<div id="page-content" class="cf">
			<!-- entry-content -->
			<div class="entry-content cf">
				<h2 class="heading">Central de Vendas</h2>

				<div id="error">
					<!-- displayed error -->
				</div>

				<form id="local-sale">
					Filme:
					<select id="movie-list">
						<!-- Movie list -->
					</select>
					</br></br>

					<div id="sale-data">
						Exibição:
						<select id="exhibition-list">
							<!-- Exhibition list -->
						</select>
						</br></br>

						<table id="table-chairs">
							<!-- automatic table -->
						</table>
						</br></br>

						<p>Poltrona atual: <strong><span id="selected-chair"></span></strong></p>
						</br></br>

						<input id="id_exhibition" type="hidden" name="id_exhibition">
						<input id="chair_num" type="hidden" name="chair_num">

						<input id="email" type="hidden" name="email">
						<input id="password" type="hidden" name="password">

						<input type="hidden" name="purchase_location" value="local">
						<input type="hidden" name="operation" value="create">
					</div>

					<input id="finish-sale" class="link-button blue" type="submit" value="Concluir Venda">
				</form>
			</div>
		</div>
	</div>
</div>
<!-- ENDS MAIN -->

<script type="text/javascript">
	$("#movie-list").change(function() {
		movieId = $(this).val();
		loadExhibitionList();

		selectedChair = '';
		clearInterval(refreshTableIntervalId);
		$('#table-chairs').empty();
	});

	$("#exhibition-list").change(function() {
		exhibitionId = $(this).val();
		createTable();

		selectedChair = '';
		updateState();
		clearInterval(refreshTableIntervalId);
		refreshTableIntervalId = setInterval(function(){updateState()},2000);
	});

	$("#local-sale").submit(function(evento) {
		event.preventDefault(); //prevenir o form de fazer submit
		if(selectedChair != '') {
			var successLink = 'index.php?id_user=' + userId;
			var errorLink = 'index.php?id_user=' + userId + '&error=true';

			var selectedExhibitionId = $('#exhibition-list option:selected').val();
			$('#id_exhibition').val(selectedExhibitionId);

			$('#chair_num').val(selectedChair);

			$('#email').val(user['email']);
			$('#password').val(user['password']);

			var serializedData = $("#sale-data input").serialize();
			sendSerializedData(serializedData, "tickets", successLink, errorLink);
		} else {
			alert('Você deve selecionar uma poltrona para prosseguir');
		}
	});
</script>

<?php
include_once 'footer.php';
?>
