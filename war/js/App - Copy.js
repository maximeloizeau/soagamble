/* global document: false */

function App() {
    this.assets = 1000.0;
    this.winnings = 0;
}

App.prototype.start = function() {
    document.getElementById('app').style.display = "block";
    document.getElementById('homepage').style.display = "none";
};

App.prototype.printSportEventTable = function(events) {
    var eventTable = document.getElementById("eventTable");
    events.forEach(function(entry) {
        var tr = document.createElement("tr");
        var td = document.createElement("td");
        tr.appendChild(td);
        var text = document.createTextNode(entry);

        var start = entry.indexOf("(") + 1;
        var end = entry.indexOf(")") - 1;
        var matchId = entry.substr(start, end);

        tr.id = "match_" + matchId;

        td.appendChild(text);
        eventTable.appendChild(tr);
    });
};
	
App.prototype.printOdds = function(odds) {
    console.log(odds);
	var row = document.getElementById("match_" + odds[0]);
    
    for(var j=1; j<odds.length;j++){
        var td = document.createElement("td");
        td.style.paddingRight = "5px";
        td.style.textAlign = "right";
        td.style.width = "40px";
        var txt = document.createTextNode(odds[j]);
        td.appendChild(txt);
        row.appendChild(td);
    }
};
	
App.prototype.printBet = function(bet) {
    if (bet !== undefined){
    	console.log(bet);
        var row = document.getElementById("match_" + bet[0]);

        // amount			
        var td = document.createElement("td");
        td.style.paddingRight = "5px";
        td.style.textAlign = "right";
        td.style.width = "60px";
        var txt = document.createTextNode(bet[1]);
        td.appendChild(txt);
        row.appendChild(td);

        // choice			
        td = document.createElement("td");
        td.style.paddingLeft = "5px";
        td.style.paddingRight = "5px";
        txt = document.createTextNode(bet[2]);
        td.appendChild(txt);
        row.appendChild(td);	
    }

};
	
App.prototype.printProfit = function(profit) {
    if (profit !== undefined){			
        var row = document.getElementById("match_" + profit[0]);

        // choice			
        var td = document.createElement("td");
        td.style.paddingLeft = "5px";
        td.style.paddingRight = "5px";
        td.style.textAlign = "center";
        var txt = document.createTextNode(profit[1]);
        td.appendChild(txt);
        row.appendChild(td);	

        // amount			
        td = document.createElement("td");
        td.style.paddingRight = "5px";
        td.style.textAlign = "right";
        td.style.width = "60px";
        txt = document.createTextNode(profit[2]);
        td.appendChild(txt);
        row.appendChild(td);
    }
};
	
App.prototype.displayProfit = function(profit){
    var div = document.getElementById("profits");
    div.innerText = "Your profits : "+profit.toFixed(2)+" €";

    this.assets = this.assets + profit;
    this.winnings = profit - this.winnings;

    div = document.getElementById("winnings");
    div.innerText = "Winnings : "+this.winnings.toFixed(2)+" €";

    this.computeAssets(0);

};
	
App.prototype.initiateSequence = function(){
    document.getElementById("profits").innerHTML="";
    var table = document.getElementById("eventTable");
    for(var i = table.rows.length - 1; i > 0; i--)
    {
        table.deleteRow(i);
    }

    document.getElementById("winnings").innerText = "";
    document.getElementById("paid_money").innerText = "";
    document.getElementById("profits").innerText = "";

    launchWorkflow();
};
	
App.prototype.computeAssets = function(paid){
    this.assets = this.assets-paid;
    console.log(this.assets, paid);
    var span = document.getElementById("assets");
    span.innerHTML="";
    var txt = document.createTextNode("Available : "+this.assets.toFixed(2)+" €");
    span.appendChild(txt);

    if (paid > 0) {
        this.winnings = paid;
        var div = document.getElementById("paid_money");
        div.innerText = "You paid: " + paid.toFixed(2) + " €";
    }
};