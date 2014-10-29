/* global document: false */

function App() {
	this.locked = false;
	this.completed = true;
    this.assets = 1000.0;
    this.winnings = 0;
    this.DEFAULT_TIME = 0;
    this.currentIdx = 0;
    this.paid = 0;
    this.runs = 0;
    this.graphValues = [ [ 'No.', 'Bet', 'Profit', 'Assets'] ]
}

App.prototype.start = function() {
    document.getElementById('app').style.display = "block";
    document.getElementById('homepage').style.display = "none";
};

App.prototype.printSportEventTable = function(events) {
    var eventTable = document.getElementById("eventTable");
    
    for(var i = 0; i < events.length-1; i++) {
    	var entry = events[i];
    	
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
    }
};
	
App.prototype.printOdds = function(odds) {
    var row = document.getElementById("match_" + odds[0]);
    
    for(var j=1; j<odds.length-1;j++){
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
    div.innerText = "You won " + profit.toFixed(2) + " €";

    this.currentIdx++;
    this.graphValues.push( [  'No ' + this.currentIdx, 
                              parseFloat(this.winnings.toFixed(2)), 
                              parseFloat(profit.toFixed(2)),
                              parseFloat(this.assets.toFixed(2))
                            ]);

    this.assets = this.assets + profit;
    this.winnings = profit - this.winnings;
    
    if (this.assets <= 0) {
    	this.assets = 0;
    	this.winnings = 0;
    }
    
    div = document.getElementById("winnings");
    div.innerText = "Profits: "+this.winnings.toFixed(2)+" €";

    this.computeAssets(0);
    this.drawGrapth();
};
	
App.prototype.initiateSequence = function(){
	if (!this.locked) {
		this.locked = true;
		
		this.runs = document.getElementById("runs").value;
		this.nextRun(true);
	}
};

App.prototype.nextRun = function(first) {
	if (this.assets <= 0) {
    	document.getElementById("remaining").innerText = "no money";
    	document.getElementById("remaining").style.add('color', 'red');
		return;
	}
	
	document.getElementById("profits").innerHTML="";
    var table = document.getElementById("eventTable");
    for(var i = table.rows.length - 1; i > 1; i--)
    {
        table.deleteRow(i);
    }

    document.getElementById("winnings").innerText = "";
    document.getElementById("paid_money").innerText = "";
    document.getElementById("profits").innerText = "";
    
    if (this.runs > 2) {
    	document.getElementById("remaining").innerText = (this.runs - 1) + " runs remaining";
    	
    } else if (this.runs > 1) {
        document.getElementById("remaining").innerText = "1 run remaining";
        	
    } else if (!first) {
    	document.getElementById("remaining").innerText = "last run";	
    }

    var waitTime = this.DEFAULT_TIME;
    var waitTimeInput = document.getElementById("waitingTime");
    
    if(waitTimeInput) {
    	waitTime = Number.parseInt(waitTimeInput.value);
    }
    
    var favorite = document.getElementById("useFavoriteCB").checked;
    launchWorkflow(waitTime, favorite, this.assets);
}
	
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
        div.innerText = "You paid " + paid.toFixed(2) + " €";
    }
};

App.prototype.drawGrapth = function() {
    var data = google.visualization.arrayToDataTable(this.graphValues);

    var options = {
      titlePosition: 'none',
      legend: { position: 'bottom' },
      height: 250,
      vAxis: {
    	  logScale: true
      }
    };

    var chart = new google.visualization.LineChart(document.getElementById('chart_div'));

    chart.draw(data, options);
    
    this.runs--;
    var that = this;
    
    if (this.runs > 0) {
    	setTimeout(function() {
    		that.nextRun(false);
    	}, 2000);
    	
    } else {
        document.getElementById("remaining").innerText = "";
    	this.locked = false;
    }
}