/*
	VALIDATOR v 1.0
	AUTHOR: Casey Vikander

	This file is to validate form inputs.  Currently it only checks to ensure the fields have been filled out.

*/

function Validator(){
	var self = this;

	self.rulesArray = [];

	self.addValidation = function(inputId, type, message){
		self.rulesArray.push({
			inputId: inputId,
			type: type,
			message: message
		});
	};	

	self.validateForm = function (){
		var i, 
			ruleLen = self.rulesArray.length,
			returnObj = {
				isValid: true,
				invalidInput: [],
				messages: []
			};
		for (i=0;i<ruleLen;i++){
			var thisRule = self.rulesArray[i];
			switch (thisRule.type){
				case "text":
					var ruleOutcome = validateText(thisRule);
					if (!ruleOutcome.isValid){
						returnObj.isValid = false;
						returnObj.messages.push(ruleOutcome.message);
						returnObj.invalidInput.push(thisRule.inputId);
					}
					break;
				case "number":
				case "phone":
				case "email":
				case "date":
					break;

			};
		}
		return returnObj;
	};

};

function validateText(rule){
	var input = $('#'+rule.inputId).val(),
		returnObj = {
			isValid: true,
			message: ""
		};
	if (input.length < 1){
		returnObj.isValid = false;
		returnObj.message = rule.message;
		$('#'+rule.inputId).addClass("invalid");
	}
	return returnObj;
};