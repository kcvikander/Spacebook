<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@attribute name="state" required="false"%>
<select class="form-control" name="state" id="state"> 
	<option value="" <% if (state.length() < 2) { %>selected="selected" <% } %>> - State - </option> 
	<option value="AL" <% if (state.equals("AL")) { %>selected="selected" <% } %>>Alabama</option> 
	<option value="AK" <% if (state.equals("AK")) { %>selected="selected" <% } %>>Alaska</option> 
	<option value="AZ" <% if (state.equals("AZ")) { %>selected="selected" <% } %>>Arizona</option> 
	<option value="AR" <% if (state.equals("AR")) { %>selected="selected" <% } %>>Arkansas</option> 
	<option value="CA" <% if (state.equals("CA")) { %>selected="selected" <% } %>>California</option> 
	<option value="CO" <% if (state.equals("CO")) { %>selected="selected" <% } %>>Colorado</option> 
	<option value="CT" <% if (state.equals("CT")) { %>selected="selected" <% } %>>Connecticut</option> 
	<option value="DE" <% if (state.equals("DE")) { %>selected="selected" <% } %>>Delaware</option> 
	<option value="DC" <% if (state.equals("DC")) { %>selected="selected" <% } %>>District Of Columbia</option> 
	<option value="FL" <% if (state.equals("FL")) { %>selected="selected" <% } %>>Florida</option> 
	<option value="GA" <% if (state.equals("GA")) { %>selected="selected" <% } %>>Georgia</option> 
	<option value="HI" <% if (state.equals("HI")) { %>selected="selected" <% } %>>Hawaii</option> 
	<option value="ID" <% if (state.equals("ID")) { %>selected="selected" <% } %>>Idaho</option> 
	<option value="IL" <% if (state.equals("IL")) { %>selected="selected" <% } %>>Illinois</option> 
	<option value="IN" <% if (state.equals("IN")) { %>selected="selected" <% } %>>Indiana</option> 
	<option value="IA" <% if (state.equals("IA")) { %>selected="selected" <% } %>>Iowa</option> 
	<option value="KS" <% if (state.equals("KS")) { %>selected="selected" <% } %>>Kansas</option> 
	<option value="KY" <% if (state.equals("KY")) { %>selected="selected" <% } %>>Kentucky</option> 
	<option value="LA" <% if (state.equals("LA")) { %>selected="selected" <% } %>>Louisiana</option> 
	<option value="ME" <% if (state.equals("ME")) { %>selected="selected" <% } %>>Maine</option> 
	<option value="MD" <% if (state.equals("MD")) { %>selected="selected" <% } %>>Maryland</option> 
	<option value="MA" <% if (state.equals("MA")) { %>selected="selected" <% } %>>Massachusetts</option> 
	<option value="MI" <% if (state.equals("MI")) { %>selected="selected" <% } %>>Michigan</option> 
	<option value="MN" <% if (state.equals("MN")) { %>selected="selected" <% } %>>Minnesota</option> 
	<option value="MS" <% if (state.equals("MS")) { %>selected="selected" <% } %>>Mississippi</option> 
	<option value="MO" <% if (state.equals("MO")) { %>selected="selected" <% } %>>Missouri</option> 
	<option value="MT" <% if (state.equals("MT")) { %>selected="selected" <% } %>>Montana</option> 
	<option value="NE" <% if (state.equals("NE")) { %>selected="selected" <% } %>>Nebraska</option> 
	<option value="NV" <% if (state.equals("NV")) { %>selected="selected" <% } %>>Nevada</option> 
	<option value="NH" <% if (state.equals("NH")) { %>selected="selected" <% } %>>New Hampshire</option> 
	<option value="NJ" <% if (state.equals("NJ")) { %>selected="selected" <% } %>>New Jersey</option> 
	<option value="NM" <% if (state.equals("NM")) { %>selected="selected" <% } %>>New Mexico</option> 
	<option value="NY" <% if (state.equals("NY")) { %>selected="selected" <% } %>>New York</option> 
	<option value="NC" <% if (state.equals("NC")) { %>selected="selected" <% } %>>North Carolina</option> 
	<option value="ND" <% if (state.equals("ND")) { %>selected="selected" <% } %>>North Dakota</option> 
	<option value="OH" <% if (state.equals("OH")) { %>selected="selected" <% } %>>Ohio</option> 
	<option value="OK" <% if (state.equals("OK")) { %>selected="selected" <% } %>>Oklahoma</option> 
	<option value="OR" <% if (state.equals("OR")) { %>selected="selected" <% } %>>Oregon</option> 
	<option value="PA" <% if (state.equals("PA")) { %>selected="selected" <% } %>>Pennsylvania</option> 
	<option value="RI" <% if (state.equals("RI")) { %>selected="selected" <% } %>>Rhode Island</option> 
	<option value="SC" <% if (state.equals("SC")) { %>selected="selected" <% } %>>South Carolina</option> 
	<option value="SD" <% if (state.equals("SD")) { %>selected="selected" <% } %>>South Dakota</option> 
	<option value="TN" <% if (state.equals("TN")) { %>selected="selected" <% } %>>Tennessee</option> 
	<option value="TX" <% if (state.equals("TX")) { %>selected="selected" <% } %>>Texas</option> 
	<option value="UT" <% if (state.equals("UT")) { %>selected="selected" <% } %>>Utah</option> 
	<option value="VT" <% if (state.equals("VT")) { %>selected="selected" <% } %>>Vermont</option> 
	<option value="VA" <% if (state.equals("VA")) { %>selected="selected" <% } %>>Virginia</option> 
	<option value="WA" <% if (state.equals("WA")) { %>selected="selected" <% } %>>Washington</option> 
	<option value="WV" <% if (state.equals("WV")) { %>selected="selected" <% } %>>West Virginia</option> 
	<option value="WI" <% if (state.equals("WV")) { %>selected="selected" <% } %>>Wisconsin</option> 
	<option value="WY" <% if (state.equals("WY")) { %>selected="selected" <% } %>>Wyoming</option>
</select>