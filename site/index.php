<?php
	$title = 'E-Movie';
	$home = true;
	include_once 'header.php';
 ?>

		<!-- MAIN -->
		<div id="main">
			<div class="wrapper cf">

			<!-- featured -->
			<div class="home-featured">
				<ul id="filter-buttons">
					<li><a href="#" data-filter="*" class="selected">Todos os Filmes</a></li>
					<li><a href="#" data-filter=".web">Lançamentos</a></li>
					<li><a href="#" data-filter=".print">Maiores 18</a></li>
					<li><a href="#" data-filter=".design">Livre</a></li>
					<li><a href="#" data-filter=".photo">Clássicos</a></li>
				</ul>

				<!-- Filter container -->
				<div id="filter-container" class="cf">

				</div><!-- ENDS Filter container -->

			</div>
			<!-- ENDS featured -->

			</div><!-- ENDS WRAPPER -->
		</div>
		<!-- ENDS MAIN -->

<?php
	include_once 'footer.php';
?>
