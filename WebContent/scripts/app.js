/**
 * Core JS File
 */
$(document).ready(function () {
    var self = this;
    window.signUpPage = {
    	step: 0
    }
    console.log("initialized");
    /* SPREAD PAGE */
    $("#next").click(function(event){
    	event.preventDefault();
    	handleSignupNext();
    });
    $("#back").click(function(event){
    	event.preventDefault();
    	handleSignupBack();
    });
    
    $("#addEmailBtn").click(function(event){
    	event.preventDefault();
    	addEmailGroup();
    });
    $(".deleteEmail").click(function(event){
    	event.preventDefault();
    	var removeId = event.currentTarget.id;
    	$('#email-group-'+removeId).addClass("hidden");
    	$('#emailAddress_'+removeId).val("");
    	$('#emailAddressType_'+removeId).val("");
    	$('#addEmailBtn').removeClass("hidden");
    });
    $("#addPhoneBtn").click(function(event){
    	event.preventDefault();
    	addPhoneGroup();
    });
    $(".deletePhone").click(function(event){
    	event.preventDefault();
    	var removeId = event.currentTarget.id;
    	$('#phone-group-'+removeId).addClass("hidden");
    	$('#phoneNumber_'+removeId).val("");
    	$('#phoneNumberType_'+removeId).val("");
    	$('#addPhoneBtn').removeClass("hidden");
    });
    $("#addDateBtn").click(function(event){
    	event.preventDefault();
    	addDateGroup();
    });
    $(".deleteDate").click(function(event){
    	event.preventDefault();
    	var removeId = event.currentTarget.id;
    	$('#contact-date-group-'+removeId).addClass("hidden");
    	$('#contactDate_'+removeId).val("");
    	$('#contactDateType_'+removeId).val("");
    	$('#contact-date-group-'+unHide).removeClass("hidden");
    	$('#addDateBtn').removeClass("hidden");
    });
    
    $(".deleteContact").click(function(event){
    	event.preventDefault();
    	var deleteId = event.currentTarget.id;
    	deleteContact(deleteId);
    });
    $(".editContact").click(function(event){
    	event.preventDefault();
    	var editId = event.currentTarget.id;
    	editContact(editId);
    });
    
    $('form').submit(function(event){
    	clearValidation();
    	var validObj, validationRequired = false, formId = event.currentTarget.id;
    	switch (formId){
    	case 'saveContactForm':
    		//event.preventDefault();
    		validationRequired = true;
    		validObj = validateSaveContactForm();
    		break;
    	case 'signUpForm':
    		validationRequired = true;
    		validObj = validateSignUpForm();
    		break;
    	case 'signInForm':
    		validationRequired = true;
    		validObj = validateSignInForm();
    		break;
    	}
    	if (validationRequired && !validObj.isValid){
			event.preventDefault();
			displayValidationMessages(validObj);
		}
    });
    
    $('#backToMain').click(function(){
    	var url = window.location.href;
		url = url.split("Spacebook/")[0] + "Spacebook/main.jsp";
		window.location.href = url;
    });
    
    $('#logout').click(function(){
    	logoutUser();
    });
});

function handleSignupNext(){
	switch (window.signUpPage.step){
	case 0:
		$('#personal').hide();
		$('#contact').show();
		$('#back').show();
		$('#next').show();
		window.signUpPage.step = 1;
		break;
	case 1:
		$('#contact').hide();
		$('#username').show();
		$('#next').hide();
		$('#signup').show();
		window.signUpPage.step = 2;
		break;
	}
};

function handleSignupBack(){
	switch (window.signUpPage.step){
	case 1:
		$('#personal').show();
		$('#contact').hide();
		$('#back').hide();
		window.signUpPage.step = 0;
		break;
	case 2:
		$('#contact').show();
		$('#username').hide();
		$('#next').show();
		$('#signup').hide();
		window.signUpPage.step = 1;
		break;
	}
};

function addEmailGroup(){
	var emailCount = 5-getElementGroupCount(".email-group.hidden");
	if (emailCount < 5){
		var unHide = emailCount + 1;
		$('#email-group-'+unHide).removeClass("hidden");
		if (emailCount == 4){
			$('#addEmailBtn').addClass("hidden");
		}
	}
}

function addPhoneGroup(){
	var phoneCount = 5-getElementGroupCount(".phone-group.hidden");
	if (phoneCount < 5){
		var unHide = phoneCount + 1;
		$('#phone-group-'+unHide).removeClass("hidden");
		if (phoneCount == 4){
			$('#addPhoneBtn').addClass("hidden");
		}
	}
}

function addDateGroup(){
	var dateCount = 5-getElementGroupCount(".contact-date-group.hidden");
	if (dateCount < 5){
		var unHide = dateCount + 1;
		$('#contact-date-group-'+unHide).removeClass("hidden");
		if (dateCount == 4){
			$('#addDateBtn').addClass("hidden");
		}
	}
}

function getElementGroupCount(element){
	return $(element).length;
}

function deleteContact(deleteId){
	$.ajax({
		method: "POST",
		url: "DeleteContact",
		data: { deleteId: deleteId }
	});
}

function editContact(editId){
	$.ajax({
		method: "POST",
		url: "EditContact",
		data: { editId: editId }
	});
}

function validateSignInForm(){
	this.validator = new Validator();
	//inputId, type, message
	this.validator.addValidation("loginUser", "text", "Username is required.");
	this.validator.addValidation("loginPassword", "text", "Password is required.");
	return this.validator.validateForm();
}

function validateSignUpForm(){
	this.validator = new Validator();
	//inputId, type, message
	this.validator.addValidation("firstName", "text", "First name is required.");
	this.validator.addValidation("lastName", "text", "Last name is required.");
	this.validator.addValidation("street", "text", "Street is required.");
	this.validator.addValidation("city", "text", "City is required.");
	this.validator.addValidation("state", "text", "State is required.");
	this.validator.addValidation("postalCode", "text", "Postal code is required.");
	this.validator.addValidation("phoneNumber", "text", "Phone number is required.");
	this.validator.addValidation("emailAddress", "text", "Email address is required.");
	this.validator.addValidation("userName", "text", "User name is required.");
	this.validator.addValidation("password", "text", "Password is required.");
	this.validator.addValidation("verifyPassword", "text", "Verify password is required.");
	return this.validator.validateForm();
}

function validateSaveContactForm(){
	this.validator = new Validator();
	//inputId, type, message
	this.validator.addValidation("firstName", "text", "First name is required.");
	this.validator.addValidation("lastName", "text", "Last name is required.");
	this.validator.addValidation("street", "text", "Street is required.");
	this.validator.addValidation("city", "text", "City is required.");
	this.validator.addValidation("state", "text", "State is required.");
	this.validator.addValidation("postalCode", "text", "Postal code is required.");
	return this.validator.validateForm();
}

function displayValidationMessages(validObj){
	var msgString = "", i, len = validObj.messages.length;
	msgString += "<ul>";
	for (i=0;i<len;i++){
		msgString += "<li>";
		msgString += validObj.messages[i];
		msgString += "</li>";
	}
	msgString += "</ul>";
	$('#errorMessage').html(msgString);
	$('#errorContainer').show();
}

function clearValidation(){
	$('#errorContainer').hide();
	$('#errorMessage').html("");
	$('.form-control').removeClass("invalid");
}
function logoutUser(){
	$.ajax({
		method: "POST",
		url: "Logout"
	}).done(function(){
		var url = window.location.href;
		url = url.split("Spacebook/")[0] + "Spacebook/";
		window.location.href = url;
	});
}