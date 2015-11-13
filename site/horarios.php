<?php
$title = 'Horários - E-Movie';
$home = false;
include_once 'header.php';
?>

<script type="text/javascript">
    var daysWeek = ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado'];

    function getFormmatedTime(dateString) {
        var date = new Date(dateString);
        var hours = date.getHours();
        if(hours < 10) {
            hours = '0' + hours;
        }

        var minutes = date.getMinutes();
        if(minutes < 10) {
            minutes = '0' + minutes;
        }

        return hours + ':' + minutes;
    }

    $(document).ready(function () {
        $.ajax({
            type: "GET",
            url: "http://localhost:8000/emovie/sessions",
            // data: datastring,
            dataType: "json",
            success: function(data) {
                console.log(data);
                if(data != '') {
                    var sessions = data;
                    var resultHTML = '<ul>';
                    for (var index in sessions) {
                        var session = sessions[index];
                        var sessionDays = daysWeek[parseInt(session['dayWeek'])] + 's';
                        var sessionTime = getFormmatedTime(session['hour']);

                        resultHTML += '<li>';
                        resultHTML += sessionDays + ' - ' + sessionTime;
                        resultHTML += '</li>';
                    }
                    resultHTML += '</ul>';

                    $('#session-list').append(resultHTML);
                } else {
                    $('#session-list').append('<p>Não existe nenhuma sessão cadastrada.</p>');
                }
            },
            error: function(){
                $('#session-list').append('<p>Não foi possível listar os horários cadstrados.</p>');
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
				<h2 class="heading">Horários</h2>

                <div id="session-list">
                <!-- Session list here with JS -->
                </div>
			</div>
		</div>
	</div>
</div>
<!-- ENDS MAIN -->

<?php
include_once 'footer.php';
?>
