<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
	String errorString = "";
	if (request.getAttribute("error") != null){
		errorString = request.getAttribute("error").toString();
	}
%>
<t:header title="Spacebook - Add/Edit Contact"></t:header>
<div class="main intro">
	<div id="errorContainer" style="display:none;"></div>
<%
	if (errorString.length() > 0){
%>
	<div class="message"><%=errorString %></div>
<%
	}
%>
	<div class="left">
		<form action="SignIn" method="get" id="signInForm">
				<h2>Sign In</h2>
				<p class="instructions">
					Already have an account?  Use the form below to log in!
				</p>
				<div class="form-group">
			      	<label for="loginUserName">Username:</label>
			      	<input type="text" class="form-control" name="loginUser" id="loginUser" placeholder="Enter Username">
			    </div>
			    <div class="form-group">
			      	<label for="loginPassword">Password:</label>
			      	<input type="password" class="form-control" name="loginPassword" id="loginPassword" placeholder="Enter Password">
			    </div>
				<div class="button-container"><input class="btn" id="signin" type="submit" value="Sign In" /></div>
			</form>
		</div>
	<div class="right">
		<h2>Sign Up</h2>
		<p class="instructions">
			New here?  Why don't you click the button below to sign up!
		</p>
		<form action="SignUp" method="get" id="signUpForm">
			<section id="personal">
				<div class="form-group">
			      <label for="firstName">First Name:</label>
			      <input type="text" class="form-control" id="firstName" name="firstName" placeholder="Enter First Name">
			    </div>
			    <div class="form-group">
			      <label for="firstName">Last Name:</label>
			      <input type="text" class="form-control" id="lastName" name="lastName" placeholder="Enter Last Name">
			    </div>
			</section>
			<section id="contact" style="display:none;">
				<h2>Contact Information</h2>
				<div class="form-group">
			      	<label for="street">Street:</label>
			      	<input type="text" class="form-control" id="street" name="street" placeholder="Enter Street">
			    </div>
			    <div class="form-group">
			      	<label for="city">City:</label>
			      	<input type="text" class="form-control" id="city" name="city" placeholder="Enter City">
			    </div>
			    <div class="form-group">
			      	<label for="state">State:</label>
					<select class="form-control" id="state" name="state">
						<option value=""> - Select State - </option>
						<option value="AL">Alabama</option>
						<option value="AK">Alaska</option>
						<option value="AZ">Arizona</option>
						<option value="AR">Arkansas</option>
						<option value="CA">California</option>
						<option value="CO">Colorado</option>
						<option value="CT">Connecticut</option>
						<option value="DE">Delaware</option>
						<option value="DC">District Of Columbia</option>
						<option value="FL">Florida</option>
						<option value="GA">Georgia</option>
						<option value="HI">Hawaii</option>
						<option value="ID">Idaho</option>
						<option value="IL">Illinois</option>
						<option value="IN">Indiana</option>
						<option value="IA">Iowa</option>
						<option value="KS">Kansas</option>
						<option value="KY">Kentucky</option>
						<option value="LA">Louisiana</option>
						<option value="ME">Maine</option>
						<option value="MD">Maryland</option>
						<option value="MA">Massachusetts</option>
						<option value="MI">Michigan</option>
						<option value="MN">Minnesota</option>
						<option value="MS">Mississippi</option>
						<option value="MO">Missouri</option>
						<option value="MT">Montana</option>
						<option value="NE">Nebraska</option>
						<option value="NV">Nevada</option>
						<option value="NH">New Hampshire</option>
						<option value="NJ">New Jersey</option>
						<option value="NM">New Mexico</option>
						<option value="NY">New York</option>
						<option value="NC">North Carolina</option>
						<option value="ND">North Dakota</option>
						<option value="OH">Ohio</option>
						<option value="OK">Oklahoma</option>
						<option value="OR">Oregon</option>
						<option value="PA">Pennsylvania</option>
						<option value="RI">Rhode Island</option>
						<option value="SC">South Carolina</option>
						<option value="SD">South Dakota</option>
						<option value="TN">Tennessee</option>
						<option value="TX">Texas</option>
						<option value="UT">Utah</option>
						<option value="VT">Vermont</option>
						<option value="VA">Virginia</option>
						<option value="WA">Washington</option>
						<option value="WV">West Virginia</option>
						<option value="WI">Wisconsin</option>
						<option value="WY">Wyoming</option>
					</select>	
			    </div>
			    <div class="form-group">
			      <label for="zipCode">Zip Code:</label>
			      <input type="number" class="form-control" id="postalCode" name="postalCode" placeholder="Enter Zip Code">
			    </div>
			    <div class="form-group">
			      <label for="phoneNumber">Phone Number:</label>
			      <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" placeholder="Enter Phone Number">
			    </div>
			    <div class="form-group">
			      <label for="email">Email Address:</label>
			      <input type="text" class="form-control" id="emailAddress" name="emailAddress" placeholder="Enter Email">
			    </div>
			</section>
			
			<section id="username" style="display:none;">
				<div class="form-group">
			      	<label for="userName">User Name:</label>
			      	<input type="text" class="form-control" id="userName" name="userName" placeholder="Enter UserName">
			    </div>
			    <div class="form-group">
			      	<label for="password">Password:</label>
			      	<input type="password" class="form-control" id="password" name="password" placeholder="Enter Password">
			    </div>
			    <div class="form-group">
			      	<label for="userName">Verify Password:</label>
			      	<input type="password" class="form-control" id="verifyPassword" name="verifyPassword" placeholder="Verify Password">
			    </div>

			</section>
			<div class="button-container">
				<span id="back" class="btn" style="display:none;">Back</span>
				<span id="next" class="btn" >Next</span>
				<input style="display:none;" class="btn" id="signup" type="submit" value="Sign Up" />
			</div>
		</form>
	</div>
</div>
<t:footer></t:footer>