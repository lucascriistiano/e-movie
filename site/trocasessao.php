<?php
$title = 'Troca de Sessão - E-Movie';
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

    var imgFolder = "img/chair/";
    var freeImg = imgFolder + "free.png";
    var occupiedImg = imgFolder + "occupied.png";
    var selectedImg = imgFolder + "selected.png";
    var selectedChair = '';

    var movies, exhibitions, chairs, ticket, roomRows;
    var movieId, exhibitionId;
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

    function addChangeForm() {
        var resultHTML = '';
        resultHTML +=
        resultHTML += '<form id="session-change">';
        resultHTML += 'Filme:';
        resultHTML += '<select id="movie-list">' + '<!-- Movie list -->' + '</select>';
        resultHTML += '</br></br>';

        resultHTML += '<div id="session-change-data">';
        resultHTML += 'Exibição:';
        resultHTML += '<select id="exhibition-list">' + '<!-- Exhibition list -->' + '</select>';
        resultHTML += '</br></br>';

        resultHTML += '<table id="table-chairs">' + '<!-- automatic table -->' + '</table>';
        resultHTML += '</br></br>';

        resultHTML += '<p>Poltrona atual: <strong><span id="selected-chair"></span></strong></p>';
        resultHTML += '</br></br>';

        resultHTML += '<input id="id_exhibition" type="hidden" name="id_exhibition">';
        resultHTML += '<input id="chair_num" type="hidden" name="chair_num">';

        resultHTML += 'Email:';
        resultHTML += '<input type="text" name="email">';
        resultHTML += '</br>';

        resultHTML += 'Senha:';
        resultHTML += '<input type="password" name="password">';
        resultHTML += '</br></br>';

        resultHTML += '<input id="ticket_id" type="hidden" name="id">';

        resultHTML += '<input type="hidden" name="purchase_location" value="internet">';
        resultHTML += '<input type="hidden" name="operation" value="update">';
        resultHTML += '</div>';

        resultHTML += '<input id="finish-change" class="link-button blue" type="submit" value="Concluir Troca">';
        resultHTML += '</form>';

        $('#change-form').html(resultHTML);

        loadMovieList();

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

        $("#session-change").submit(function(evento) {
            event.preventDefault(); //prevenir o form de fazer submit
            if(selectedChair != '') {
                var selectedExhibitionId = $('#exhibition-list option:selected').val();
                $('#id_exhibition').val(selectedExhibitionId);
                $('#chair_num').val(selectedChair);
                $('#ticket_id').val(ticket['id']);

                var serializedData = $("#session-change-data input").serialize();
                sendSerializedData(serializedData, "tickets", "operacaorealizada.php", "erro.php");
            } else {
                alert('Você deve selecionar uma poltrona para prosseguir');
            }
        });
    }
</script>

<!-- MAIN -->
<div id="main">
    <div class="wrapper cf">
        <!-- page content-->
        <div id="page-content" class="cf">
            <!-- entry-content -->
            <div class="entry-content cf">
                <h2 class="heading">Troca de Sessão</h2>

                <center>
                    <form id="consult-token">
                        Token:<br/>
                        <input type="text" name="token" size="25">
                        <br/>
                        <br/>
                        <input type="hidden" name="operation" value="retrieveToken" required>
                        <input id="consult-token-button" class="link-button blue" type="submit" value="Consultar">
                    </form>

                    </br>
                    <div id="error">
                        <!-- displayed error -->
                    </div>
                </center>

                <div id="change-form">
                    <!-- form addeded here -->
                </div>
            </div>
        </div>
    </div>
</div>
<!-- ENDS MAIN -->

<script type="text/javascript">
    $("#consult-token").submit(function(evento) {
        var datastring = $("#consult-token").serialize();
        console.log(datastring);

        event.preventDefault(); //prevenir o form de fazer submit

        $("#error").empty();
        $("#change-form").empty();

        $.ajax({
            type: "GET",
            url: "http://localhost:8000/emovie/tickets",
            data: datastring,
            dataType: "json",
            success: function(data) {
                ticket = data;
                if(ticket != null) {
                    addChangeForm();
                } else {
                    $("#error").html('<p>O token inserido não corresponde a nenhum ticket cadastrado.</p>');
                }
            },
            error: function(){
                window.location.href = "erro.php";
            }
        });
    });
</script>

<?php
include_once 'footer.php';
?>
