/**
 * 
 */
var tableData = [];
var tableId;
var user_turns = true;
$(document).ready(function(){
	drowTable("#caro_table");
	$("#caro_table").on("mouseenter", ".table_cell",function() {
		focusToCell($(this))
	}).on("mouseout", ".table_cell", function(){
		disFocusFromCell($(this));
	}).on("click", ".table_cell", function(){
		userClick($(this));
	});
	registerTable();
});

function focusToCell(cell) {
	if(!user_turns) return;
	var x = Number($(cell).attr("x"));
	var y = Number($(cell).attr("y"));
	if(tableData[x][y] == 0) {
		$(cell).css("background-color", "yellow");
	}
}
function disFocusFromCell(cell) {
	$(cell).css("background-color", "")
}

function userClick (cell) {
	if(!user_turns) return;
	var x = Number($(cell).attr("x"));
	var y = Number($(cell).attr("y"));
	if(tableData[x][y] == 0) {
		submitUserStep(x, y);
	}
}

function addUserStep(x, y) {
	tableData[x][y] = 1;
	$("[x="+x+"][y="+y+"]").html("<p class='user-flag'>X</p>");
	user_turns = false;
}

function finishGame (winArr) {
	$.each(winArr, function(index, value) {
		$("[x="+value[0]+"][y="+value[1]+"]").css("background-color", "blue");
	});
}

function submitUserStep(x, y) {
	$.ajax({
		type: "GET",
		url: "add-user-step?tableId=" + tableId + "&x=" + x + "&y=" + y,
		dataType: "json",
		success: function(response) {
			addUserStep(response.newStep.x, response.newStep.y);
			if(response.result.isWin) {
				finishGame(response.result.winArr);
			} else {
				requestRobotStep();
			}
        },
        error: function(response) {
        	alert(response);
        }
	});
}

function requestRobotStep() {
	$.ajax({
		type: "GET",
		url: "request-robot-step",
		dataType: "json",
		success: function(response) {
			addRobotStep(response.newStep.x, response.newStep.y);
			finishGame(response.result.winArr);
		},
		error: function(response) {
        	console.log(response);
        }
	});
}

function registerTable() {
	$.ajax({
		type: "GET",
		url: "register-table",
		dataType: "text",
		success: function(response) {
			tableId = Number(response);
		},
		error: function(response) {
			console.log(response);
        }
	});
}

function addRobotStep(x, y) {
	tableData[x][y] = -1;
	$("[x="+x+"][y="+y+"]").html("<p class='robot-flag'>O</p>");
	user_turns = true;
}

function drowTable(tableDiv) {
	var TABLE_ROW = 30;
	var TABLE_COLLUMN = 30;
	var tableHTML = "";
	for(var i = 0; i< TABLE_ROW; i++) {
		var b = [];
		tableHTML += "<div class='table_row'>";
		for(var j = 0; j< TABLE_COLLUMN; j++) {
			b[j] = 0;
			tableHTML += "<div class='table_cell' style='width: 20px' x="+i+" y="+j+"></div>";
		}
		tableData[i] = b;
		tableHTML += "</div>";
	}
	$(tableDiv).append(tableHTML);
}