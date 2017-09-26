/**
 * 
 */
var tableData = [];
var tableId;
var user_turns = true;
var isFinished = false;

$(document).ready(function(){
	$("#caro_table").on("mouseenter", ".table_cell",function() {
		focusToCell($(this));
	}).on("mouseout", ".table_cell", function(){
		disfocusFromCell($(this));
	}).on("mouseleave", ".table_cell", function(){
		disfocusFromCell($(this));
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
function disfocusFromCell(cell) {
	if(!user_turns) return;
	$(cell).css("background-color", "");
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
	user_turns = false;
	$("[x="+x+"][y="+y+"]").html("<p class='user-flag'>X</p>");
}

function finishGame (winRow) {
	$.each(winRow, function(index, value) {
		$("[x="+winRow[index].x+"][y="+winRow[index].y+"]").css("background-color", "goldenrod");
	});
}

function submitUserStep(x, y) {
	$.ajax({
		type: "GET",
		url: "add-user-step?tableId=" + tableId + "&x=" + x + "&y=" + y,
		dataType: "json",
		success: function(response) {
			addUserStep(response.cell.x, response.cell.y);
			if(response.result.isWin) {
				finishGame(response.result.winRow);
				setTimeout(function(){
					alert("You are win.");
					isFinished = true;
					registerTable();
				}, 1000);
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
		url: "request-robot-step?tableId="+tableId,
		dataType: "json",
		success: function(response) {
			addRobotStep(response.cell.x, response.cell.y);
			if(response.result.isWin) {
				finishGame(response.result.winRow);
				setTimeout(function(){
					alert("Doraemon is WIN.");
					isFinished = true;
					registerTable();
				}, 1000);
			} else {
				user_turns = true;
			}
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
			tableId = response;
			isFinished = false;
			resetTable();
		},
		error: function(response) {
			console.log(response);
        }
	});
}

function addRobotStep(x, y) {
	tableData[x][y] = -1;
	$("[x="+x+"][y="+y+"]").html("<p class='robot-flag'>O</p>");
}

function drowTable(tableDiv) {
	var TABLE_ROW = 30;
	var TABLE_COLLUMN = 30;
	tableData = [];
	var tableHTML = "";
	for(var y = TABLE_ROW -1; y >= 0; y--) {
		var rowCell = [];
		tableHTML += "<div class='table_row'>";
		tableHTML += "<div class='row_title' style='width: 20px'>" + y + "</div>";
		for(var x = 0; x < TABLE_ROW; x++) {
			rowCell[x] = 0;
			tableHTML += "<div class='table_cell' style='width: 20px' x="+x+" y="+y+"></div>";
		}
		tableData[y] = rowCell;
		tableHTML += "</div>";
	}
	tableHTML += "<div class='table_row'>";
	tableHTML += "<div class='row_title' style='width: 20px'></div>";
	for(var x = 0; x < TABLE_ROW; x++) {
		tableHTML += "<div class='collumn_title' style='width: 20px'>"+x+"</div>";
	}
	tableHTML += "</div>";
	$(tableDiv).html(tableHTML);
}

function resetTable() {
	drowTable("#caro_table");
	user_turns = true;
}