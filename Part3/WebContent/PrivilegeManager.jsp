<%@page import="java.sql.Connection" %>
<%@page import="java.sql.ResultSet" %>
<%@page import="java.sql.SQLException" %>
<%@page import="java.sql.Statement" %>
<%@page import="cs122b.DB.*" %>
<%@page import="cs122b.Models.*" %>
<%@page import="java.util.*" %>
<%@page import="cs122b.Tables.*" %>
<%@page import="cs122b.Utilities.*" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    
<!DOCTYPE html>
<html lang="en">
	<head>
		<!-- Meta -->
		<meta charset="utf-8">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
		<meta name="description" content="">
		<meta name="author" content="">
	    <meta name="keywords" content="MediaCenter, Template, eCommerce">
	    <meta name="robots" content="all">

	    <title>User Manager</title>

	    <!-- Bootstrap Core CSS -->
	    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
	    
	    <!-- Customizable CSS -->
	    <link rel="stylesheet" href="assets/css/main.css">
	    <link rel="stylesheet" href="assets/css/green.css">
	    <link rel="stylesheet" href="assets/css/owl.carousel.css">
		<link rel="stylesheet" href="assets/css/owl.transitions.css">
		<!--<link rel="stylesheet" href="assets/css/owl.theme.css">-->
		<link href="assets/css/lightbox.css" rel="stylesheet">
		<link rel="stylesheet" href="assets/css/animate.min.css">
		<link rel="stylesheet" href="assets/css/rateit.css">
		<link rel="stylesheet" href="assets/css/bootstrap-select.min.css">

		<!-- Icons/Glyphs -->
		<link rel="stylesheet" href="assets/css/font-awesome.min.css">

        <!-- Fonts --> 
		<link href='http://fonts.googleapis.com/css?family=Roboto:300,400,500,700' rel='stylesheet' type='text/css'>
		
		<!-- Favicon -->
		<link rel="shortcut icon" href="assets/images/favicon.ico">

		<!-- HTML5 elements and media queries Support for IE8 : HTML5 shim and Respond.js -->
		<!--[if lt IE 9]>
			<script src="assets/js/html5shiv.js"></script>
			<script src="assets/js/respond.min.js"></script>
		<![endif]-->

	</head>
    <body class="cnt-home">
	
		
	
		<!-- ============================================== HEADER ============================================== -->
<header class="header-style-1">

	<!-- ============================================== TOP MENU ============================================== -->
<div class="top-bar animate-dropdown">
	<div class="container">
		<div class="header-top-inner">
			<div class="cnt-account">
				<ul class="list-unstyled">
					<li><a href="#"><i class="icon fa fa-user"></i>My Account</a></li>
					<li><a href="#"><i class="icon fa fa-shopping-cart"></i>My Cart</a></li>
					<li><a href="#"><i class="icon fa fa-key"></i>Checkout</a></li>
					<li><a href="#"><i class="icon fa fa-sign-in"></i>Login</a></li>
				</ul>
			</div><!-- /.cnt-account -->
			<div class="clearfix"></div>
		</div><!-- /.header-top-inner -->
	</div><!-- /.container -->
</div><!-- /.header-top -->
<!-- ============================================== TOP MENU : END ============================================== -->
	<div class="main-header">
		<div class="container">
			<div class="row">
				<div class="col-xs-12 col-sm-12 col-md-6 top-search-holder">
<!-- ============================================================= SEARCH AREA ============================================================= -->
<div class="search-area">
    <form>
        <div class="control-group">

            <ul class="categories-filter animate-dropdown">
                <li class="dropdown">

                    <a class="dropdown-toggle"  data-toggle="dropdown" href="index.php?page=category-grid">Categories <b class="caret"></b></a>

                    <ul class="dropdown-menu" role="menu" >
                        <li class="menu-header">Computer</li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="index.php?page=category-grid">- Laptops</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="index.php?page=category-grid">- Tv & audio</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="index.php?page=category-grid">- Gadgets</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="index.php?page=category-grid">- Cameras</a></li>

                    </ul>
                </li>
            </ul>

            <input class="search-field" placeholder="Search here..." />

            <a class="search-button" href="#" ></a>    

        </div>
    </form>
</div><!-- /.search-area -->
<!-- ============================================================= SEARCH AREA : END ============================================================= -->				</div><!-- /.top-search-holder -->

				<div class="col-xs-12 col-sm-12 col-md-3 animate-dropdown top-cart-row">
				</div><!-- /.top-cart-row -->
			</div><!-- /.row -->

		</div><!-- /.container -->

	</div><!-- /.main-header -->

	<!-- ============================================== NAVBAR ============================================== -->
<div class="header-nav animate-dropdown">
    <div class="container">
        <div class="yamm navbar navbar-default" role="navigation">
            <div class="navbar-header">
                <button data-target="#mc-horizontal-menu-collapse" data-toggle="collapse" class="navbar-toggle collapsed" type="button">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>
            <div class="nav-bg-class">
                <div class="navbar-collapse collapse" id="mc-horizontal-menu-collapse">
	<div class="nav-outer">
		<ul class="nav navbar-nav">
			<li class="active dropdown yamm-fw">
				<a href="home.html" data-hover="dropdown" class="dropdown-toggle" data-toggle="dropdown">Home</a>
			</li>
		</ul><!-- /.navbar-nav -->
		<div class="clearfix"></div>				
	</div><!-- /.nav-outer -->
</div><!-- /.navbar-collapse -->


            </div><!-- /.nav-bg-class -->
        </div><!-- /.navbar-default -->
    </div><!-- /.container-class -->

</div><!-- /.header-nav -->
<!-- ============================================== NAVBAR : END ============================================== -->

</header>

<!-- ============================================== HEADER : END ============================================== -->
<div class="breadcrumb">
	<div class="container">
		<div class="breadcrumb-inner">
			<ul class="list-inline list-unstyled">
				<li><a href="#">Home</a></li>
				<li class='active'>User Manager</li>
			</ul>
		</div><!-- /.breadcrumb-inner -->
	</div><!-- /.container -->
</div><!-- /.breadcrumb -->

<div class="body-content outer-top-bd">
	<div class="container">
		<div class="checkout-box faq-page inner-bottom-sm">
			<div class="row">
				<div class="col-md-12">
					<h2>User Privileges Manager</h2>
					<div class="panel-group checkout-steps" id="accordion">
						<!-- checkout-step-01  -->
						<div class="panel panel-default checkout-step-01">
							<!-- panel-heading -->
								<div class="panel-heading">
									<h4 class="unicase-checkout-title">
										<a data-toggle="collapse" class="" data-parent="#accordion" href="#collapseOne">
										  <span>2</span>Manage Privileges
										</a>
									</h4>
								</div>
							<!-- panel-heading -->
							<div id="collapseOne" class="panel-collapse collapse in">
								<!-- panel-body  -->
								<div class="panel-body">
                                    <div class="col-md-12 col-sm-12" style="width:100%">
                                        <div>
                                            <h4 class="checkout-subtitle">List of Selected Users Permissions</h4>
                                            <h5 class="checkout-subtitle">Check the revoke button, and the permissions if you would like to revoke from this user.</h5>
                                            <select name="permissions" size="10" style="height:100px;">
                                                <% 							
													MovieDB db = new MovieDB();
													Connection con = ConnectionManager.getConnection();
													Statement stmt = null;
													String query = "show grants for " + session.getAttribute("user");
													try {
														boolean isFirst = true;
														stmt = con.createStatement();
														ResultSet rs = stmt.executeQuery(query);
														int i = 1;
														while (rs.next()) {
															if (isFirst) {
																isFirst = false;
																continue;
															}
															out.println("<option value=\"permission\">" + rs.getString(i) + "</option>");
														}
													} catch (SQLException e) {
														System.out.println("error");
													}
												%>
                                            </select>
                                        </div>
                                    </div>
									<div class="col-md-3 col-sm-3">
										<form class="permission-selection" role="form" action="privilegemanager" method="POST">
											<h4 class="checkout-subtitle">Database Permissions</h4>
	                                        <div id="container">
	                                            <h5 class="checkout-subitle">Database to apply to: </h5>
	                                            <input name="database-name1" placeholder="Database name">
	                                        </div>
	                                        <br>
											<input type="checkbox" name="CREATE">CREATE<br>
                                            <input type="checkbox" name="DROP">DROP<br>
                                            <input type="checkbox" name="EVENT">EVENT<br>
                                            <input type="checkbox" name="GRANT OPTION">GRANT OPTION<br>
                                            <input type="checkbox" name="LOCK TABLES">LOCK TABLES<br>
                                            <input type="checkbox" name="CREATE ROUTINE">CREATE ROUTINE<br>
                                            <input type="checkbox" name="ALL">ALL<br>
                                            <input type="checkbox" name="REVOKE">REVOKE?
											<br><br>
											<button type="submit" class="btn-upper btn btn-primary">Add Permisions</button>
										</form>
									</div>
									<div class="col-md-3 col-sm-3">
										<form class="permission-selection" role="form" action="privilegemanager" method="POST">
	                                        <h4 class="checkout-subtitle">Table Permissions</h4>
	                                        <div id="container">
	                                            <h5 class="checkout-subitle">Database to apply to: </h5>
	                                            <input name="database-name2" placeholder="Database name">
	                                        </div>
	                                        <div id="container">
	                                            <h5 class="checkout-subitle">Table to apply to: </h5>
	                                            <input name="table-name" placeholder="Table name">
	                                        </div>
	                                        <br>
											<input type="checkbox" name="ALTER">ALTER<br>
                                            <input type="checkbox" name="CREATE VIEW">CREATE VIEW<br>
                                            <input type="checkbox" name="CREATE">CREATE<br>
                                            <input type="checkbox" name="DELETE">DELETE<br>
                                            <input type="checkbox" name="DROP">DROP<br>
                                            <input type="checkbox" name="INSERT">INSERT<br>
                                            <input type="checkbox" name="SELECT">SELECT<br>
                                            <input type="checkbox" name="UPDATE">UPDATE<br>
                                            <input type="checkbox" name="REVOKE">REVOKE?
											<br><br>
											<button type="submit" class="btn-upper btn btn-primary">Add Permisions</button>
										</form>
									</div>
                                    <div class="col-md-3 col-sm-3">
                                    	<form class="permission-selection" role="form" action="privilegemanager" method="POST">
											<h4 class="checkout-subtitle">Column Permissions</h4>
											<div id="container">
	                                            <h5 class="checkout-subitle">Database to apply to: </h5>
	                                            <input name="database-name3" placeholder="Database name">
	                                        </div>
	                                        <div id="container">
	                                            <h5 class="checkout-subitle">Table to apply to: </h5>
	                                            <input name="table-name" placeholder="Table name">
	                                        </div>
	                                        <div id="container">
	                                            <h5 class="checkout-subitle">Column to apply to: </h5>
	                                            <input name="column-name" placeholder="Column name">
	                                        </div>
	                                        <br>
											<input type="checkbox" name="SELECT">SELECT<br>
	                                        <input type="checkbox" name="INSERT">INSERT<br>
	                                        <input type="checkbox" name="UPDATE">UPDATE<br>
	                                        <input type="checkbox" name="REVOKE">REVOKE?
											<br><br>
											<button type="submit" class="btn-upper btn btn-primary">Add Permisions</button>
										</form>
									</div>
                                    <div class="col-md-3 col-sm-3">
                                    	<form class="permission-selection" role="form" action="privilegemanager" method="POST">
											<h4 class="checkout-subtitle">Stored Procedure Permissions</h4>
											<div id="container">
	                                            <h5 class="checkout-subitle">Database to apply to: </h5>
	                                            <input name="database-name4" placeholder="Database name">
	                                        </div>
	                                        <div id="container">
	                                            <h5 class="checkout-subitle">Stored Procedure to apply to: </h5>
	                                            <input name="stored-procedure-name" placeholder="Stored Procedure name">
	                                        </div>
	                                        <br>
											<input type="checkbox" name="ALTER ROUTINE">ALTER ROUTINE<br>
                                            <input type="checkbox" name="EXECUTE">EXECUTE<br>
                                            <input type="checkbox" name="GRANT OPTION">GRANT OPTION<br>
                                            <input type="checkbox" name="REVOKE">REVOKE?
											<br><br>
											<button type="submit" class="btn-upper btn btn-primary">Add Permisions</button>
										</form>
									</div>
								</div><!-- panel-body  -->
							</div><!-- row -->
						</div>
						<!-- checkout-step-01  -->
					</div><!-- /.checkout-steps -->
				</div>
			</div><!-- /.row -->
		</div><!-- /.checkout-box -->
	</div><!-- /.container -->
</div><!-- /.body-content -->
<!-- ============================================================= FOOTER ============================================================= -->
<footer id="footer" class="footer color-bg">
    <div class="copyright-bar">
        <div class="container">
            <div class="col-xs-12 col-sm-6 no-padding">
                <div class="copyright">
                   Copyright � 2014
                    <a href="home.html">Unicase Shop.</a>
                    - All rights Reserved
                </div>
            </div>
        </div>
    </div>
</footer>
<!-- ============================================================= FOOTER : END============================================================= -->
	<!-- JavaScripts placed at the end of the document so the pages load faster -->
	<script src="assets/js/jquery-1.11.1.min.js"></script>
	
	<script src="assets/js/bootstrap.min.js"></script>
	
	<script src="assets/js/bootstrap-hover-dropdown.min.js"></script>
	<script src="assets/js/owl.carousel.min.js"></script>
	
	<script src="assets/js/echo.min.js"></script>
	<script src="assets/js/jquery.easing-1.3.min.js"></script>
	<script src="assets/js/bootstrap-slider.min.js"></script>
    <script src="assets/js/jquery.rateit.min.js"></script>
    <script type="text/javascript" src="assets/js/lightbox.min.js"></script>
    <script src="assets/js/bootstrap-select.min.js"></script>
    <script src="assets/js/wow.min.js"></script>
	<script src="assets/js/scripts.js"></script>
	
	<script src="switchstylesheet/switchstylesheet.js"></script>
	
	<script>
		$(document).ready(function(){ 
			$(".changecolor").switchstylesheet( { seperator:"color"} );
			$('.show-theme-options').click(function(){
				$(this).parent().toggleClass('open');
				return false;
			});
		});

		$(window).bind("load", function() {
		   $('.show-theme-options').delay(2000).trigger('click');
		});
	</script>
</body>
</html>